"use strict";
define(["backbone", "views/employee"], function(Backbone, Employee) {

	var EntryList = Backbone.View.extend({
		initialize: function() {
			this.listenTo(this.collection, "add", this.renderEntry);
		},
		renderEntry: function(model, collection, options) {
			if(options.add) {
				var employee = new Employee({ model: model });
				this.$el.append(employee.render().el);
			} else {
				// here we could implement update or delete handling
			}
		}
	});

	return EntryList;
});
