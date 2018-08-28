var app = angular.module("app");
app.controller('quizController', ['$scope', '$rootScope', '$location', 'stompService', function ($scope, $rootScope, $location, stompService, data2) {
    var self = this;
    self.isSelected = false;
    self.correct = null;
    self.correctAnswerIndex = null;
    self.isGameOver = false;    
    self.users = null;
    self.points = null;
    self.message = null;
    if (!$rootScope.topic) {
        $location.path("/main");
    }
    self.question = null;
    self.hasAnswered = false;
    stompService.subscribe(`/topic/questionResponse/${$rootScope.topic}`, function (questionResponse) {
        self.hasAnswered = false;
        self.question = questionResponse;
        $scope.$apply();
    });

    stompService.subscribe(`/topic/gameResults/${$rootScope.topic}`, function(results){
        self.users = Object.keys(results);
        self.points = Object.values(results);
        if(self.points[0] > self.points[1]){
            if(self.users[0]==$rootScope.loggedUser.username){
                self.message = "You win!";
            }
            else {
                self.message = "You lost! :(";
            }            
        }
        else if(self.points[1] > self.points[0]){
            if(self.users[1]==$rootScope.loggedUser.username){
                self.message = "You win!";
            }
            else {
                self.message = "You lost! :(";
            }
        }
        else{
            self.message = "It's a tie!"
        }
        $scope.$apply();
    });

    stompService.subscribe(`/topic/gameOver/${$rootScope.topic}`, function (gameOverResponse) {
        self.isGameOver = true;
        $scope.$apply();
        console.log("Game Over");
    });    


    $scope.init = function () {
        stompService.publish('/app/requestQuestion', { "topic": $rootScope.topic });
    }

    self.sendQuestionToCheck = function () {
        self.hasAnswered = true;
        self.answerChosen = self.question.possibleAnswers[self.selected];
        setTimeout(function () {
            for (var i = 0; i < self.question.possibleAnswers.length; i++) {
                if (self.question.correctAnswer == self.question.possibleAnswers[i]) {
                    self.correctAnswerIndex = i;
                    $scope.$apply();
                }
            }
            setTimeout(function () {
                stompService.publish('/app/answerQuestion', {
                    "topic": $rootScope.topic,
                    "questionText": self.question.questionText, "correctAnswer": self.question.correctAnswer,
                    "answerChosen": self.answerChosen, "username": $rootScope.loggedUser.username
                });
                self.answerChosen = null;
                self.correctAnswerIndex = null;
            }, 3000);

        }, 3000);
    };

    self.select = function (numberSelected) {
        self.selected = numberSelected;
    };

    self.goToLeaderboard = function(){
        $location.path('/leaderboard');
    }

    $scope.init();
}]);