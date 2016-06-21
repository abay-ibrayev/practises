"use strict";
define(["backbone", "models/employeeRecord"],
	function(Backbone, EmployeeRecord) {

	var Directory = Backbone.View.extend({
		events: {
			"keyup :input": "handleInputKeyup",
			"submit": "handleClickAdd",
			"reset": "handleClickReset"
		},
		initialize: function() {
			// schedule an immediate reset of the form to initialise validation etc.
			this.scheduleReset();
		},

		// event handlers
		handleInputKeyup: function() {
			// update the validation messages
			this.buildAndValidateModel();
		},
		handleClickReset: function() {
			this.scheduleReset();
		},
		handleClickAdd: function(e) {
			// prevent default HTML form submission
			e.preventDefault();
			// retrieve data
			var employee = this.buildAndValidateModel();
			// try to add, but only if valid and save succeeds
			this.collection.create(employee, { wait: true });
			// if didn't succeed return
			if(!_.contains(this.collection.models, employee)) { return; }
			// reset form
			this.$("form").trigger("reset");
		},

		// shared logic
		scheduleReset: function() {
			// set focus back to the first field
			this.$(":text:visible:first").focus();
			// schedule validation once the reset event has completed
			setTimeout(this.buildAndValidateModel.bind(this), 0);
		},
		buildAndValidateModel: function() {
			// get all the form fields
			var fields = this.$("form").serializeArray();
			// function to create object property from field
			function compose(obj, field) { obj[field.name] = field.value; return obj; }
			// reduce the list of fields to an object hash
			var attrs = _.reduce(fields, compose, {});
			// build the model
			var model = new EmployeeRecord(attrs);
			// clear any existing validation messages
			this.$(".error-message").text("");
			// no errors? (n.b. also causes validation to run)
			if(model.isValid()) { return model; }
			// display error messages based on validation of model
			_.each(model.validationError, this.displayValidationMessage.bind(this));
			// return the model
			return model;
		},
		displayValidationMessage: function(err) {
			// build CSS selector for the validation message
			var selector = "[name='" + err.attr + "']+.error-message";
			// set the text content of the validator
			this.$(selector).text(err.error);
		}

	});

	return Directory;
});
