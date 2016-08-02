angular.module('EProc.Common')
    .controller('AppMenuCtrl', AppMenuCtrl);

function AppMenuCtrl($scope, AppMenuService) {
    var appMenu = this;

    $scope.menuItems = AppMenuService.getMenuItems();
}