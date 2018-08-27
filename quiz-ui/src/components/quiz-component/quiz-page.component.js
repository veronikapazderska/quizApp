var app = angular.module("app");
app.controller('quizController', ['$scope', '$rootScope', '$location', 'stompService', function ($scope, $rootScope, $location, stompService) {
    var self = this;
    self.isSelected = false;
    self.correct = null;
    self.correctAnswerIndex = null;
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

    stompService.subscribe('/topic/gameOver', function (gameOverResponse) {

    });

    $scope.init = function () {
        stompService.publish('/app/requestQuestion', { "topic": $rootScope.topic });
    }

    self.sendQuestionToCheck = function () {
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
                self.hasAnswered = true;
                self.answerChosen = null;
                self.correctAnswerIndex = null;
            }, 3000);

        }, 3000);
    };

    self.select = function (numberSelected) {
        self.selected = numberSelected;
    };

    $scope.init();
}]);