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
                        <h1>...from...</h1><input type="text" class="search-query" ng-model="objects.user"
                                                  style="width:200px;height:50px;font-size:20px;font-family:'Crete Round', serif;"
                                                  placeholder="VK id here...">

                        <h1>...to...</h1><input type="text" class="search-query" ng-model="objects.destination"
                                                style="width:200px;height:50px;font-size:20px;font-family:'Crete Round', serif;"
                                                placeholder="...and here...">
                        <br><br><input type="submit" class="btn btn-primary" ng-click="$emit('searchStarts')"
                                       style="width:225px;height:50px;font-size:20px;font-family:'Crete Round', serif;"/>
                    </div>
                </div>
            </div>
            <div ng-show="search_starts">

                <table class="table table-bordered table-striped table-hover sht-table">
                    <tr>
                        <th>
                            {{ objects.user }} - {{ids.objectID.name}}
                        </th>
                        <th>
                            {{ objects.destination }} - {{ids.destinationID.name}}
                        </th>
                    </tr>
                </table>
                <div class="chain pull-left" ng-show="!search_finished">
                    <h1>Working...</h1>
                    <img src="/resources/images/loading_animation.gif" alt=""/>
                </div>
                <div class="chain pull-left" ng-show="search_finished">
                    <div ng-repeat="cell in resultChain">
                        <div ng-show="!$first" class="arrow">
                            <img style="height:50px;" ng-src="/resources/images/arrow-down-icon.png" alt=""/>
                        </div>
                        <div class="user">
                            <img style="width:100px;" class="img-circle" ng-src="{{cell.photoLink}}" alt=""/>

                            <div class="username">
                                {{cell.name}}
                            </div>
                        </div>
                    </div>
                </div>
                <div class="status-table pull-right">
                    <table class="table table-bordered table-striped table-hover">
                        <tr>
                            <td>
                                StatusMessage
                            </td>
                            <td>
                                {{ display.statusMessage }}
                            </td>
                        </tr>
                        <tr>
                            <td>
                                Steps
                            </td>
                            <td>
                                {{ step.number }}
                            </td>
                        </tr>
                        <tr>
                            <td>
                                IDS
                            </td>
                            <td>
                                {{ step.currentOwners | join | cut }}
                            </td>
                        </tr>
                    </table>
                </div>
                <div class="clearfix"></div>
            </div>
        </div>

    </div>
</div>
</body>
</html>
