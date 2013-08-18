<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en" ng-app="sht">
<head>
    <meta charset="UTF-8">
    <title>Six Hands Theory</title>
    <link rel="stylesheet" href="/resources/css/bootstrap.css">
    <link rel="stylesheet" href="/resources/css/style.css">
    <script type="text/javascript" src="/resources/js/angular.min.js"></script>
    <script type="text/javascript" src="/resources/js/angular-services.js"></script>
    <script type="text/javascript" src="/resources/js/app.js"></script>
    <script type="text/javascript" src="/resources/js/services.js"></script>
    <script type="text/javascript" src="/resources/js/controllers.js"></script>
</head>
<body ng-controller="ShtCtrl">
<div class="container">
    <div class="main-form">

        <div class="well" style="height:100%;">
            <h1 class="title">How many steps...</h1>
            <hr>
            <div class="form-search" ng-show="!search_starts">
                <div class="control-group">
                    <div>
                        <h1>...from...</h1><input type="text" class="search-query" ng-model="objects.user" style="width:200px;height:50px;font-size:20px;font-family:'Crete Round', serif;" placeholder="VK id here...">
                        <h1>...to...</h1><input type="text" class="search-query" ng-model="objects.destination" style="width:200px;height:50px;font-size:20px;font-family:'Crete Round', serif;" placeholder="...and here...">
                        <br><br><input type="submit" class="btn btn-primary" ng-click="$emit('searchStarts')" style="width:225px;height:50px;font-size:20px;font-family:'Crete Round', serif;"/>
                    </div>
                </div>
            </div>
            <div ng-show="search_starts">

                <table class="table table-bordered table-striped table-hover sht-table">
                    <tr>
                        <th>
                            {{ objects.user }}
                        </th>
                        <th>
                            {{ objects.destination }}
                        </th>
                    </tr>
                </table>
                <div class="chain">
                    <div class="arrow">
                        <img src="/resources/images/arrow-down-icon.png" alt=""/>
                    </div>
                    <div class="user">
                        <img class="img-circle" src="http://cs322221.vk.me/v322221664/2f81/-4cm9G4EK9s.jpg" alt=""/>
                        <div class="username">
                            Ирина Савчук
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>
</body>
</html>
