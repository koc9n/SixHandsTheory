angular.module('shtServices', ['ngResource']).
    factory("API", function ($resource) {
        return $resource('/:method/:param_first/:param_second', {
            method: "@method",
            param_first: "@param_first",
            param_second: "@method"
        }, {
            getFriends: {method: "GET", params: {method: "getfriends"}},
            getUser: {method: "GET", params: {method: "getuser"}},
            getChain: {method: "GET", params: {method: "getchain"}}
        });
    });