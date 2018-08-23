var app = angular.module("app");
app.controller('quizController', ['$scope', '$rootScope', '$location', 'stompService', function ($scope, $rootScope, $location, stompService) {
    var self = this;
    self.isSelected = false;
    if (!$rootScope.topic) {
        $location.path("/main");
    }

    self.question = null;
    stompService.subscribe(`/topic/questionResponse/${$rootScope.topic}`, function (questionResponse) {
        self.question = questionResponse;
        $scope.$apply();
    });

    self.sendQuestionToCheck = function () {
        self.answerChosen = self.question.possibleAnswers[self.selected];
        if(self.answerChosen===self.question.correctAnswer){
            alert("Correct answer");
        }
        else {
            alert("Wrong answer");
        }

    };

    self.select = function (numberSelected) {
        self.selected = numberSelected;        
    };

    stompService.publish('/app/requestQuestion', { "topic": $rootScope.topic });



}]);