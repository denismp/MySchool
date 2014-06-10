/*
 * File: app/view/daily/NewDailyForm.js
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

Ext.define('MySchool.view.daily.NewDailyForm', {
	extend: 'Ext.form.Panel',
	alias: 'widget.dailynewdailyform',

	requires: [
		'MySchool.view.common.QuarterSubjectComboBox',
		'MySchool.view.common.MonthComboBox',
		'Ext.form.FieldSet',
		'Ext.form.field.ComboBox',
		'Ext.form.field.Number',
		'Ext.form.field.Hidden',
		'Ext.button.Button'
	],

	draggable: true,
	floating: true,
	frame: true,
	itemId: 'dailynewformpanel',
	width: 500,
	bodyPadding: 10,
	title: 'New Daily Form',

	initComponent: function() {
		var me = this;

		Ext.applyIf(me, {
			items: [
				{
					xtype: 'fieldset',
					itemId: 'dailyfieldset',
					title: 'Daily Fields',
					items: [
						{
							xtype: 'commonquartersubjectcombobox',
							anchor: '100%'
						},
						{
							xtype: 'commonmonthcombobox',
							anchor: '100%'
						},
						{
							xtype: 'numberfield',
							anchor: '100%',
							itemId: 'daily-day',
							fieldLabel: 'day',
							name: 'daily_day',
							maxValue: 31,
							minValue: 1
						},
						{
							xtype: 'hiddenfield',
							anchor: '100%',
							itemId: 'daily-resourcesused',
							fieldLabel: 'resourcesUsed',
							name: 'resourcesUsed',
							value: 'resources used'
						},
						{
							xtype: 'hiddenfield',
							anchor: '100%',
							itemId: 'daily-studydetails',
							fieldLabel: 'studyDetails',
							name: 'studyDetails',
							value: 'study details'
						},
						{
							xtype: 'hiddenfield',
							anchor: '100%',
							itemId: 'daily-evaluation',
							fieldLabel: 'evaluation',
							name: 'evaluation',
							value: 'evaluation'
						},
						{
							xtype: 'hiddenfield',
							anchor: '100%',
							itemId: 'daily-correction',
							fieldLabel: 'correction',
							name: 'correction',
							value: 'corrections'
						},
						{
							xtype: 'hiddenfield',
							anchor: '100%',
							itemId: 'daily-dailyaction',
							fieldLabel: 'dailyAction',
							name: 'dailyAction',
							value: 'daily actions'
						},
						{
							xtype: 'hiddenfield',
							anchor: '100%',
							itemId: 'daily-comments',
							fieldLabel: 'comments',
							name: 'comments',
							value: 'comments'
						},
						{
							xtype: 'hiddenfield',
							anchor: '100%',
							itemId: 'daily-studentname',
							fieldLabel: 'studentname',
							name: 'studentUserName'
						},
						{
							xtype: 'hiddenfield',
							anchor: '100%',
							itemId: 'daily-subjectname',
							fieldLabel: 'subjectname',
							name: 'subjName'
						},
						{
							xtype: 'hiddenfield',
							anchor: '100%',
							itemId: 'daily-quarter',
							fieldLabel: 'quarter',
							name: 'qtrName'
						},
						{
							xtype: 'hiddenfield',
							anchor: '100%',
							itemId: 'daily-quarteryear',
							fieldLabel: 'year',
							name: 'daily_year'
						},
						{
							xtype: 'hiddenfield',
							anchor: '100%',
							itemId: 'daily-studentid',
							fieldLabel: 'studentid',
							name: 'studentId'
						},
						{
							xtype: 'hiddenfield',
							anchor: '100%',
							itemId: 'daily-subjectid',
							fieldLabel: 'subjectId',
							name: 'subjId'
						},
						{
							xtype: 'hiddenfield',
							anchor: '100%',
							itemId: 'daily-quarterid',
							fieldLabel: 'quarterid',
							name: 'qtrId'
						}
					]
				},
				{
					xtype: 'button',
					itemId: 'dailycancel',
					text: 'Canel'
				},
				{
					xtype: 'button',
					itemId: 'dailysubmit',
					text: 'Submit'
				}
			]
		});

		me.callParent(arguments);
	}

});