angular.module('EProc.Common')
    .service('FileUploadService', ['$http', 'EndpointConfigService',
        function ($http, EndpointConfigService) {
            var service = this;

            service.getFileUploadUrl = function () {
                return EndpointConfigService.getUrl('/files/upload');
            };

            service.deleteFile = function (fileMetadata) {
                return $http.post(EndpointConfigService.getUrl('/files/remove'), fileMetadata);
            };
        }]);
