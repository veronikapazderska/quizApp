var app = angular.module("app");
app.controller('newQuestionController', ['$scope', '$rootScope', '$location', 'stompService', function ($scope, $rootScope, $location, stompService) {

    var self = this;

    if (localStorage.getItem('user')) {
        $rootScope.loggedUser = JSON.parse(localStorage.getItem('user'));
    }

    self.message = "";
    self.categories = ["Entertainment", "Science", "Mythology", "Geography", "History"];
    self.mandatoryMessage = "";

    self.sendQuestion = function () {     

        var question = {};
        question.questionText = $scope.questionText;
        question.category = $scope.category;
        question.correctAnswer = $scope.correctAnswer;
        question.wrongAnswer1 = $scope.wrongAnswer1;
        question.wrongAnswer2 = $scope.wrongAnswer2;
        question.wrongAnswer3 = $scope.wrongAnswer3;

        
        if(!$scope.questionText || !$scope.category || !$scope.correctAnswer || !$scope.wrongAnswer1 || !$scope.wrongAnswer2 || !$scope.wrongAnswer3){
            self.mandatoryMessage = "All fields are required!";
            $scope.$apply();
            return;
        }


        self.sendQuestionSuccess();
        self.sendQuestionFailed();

        stompService.publish('/app/newQuestionRequest', question);
    }

    self.sendQuestionSuccess = function () {
        stompService.subscribe("/topic/sendNewQuestionSuccessful", function (sendNewQuestionSuccessful) {
            self.mandatoryMessage = "";
            self.message = sendNewQuestionSuccessful.message;
            $scope.$apply();         
            if(self.message!=""){
                setTimeout(function(){
                    $scope.questionText = "";
                    $scope.category = "";
                    $scope.correctAnswer = "";
                    $scope.wrongAnswer1 = "";
                    $scope.wrongAnswer2 = "";
                    $scope.wrongAnswer3 = "";
                    self.message = "";
                    $scope.$apply();
                }, 1500);
            }
        });
    }

    self.sendQuestionFailed = function () {
        stompService.subscribe("/topic/sendNewQuestionFailed", function (sendNewQuestionFailed) {
            self.message = sendNewQuestionFailed.message;
            $scope.$apply();
        });
    }

    stompService.subscribe('/topic/gameRequest/' + $rootScope.loggedUser.username, function (gameRequest) {
        if(gameRequest.body) {
            gameRequest = JSON.parse(gameRequest.body);
        }              
        self.sender = gameRequest.sender;
        if (gameRequest.receiver == $rootScope.loggedUser.username) {
            self.gameRequest = gameRequest;
            $rootScope.isInvited = true;
            $scope.$apply();
        }       
    });

    self.logOut = function () {
        stompService.subscribe("/topic/logOut/" + $rootScope.loggedUser.username, function (logoutResponse) {
            localStorage.setItem("user", "");
            $location.path('/');
            $scope.$apply();
        });

        stompService.publish("/app/logoutRequest", {username: $rootScope.loggedUser.username});
    };

    self.redirectToHome = function(){
        $location.path('/main');
    }

}]);