var app = angular.module("app");
app.controller('questionsController', ['$scope', '$rootScope', '$location', 'stompService', function ($scope, $rootScope, $location, stompService) {
    var self = this;
    self.questions = [];
    self.getQuestions = function () {

        self.questionsReceivedSuccessfully();
        self.questionsReceiveFailed();

        stompService.publish('/app/getQuestionsRequest', {});

    };

    self.questionsReceivedSuccessfully = function () {
        stompService.subscribe('/topic/sendQuestionsSuccess', function (sendQuestionsSuccess) {
            self.questions = sendQuestionsSuccess.questions;
            $scope.$apply();
            console.log('Questions: ', self.questions);
            console.log('Question from server: ', sendQuestionsSuccess.questions);
        });
    };

    self.questionsReceiveFailed = function () {
        stompService.subscribe('/topic/sendQuestionsFailed', function (sendQuestionsFailed) {
            console.log('Error, questions were not sent');
            self.errorMessage = sendQuestionsFailed.message;
        });
    };

    self.getQuestions();

}]);