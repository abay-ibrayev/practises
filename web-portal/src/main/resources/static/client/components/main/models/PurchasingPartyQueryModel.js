angular.module('EProc.Common')
    .service('PurchasingPartyQueryModel', ['$http', 'EndpointConfigService',
        function ($http, EndpointConfigService) {
            var service = this,
                MODEL = '/purchase/parties';

            service.purchasersTree = [];

            service.allParents = function () {
                return $http.get(EndpointConfigService.getUrl(MODEL))
                    .then(function (result) {
                        return result.data;
                    });
            };

            service.childrenParties = function (parentId) {
                return $http.get(EndpointConfigService.getUrl(MODEL) + '/' + parentId)
                    .then(function (result) {
                        return result.data;
                    });
            }

            service.getParent = function (purchaserId) {
                var parent = this._findPurchaser(purchaserId, this.purchasersTree);
                return parent;
            }

            service.addPurchaser = function (purchaser) {
                if (purchaser.parentPartyId) {
                    var parent = this._findPurchaser(purchaser.parentPartyId, this.purchasersTree);
                    if (parent) {
                        parent.children.push({
                            'id': purchaser.id, 'label': purchaser.shortName,
                            'children': [], 'parent': parent
                        });
                    }
                } else {
                    this.purchasersTree.push({
                        'id': purchaser.id, 'label': purchaser.shortName,
                        'children': [], 'parent': null
                    });
                }
            }

            service.getPathToRoot = function (purchaserId) {
                var path = [];

                var purchaser = purchaserId ? this._findPurchaser(purchaserId, this.purchasersTree) : null;
                if (purchaser) {
                    path.push({'id': purchaser.id, 'label': purchaser.label});

                    var cur = purchaser;
                    while (cur.parent != null) {
                        path.unshift({'id': cur.parent.id, 'label': cur.parent.label});
                        cur = cur.parent;
                    }
                }

                path.unshift({'id': 0, 'label': 'Организации'});

                return path;
            }

            service.addChildrenParties = function (parentPartyId, addChildrenPartiesCmd) {
                return $http.post(EndpointConfigService.getUrl(MODEL) + '/' + parentPartyId, addChildrenPartiesCmd)
                    .then(function (result) {
                        return result.data;
                    });
            }

            service._findPurchaser = function (purchaserId, collection) {
                for (var i = 0, n = collection.length; i < n; i++) {
                    var node = collection[i];
                    if (node.id == purchaserId) {
                        return node;
                    }
                    var found = this._findPurchaser(purchaserId, node.children);
                    if (found) {
                        return found;
                    }
                }

                return null;
            }
        }
    ]);