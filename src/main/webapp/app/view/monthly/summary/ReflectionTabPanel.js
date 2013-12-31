/*
 * File: app/view/monthly/summary/ReflectionTabPanel.js
 *
 * This file was generated by Sencha Architect version 3.0.1.
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

Ext.define('MySchool.view.monthly.summary.ReflectionTabPanel', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.monthlyreflectionstabpanel',

    requires: [
        'Ext.form.field.TextArea'
    ],

    itemId: 'monthlyrealizationstabpanel',
    layout: 'fit',
    title: 'Realizations',

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            dockedItems: [
                {
                    xtype: 'textareafield',
                    dock: 'top',
                    itemId: 'realizationstextbox',
                    minHeight: 320
                }
            ]
        });

        me.callParent(arguments);
    }

});