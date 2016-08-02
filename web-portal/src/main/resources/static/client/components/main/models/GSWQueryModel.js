angular.module('EProc.Common')
    .service('GSWQueryModel',
        function($http, EndpointConfigService) {
            var service = this,
                MODEL = 'gsw/search/query';

            service.all = function() {
                return $http.get(EndpointConfigService.getUrl(MODEL))
                    .then(
                        function(result) {
                            console.log(result);
                            return result;
                        }
                    );
            };
        }
    );
