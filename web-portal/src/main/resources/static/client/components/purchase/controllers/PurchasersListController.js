angular.module('EProc.Purchasers')
    .controller('purchasersListCtrl',
        function ($scope, $state, $stateParams, PurchasingPartyQueryModel) {
            $scope.view = 'client/components/purchase/tmpl/purchasers/list.html';

            if ($stateParams.purchaserId && $stateParams.purchaserId > 0) {
                PurchasingPartyQueryModel.childrenParties($stateParams.purchaserId).then(function (result) {
                    $scope._handlePurchasersResult(result);

                    $scope.purchasersHierarchy = PurchasingPartyQueryModel.getPathToRoot($stateParams.purchaserId);
                    console.log($scope.purchasersHierarchy);
                });
            } else {
                PurchasingPartyQueryModel.allParents().then(function (result) {
                    $scope._handlePurchasersResult(result);

                    $scope.purchasersHierarchy = PurchasingPartyQueryModel.getPathToRoot();
                });
            }

            $scope.viewPurchaser = function (purchaser) {
            }

            $scope.viewProcurementPlan = function (purchaser) {
                $state.go('procplan', {'purchaserId': purchaser.id});
            }

            $scope._asThumbnailsMode = function (purchasersList) {
                var result = [];

                var row = [];

                result.push(row);
                for (var i = 0, len = purchasersList.length; i < len; ++i) {
                    row.push(purchasersList[i]);
                    if (row.length == 3) {
                        row = [];
                        result.push(row);
                    }
                }

                return result;
            }

            $scope._handlePurchasersResult = function (result) {
                $scope.purchasersList = result;
                $scope.purchasersAsTwoDimArray = $scope._asThumbnailsMode($scope.purchasersList);

                $scope.showPurchasers = $scope.purchasersList && $scope.purchasersList.length > 0;

                for (var i = 0, n = $scope.purchasersList.length; i < n; i++) {
                    PurchasingPartyQueryModel.addPurchaser($scope.purchasersList[i]);
                }

                if ($scope.purchasersList && $scope.purchasersList.length > 10) {
                    $scope.view = 'client/components/purchase/tmpl/purchasers/list.html';
                } else {
                    $scope.view = 'client/components/purchase/tmpl/purchasers/thumbnails.html';
                }
            }
        });
