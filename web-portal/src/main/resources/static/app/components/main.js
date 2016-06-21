"use strict";
var webjars = {
	versions: {
        'jquery': '3.0.0',
        'requirejs-domready': '2.0.1',
        'underscorejs': '1.8.3',
        'requirejs-text': '2.0.15',
        'backbonejs': '1.3.2'
    },
	path: function(webjarid, path) {
		return '/webjars/' + webjarid + '/' + webjars.versions[webjarid] + '/' + path;
	}
};

requirejs.config({
	baseUrl: "app/components",
	paths: {
		// third party
		jquery: webjars.path("jquery", "jquery.min"),
		domReady: webjars.path("requirejs-domready", "domReady"),
		text: webjars.path("requirejs-text", "text"),
		backbone: webjars.path("backbonejs", "backbone-min"),
		underscore: webjars.path("underscorejs", "underscore-min"),

		// application
		collections: "directory/collections",
		models: "directory/models",
		views: "directory/views",
		templates: "directory/templates",
		partials: "directory/partials"
	}
});

require([ "app" ], function(app) {
	app.init();
});
