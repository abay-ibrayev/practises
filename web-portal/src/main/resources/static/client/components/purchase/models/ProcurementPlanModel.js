angular.module('EProc.Purchasers')
    .service('ProcurementPlanModel',
        function ($http, EndpointConfigService) {
            var service = this,
                MODEL = '/procurement/plan';

            service.findProcurementPlan = function (purchaser) {
                return $http.get(EndpointConfigService.getUrl('/' + purchaser.id + '/procurement/plan'))
                    .then(function (result) {
                        return result.data;
                    });
            }

            service.getProcurementItem = function (itemId) {
                return $http.get(EndpointConfigService.getUrl(MODEL + '/' + 'items' + '/' + itemId))
                    .then(function (result) {
                        return result.data;
                    });
            }
        });