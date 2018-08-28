/*var app = angular.module('app');
app.component('over', {
    template: `<div class="jumbotron" id="over">
    <div class="container">
        <h1 class="display-4">Game Over</h1>
        <p class="lead">Results: </p>
        <p class="lead">Results: {{$ctrl.allResultsCount}}</p>
    </div>
</div>`,

    bindings: {
        results: '<'
    },

    controller: class {
        get allResultsCounts() {
            return this.results.length;
        }
    }
    
    //['$scope', '$rootScope', 'stompService', function ($scope, $rootScope, stompService) {}]

}); */

var app = angular.module('app');
app.directive('over', function(){

    return {
        scope: {},
        bindToController: {
            users: '=', 
            points: '=',
            message: '=',
            goToLeaderboard: '&'
        },
        controller: function() {},
        controllerAs: 'ctrl',
        template: `<div class="jumbotron" id="over">
                    <div class="container">
                        <h1 class="display-3">GAME OVER</h1>
                        <hr>
                        <h1 class="display-4"><strong>{{ctrl.users[0]}}</strong> - {{ctrl.points[0]}} points</h1>
                        <h1 class="display-4"><strong>{{ctrl.users[1]}}</strong> - {{ctrl.points[1]}} points</h1>
                        <hr>
                        <h1 class="display-3">{{ctrl.message}}</h1>
                        <button type="button" class="btn btn-dark btn-lg" ng-click="ctrl.goToLeaderboard()">Start New Game</button>
                        <button type="button" class="btn btn-dark btn-lg">Go to Leaderboards</button>
                        <button type="button" class="btn btn-dark btn-lg">Go to Homepage</button>
                    </div>
                </div>`
    }
});
