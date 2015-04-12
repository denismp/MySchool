/*
 * File: app/view/common/MyTabPanel.js
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

Ext.define('MySchool.view.common.MyTabPanel', {
	extend: 'Ext.panel.Panel',
	alias: 'widget.commonmytabpanel',

	requires: [
		'Ext.form.Panel',
		'Ext.form.field.TextArea',
		'Ext.button.Button'
	],

	itemId: 'mypanel419',
	minWidth: 273,
	autoScroll: true,
	layout: 'fit',
	title: 'MyTapPanel',

	initComponent: function() {
		var me = this;

		Ext.applyIf(me, {
			dockedItems: [
				{
					xtype: 'form',
					dock: 'top',
					minHeight: 250,
					autoScroll: true,
					bodyPadding: 10,
					dockedItems: [
						{
							xtype: 'textareafield',
							dock: 'top',
							disabled: true,
							itemId: 'mytextbox',
							minHeight: 220,
							name: 'mytextbox'
						}
					],
					items: [
						{
							xtype: 'button',
							itemId: 'editmytabpanel',
							text: 'Edit',
							tooltip: 'Click edit to modify',
							tooltipType: 'title'
						}
					]
				}
			]
		});

		me.processCommonMyTabPanel(me);
		me.callParent(arguments);
	},

	processCommonMyTabPanel: function(config) {
		debugger;
		var itemId = this.getItemId();

		var dockedItems = config.dockedItems;
		var form		= dockedItems[0];
		//var button		= form.dockedItems[1];
		var button		= form.items[0];
		button.itemId	= 'edit' + itemId;
		var textbox		= form.dockedItems[0];
		textbox.itemId	= itemId + 'textbox';
		textbox.name	= textbox.itemId;

		return config;
	}

});