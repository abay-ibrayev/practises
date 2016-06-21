"use strict";
define([ "jquery", "backbone" ], function($, Backbone) {

	var validators = {
		"*": [ {
			expr: /\S/,
			message: "Required"
		} ],
		"phone": [ {
			expr: /^[0-9]{3}-[0-9]{3}-[0-9]{4}$/,
			message: "Invalid"
		} ],
		"email": [ {
			expr: /^[a-z0-9!#$%&'*+\/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+\/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$/i, // thanks: http://stackoverflow.com/a/1373724/275501
			message: "Invalid"
		} ]
	};

	function validateField(value, key) {
		// compile a list of rules for this prop - the wildcard rules plus any field-specific rules
		var rules = validators["*"].concat(validators[key] || []);
		// find the first broken rule (if any)
		var broken = _.find(rules, function(rule) { return !rule.expr.test(value); });
		// return a description if broken, otherwise null
		return broken ? { "attr": key, "error": broken.message } : null;
	}

	var EmployeeRecord = Backbone.Model.extend({
		validate: function(attrs) {
			// run validateField on each field
			var validated = _.mapObject(attrs, validateField);
			// compile any errors returned (removing any returned nulls)
			var attrsInError = _.compact(_.values(validated));
			// if any errors, return the array, otherwise null
			return attrsInError.length ? attrsInError : null;
		},
		sync: function(method, model, options) {
			// syncing to the server is not yet implemented, so invoke success immediately:
			options.success();
		}
	});

	return EmployeeRecord;
});
