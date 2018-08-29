var app = angular.module("app", ["ngRoute",'ngAnimate', 'ngSanitize', 'ui.bootstrap']);
app.config(function($routeProvider) {
    $routeProvider
        .when("/takeQuiz", {
            templateUrl : "src/components/quiz-component/quiz-page.html",
            controller: 'quizController',
            controllerAs: "quizCtrl"
        })
        .when("/", {
            templateUrl: 'src/components/login-component/login-page.html',
            controller: 'loginController',
            controllerAs:'loginCtrl'
        })
        .when("/register", {
            templateUrl: 'src/components/register-component/register-page.html',
            controller: 'registerController',
            controllerAs:'regCtrl'
        })
        .when('/main', {
            templateUrl: 'src/components/main-page-component/main-page.html',
            controller: 'mainPageController',
            controllerAs: 'mainCtrl'
        })
        .when('/quizz', {
            templateUrl : 'src/components/questions-component/question-page.html',
            controller: 'questionsController',
            controllerAs: 'questionCtrl'
        })
        .when('/activeUsers', {
            templateUrl: 'src/components/active-users-component/active-users.html',
            controller: 'activeUsersController',
            controllerAs: 'activeCtrl'
        })
        .when('/leaderboard', {
            templateUrl: 'src/components/leaderboard-component/leaderboard.html',
            controller: 'leaderboardController',
            controllerAs: 'leaderCtrl'
        })
        .when('/newQuestion', {
            templateUrl: 'src/components/new-question-component/new-question.html',
            controller: 'newQuestionController',
            controllerAs: 'newCtrl'
        })
        .otherwise({
            template : "<h1 style='color: white'>None</h1><p style='color: white'>Nothing has been selected</p>"
        });


});