var app = angular.module("app");
app.controller('quizController', ['$scope', '$rootScope', '$location', 'stompService', function ($scope, $rootScope, $location, stompService) {
    var self = this;
    self.isSelected = false;
    if (!$rootScope.topic) {
        $location.path("/main");
    }
    self.question = null;
    self.hasAnswered = false;
    stompService.subscribe(`/topic/questionResponse/${$rootScope.topic}`, function (questionResponse) {
        console.log('Message: ', questionResponse);
        self.hasAnswered = false;
        self.question = questionResponse;
        $scope.$apply();
    });

    stompService.subscribe('/topic/gameOver', function (gameOverResponse){

    });

    $scope.init = function() {
        stompService.publish('/app/requestQuestion', { "topic": $rootScope.topic });
    }

    self.sendQuestionToCheck = function () {
        self.answerChosen = self.question.possibleAnswers[self.selected];
        stompService.publish('/app/answerQuestion', {"topic": $rootScope.topic, 
        "questionText":self.question.questionText, "correctAnswer": self.question.correctAnswer,
        "answerChosen": self.answerChosen, "username": $rootScope.loggedUser.username});
        self.hasAnswered = true;
        self.answerChosen = null;
    };

    self.select = function (numberSelected) {
        self.selected = numberSelected;        
    };

    $scope.init();
}]);