angular.module("sht", ['shtServices']).
    config(['$routeProvider', function ($routeProvider) {
        $routeProvider.
            otherwise({redirectTo: '/'});
    }]).
    filter('cut',function () {
        return function (input) {
            if (input.length > 30)
                input = input.substr(0, 200) + "...";

            return input;
        }
    }).
    filter('join', function () {
        return function (input) {
            return input.join();
        }
    });