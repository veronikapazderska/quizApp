var app = angular.module("app");
app.controller('activeUsersController', ['$scope', '$rootScope', '$location', 'stompService', function ($scope, $rootScope, $location, stompService) {
    var self = this;
    self.activeUsers = [];
    stompService.subscribe('/topic/activeUsers', function (activeUsers) {
        self.activeUsers = activeUsers.activeUsers;
        $scope.$apply();
    });
    stompService.publish('/app/activeUsersRequest', {});
}]);