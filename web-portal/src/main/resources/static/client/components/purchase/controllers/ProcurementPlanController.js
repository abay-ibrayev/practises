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
                        size: 'lg',
                        resolve: {
                            procItem: function () {
                                return procItem;
                            },
                            purchaser: function () {
                                return $scope.purchaser;
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
        function ($scope, $uibModalInstance, ProcurementPlanModel, procItem, purchaser) {
            ProcurementPlanModel.getProcurementItem(procItem.id).then(function (result) {

                $scope.pItem = result;

                var item = $scope.pItem;
                $scope.itemAttrs = [
                    {'name': '№', 'value': item.itemNo},
                    {'name': 'Наименование организации', 'value': purchaser.shortName},
                    {'name': 'Код  ТРУ', 'value': item.gswUniqueCode},
                    {'name': 'Наименование закупаемых ТРУ', 'value': item.gswName},
                    {'name': 'Краткая характеристика (описание) ТРУ', 'value': item.gswShortDescription},
                    {'name': 'Дополнительная характеристика', 'value': item.gswAdditionalDescription},
                    {'name': 'Способ закупок', 'value': item.procurementMode},
                    {'name': 'Прогноз казахстанского содержания, %', 'value': item.localContentForecast},
                    {'name': 'Код КАТО места осуществления закупки', 'value': item.placeKatoCode},
                    {'name': 'Место (адрес) осуществления закупок', 'value': item.placeAddressText},
                    {'name': 'Срок осуществления закупок', 'value': item.period},
                    {
                        'name': 'Регион, место поставки товара, выполнения работ, оказания услуг',
                        'value': item.deliveryDestination
                    },
                    {
                        'name': 'Условия поставки по ИНКОТЕРМС 2000, условия оплаты и график поставки',
                        'value': item.deliveryConditions
                    },
                    {
                        'name': 'Срок и график поставки товара, выполнения работ, оказания услуг',
                        'value': item.deliveryTimeText
                    },
                    {'name': 'Условия оплаты (размер авансового платежа), %', 'value': item.paymentConditionsText},
                    {'name': 'Единица измерения', 'value': item.measurementUnit},
                    {'name': 'Количество, объем', 'value': item.itemAmount},
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