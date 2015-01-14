/*
 * File: app/view/monthly/evaluation/DetailsPanel.js
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

Ext.define('MySchool.view.monthly.evaluation.DetailsPanel', {
	extend: 'Ext.panel.Panel',
	alias: 'widget.monthlyevaluationdetailspanel',

	requires: [
		'MySchool.view.monthly.evaluation.GridPanel',
		'MySchool.view.monthly.evaluation.TabPanel',
		'MySchool.view.monthly.evaluation.RefreshTool',
		'MySchool.view.monthly.evaluation.SearchTool',
		'MySchool.view.monthly.evaluation.NewTool',
		'MySchool.view.monthly.evaluation.SaveTool',
		'MySchool.view.monthly.evaluation.DeleteTool',
		'MySchool.view.monthly.evaluation.LockTool',
		'Ext.grid.Panel',
		'Ext.tab.Panel',
		'Ext.panel.Tool'
	],

	height: 673,
	itemId: 'monthlyevaluatondetailspanel',
	title: 'Monthly Evaluation Details',

	initComponent: function() {
		var me = this;

		Ext.applyIf(me, {
			dockedItems: [
				{
					xtype: 'monthlyevaluationgridpanel',
					height: 334,
					dock: 'top'
				},
				{
					xtype: 'monthlyevaluationtabpanel',
					dock: 'bottom'
				}
			],
			tools: [
				{
					xtype: 'monthlyevaluationrefreshtool'
				},
				{
					xtype: 'monthlyevaluationsearchtool',
					disabled: true
				},
				{
					xtype: 'monthlyevaluationnewtool'
				},
				{
					xtype: 'monthlyevaluationsavetool'
				},
				{
					xtype: 'monthlyevaluationdeletetool'
				},
				{
					xtype: 'monthlyevaluationlocktool'
				}
			]
		});

		me.callParent(arguments);
	}

});