"use strict";
define([ "backbone", "models/employeeRecord" ], function(Backbone, EmployeeRecord) {

	var Entries = Backbone.Collection.extend({
		model: EmployeeRecord
	});

	return Entries;
});
