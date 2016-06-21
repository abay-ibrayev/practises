"use strict";
define([ "underscore", "backbone", "text!templates/entrytemplate.html"],
         function(_, Backbone, templateHTML) {

	var Employee = Backbone.View.extend({
		tagName: "li",
		template: _.template(templateHTML),
		render: function() {
			this.$el.html(this.template(this.model.toJSON()));
			return this;
		},
		events: {
			"click .remove-entry": "removeEntry"
		},
		removeEntry: function() {
			// its model
			this.model.destroy();
			// this view
			this.remove();
		}
	});

	return Employee;
});
