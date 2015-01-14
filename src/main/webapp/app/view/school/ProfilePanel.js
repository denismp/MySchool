/*
 * File: app/view/school/ProfilePanel.js
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

Ext.define('MySchool.view.school.ProfilePanel', {
	extend: 'Ext.panel.Panel',
	alias: 'widget.schoolprofilepanel',

	requires: [
		'MySchool.view.school.ProfileGridPanel',
		'MySchool.view.school.ProfileForm',
		'Ext.grid.Panel',
		'Ext.form.Panel',
		'Ext.panel.Tool'
	],

	itemId: 'schoolprofiledetailspanel',
	layout: 'fit',
	title: 'School Profiles Details',

	initComponent: function() {
		var me = this;

		Ext.applyIf(me, {
			dockedItems: [
				{
					xtype: 'schoolprofilegridpanel',
					dock: 'top'
				},
				{
					xtype: 'schoolprofileform',
					dock: 'bottom'
				}
			],
			tools: [
				{
					xtype: 'tool',
					itemId: 'schoolrefreshtool',
					tooltip: 'Refresh',
					type: 'refresh'
				},
				{
					xtype: 'tool',
					disabled: true,
					itemId: 'schoolsearchtool',
					tooltip: 'Search',
					type: 'search',
					listeners: {
						click: {
							fn: me.onSchoolsearchtoolClick,
							scope: me
						}
					}
				},
				{
					xtype: 'tool',
					itemId: 'schoolnewtool',
					tooltip: 'New',
					type: 'plus',
					listeners: {
						click: {
							fn: me.onSchoolnewtoolClick,
							scope: me
						}
					}
				},
				{
					xtype: 'tool',
					itemId: 'schoolsavetool',
					tooltip: 'Save',
					type: 'save'
				},
				{
					xtype: 'tool',
					disabled: true,
					itemId: 'schooldeletetool',
					tooltip: 'Delete',
					type: 'minus',
					listeners: {
						click: {
							fn: me.onSchooldeletetoolClick,
							scope: me
						}
					}
				},
				{
					xtype: 'tool',
					disabled: true,
					itemId: 'schoollocktool',
					tooltip: 'Lock',
					type: 'pin',
					listeners: {
						click: {
							fn: me.onSchoollocktoolClick,
							scope: me
						}
					}
				}
			]
		});

		me.callParent(arguments);
	},

	onSchoolsearchtoolClick: function(tool, e, eOpts) {

	},

	onSchoolnewtoolClick: function(tool, e, eOpts) {

	},

	onSchooldeletetoolClick: function(tool, e, eOpts) {

	},

	onSchoollocktoolClick: function(tool, e, eOpts) {

	}

});