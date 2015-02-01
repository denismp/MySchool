/*
 * File: app/view/subject/EditForm.js
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

Ext.define('MySchool.view.subject.EditForm', {
	extend: 'Ext.form.Panel',

	requires: [
		'Ext.form.field.TextArea',
		'Ext.button.Button'
	],

	draggable: true,
	floating: true,
	height: 413,
	itemId: 'subjecteditform',
	width: 825,
	bodyPadding: 10,
	title: 'Edit Subject',

	initComponent: function() {
		var me = this;

		Ext.applyIf(me, {
			items: [
				{
					xtype: 'textareafield',
					anchor: '100%',
					itemId: 'editsubjectdescription',
					fieldLabel: 'Description',
					name: 'subjDescription'
				},
				{
					xtype: 'textareafield',
					anchor: '100%',
					itemId: 'editsubjectobjectives',
					fieldLabel: 'Objectives',
					name: 'subjObjectives'
				},
				{
					xtype: 'button',
					itemId: 'editsubjectcancelbutton',
					text: 'Cancel'
				},
				{
					xtype: 'button',
					itemId: 'editsubjectsubmitbutton',
					text: 'Submit'
				}
			]
		});

		me.callParent(arguments);
	}

});