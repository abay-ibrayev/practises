angular.module('EProc.Purchasers')
    .directive('purchasersView',
        function () {
            return {
                scope: {
                    view: '=purchasersView',
                },
                replace: true,
                link: function (scope, el, attr) {
                    scope.views = [{
                        name: 'List',
                        template: 'client/components/purchase/tmpl/purchasers/list.html',
                        icon: 'btn btn-default navbar-btn'
                    }, {
                        name: 'Grid',
                        template: 'client/components/purchase/tmpl/purchasers/thumbnails.html',
                        icon: 'btn btn-default navbar-btn'
                    }];
                },
                controller: ['$scope', function ($scope) {
                    $scope.switchView = function (view) {
                        $scope.view = view.template;
                    }
                }]
            }
        });