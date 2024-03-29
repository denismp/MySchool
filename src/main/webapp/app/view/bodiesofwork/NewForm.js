/*
 * File: app/view/bodiesofwork/NewForm.js
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

Ext.define('MySchool.view.bodiesofwork.NewForm', {
	extend: 'Ext.form.Panel',
	alias: 'widget.newbodiesofworkform',

	requires: [
		'MySchool.view.common.QuarterSubjectComboBox',
		'Ext.form.FieldSet',
		'Ext.form.field.ComboBox',
		'Ext.form.field.TextArea',
		'Ext.form.field.Hidden',
		'Ext.form.field.Number',
		'Ext.button.Button'
	],

	draggable: true,
	floating: true,
	frame: true,
	itemId: 'newbodiesofworkform',
	width: 500,
	title: 'New Body of work for Student',
	jsonSubmit: true,

	initComponent: function() {
		var me = this;

		me.initialConfig = Ext.apply({
			jsonSubmit: true
		}, me.initialConfig);

		Ext.applyIf(me, {
			items: [
				{
					xtype: 'fieldset',
					title: 'Bodies of work fields',
					items: [
						{
							xtype: 'combobox',
							anchor: '100%',
							itemId: 'newbodiesofworkform-studentName',
							fieldLabel: 'Student User Name',
							name: 'studentUserName',
							readOnly: true,
							emptyText: 'Select one.',
							displayField: 'userName',
							forceSelection: true,
							store: 'student.StudentStore',
							valueField: 'userName'
						},
						{
							xtype: 'textfield',
							anchor: '100%',
							itemId: 'newbodiesofworkform-workName',
							fieldLabel: 'Work Name',
							name: 'workName',
							allowOnlyWhitespace: false
						},
						{
							xtype: 'commonquartersubjectcombobox',
							itemId: 'newbodiesofworkform-quarter',
							anchor: '100%',
							listeners: {
								boxready: {
									fn: me.bodyofworkscomboboxready,
									scope: me
								}
							}
						},
						{
							xtype: 'textareafield',
							anchor: '100%',
							itemId: 'newbodiesofworkform-what',
							fieldLabel: 'What',
							name: 'what',
							allowOnlyWhitespace: false
						},
						{
							xtype: 'textareafield',
							anchor: '100%',
							itemId: 'newbodiesofworkform-description',
							fieldLabel: 'Description',
							name: 'description',
							allowOnlyWhitespace: false
						},
						{
							xtype: 'textareafield',
							anchor: '100%',
							itemId: 'newbodiesofworkform-objective',
							fieldLabel: 'Objective',
							name: 'objective',
							allowOnlyWhitespace: false
						},
						{
							xtype: 'hiddenfield',
							anchor: '100%',
							itemId: 'newbodiesofwork-qtrname',
							fieldLabel: 'qtrName',
							name: 'qtrName'
						},
						{
							xtype: 'numberfield',
							anchor: '100%',
							hidden: true,
							itemId: 'newbodiesofwork-qtryear',
							fieldLabel: 'Label',
							name: 'qtrYear'
						},
						{
							xtype: 'hiddenfield',
							anchor: '100%',
							itemId: 'newbodiesofwork-locked',
							fieldLabel: 'Label',
							name: 'locked'
						},
						{
							xtype: 'hiddenfield',
							anchor: '100%',
							itemId: 'newbodiesofwork-whoupdated',
							fieldLabel: 'Label',
							name: 'whoUpdated'
						},
						{
							xtype: 'hiddenfield',
							anchor: '100%',
							itemId: 'newbodiesofwork-lastupdated',
							fieldLabel: 'Label',
							name: 'lastUpdated'
						},
						{
							xtype: 'hiddenfield',
							anchor: '100%',
							itemId: 'newbodiesofwork-subjectgradelevel',
							fieldLabel: 'Label',
							name: 'subjGradeLevel'
						},
						{
							xtype: 'hiddenfield',
							anchor: '100%',
							itemId: 'newbodiesofwork-subjectcredithours',
							fieldLabel: 'Label',
							name: 'subjCreditHours'
						},
						{
							xtype: 'numberfield',
							anchor: '100%',
							hidden: true,
							itemId: 'newbodiesofwork-subjectid',
							fieldLabel: 'Label',
							name: 'subjId'
						},
						{
							xtype: 'numberfield',
							anchor: '100%',
							hidden: true,
							itemId: 'newbodiesofwork-qtrid',
							fieldLabel: 'Label',
							name: 'qtrId'
						},
						{
							xtype: 'numberfield',
							anchor: '100%',
							hidden: true,
							itemId: 'newbodiesofwork-studentid',
							fieldLabel: 'Label',
							name: 'studentId'
						},
						{
							xtype: 'numberfield',
							anchor: '100%',
							hidden: true,
							itemId: 'newbodiesofwork-id',
							fieldLabel: 'Label',
							name: 'id'
						},
						{
							xtype: 'numberfield',
							anchor: '100%',
							hidden: true,
							itemId: 'newbodiesofwork-version',
							fieldLabel: 'Label',
							name: 'version'
						},
						{
							xtype: 'hiddenfield',
							anchor: '100%',
							itemId: 'newbodiesofwork-subjectname',
							fieldLabel: 'Label',
							name: 'subjName'
						}
					]
				},
				{
					xtype: 'button',
					itemId: 'newbodiesofworkcancel',
					text: 'Cancel'
				},
				{
					xtype: 'button',
					itemId: 'newbodiesofworksubmit',
					text: 'Submit'
				},
				{
					xtype: 'button',
					disabled: true,
					hidden: true,
					itemId: 'newbodiesofworkcreate',
					text: 'Create'
				}
			]
		});

		me.callParent(arguments);
	},

	bodyofworkscomboboxready: function(component, width, height, eOpts) {
		var myStore = component.getStore();
		myStore.reload();
	}

});