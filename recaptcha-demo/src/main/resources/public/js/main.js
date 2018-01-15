angular.module("reCaptchaDemo", [])
    .controller("AppCtrl", function ($scope){
        $scope.auth = {};
        $scope.sendForm = function(auth) {
            window.alert(auth.email);
        }
    });