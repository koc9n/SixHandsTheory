function ShtCtrl($scope, API) {
    $scope.objects = {
        user: "",
        destination: ""
    };

    $scope.ids = {
        objectID: "",
        destinationID: ""
    };

    $scope.chain = {};

    $scope.friendsList = {};

    $scope.step = {
        number: 0,
        currentOwners: []
    };

    $scope.search_starts = false;

    $scope.$on("searchStarts", function () {
        $scope.search_starts = true;
        $scope.fillUsersIDs();
    });

    $scope.fillUsersIDs = function () {
        $scope.ids.objectID = API.getUser({
            param_first: $scope.objects.user
        });
        $scope.ids.destinationID = API.getUser({
            param_first: $scope.objects.destination
        }, function () {
            $scope.$emit("start")
        });
    };

    $scope.$on("start", function () {
        $scope.makeStep();
    });

    $scope.makeStep = function () {
        $scope.step.number++
        var paramIds = $scope.step.currentOwners.join();
        var paramStep = $scope.step.number;

        var queryResult = API.getFriends({
            param_first: paramStep,
            param_second: paramIds
        }, function () {
            $scope.friendsList = queryResult;
            $scope.tryToFind();
        });
    };

    $scope.tryToFind = function () {
        if ($scope.friendsList.friends.indexOf($scope.ids.objectID) != -1) {
            return $scope.$emit('found');
        } else {
            $scope.step.currentOwners = $scope.friendsList.friends;
            return $scope.$emit("start");
        }
    };

    $scope.$on("found", function () {
        var lastStepNumber = $scope.step.number;
        var startId = $scope.ids.objectID;

        var chain = API.getChain({
            param_first: lastStepNumber,
            param_second: startId
        }, function(){
            $scope.chain = chain;
            $scope.drawResult();
        })
    });

    $scope.drawResult = function () {
        console.log($scope.chain);
    }


}


ShtCtrl.inject = ["$scope", "API"];