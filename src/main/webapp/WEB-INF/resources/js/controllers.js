function ShtCtrl($scope, API) {
    $scope.objects = {
        user: "",
        destination: ""
    };

    $scope.ids = {
        objectID: 0,
        destinationID: 0
    };

    $scope.search_finished = false;

    $scope.display = {
        statusMessage: "Начинаем..."
    }

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
        $scope.display.statusMessage = "Узнаем кое что об объектах...";
        $scope.ids.objectID = API.getUser({
            param_first: $scope.objects.user
        });
        $scope.ids.destinationID = API.getUser({
            param_first: $scope.objects.destination
        }, function () {
            $scope.step.currentOwners = [
                $scope.ids.destinationID.id
            ];
            $scope.$emit("start")
        });
    };

    $scope.$on("start", function () {
        $scope.makeStep();
    });

    $scope.makeStep = function () {
        $scope.display.statusMessage = "Чешим затылок...";
        $scope.step.number++
        var paramIds = $scope.step.currentOwners.join();
        var paramStep = $scope.step.number;

        var queryResult = API.getFriends({
            step: paramStep,
            ids: paramIds
        }, function () {
            $scope.friendsList = queryResult;
            $scope.tryToFind();
        });
    };

    $scope.tryToFind = function () {
        $scope.display.statusMessage = "Тыкаем пальцем в небо...";
        if ($scope.friendsList.friends.length != 0) {
            if ($scope.friendsList.friends.indexOf($scope.ids.objectID.id) != -1) {
                return $scope.$emit('found');
            } else {
                $scope.step.currentOwners = $scope.friendsList.friends;
                return $scope.$emit("start");
            }
        } else {
            $scope.$emit("fail");
        }

    };

    $scope.$on("found", function () {
        $scope.display.statusMessage = "УРА!! Нашли...";
        var lastStepNumber = $scope.step.number;
        var startId = $scope.ids.objectID.id;

        var chain = API.getChain({
            param_first: lastStepNumber,
            param_second: startId
        }, function () {
            $scope.chain = chain;
            $scope.drawResult();
        })
    });

    $scope.drawResult = function () {
        $scope.display.statusMessage = "На этом всё...";
        $scope.search_finished = true;
        console.log($scope.chain);
    }

    $scope.$on("fail", function(){
        $scope.display.statusMessage = "К сожалению ничего не вышло...";
        console.log("fail");
    })


}


ShtCtrl.inject = ["$scope", "API"];