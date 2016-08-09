angular.module('EProcAdmin')
    .service('ProcurementPlanService', ['$http', '$window', 'EndpointConfigService',
        function ($http, $window, EndpointConfigService) {
            var service = this;

            service.importProcurementPlan = function (importProcurementPlanCmd) {
                return $http.post(EndpointConfigService.getUrl('/' + importProcurementPlanCmd.purchasingPartyId
                    + '/procurement/plans'), importProcurementPlanCmd)
                    .then(function (response) {
                            $window.alert('Загрузка плана закупок прошла успешно');
                            return response.data;
                        },
                        function (response) {
                            if (response.status != 404) {
                                $window.alert(response.data.error);
                            }
                            console.log(response);
                        });
            }

            service.loadProcurementPlanSettings = function (purchaserId) {
                return $http.get(EndpointConfigService.getUrl('/procurement/plan/settings/' + purchaserId))
                    .then(
                        function (response) {
                            console.log(response);
                            return response.data;
                        },
                        function (response) {
                            if (response.status != 404) {
                                $window.alert(response.data.error);
                            }
                            console.log(response);
                        }
                    );
            }

            service.storeProcurementPlanSettings = function (purchaserId, procPlanLoadSettings) {
                return $http.post(EndpointConfigService.getUrl('/procurement/plan/settings/' + purchaserId),
                    procPlanLoadSettings)
                    .then(
                        function (response) {
                            console.log(response);
                            return response.data;
                        },
                        function (response) {
                            if (response.status != 404) {
                                $window.alert(response.data.error);
                            }
                            console.log(response);
                        }
                    );
            }
        }]);