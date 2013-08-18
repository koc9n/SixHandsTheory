angular.module("sht", ['shtServices']).
    config(['$routeProvider', function ($routeProvider) {
        $routeProvider.
            otherwise({redirectTo: '/'});
    }]);