/*
 * File: app/view/bodiesofwork/BodiesOfWorkPanel.js
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

Ext.define('MySchool.view.bodiesofwork.BodiesOfWorkPanel', {
	extend: 'Ext.panel.Panel',
	alias: 'widget.mybodiesofworkpanel',

	requires: [
		'MySchool.view.bodiesofwork.GridPanel',
		'MySchool.view.bodiesofwork.DetailsTabPanel',
		'MySchool.view.bodiesofwork.RefreshTool',
		'MySchool.view.bodiesofwork.SearchTool',
		'MySchool.view.bodiesofwork.NewTool',
		'MySchool.view.bodiesofwork.SaveTool',
		'MySchool.view.bodiesofwork.DeleteTool',
		'MySchool.view.bodiesofwork.LockTool',
		'Ext.grid.Panel',
		'Ext.tab.Panel',
		'Ext.panel.Tool'
	],

	height: 673,
	id: 'bodiesofworkpanel',
	title: 'Bodies Of Work By Student And Subject',

	initComponent: function() {
		var me = this;

		Ext.applyIf(me, {
			dockedItems: [
				{
					xtype: 'mybodiesofworkgridpanel',
					height: 334,
					dock: 'top'
				},
				{
					xtype: 'bodiesofworkdetailstabpanel',
					dock: 'bottom'
				}
			],
			tools: [
				{
					xtype: 'bodiesofworkrefreshtool'
				},
				{
					xtype: 'bodiesofworksearchtool',
					disabled: true
				},
				{
					xtype: 'bodiesofworknewtool'
				},
				{
					xtype: 'bodiesofworksavetool'
				},
				{
					xtype: 'bodiesofworkdeletetool'
				},
				{
					xtype: 'bodiesofworklocktool'
				}
			]
		});

		me.callParent(arguments);
	}

});