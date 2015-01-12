/*
 * File: app/view/subject/SubjectsGridPanel.js
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

Ext.define('MySchool.view.subject.SubjectsGridPanel', {
	extend: 'Ext.grid.Panel',
	alias: 'widget.subjectsgridpanel',

	requires: [
		'Ext.grid.column.Number',
		'Ext.grid.column.CheckColumn',
		'Ext.form.field.Checkbox',
		'Ext.grid.View',
		'Ext.grid.plugin.CellEditing',
		'Ext.form.field.Number',
		'Ext.grid.column.Date'
	],

	itemId: 'subjectsgrid',
	width: 1000,
	autoScroll: true,
	title: '[student name] Subjects',
	forceFit: true,
	store: 'subject.SubjectStore',

	initComponent: function() {
		var me = this;

		Ext.applyIf(me, {
			columns: [
				{
					xtype: 'numbercolumn',
					hidden: true,
					dataIndex: 'id',
					text: 'id',
					format: '000000'
				},
				{
					xtype: 'numbercolumn',
					dataIndex: 'subjId',
					text: 'subjId',
					format: '000000'
				},
				{
					xtype: 'numbercolumn',
					hidden: true,
					dataIndex: 'qtrId',
					text: 'qtrId',
					format: '000000'
				},
				{
					xtype: 'numbercolumn',
					hidden: true,
					dataIndex: 'schoolId',
					text: 'schoolId',
					format: '000000'
				},
				{
					xtype: 'gridcolumn',
					hidden: true,
					dataIndex: 'subjVersion',
					text: 'subjVersion'
				},
				{
					xtype: 'gridcolumn',
					hidden: true,
					dataIndex: 'qtrVersion',
					text: 'termVersion'
				},
				{
					xtype: 'gridcolumn',
					dataIndex: 'subjName',
					text: 'Subject Name'
				},
				{
					xtype: 'gridcolumn',
					dataIndex: 'schoolName',
					text: 'School Name',
					editor: {
						xtype: 'textfield'
					}
				},
				{
					xtype: 'numbercolumn',
					dataIndex: 'subjGradeLevel',
					text: 'Grade Level',
					format: '00'
				},
				{
					xtype: 'numbercolumn',
					dataIndex: 'subjCreditHours',
					text: 'Credit Hours'
				},
				{
					xtype: 'checkcolumn',
					hidden: true,
					dataIndex: 'qtrCompleted',
					text: 'Complete?',
					editor: {
						xtype: 'checkboxfield'
					}
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
					dataIndex: 'studentName',
					text: 'Student User Name'
				},
				{
					xtype: 'gridcolumn',
					dataIndex: 'facultyUserName',
					text: 'Faculty User Name'
				},
				{
					xtype: 'gridcolumn',
					hidden: true,
					dataIndex: 'qtrGrade',
					text: 'Grade',
					editor: {
						xtype: 'numberfield',
						allowOnlyWhitespace: false,
						maxValue: 4,
						minValue: 0
					}
				},
				{
					xtype: 'gridcolumn',
					hidden: true,
					dataIndex: 'qtrGradeType',
					text: 'GradeType',
					editor: {
						xtype: 'numberfield',
						allowOnlyWhitespace: false,
						maxValue: 2,
						minValue: 1
					}
				},
				{
					xtype: 'checkcolumn',
					hidden: true,
					dataIndex: 'qtrLocked',
					text: 'Locked?',
					editor: {
						xtype: 'checkboxfield'
					}
				},
				{
					xtype: 'gridcolumn',
					hidden: true,
					dataIndex: 'qtrWhoUpdated',
					text: 'Who Updated'
				},
				{
					xtype: 'datecolumn',
					hidden: true,
					dataIndex: 'qtrLastUpdated',
					text: 'Last Updated'
				}
			],
			viewConfig: {
				id: 'subjectsgridview'
			},
			plugins: [
				Ext.create('Ext.grid.plugin.CellEditing', {

				})
			]
		});

		me.callParent(arguments);
	}

});