angular.module('EProc.Purchasers')
    .controller('procurementPlanCtrl',
        function ($scope, $state, $stateParams, $uibModal, ProcurementPlanModel, $log) {
            ProcurementPlanModel.findProcurementPlan({'id': $stateParams.purchaserId}).then(function (result) {
                $scope.procurementPlan = {'items': []};
                if (result) {
                    $scope.procurementPlan = result;
                    $scope.purchaser = $scope.procurementPlan.purchasingParty;
                }
                $scope.displayedCollection = [].concat(result.items);

                $scope.showItem = function (procItem) {
                    var showItemModal = $uibModal.open({
                        animation: true,
                        templateUrl: 'client/components/purchase/tmpl/procurementItem.html',
                        controller: 'procurementItemCtrl',
                        size: 'md',
                        resolve: {
                            procItem: function () {
                                return procItem;
                            }
                        }
                    });

                    showItemModal.result.then(function (itemId) {
                        $log.info(itemId);
                    }, function () {
                        $log.info('Modal dismissed at: ' + new Date());
                    });
                }
            });
        })
    .controller('procurementItemCtrl',
        function ($scope, $uibModalInstance, ProcurementPlanModel, procItem) {
            ProcurementPlanModel.getProcurementItem(procItem.id).then(function (result) {

                $scope.pItem = result;

                var item = $scope.pItem;
                $scope.itemAttrs = [
                    {'name': '№', 'value': item.itemNo},
                    {'name': 'Код  ТРУ', 'value': item.gswUniqueCode},
                    {'name': 'Наименование закупаемых ТРУ', 'value': item.gswName},
                    {'name': 'Краткая характеристика (описание) ТРУ', 'value': item.gswShortDescription},
                    {'name': 'Дополнительная характеристика', 'value': item.gswAdditionalDescription},
                    {'name': 'Прогнозируемый период', 'value': item.period},
                    {'name': 'Количество, объем', 'value': item.itemAmount},
                    {'name': 'Единица измерения', 'value': item.measurementUnit},
                    {'name': 'Маркетинговая цена за единицу без НДС, тенге', 'value': item.marketingUnitPrice},
                    {'name': 'Сумма, планируемая для закупок ТРУ без НДС, тенге', 'value': item.totalCost},
                    {'name': 'Сумма,  планируемая для закупки ТРУ с НДС, тенге', 'value': item.totalCostVAT}
                ];
            });

            $scope.ok = function () {
                $uibModalInstance.close($scope.pItem.id);
            };

            $scope.cancel = function () {
                $uibModalInstance.dismiss('cancel');
            };
        });