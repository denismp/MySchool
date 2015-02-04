/*
 * File: app/view/guardian/AddChildDialog.js
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

Ext.define('MySchool.view.guardian.AddChildDialog', {
	extend: 'Ext.window.Window',

	requires: [
		'Ext.form.Panel',
		'Ext.form.FieldSet',
		'Ext.form.field.Text',
		'Ext.button.Button'
	],

	height: 151,
	itemId: 'guardianaddchilddialog',
	width: 390,
	title: 'Associate Student To Guardian',

	initComponent: function() {
		var me = this;

		Ext.applyIf(me, {
			dockedItems: [
				{
					xtype: 'form',
					dock: 'top',
					height: 112,
					itemId: 'gaurdianaddchildform',
					width: 390,
					bodyPadding: 10,
					dockedItems: [
						{
							xtype: 'fieldset',
							dock: 'top',
							height: 51,
							itemId: 'guardiannaddchildformfieldset',
							padding: 5,
							items: [
								{
									xtype: 'textfield',
									anchor: '100%',
									itemId: 'guardianaddchildform_username',
									fieldLabel: 'Student User Name',
									name: 'username',
									listeners: {
										boxready: {
											fn: me.onGuardianaddchildform_usernameBoxReady,
											scope: me
										}
									}
								}
							]
						}
					],
					items: [
						{
							xtype: 'button',
							itemId: 'guardianaddchildcancel',
							text: 'Cancel'
						},
						{
							xtype: 'button',
							itemId: 'guardianaddchildsubmit',
							text: 'Submit'
						}
					]
				}
			]
		});

		me.callParent(arguments);
	},

	onGuardianaddchildform_usernameBoxReady: function(component, width, height, eOpts) {
		component.focus(false, 200);
	}

});