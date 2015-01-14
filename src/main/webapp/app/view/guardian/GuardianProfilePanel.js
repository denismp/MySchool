/*
 * File: app/view/guardian/GuardianProfilePanel.js
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

Ext.define('MySchool.view.guardian.GuardianProfilePanel', {
	extend: 'Ext.panel.Panel',
	alias: 'widget.guardianguardianprofilepanel',

	requires: [
		'MySchool.view.guardian.ProfileGridGuardianOnlyPanel',
		'MySchool.view.guardian.GuardianOnlyProfileForm',
		'Ext.grid.Panel',
		'Ext.form.Panel',
		'Ext.panel.Tool'
	],

	itemId: 'guardianonlyprofilepanel',
	layout: 'fit',
	title: 'Guardian Profile Details',

	initComponent: function() {
		var me = this;

		Ext.applyIf(me, {
			dockedItems: [
				{
					xtype: 'guardianprofilegridguardianonlypanel',
					dock: 'top'
				},
				{
					xtype: 'guardianguardianonlyprofileform',
					dock: 'bottom'
				}
			],
			tools: [
				{
					xtype: 'tool',
					itemId: 'onlyguardianrefreshtool',
					tooltip: 'Refresh',
					type: 'refresh'
				},
				{
					xtype: 'tool',
					disabled: true,
					itemId: 'onlyguardiansearchtool',
					tooltip: 'Search',
					type: 'search'
				},
				{
					xtype: 'tool',
					itemId: 'onlyguardiannewtool',
					tooltip: 'New',
					type: 'plus'
				},
				{
					xtype: 'tool',
					itemId: 'onlyfguardiansavetool',
					tooltip: 'Save',
					type: 'save'
				},
				{
					xtype: 'tool',
					disabled: true,
					itemId: 'onlyguardiandeletetool',
					tooltip: 'Delete',
					type: 'minus'
				},
				{
					xtype: 'tool',
					disabled: true,
					itemId: 'onlyguardianlocktool',
					type: 'pin'
				}
			]
		});

		me.callParent(arguments);
	}

});