var app = angular.module("app");
app.controller('pageNotFoundController', ['$location', function ($location) {
    var self = this;
    self.redirectToHome = function(){
        $location.path('/main');
    };
}]);