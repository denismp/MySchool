/*
 * File: app/view/subject/QuarterYearComboBox.js
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

Ext.define('MySchool.view.subject.QuarterYearComboBox', {
	extend: 'Ext.form.field.ComboBox',
	alias: 'widget.quarteryearcombobox',

	itemId: 'quarteryearcombobox',
	fieldLabel: 'Quarter Year',
	emptyText: 'Select one.',
	displayField: 'name',
	forceSelection: true,
	queryMode: 'local',
	store: 'subject.QuarterYearStore',
	valueField: 'value',

	initComponent: function() {
		var me = this;

		me.callParent(arguments);
	}

});