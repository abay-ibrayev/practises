angular.module('EProc.Profile')
    .controller('profileDetailsCtrl',
        function ($scope) {
            $scope.profile = {
                party: {
                    displayName: 'ТОО "ИВАНОВ"',
                    requisites: [
                        {'name': 'БИН', 'value': '00000000'},
                        {'name': 'БИК', 'value': '000000000'}
                    ],
                    addressString: 'Республика Казахстан, г.Астана, ул. Бараева, д.28, офис 1',
                },
                generalManager: {
                    'fullName': 'ИВАНОВ И.И.'
                },
                employees: [
                    {
                        'fullName': 'ПЕТРОВ П.П.',
                        'position': 'Менеджер'
                    },
                    {
                        'fullName': 'ПЕТРОВ П.П.',
                        'position': 'Менеджер'
                    }
                ]
            };
        });