/**
 * Copyright (c) 2014, 2016, Oracle and/or its affiliates.
 * The Universal Permissive License (UPL), Version 1.0
 */
"use strict";

requirejs.config({
  // ロードする（可能性のある）JavaScriptライブラリの構成情報
  paths: {
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
  },
  // AMD (Asynchronous Module Definition; ライブラリのモジュール化や非同期ロードのためのお約束) に
  // 非対応のライブラリをモジュール化するための構成
  shim: {
    "jquery": {
      exports: ["jQuery", "$"]
    }
  }
});
require([
  // このモジュールが依存しているモジュールたち
  "ojs/ojcore",
  "knockout",
  "jquery",
  "ojs/ojknockout",
  "ojs/ojmodel",
  "ojs/ojknockout-model",
  "ojs/ojarraytabledatasource",
  "ojs/ojpagingtabledatasource",
  "ojs/ojtable",
  "ojs/ojpagingcontrol",
  "ojs/ojchart",
],
function(oj, ko, $) {

  // Employee リソースのレコードを表すオブジェクトの定義
  var EmployeeModel = oj.Model.extend({
    idAttribute: "employeeId",
    parse: function (response) {
      // JSON オブジェクトから ViewModel オブジェクトで使用する形式に変換する
      return {
        employeeId: response["employeeId"],
        firstName:  response["firstName"],
        lastName:   response["lastName"],
        jobId:      response["job"]["jobId"],
        jobTitle:   response["job"]["jobTitle"],
        salary:     response["salary"]
      };
    }
  });

  // Employee リソースのコレクション表すオブジェクトの定義
  var EmployeesCollection = oj.Collection.extend({
    url:   location.protocol + "//" + location.host + "/hr/employees",
    model: new EmployeeModel()
  });

  // index.html 内の id="mainContent" の状態を保持する ViewModel
  function MainViewModel() {
    var self = this;

    // Knockout.jsによって監視されているので双方向データバインドが有効なプロパティ
    self.titleLabel  = ko.observable("Java Day Tokyo 2016 Hackathon");
    self.employees   = ko.observableArray();
    self.chartGroups = ko.observableArray(["給与"]);

    // RESTサービス呼び出しのための Collectionのインスタンスを生成
    self.empCollection = new EmployeesCollection();
    self.empCollection.fetch({
      success: function (collection, response, options) {
        // サービス呼び出しが成功した時の実行されるコールバック関数
        self.employees(oj.KnockoutUtils.map(collection));
      },
      error: function (jqXHR, textStatus, errorThrown) {
        oj.Logger.error("Error: " + textStatus);
      }
    });

    // 表形式（ojTable コンポーネント）で表示されるデータのコレクション
    // self.employees に変更があるとコールバック関数が呼ばれる
    self.tableDataSource = ko.computed(function () {
      return new oj.PagingTableDataSource(new oj.ArrayTableDataSource(self.employees()));
    });

    // チャート（ojChart コンポーネント）で表示するためのを抽出
    // self.employees に変更があるとコールバック関数が呼ばれる
    // チャートのデータは次のようなオブジェクトの配列
    // { name: <シリーズデータの名前>, items: [ <グループ#1 の値>, <グループ#2の値>, ... ] }
    self.chartSeries = ko.computed(function () {
      var seriesValues = [];
      if (self.employees().length !== 0) {
        var values = {};
        $.each(self.employees(), function (index, emp) {
          var jobId    = emp.jobId();
          var jobTitle = emp.jobTitle();
          var salary   = emp.salary();
          if (values[jobId]) {
            values[jobId].items[0] += salary;
          }
          else {
            values[jobId] = { name: jobTitle, items: [ salary ] };
          }
        });
        $.each(values, function (key, value) {
          seriesValues.push(value);
        });
      }
      return seriesValues;
    });
  };

  $(document).ready(function() {
    ko.applyBindings(new MainViewModel(), document.getElementById("mainContent"));
  });
});
