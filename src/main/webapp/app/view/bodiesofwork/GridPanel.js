/*
 * File: app/view/bodiesofwork/GridPanel.js
 *
 * This file was generated by Sencha Architect version 3.1.0.
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

Ext.define('MySchool.view.bodiesofwork.GridPanel', {
	extend: 'Ext.grid.Panel',
	alias: 'widget.mybodiesofworkgridpanel',

	requires: [
		'Ext.grid.column.Number',
		'Ext.grid.column.Date',
		'Ext.grid.column.CheckColumn',
		'Ext.form.field.Checkbox',
		'Ext.grid.View'
	],

	itemId: 'bodiesofworkssubjectsgrid',
	autoScroll: true,
	title: '[student name] Bodies Of Work',
	forceFit: true,
	store: 'bodiesofwork.MyJsonStore',

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
					dataIndex: 'subjGradeLevel',
					text: 'Grade Level',
					format: '00.00'
				},
				{
					xtype: 'numbercolumn',
					dataIndex: 'subjCreditHours',
					text: 'Credit Hours',
					format: '00.00'
				},
				{
					xtype: 'gridcolumn',
					dataIndex: 'qtrName',
					text: 'Term'
				},
				{
					xtype: 'gridcolumn',
					dataIndex: 'qtrYear',
					text: 'Year'
				},
				{
					xtype: 'gridcolumn',
					dataIndex: 'workName',
					text: 'Body Of Work'
				},
				{
					xtype: 'datecolumn',
					dataIndex: 'lastUpdated',
					text: 'Last Update'
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
					xtype: 'gridcolumn',
					dataIndex: 'studentUserName',
					text: 'Student Name'
				},
				{
					xtype: 'gridcolumn',
					dataIndex: 'facultyUserName',
					text: 'Faculty Name'
				},
				{
					xtype: 'gridcolumn',
					dataIndex: 'whoUpdated',
					text: 'Who Updated'
				},
				{
					xtype: 'numbercolumn',
					hidden: true,
					dataIndex: 'id',
					text: 'id',
					format: '000000'
				},
				{
					xtype: 'numbercolumn',
					hidden: true,
					dataIndex: 'version',
					text: 'version',
					format: '000000'
				},
				{
					xtype: 'numbercolumn',
					hidden: true,
					dataIndex: 'subjId',
					text: 'subjId',
					format: '000000'
				},
				{
					xtype: 'numbercolumn',
					hidden: true,
					dataIndex: 'qtrId',
					text: 'termId',
					format: '000000'
				}
			],
			viewConfig: {
				itemId: 'bodiesofworksubjectsview'
			}
		});

		me.callParent(arguments);
	}

});