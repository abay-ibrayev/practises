angular.module('EProc.Common')
    .service('AppMenuService',
        function() {
            var service = this;

            service.getMenuItems = function() {
                return [
                    {
                        path: 'myprofile',
                        label: 'Профиль'
                    },
                    {
                        path: 'favorites',
                        label: 'Закладки'
                    },
                    {
                        path: 'purchasers',
                        label: 'Список организаций'
                    },
                    {
                        path: 'proposals',
                        label: 'Коммерческие предложения'
                    },
                    {
                        path: 'settings',
                        label: 'Настройки'
                    },
                    {
                        path: 'tenders',
                        label: 'Тендеры онлайн'
                    },
                    {
                        path: 'tender_platform',
                        label: 'Тендерная площадка'
                    },
                    {
                        path: 'suppliers',
                        label: 'Каталог компаний'
                    }
                ];
            }
        }
    );