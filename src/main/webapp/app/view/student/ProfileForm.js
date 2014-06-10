/*
 * File: app/view/student/ProfileForm.js
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

Ext.define('MySchool.view.student.ProfileForm', {
	extend: 'Ext.form.Panel',
	alias: 'widget.studentprofileform',

	requires: [
		'Ext.form.field.Text',
		'Ext.button.Button'
	],

	itemId: 'studentprofileform',
	maxHeight: 450,
	minHeight: 450,
	autoScroll: true,
	bodyPadding: 10,
	collapseFirst: false,
	frameHeader: false,
	title: 'Student Profile Form',

	initComponent: function() {
		var me = this;

		Ext.applyIf(me, {
			items: [
				{
					xtype: 'textfield',
					itemId: 'student-firstname',
					width: 300,
					fieldLabel: 'First Name',
					name: 'firstName'
				},
				{
					xtype: 'textfield',
					itemId: 'student-middlename',
					width: 300,
					fieldLabel: 'Middle Name',
					name: 'middleName'
				},
				{
					xtype: 'textfield',
					itemId: 'student-lastname',
					width: 300,
					fieldLabel: 'Last Name',
					name: 'lastName'
				},
				{
					xtype: 'textfield',
					itemId: 'student-phone1',
					fieldLabel: 'Phone1',
					name: 'phone1'
				},
				{
					xtype: 'textfield',
					itemId: 'student-phone',
					fieldLabel: 'Phone2',
					name: 'phone2'
				},
				{
					xtype: 'textfield',
					itemId: 'student-address1',
					width: 300,
					fieldLabel: 'Address1',
					name: 'address1'
				},
				{
					xtype: 'textfield',
					itemId: 'student-address2',
					width: 300,
					fieldLabel: 'Address2',
					name: 'address2'
				},
				{
					xtype: 'textfield',
					itemId: 'student-city',
					width: 300,
					fieldLabel: 'City',
					name: 'city'
				},
				{
					xtype: 'textfield',
					itemId: 'student-province',
					width: 300,
					fieldLabel: 'Province/State',
					name: 'province'
				},
				{
					xtype: 'textfield',
					itemId: 'student-postalcode',
					fieldLabel: 'Postal Code',
					name: 'postalCode'
				},
				{
					xtype: 'textfield',
					itemId: 'student-country',
					fieldLabel: 'Country',
					name: 'country'
				},
				{
					xtype: 'textfield',
					itemId: 'student-email',
					width: 300,
					fieldLabel: 'Email',
					name: 'email'
				},
				{
					xtype: 'button',
					itemId: 'studentprofileformeditbutton',
					text: 'Edit'
				},
				{
					xtype: 'button',
					disabled: true,
					itemId: 'studentprofileformcancelbutton',
					text: 'Cancel'
				},
				{
					xtype: 'button',
					disabled: true,
					itemId: 'studentprofileformsavebutton',
					text: 'Save'
				}
			]
		});

		me.callParent(arguments);
	}

});