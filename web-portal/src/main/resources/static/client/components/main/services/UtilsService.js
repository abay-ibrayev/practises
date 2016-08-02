angular.module('EProc.Common')
    .service('UtilsService',
        function() {
            var service = this;

            service.objectToArray = function(content) {
                if (content.data instanceof Object && !Array.isArray(content.data)) {
                    var newArray = [];

                    for (var key in content.data) {
                        var item = content.data[key];
                        item.id = key;
                        newArray.push(item);
                    }
                    return newArray;

                } else {
                    return content.data;
                }
            };
        }
    );
