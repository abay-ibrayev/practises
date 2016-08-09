var eProcApp = angular.module('EProc',
    [
        'ui.router',
        'ui.bootstrap',
        'smart-table',
        'EProc.Common',
        'EProc.Profile',
        'EProc.controllers',
        'EProc.appData',
        'EProc.Purchasers'
    ]);

eProcApp.config(function ($stateProvider, $urlRouterProvider) {

    $stateProvider

        .state('home', {
            url: '/home',
            templateUrl: 'client/components/customerorders/partials/openOrders.html',
            controller: 'openOrdersController'
        })

        .state('myprofile', {
            url: '/myprofile',
            templateUrl: 'client/components/customerorders/partials/campaigns.html',
        })

        .state('purchasers', {
            url: '/purchasers/:purchaserId',
            views: {
                '': {
                    templateUrl: 'client/components/purchase/tmpl/purchasers.html',
                    controller: 'purchasersListCtrl'
                },
                'profile@purchasers': {
                    templateUrl: 'client/components/profile/tmpl/profileShortDetails.html',
                    controller: 'profileDetailsCtrl',
                }
            }
        })

        .state('procplan', {
            url: '/procplan/:purchaserId',
            views: {
                '': {
                    templateUrl: 'client/components/purchase/tmpl/procurementPlan.html',
                    controller: 'procurementPlanCtrl'
                },
                'profile@procplan': {
                    templateUrl: 'client/components/profile/tmpl/profileShortDetails.html',
                    controller: 'profileDetailsCtrl'
                }
            }
        })

        .state('procitem', {
            url: '/procitem/:itemId',
            templateUrl: 'client/components/purchase/tmpl/procurementItem.html',
            controller: 'procurementItemCtrl'
        })

        .state('products', {
            url: '/products',
            templateUrl: 'client/components/customerorders/partials/help.html',
        })

        .state('companies', {
            url: '/companies',
            templateUrl: 'client/components/customerorders/partials/billShipInfo.html',
            controller: 'customerShippingController'
        });

    //$urlRouterProvider.otherwise('/home');
});

/*
eProcApp.run(['$state', '$cookies', '$rootScope', function ($state, $cookies, $rootScope) {
    $rootScope.$on('$stateChangeStart', function (e, toState, toParams, fromState, fromParams) {
        $rootScope.previousState = from;
    });
}]);*/