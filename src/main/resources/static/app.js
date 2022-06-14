angular.module('market-front', []).controller('appController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/market/api/v1';
    let currentPageIndex = 1;
    var cur_product;
    $scope.loadProducts = function (pageIndex = 1) {
        currentPageIndex = pageIndex;
        $http({
            url: contextPath + '/products',
            method: 'GET',
            params: {
                p: pageIndex
            }
        }).then(function (response) {
            console.log(response);
            $scope.productsPage = response.data;
            $scope.paginationArray = $scope.generatePagesIndexes(1, $scope.productsPage.totalPages);
        });
    }

    $scope.removeInfo = function (product) {
        $http({
            url: contextPath + '/products/'+ product.id,
            method: 'DELETE'
        }).then(function (response) {
            console.log(response);
            $scope.loadProducts(currentPageIndex);
        });
    }

    $scope.changeInfo = function (product) {
        $scope.new_product=$scope.cur_product = product;

    }

    $scope.removeAll = function () {
        $http({
            url: contextPath + '/products',
            method: 'DELETE'
        }).then(function (response) {
            console.log(response);
            $scope.loadProducts(currentPageIndex);
        });
    }

    $scope.createNewProductOrChangeExisting = function () {
        if($scope.cur_product == null) {
            $http.post(contextPath + '/products', $scope.new_product)
                .then(function successCallback(response) {
                        $scope.loadProducts(response.data.totalPages);
                        $scope.new_product = null;
                    }, function failCallback(response) {
                        alert(response.data.message);
                    }
                );
        } else {
            $http.put(contextPath + '/products', $scope.new_product)
                .then(function successCallback(response) {
                        $scope.loadProducts(currentPageIndex);
                        $scope.new_product = null;
                    }, function failCallback(response) {
                        alert(response.data.message);
                    }
                );
            $scope.cur_product = null;
        }
    }

    $scope.generatePagesIndexes = function (startPage, endPage) {
        let arr = [];
        for (let i = startPage; i < endPage + 1; i++) {
            arr.push(i);
        }
        return arr;
    }

    $scope.nextPage = function () {
        currentPageIndex++;
        if (currentPageIndex > $scope.productsPage.totalPages) {
            currentPageIndex = $scope.productsPage.totalPages;
        }
        $scope.loadProducts(currentPageIndex);
    }

    $scope.prevPage = function () {
        currentPageIndex--;
        if (currentPageIndex < 1) {
            currentPageIndex = 1;
        }
        $scope.loadProducts(currentPageIndex);
    }



    $scope.loadProducts(1);


});