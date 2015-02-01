/*
 * File: app/view/monthly/evaluation/GridPanel.js
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

Ext.define('MySchool.view.monthly.evaluation.GridPanel', {
	extend: 'Ext.grid.Panel',
	alias: 'widget.monthlyevaluationgridpanel',

	requires: [
		'Ext.grid.column.Number',
		'Ext.grid.View',
		'Ext.form.field.Number',
		'Ext.grid.column.CheckColumn',
		'Ext.form.field.Checkbox',
		'Ext.grid.column.Date',
		'Ext.grid.plugin.CellEditing'
	],

	itemId: 'monthlyevaluationgridpanel',
	width: 150,
	autoScroll: true,
	title: '[student name]',
	forceFit: true,
	store: 'monthly.EvaluationRatingsStore',

	initComponent: function() {
		var me = this;

		Ext.applyIf(me, {
			columns: [
				{
					xtype: 'gridcolumn',
					dataIndex: 'subjName',
					text: 'Subj Name'
				},
				{
					xtype: 'gridcolumn',
					dataIndex: 'qtrName',
					text: 'Term'
				},
				{
					xtype: 'numbercolumn',
					dataIndex: 'qtrYear',
					text: 'Year',
					format: '0000'
				},
				{
					xtype: 'numbercolumn',
					dataIndex: 'month_number',
					text: 'Month',
					format: '00'
				},
				{
					xtype: 'numbercolumn',
					dataIndex: 'levelOfDifficulty',
					text: 'LevelOfDifficulty',
					format: '00',
					editor: {
						xtype: 'numberfield',
						maxValue: 10,
						minValue: 5
					}
				},
				{
					xtype: 'numbercolumn',
					dataIndex: 'criticalThinkingSkills',
					text: 'CriticalThinking',
					format: '00',
					editor: {
						xtype: 'numberfield',
						maxValue: 10,
						minValue: 5
					}
				},
				{
					xtype: 'numbercolumn',
					dataIndex: 'effectiveCorrectionActions',
					text: 'CorrectiveActions',
					format: '00',
					editor: {
						xtype: 'numberfield',
						maxValue: 10,
						minValue: 5
					}
				},
				{
					xtype: 'numbercolumn',
					dataIndex: 'accuratelyIdCorrections',
					text: 'AccuratelyIDsCorrections',
					format: '00',
					editor: {
						xtype: 'numberfield',
						maxValue: 10,
						minValue: 5
					}
				},
				{
					xtype: 'numbercolumn',
					dataIndex: 'completingCourseObjectives',
					text: 'CompletesCourseObjectives',
					format: '00',
					editor: {
						xtype: 'numberfield',
						maxValue: 10,
						minValue: 5
					}
				},
				{
					xtype: 'numbercolumn',
					dataIndex: 'thoughtfulnessOfReflections',
					text: 'ThoughtfulnessOfReflections',
					format: '00',
					editor: {
						xtype: 'numberfield',
						maxValue: 10,
						minValue: 5
					}
				},
				{
					xtype: 'numbercolumn',
					dataIndex: 'responsibilityOfProgress',
					text: 'ResponsibilityOfProgress',
					format: '00',
					editor: {
						xtype: 'numberfield',
						maxValue: 10,
						minValue: 5
					}
				},
				{
					xtype: 'numbercolumn',
					dataIndex: 'workingEffectivelyWithAdvisor',
					text: 'WorksEffWithAdvisor',
					format: '00',
					editor: {
						xtype: 'numberfield',
						maxValue: 10,
						minValue: 5
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
					xtype: 'gridcolumn',
					dataIndex: 'whoUpdated',
					text: 'Who Updated'
				},
				{
					xtype: 'datecolumn',
					hidden: true,
					dataIndex: 'lastUpdated',
					text: 'Last Updated',
					format: 'm/d/Y'
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
					dataIndex: 'qtrId',
					text: 'termId',
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
					dataIndex: 'studentId',
					text: 'studentId',
					format: '000000'
				},
				{
					xtype: 'gridcolumn',
					hidden: true,
					dataIndex: 'id',
					text: 'id'
				}
			],
			viewConfig: {
				itemId: 'monthlevaluationsgridview'
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