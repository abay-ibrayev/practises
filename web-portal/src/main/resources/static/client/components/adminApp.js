var eProcAdmApp = angular.module('EProcAdminApp',
    [
        'ui.router',
        'ui.bootstrap',
        'ui.uploader',
        'smart-table',
        'EProc.Common',
        'EProcAdmin'
    ]);

eProcAdmApp.config(function ($stateProvider, $urlRouterProvider) {

    $stateProvider

        .state('admPurchaser', {
            url: '/purchasers',
            templateUrl: 'client/components/admin/tmpl/purchasingPartiesMng.html',
            controller: 'purchasingPartiesMngCtrl'
        })

        .state('admProcurementPlan', {
            url: '/procplan',
            templateUrl: 'client/components/admin/tmpl/procurementPlans.html',
            controller: 'procurementPlansMngCtrl'
        });

    $urlRouterProvider.otherwise('/purchasers');
});

eProcAdmApp.value('PROC_ITEM_ATTRS', [
    {name: 'ATTR_ITEM_NO', label: '№'},
    {name: 'ATTR_GSW_UNIQUE_NUMBER', label: 'Код ТРУ'},
    {name: 'ATTR_GSW_NAME', label: 'Наименование закупаемых товаров, работ и услуг'},
    {name: 'ATTR_GSW_SHORT_DESCRIPTION', label: 'Краткая характеристика (описание) товаров, работ и услуг'},
    {name: 'ATTR_GSW_ADDITIONAL_DESCRIPTION', label: 'Дополнительная характеристика'},
    {name: 'ATTR_PROCUREMENT_MODE', label: 'Способ закупок'},
    {name: 'ATTR_PAYMENT_CONDITIONS', label: 'Условия оплаты (размер авансового платежа),%'},
    {name: 'ATTR_TIME_PERIOD', label: 'Срок осуществления закупок (предполагаемая дата/месяц проведения)'},
    {name: 'ATTR_PLACE_KATO_CODE', label: 'Код КАТО места осуществления закупок'},
    {name: 'ATTR_PLACE_ADDRESS', label: 'Место (адрес)  осуществления закупок'},
    {name: 'ATTR_DELIVERY_DESTINATION', label: 'Регион, место поставки товара, выполнения работ, оказания услуг'},
    {name: 'ATTR_DELIVERY_CONDITIONS', label: 'Условия поставки'},
    {name: 'ATTR_MEASUREMENT_UNIT', label: 'Единица измерения'},
    {name: 'ATTR_ITEM_AMOUNT', label: 'Количество, объем'},
    {name: 'ATTR_MARKETING_UNIT_PRICE', label: 'Маркетинговая цена за единицу, тенге без НДС'},
    {name: 'ATTR_TOTAL_COST', label: 'Сумма, планируемая для закупок ТРУ без НДС, тенге'},
    {name: 'ATTR_TOTAL_COST_VAT', label: 'Сумма,  планируемая для закупки ТРУ с НДС, тенге'},
    {name: 'ATTR_COMMENTS', label: 'Примечание'}
]);
