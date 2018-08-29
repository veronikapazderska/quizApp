var app = angular.module('app');
app.component('invitation', {
    template: `<div class="jumbotron" id="invitation">
    <div class="container">
        <h1 class="display-4">You have been invited to start a game by {{$ctrl.data.sender}}</h1>
        <button class="btn btn-primary btn-lg" ng-click="$ctrl.accept()">Accept</button>
        <button class="btn btn-danger btn-lg" ng-click="$ctrl.decline()">Decline</button>        
    </div>
</div>`,
    controller: ['$location', '$scope', '$rootScope', 'stompService', function ($location, $scope, $rootScope, stompService) {
        var self = this;
        
        self.accept = function () {        
            stompService.subscribe(`/topic/gameStarts/${self.data.sender}-${self.data.receiver}`, function (gameInvitationResponse){
                $rootScope.topic = `${self.data.sender}-${self.data.receiver}`;       
                $location.path('/takeQuiz');
                $rootScope.isInvited = false;
                $scope.$apply();
            });
            self.sendGameResponse(true);            
        };        
        

        self.decline = function () {
            self.sendGameResponse(false);
            $rootScope.isInvited = false;
            $scope.$apply();
        };

        self.sendGameResponse = function (hasConfirmed) {
            var gameInvitationResponse = {
                "hasConfirmed": hasConfirmed,
                "sender": self.data.sender,
                "receiver": self.data.receiver
            };
            stompService.publish('/app/gameResponse', gameInvitationResponse);
        };
    }],

    bindings: {
        data: '<'
    }
    
});
