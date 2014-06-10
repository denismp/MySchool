/*
 * File: app/view/common/MonthComboBox.js
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

Ext.define('MySchool.view.common.MonthComboBox', {
	extend: 'Ext.form.field.ComboBox',
	alias: 'widget.commonmonthcombobox',

	itemId: 'common-month',
	fieldLabel: 'Month',
	name: 'combomonth',
	allowOnlyWhitespace: false,
	emptyText: 'Select one.',
	displayField: 'name',
	forceSelection: true,
	queryMode: 'local',
	store: 'common.MonthStore',
	valueField: 'id',

	initComponent: function() {
		var me = this;

		me.callParent(arguments);
	}

});