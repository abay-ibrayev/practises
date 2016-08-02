angular.module('EProc.Purchasers')
    .directive('searchWatchModel', function () {
        return {
            require: '^stTable',
            scope: {
                searchWatchModel: '='
            },
            link: function (scope, ele, attr, ctrl) {
                var table = ctrl;

                scope.$watch('searchWatchModel', function (val) {
                    ctrl.search(val);
                });
            }
        };
    });