/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2014 Red Hat, Inc., and individual contributors
 * as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
(function () {
  'use strict';

  var module = angular.module('pnc.import', [
    'ui.router',
    'ui.bootstrap',
    'pnc.common.restclient',
    'pnc.common.util',
    'angularUtils.directives.uiBreadcrumbs',
    'pnc.common.directives',
    'angular-websocket',
    'pnc.common.da-client'
  ]);

  module.run([
    '$rootScope',
    '$uibModal',
    '$state',
    function ($rootScope, $uibModal, $state) {
      /* jshint unused: false */
      $rootScope.$on('$stateChangeStart', function(event, toState, toParams, fromState, fromParams) {
        if(fromState.name === 'import.product' && $rootScope.importProductState === 'bc' && !$rootScope.productImportResetConfirmed) {
          event.preventDefault();
          $uibModal.open({
            templateUrl: 'common/directives/pnc-confirm-click/pnc-confirm-click.html',
            controller: [
              '$scope',
              function ($scope) {
                $scope.message = 'Do you want to leave the page? Current import process data will be lost.' +
                  ' You could open the other page in a new tab.';
                $scope.confirm = function () {
                  $scope.$close();
                  $rootScope.productImportResetConfirmed = true;
                  $state.go(toState,toParams);
                };
                $scope.cancel = function () {
                  $scope.$dismiss();
                };
              }
            ]
          });
        }
        $rootScope.productImportResetConfirmed = false;
      });
    }
  ]);
})();
