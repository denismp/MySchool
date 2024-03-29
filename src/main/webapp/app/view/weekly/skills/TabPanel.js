/*
 * File: app/view/weekly/skills/TabPanel.js
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

Ext.define('MySchool.view.weekly.skills.TabPanel', {
	extend: 'Ext.panel.Panel',
	alias: 'widget.weeklydetailspanel',

	requires: [
		'MySchool.view.weekly.skills.GridPanel',
		'MySchool.view.weekly.skills.DetailsTabPanel',
		'MySchool.view.weekly.skills.RefreshTool',
		'MySchool.view.weekly.skills.SearchTool',
		'MySchool.view.weekly.skills.NewTool',
		'MySchool.view.weekly.skills.SaveTool',
		'MySchool.view.weekly.skills.DeleteTool',
		'MySchool.view.weekly.skills.LockTool',
		'Ext.grid.Panel',
		'Ext.tab.Panel',
		'Ext.panel.Tool'
	],

	height: 673,
	itemId: 'weeklydetailspanel',
	width: 1398,
	title: 'Weekly Details',

	initComponent: function() {
		var me = this;

		Ext.applyIf(me, {
			dockedItems: [
				{
					xtype: 'weeklyskillsgridpanel',
					height: 334,
					itemId: 'weeklyskillsgridpanel',
					minHeight: 150,
					dock: 'top'
				},
				{
					xtype: 'weeklyskillsdetailstabpanel',
					itemId: 'weeklyskillsdetailstabpanel',
					dock: 'bottom'
				}
			],
			tools: [
				{
					xtype: 'weeklyskillsrefreshtool'
				},
				{
					xtype: 'weeklyskillssearchtool',
					disabled: true
				},
				{
					xtype: 'weeklyskillsnewtool'
				},
				{
					xtype: 'weeklyskillssavetool'
				},
				{
					xtype: 'weeklyskillsdeletetool'
				},
				{
					xtype: 'weeklyskillslocktool'
				}
			]
		});

		me.callParent(arguments);
	}

});