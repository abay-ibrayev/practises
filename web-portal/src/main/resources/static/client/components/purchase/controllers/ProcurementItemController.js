angular.module('EProc.Purchasers')
    .controller('procurementItemCtrl',
        function ($scope, $rootScope, $state, $stateParams, ProcurementPlanModel) {
            ProcurementPlanModel.getProcurementItem($stateParams.itemId).then(function (result) {

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

            $scope.goBack = function () {
                if ($rootScope.previousState != null) {
                    $state.go($rootScope.previousState.state.name, $rootScope.previousState.stateParams);
                }
            }
        });