/*
 * File: app/view/weekly/evaluation/GridPanel.js
 *
 * This file was generated by Sencha Architect version 3.0.4.
 * http://www.sencha.com/products/architect/
 *
 * This file requires use of the Ext JS 4.2.x library, under independent license.
 * License of Sencha Architect does not include license for Ext JS 4.2.x. For more
 * details see http://www.sencha.com/license or contact license@sencha.com.
 *
 * This file will be auto-generated each and everytime you save your project.
 *
 * Do NOT hand edit this file.
 */

Ext.define('MySchool.view.weekly.evaluation.GridPanel', {
	extend: 'Ext.grid.Panel',
	alias: 'widget.weeklyevaluationgridpanel',

	requires: [
		'Ext.grid.column.Number',
		'Ext.form.field.Number',
		'Ext.grid.column.CheckColumn',
		'Ext.form.field.Checkbox',
		'Ext.grid.View',
		'Ext.grid.plugin.CellEditing',
		'Ext.grid.column.Date'
	],

	itemId: 'weeklyevaluationgridpanel',
	autoScroll: true,
	title: '[student name]Weekly Evaluation By Subject And Week',
	forceFit: true,
	store: 'weekly.EvaluationsRatingsStore',

	initComponent: function() {
		var me = this;

		Ext.applyIf(me, {
			columns: [
				{
					xtype: 'gridcolumn',
					dataIndex: 'subjName',
					text: 'Subject Name'
				},
				{
					xtype: 'numbercolumn',
					dataIndex: 'qtrYear',
					text: 'Year',
					format: '0000'
				},
				{
					xtype: 'numbercolumn',
					dataIndex: 'week_month',
					text: 'Month',
					format: '00'
				},
				{
					xtype: 'numbercolumn',
					dataIndex: 'week_number',
					text: 'Week',
					format: '0'
				},
				{
					xtype: 'numbercolumn',
					dataIndex: 'motivation',
					text: 'Motivation',
					format: '00',
					editor: {
						xtype: 'numberfield',
						maxValue: 10,
						minValue: 6
					}
				},
				{
					xtype: 'numbercolumn',
					dataIndex: 'organization',
					text: 'Organization',
					format: '00',
					editor: {
						xtype: 'numberfield',
						minValue: 0
					}
				},
				{
					xtype: 'numbercolumn',
					dataIndex: 'effectiveUseOfStudyTime',
					text: 'Effective Use Of Time',
					format: '00',
					editor: {
						xtype: 'numberfield',
						maxValue: 10,
						minValue: 6
					}
				},
				{
					xtype: 'numbercolumn',
					dataIndex: 'qualityOfWork',
					text: 'Quality Of Work',
					format: '00',
					editor: {
						xtype: 'numberfield',
						maxValue: 10,
						minValue: 6
					}
				},
				{
					xtype: 'numbercolumn',
					dataIndex: 'accuracyOfWork',
					text: 'Accuracy Of Work',
					format: '00',
					editor: {
						xtype: 'numberfield',
						maxValue: 10,
						minValue: 6
					}
				},
				{
					xtype: 'numbercolumn',
					dataIndex: 'complexityOfWork',
					text: 'Complexity Of Work',
					format: '00',
					editor: {
						xtype: 'numberfield',
						maxValue: 10,
						minValue: 6
					}
				},
				{
					xtype: 'numbercolumn',
					dataIndex: 'growth',
					text: 'Growth',
					format: '00',
					editor: {
						xtype: 'numberfield',
						maxValue: 10,
						minValue: 6
					}
				},
				{
					xtype: 'numbercolumn',
					dataIndex: 'consistency',
					text: 'Consistency',
					format: '00',
					editor: {
						xtype: 'numberfield',
						maxValue: 10,
						minValue: 6
					}
				},
				{
					xtype: 'checkcolumn',
					dataIndex: 'locked',
					menuText: 'false',
					text: 'Locked?',
					editor: {
						xtype: 'checkboxfield'
					}
				},
				{
					xtype: 'numbercolumn',
					hidden: true,
					dataIndex: 'studentId',
					text: 'studentid',
					format: '000000'
				},
				{
					xtype: 'numbercolumn',
					hidden: true,
					dataIndex: 'subjId',
					text: 'idsubject'
				},
				{
					xtype: 'numbercolumn',
					hidden: true,
					dataIndex: 'weeklyevaluationId',
					text: 'id',
					format: '000000'
				},
				{
					xtype: 'numbercolumn',
					hidden: true,
					dataIndex: 'weeklyevaluationId',
					text: 'evaluationid',
					format: '000000'
				},
				{
					xtype: 'numbercolumn',
					dataIndex: 'version',
					text: 'version',
					format: '000000'
				},
				{
					xtype: 'gridcolumn',
					hidden: true,
					dataIndex: 'studentUserName',
					text: 'studentUserName'
				},
				{
					xtype: 'datecolumn',
					hidden: true,
					dataIndex: 'lastUpdated',
					text: 'lastUpdated',
					format: 'm/d/Y'
				},
				{
					xtype: 'gridcolumn',
					hidden: true,
					dataIndex: 'whoUpdated',
					text: 'whoUpdated'
				}
			],
			viewConfig: {
				height: 700,
				id: 'weeklyevaluationsubjectsgridview'
			},
			plugins: [
				Ext.create('Ext.grid.plugin.CellEditing', {
					clicksToEdit: 1
				})
			]
		});

		me.callParent(arguments);
	}

});