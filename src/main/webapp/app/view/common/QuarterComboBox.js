/*
 * File: app/view/common/QuarterComboBox.js
 *
 * This file was generated by Sencha Architect version 3.0.2.
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

Ext.define('MySchool.view.common.QuarterComboBox', {
    extend: 'Ext.form.field.ComboBox',
    alias: 'widget.commonquartercombobox',

    requires: [
        'Ext.view.BoundList',
        'Ext.XTemplate'
    ],

    itemId: 'common-quartersubject',
    fieldLabel: 'Choose Quarter Subject',
    name: 'comboquarter',
    allowOnlyWhitespace: false,
    emptyText: 'Select one',
    forceSelection: true,
    queryMode: 'local',
    store: 'subject.SubjectStore',
    valueField: 'id',

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            listConfig: {
                xtype: 'boundlist',
                tpl: [
                    '        \'<tpl for=".">\',',
                    '            \'<div class="x-boundlist-item">{qtrYear}/{qtrName} - {subjName}</div>\',',
                    '        \'</tpl>\'',
                    ''
                ],
                itemSelector: 'div',
                itemTpl: [
                    '{qtrYear}/{qtrName} - {subjName}'
                ]
            }
        });

        me.callParent(arguments);
    }

});