angular.module('EProcAdmin')
    .controller('purchasingPartiesMngCtrl', ['$scope', 'PurchasingPartyQueryModel', '$log', '$timeout',
        'PROC_ITEM_ATTRS', 'uiUploader', '$uibModal', 'FileUploadService', 'ProcurementPlanService',
        function ($scope, PurchasingPartyQueryModel, $log, $timeout, PROC_ITEM_ATTRS, uiUploader, $uibModal,
                  FileUploadService, ProcurementPlanService) {
            $scope.displayedPurchasers = [];

            var element = document.getElementById('procPlanFile');
            element.addEventListener('change', function (e) {
                var files = e.target.files;
                if (files && files.length > 0) {
                    uiUploader.removeAll();
                    uiUploader.addFiles(files);
                    $scope.files = uiUploader.getFiles();
                    $scope.$apply();
                }
            });

            _retrieveAllParents($scope);

            $scope.itemSelected = function (purchaser) {
                $log.info(purchaser);
                $log.info(purchaser.isSelected);

                if (purchaser.isSelected) {
                    $scope.purchaser = angular.copy(purchaser);

                    _retrieveAndInitPurchaserInfo($scope);
                }
            }

            $scope.showChildren = function (purchaser) {
                if (purchaser == null) {
                    return;
                }

                _retrieveChildren($scope, purchaser);
            }

            $scope.addChildrenParties = function () {
                var addChildrenModal = $uibModal.open({
                    animation: false,
                    templateUrl: 'client/components/admin/tmpl/addChildrenParties.html',
                    controller: 'addChildrenPartiesCtrl',
                    size: 'md',
                    resolve: {
                        purchaser: function () {
                            return $scope.purchaser;
                        }
                    }
                });

                addChildrenModal.result.then(function (addChildrenPartiesCmd) {
                    $log.info("add children cmd: ", addChildrenPartiesCmd);

                    PurchasingPartyQueryModel.addChildrenParties($scope.purchaser.id, addChildrenPartiesCmd)
                        .then(function (result) {
                            $log.info(result);
                        }, function (result) {
                            $log.info(result);
                        });
                }, function () {
                    $log.info('Modal dismissed at: ' + new Date());
                });
            }

            $scope.goTopLevel = function () {
                if ($scope.currentParentId == null) {
                    return;
                }

                var parent = PurchasingPartyQueryModel.getParent($scope.currentParentId);
                if (parent.parent) {
                    _retrieveChildren($scope, parent.parent);
                } else {
                    _retrieveAllParents($scope);
                }
            }

            $scope.showProcPlanSettings = function () {


                var showItemModal = $uibModal.open({
                    animation: true,
                    templateUrl: 'client/components/admin/tmpl/procPlanLoadSettings.html',
                    controller: 'procPlanLoadSettingsCtrl',
                    size: 'md',
                    resolve: {
                        purchaser: function () {
                            return $scope.purchaser;
                        },
                        procItemAttrs: function () {
                            return PROC_ITEM_ATTRS
                        },
                        procPlanSettings: function () {
                            return $scope.procPlanSettings;
                        }
                    }
                });

                showItemModal.result.then(function (procPlanSettings) {
                    $log.info("settings: ", procPlanSettings);

                    ProcurementPlanService.storeProcurementPlanSettings($scope.purchaser.id, procPlanSettings)
                        .then(function (result) {
                            $log.info(result);
                        });
                }, function () {
                    $log.info('Modal dismissed at: ' + new Date());
                });
            }

            $scope.uploadPlan = function () {
                $log.info('uploading...');
                uiUploader.startUpload({
                    url: FileUploadService.getFileUploadUrl(),
                    concurrency: 1,
                    onProgress: function (file) {
                        $log.info(file.name + '=' + file.humanSize);
                        $scope.$apply();
                    },
                    onCompleted: function (file, response) {
                        var responseData = angular.fromJson(response);

                        $scope.uploadedFileRef = responseData['path'];

                        $log.info(response);

                        $log.info("message: ", $scope.uploadedFileRef);
                    },
                    onCompletedAll: function (files) {
                    }
                });
            }

            $scope.removeFile = function (file) {
                $log.info('deleting=' + file);
                uiUploader.removeFile(file);

                $scope.uploadedFileRef = null;
            }

            $scope.isLoadDisabled = function () {
                return $scope.uploadedFileRef == null || $scope.procPlanSettings == null;
            }

            $scope.loadProcurementPlan = function () {
                var importProcurementPlanCmd = {
                    'purchasingPartyId': $scope.purchaser.id,
                    'filePathRefs': [$scope.uploadedFileRef],
                    'settings': $scope.procPlanSettings,
                };

                $log.info(importProcurementPlanCmd);

                ProcurementPlanService.importProcurementPlan(importProcurementPlanCmd);
            }

            function _handlePurchasersResult(result) {
                $scope.purchasersList = result;
                if ($scope.purchasersList.length > 0) {
                    $timeout(function () {
                        angular.element('#purchasingParty_' + $scope.purchasersList[0].id).triggerHandler('click');
                    }, 100);
                    for (var i = 0, n = $scope.purchasersList.length; i < n; i++) {
                        PurchasingPartyQueryModel.addPurchaser($scope.purchasersList[i]);
                    }
                }
            }

            function _retrieveAllParents(scopeObj) {
                scopeObj.currentParentId = null;

                PurchasingPartyQueryModel.allParents().then(function (result) {
                    _handlePurchasersResult(result);
                });
            }

            function _retrieveChildren(scopeObj, purchaser) {
                if (purchaser == null) {
                    return;
                }
                scopeObj.currentParentId = purchaser.id;
                PurchasingPartyQueryModel.childrenParties(purchaser.id).then(function (result) {
                    _handlePurchasersResult(result);
                });
            }

            function _retrieveAndInitPurchaserInfo(scopeObj) {
                scopeObj.procPlanSectionCollapsed = true;

                scopeObj.uploadedFileRef = null;

                ProcurementPlanService.loadProcurementPlanSettings(scopeObj.purchaser.id).then(function (result) {
                    $log.info("procplan settings: ", result);

                    if (result) {
                        scopeObj.procPlanSettings = result;
                    } else {
                        scopeObj.procPlanSettings = {'columnMapping': {}};
                        for (var i = 0, n = PROC_ITEM_ATTRS.length; i < n; i++) {
                            scopeObj.procPlanSettings.columnMapping[PROC_ITEM_ATTRS[i].name] = '';
                        }
                    }
                });

                scopeObj.files = [];
            }
        }])
    .controller('procPlanLoadSettingsCtrl', ['$scope', '$uibModalInstance', 'purchaser', 'procItemAttrs',
        'procPlanSettings',
        function ($scope, $uibModalInstance, purchaser, procItemAttrs, procPlanSettings) {
            $scope.procItemAttrs = procItemAttrs;

            $scope.procPlanSettings = procPlanSettings;

            $scope.ok = function () {
                $uibModalInstance.close($scope.procPlanSettings);
            };

            $scope.cancel = function () {
                $uibModalInstance.dismiss('cancel');
            };
        }])
    .controller('addChildrenPartiesCtrl', ['$scope', '$uibModalInstance', 'purchaser',
        function ($scope, $uibModalInstance, purchaser) {
            $scope.purchaser = purchaser;

            $scope.childrenPartiesModel = '';

            $scope.ok = function () {
                if ($scope.childrenPartiesModel) {
                    var partyNames = $scope.childrenPartiesModel.split("\n");

                    var addChilrenPartiesCmd = {
                        'parentPartyId': $scope.purchaser.id,
                        'childPartyNames': partyNames
                    }

                    $uibModalInstance.close(addChilrenPartiesCmd);
                }
            };

            $scope.cancel = function () {
                $uibModalInstance.dismiss('cancel');
            };
        }]);