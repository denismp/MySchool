/*
 * File: app/view/student/NewDialog.js
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

Ext.define('MySchool.view.student.NewDialog', {
	extend: 'Ext.window.Window',
	alias: 'widget.studentnewdialog',

	requires: [
		'MySchool.view.subject.FacultyNamesComboBox',
		'Ext.form.Panel',
		'Ext.form.FieldSet',
		'Ext.form.field.ComboBox',
		'Ext.form.field.Date',
		'Ext.button.Button'
	],

	draggable: false,
	height: 597,
	itemId: 'studentnewdialog',
	width: 390,
	title: 'New Student',

	initComponent: function() {
		var me = this;

		Ext.applyIf(me, {
			dockedItems: [
				{
					xtype: 'form',
					dock: 'top',
					frame: true,
					height: 553,
					itemId: 'studentnewform',
					width: 378,
					bodyPadding: 10,
					dockedItems: [
						{
							xtype: 'fieldset',
							dock: 'top',
							height: 502,
							itemId: 'studentnewformfieldset',
							padding: 5,
							items: [
								{
									xtype: 'subjectfacultynamescombobox',
									anchor: '100%'
								},
								{
									xtype: 'combobox',
									anchor: '100%',
									itemId: 'studentnewform_schoolcombobox',
									fieldLabel: 'School',
									name: 'schoolName',
									emptyText: 'Select one.',
									displayField: 'name',
									forceSelection: true,
									store: 'subject.SchoolsStore',
									valueField: 'name'
								},
								{
									xtype: 'textfield',
									anchor: '100%',
									itemId: 'studentnewform_firstname',
									fieldLabel: 'First Name',
									name: 'firstname'
								},
								{
									xtype: 'textfield',
									anchor: '100%',
									itemId: 'studentnewform_middlename',
									fieldLabel: 'Middle',
									name: 'middlename'
								},
								{
									xtype: 'textfield',
									anchor: '100%',
									itemId: 'studentnewform_lastname',
									fieldLabel: 'Last Name',
									name: 'lastname'
								},
								{
									xtype: 'datefield',
									anchor: '100%',
									itemId: 'studentnewform_dob',
									fieldLabel: 'DOB',
									name: 'dob'
								},
								{
									xtype: 'textfield',
									anchor: '100%',
									itemId: 'studentnewform_phone1',
									fieldLabel: 'Phone1',
									name: 'phone1'
								},
								{
									xtype: 'textfield',
									anchor: '100%',
									itemId: 'studentnewform_phone2',
									fieldLabel: 'Phone2',
									name: 'phone2'
								},
								{
									xtype: 'textfield',
									anchor: '100%',
									itemId: 'studentnewform_address1',
									fieldLabel: 'Address1',
									name: 'address1'
								},
								{
									xtype: 'textfield',
									anchor: '100%',
									itemId: 'studentnewform_address2',
									fieldLabel: 'Address2',
									name: 'address2'
								},
								{
									xtype: 'textfield',
									anchor: '100%',
									itemId: 'studentnewform_city',
									fieldLabel: 'City',
									name: 'city'
								},
								{
									xtype: 'textfield',
									anchor: '100%',
									itemId: 'studentnewform_state',
									fieldLabel: 'State/Province',
									name: 'state'
								},
								{
									xtype: 'textfield',
									anchor: '100%',
									itemId: 'studentnewform_postalcode',
									fieldLabel: 'Postal Code',
									name: 'postalcode'
								},
								{
									xtype: 'textfield',
									anchor: '100%',
									itemId: 'studentnewform_country',
									fieldLabel: 'Country',
									name: 'country'
								},
								{
									xtype: 'textfield',
									anchor: '100%',
									itemId: 'studentnewform_email',
									fieldLabel: 'Email',
									name: 'email'
								},
								{
									xtype: 'textfield',
									anchor: '100%',
									itemId: 'studentnewform_username',
									fieldLabel: 'User Name',
									name: 'username'
								},
								{
									xtype: 'textfield',
									anchor: '100%',
									itemId: 'studentnewform_password',
									fieldLabel: 'Password',
									name: 'password'
								}
							]
						}
					],
					items: [
						{
							xtype: 'button',
							itemId: 'studentcancel',
							text: 'Cancel'
						},
						{
							xtype: 'button',
							itemId: 'studentsubmit',
							text: 'Submit'
						}
					]
				}
			]
		});

		me.callParent(arguments);
	}

});