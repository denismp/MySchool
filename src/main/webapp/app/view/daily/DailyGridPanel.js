/*
 * File: app/view/daily/DailyGridPanel.js
 *
 * This file was generated by Sencha Architect version 3.2.0.
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

Ext.define('MySchool.view.daily.DailyGridPanel', {
	extend: 'Ext.grid.Panel',
	alias: 'widget.dailygridpanel',

	requires: [
		'Ext.grid.column.Number',
		'Ext.form.field.Number',
		'Ext.grid.column.CheckColumn',
		'Ext.form.field.Checkbox',
		'Ext.grid.View',
		'Ext.grid.plugin.CellEditing',
		'Ext.grid.column.Date'
	],

	itemId: 'dailygridpanel',
	autoScroll: true,
	title: '[student name] Daily Details',
	forceFit: true,
	store: 'daily.MyJsonStore',

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
					dataIndex: 'daily_year',
					text: 'Year',
					format: '0000'
				},
				{
					xtype: 'numbercolumn',
					dataIndex: 'daily_month',
					text: 'Month',
					format: '00'
				},
				{
					xtype: 'numbercolumn',
					dataIndex: 'daily_day',
					text: 'Day',
					format: '0'
				},
				{
					xtype: 'gridcolumn',
					dataIndex: 'daily_hours',
					text: 'Daily Hours',
					editor: {
						xtype: 'numberfield',
						allowOnlyWhitespace: false,
						maxValue: 24,
						minValue: 0
					}
				},
				{
					xtype: 'checkcolumn',
					hidden: true,
					dataIndex: 'locked',
					text: 'Locked?',
					editor: {
						xtype: 'checkboxfield'
					}
				},
				{
					xtype: 'numbercolumn',
					hidden: true,
					dataIndex: 'studentId',
					text: 'studentId',
					format: '000000'
				},
				{
					xtype: 'numbercolumn',
					hidden: true,
					dataIndex: 'dailyId',
					text: 'dailyId',
					format: '000000'
				},
				{
					xtype: 'numbercolumn',
					hidden: true,
					dataIndex: 'subjId',
					text: 'subjectId'
				},
				{
					xtype: 'gridcolumn',
					dataIndex: 'studentUserName',
					text: 'Student User Name'
				},
				{
					xtype: 'gridcolumn',
					dataIndex: 'facultyUserName',
					text: 'Faculty User Name'
				},
				{
					xtype: 'datecolumn',
					dataIndex: 'lastUpdated',
					text: 'Last Updated',
					format: 'm/d/Y'
				},
				{
					xtype: 'gridcolumn',
					dataIndex: 'whoUpdated',
					text: 'Who Updated'
				},
				{
					xtype: 'numbercolumn',
					hidden: true,
					dataIndex: 'version',
					text: 'version',
					format: '000000'
				}
			],
			viewConfig: {
				itemId: 'dailygridview'
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