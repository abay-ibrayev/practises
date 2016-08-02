var appData = angular.module("EProc.appData", []);

// dummy data for our application
appData.value("customerData", {
	customerList : {
		CL00001 : {
			custNum : "CL00001",
			name : "Wellness Clinic",
			contactInfo : {
				name : "Dr. Jim Hill",
				phone : "555-555-1234",
				email : "jh@wellnessclinic.com"
			},
			shippingAddress : {
				street : "123 WC St.",
				city : "Charlotte",
				state : "NC",
				zip : "28277"
			},
			billingAddress : {
				street : "4545 Medical Plaza",
				city : "Charolotte",
				state : "NC",
				zip : "28210"
			}
		},
		CL00002 : {
			custNum : "CL00002",
			name : "Medical Center",
			contactInfo : {
				name : "Dr. Ann Freemont",
				phone : "555-555-4321",
				email : "afreemont@medcenter.com"
			},
			shippingAddress : {
				street : "1212 S. Clemont St. Suite A",
				city : "Charlotte",
				state : "NC",
				zip : "sjones@someuniv.edu"
			},
			billingAddress : {
				street : "1212 S. Clemont St. Suite B",
				city : "Charlotte",
				state : "NC",
				zip : "sjones@someuniv.edu"
			}
		},
		CL00003 : {
			custNum : "CL00003",
			name : "Metro Family Clinic",
			contactInfo : {
				name : "Dr. Rita Fernandez",
				phone : "555-555-4141",
				email : "rfernandez@mfc.com"
			},
			shippingAddress : {
				street : "4241 N. 22nd St.",
				city : "Matthews",
				state : "NC",
				zip : "28105"
			},
			billingAddress : {
				street : "1313 S. End Point",
				city : "Mint Hill",
				state : "NC",
				zip : "28227"
			}
		},
		CL00004 : {
			custNum : "CL00004",
			name : "CMR Health Center",
			contactInfo : {
				name : "Dr. Alan Acebedo",
				phone : "555-555-6969",
				email : "alanacebedo@crmhc.com"
			},
			shippingAddress : {
				street : "4420 West Mt. Plaza",
				city : "Charlotte",
				state : "NC",
				zip : "28277"
			},
			billingAddress : {
				street : "1313 Moss Lane",
				city : "Charlotte",
				state : "NC",
				zip : "28277"
			}
		},
		CL00005 : {
			custNum : "CL00005",
			name : "Mecklenburg Pediatrics",
			contactInfo : {
				name : "Dr. Anna Smith",
				phone : "555-555-8998",
				email : "asmith@mckped.com"
			},
			shippingAddress : {
				street : "877 N. Polk St.",
				city : "Charlotte",
				state : "NC",
				zip : "28206"
			},
			billingAddress : {
				street : "1001 N. Polk St., Suit B",
				city : "Charlotte",
				state : "NC",
				zip : "28206"
			}
		},
		CL00006 : {
			custNum : "CL00006",
			name : "R.P. Taylor Hospital",
			contactInfo : {
				name : "Dr. Monica Miller",
				phone : "555-555-7934",
				email : "mmiller@rpth.com"
			},
			shippingAddress : {
				street : "1138 Hawthorn Ln Bldg 1",
				city : "Charlotte",
				state : "NC",
				zip : "28204"
			},
			billingAddress : {
				street : "1138 Hawthorn Ln Bldg 2",
				city : "Charlotte",
				state : "NC",
				zip : "28204"
			}
		}

	},
	customerHistory : {
		CL00001 : [ {
			orderNumber : "838383",
			name : "Wellness Clinic",			
			date : "10/20/2014",
			total : "$8,345.48",
			status : "Ready To Ship"
		}, {
			orderNumber : "292922",
			name : "Wellness Clinic",
			date : "10/18/2014",
			total : "$13,222.00",
			status : "Shipped"
		}, {
			orderNumber : "129837",
			name : "Wellness Clinic",
			date : "10/15/2014",
			total : "$4,141.17",
			status : "Shipped"
		} ],
		CL00002 : [ {
			orderNumber : "763473",
			name : "Medical Center",
			date : "10/20/2014",
			total : "$1,200.19",
			status : "Ready To Ship"
		}, {
			orderNumber : "938383",
			name : "Medical Center",
			date : "10/15/2014",
			total : "$5,480.00",
			status : "Shipped"
		}, {
			orderNumber : "717171",
			name : "Medical Center",
			date : "10/10/2014",
			total : "$12,987.14",
			status : "Shipped"
		}, {
			orderNumber : "737333",
			name : "Medical Center",
			date : "10/03/2014",
			total : "$2,230.90",
			status : "Shipped"
		} ],
		CL00003 : [ {
			orderNumber : "292922",
			name : "Metro Family Clinic",
			date : "10/13/2014",
			total : "$450.00",
			status : "Shipped"
		}, {
			orderNumber : "101010",
			name : "Metro Family Clinic",
			date : "10/10/2014",
			total : "$100.00",
			status : "Shipped"
		}, {
			orderNumber : "238383",
			name : "Metro Family Clinic",
			date : "10/08/2014",
			total : "$1,050.30",
			status : "Shipped"
		} ],
		CL00004 : [ {
			orderNumber : "898983",
			name : "CMR Health Center",
			date : "10/20/2014",
			total : "$750.00",
			status : "On Hold"
		}, {
			orderNumber : "939393",
			name : "CMR Health Center",
			date : "10/19/2014",
			total : "$100.00",
			status : "Shipped"
		}, {
			orderNumber : "191817",
			name : "CMR Health Center",
			date : "10/08/2014",
			total : "$1,050.30",
			status : "Shipped"
		} ],
		CL00005 : [ {
			orderNumber : "910187",
			name : "Mecklenburg Pediatrics",
			date : "10/15/2014",
			total : "$12,987.14",
			status : "Shipped"
		}, {
			orderNumber : "823820",
			name : "Mecklenburg Pediatrics",
			date : "10/13/2014",
			total : "$2,230.38",
			status : "Shipped"
		} ],
		CL00006 : [ {
			orderNumber : "635353",
			name : "R.P. Taylor Hospital",
			date : "10/20/2014",
			total : "$1,200.19",
			status : "Shipped"
		}, {
			orderNumber : "761839",
			name : "R.P. Taylor Hospital",
			date : "10/15/2014",
			total : "$5,480.00",
			status : "Shipped"
		}, {
			orderNumber : "282923",
			name : "R.P. Taylor Hospital",
			date : "10/10/2014",
			total : "$12,987.14",
			status : "Shipped"
		}, {
			orderNumber : "191811",
			name : "R.P. Taylor Hospital",
			date : "10/03/2014",
			total : "$2,230.90",
			status : "Shipped"
		} ]

	},
	getCustomerList : function() {
		return this.customerList;
	},
	getInfoForCust : function(custNum) {
		return this.customerList[custNum];
	},
	getAllHistory : function() {
		var historyArray = new Array();
		for ( var customerID in this.customerList) {
			historyArray.push.apply(historyArray, this
					.getAllHistoryForCust(customerID));
		}
		return historyArray;
	},
	getAllHistoryForCust : function(custNum) {
		return this.customerHistory[custNum];
	},
	getOnlyOpenOrders : function(orderHistory) {
		var recentHistArray = new Array();
		for ( var i = 0; i < orderHistory.length; i++) {
			if (orderHistory[i].status != "Shipped") {
				recentHistArray.push(orderHistory[i]);
			}
		}
		return recentHistArray;
	},
	getOpenOrders : function() {
		return this.getOnlyOpenOrders(this.getAllHistory());
	}
});

