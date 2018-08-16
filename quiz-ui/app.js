var app = angular.module("app", ["ngRoute"]);
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
        });
});