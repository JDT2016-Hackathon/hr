/**
 * Copyright (c) 2014, 2016, Oracle and/or its affiliates.
 * The Universal Permissive License (UPL), Version 1.0
 */
"use strict";

requirejs.config({
  // Path mappings for the logical module names
  paths:
  //injector:mainReleasePaths
  {
    "knockout":       "libs/knockout/knockout-3.4.0.debug",
    "jquery":         "libs/jquery/jquery-2.1.3",
    "jqueryui-amd":   "libs/jquery/jqueryui-amd-1.11.4",
    "promise":        "libs/es6-promise/promise-1.0.0",
    "hammerjs":       "libs/hammer/hammer-2.0.4",
    "ojdnd":          "libs/dnd-polyfill/dnd-polyfill-1.0.0",
    "ojs":            "libs/oj/v2.0.1/debug",
    "ojL10n":         "libs/oj/v2.0.1/ojL10n",
    "ojtranslations": "libs/oj/v2.0.1/resources",
    "text":           "libs/require/text",
    "signals":        "libs/js-signals/signals"
  }
  //endinjector
  ,
  // Shim configurations for modules that do not expose AMD
  shim: {
    "jquery": {
      exports: ["jQuery", "$"]
    }
  }
});
require([
  "ojs/ojcore",
  "knockout",
  "jquery",
  "ojs/ojknockout",
  "ojs/ojmodel",
  "ojs/ojcollectiontabledatasource",
  "ojs/ojpagingtabledatasource",
  "ojs/ojtable",
  "ojs/ojpagingcontrol",
  "ojs/ojchart",
],
function(oj, ko, $) {

  var EmployeeModel = oj.Model.extend({
    idAttribute: "employeeId"
  });

  var EmployeesCollection = oj.Collection.extend({
    url:   location.protocol + "//" + location.host + "/hr/employees",
    model: new EmployeeModel()
  });

  function MainViewModel() {
    var self = this;
    self.titleLabel = "Java Day Tokyo 2016 Hackathon";
    self.empCollection = new EmployeesCollection();
    self.chartSeries = ko.observableArray();

    self.empCollection.fetch({
      success: function (collection, response, options) {
        var values = {};
        $.each(response, function (index, emp) {
          var jobId    = emp["job"]["jobId"];
          var jobTitle = emp["job"]["jobTitle"];
          var salary   = emp["salary"];

          if (values[jobId]) {
            values[jobId]["items"][0] += salary;
          }
          else {
            values[jobId] = {
              name:  jobTitle,
              items: [ salary ]
            };
          }
        });
        var array = [];
        $.each(values, function (key, value) {
          array.push(value);
        });
        self.chartSeries(array);
      }
    });

    self.tableDataSource =
      new oj.PagingTableDataSource(new oj.CollectionTableDataSource(self.empCollection));
  };

  $(document).ready(function() {
    ko.applyBindings(new MainViewModel(), document.getElementById("mainContent"));
  });
});
