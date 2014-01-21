/*
 * File: app/view/monthly/summary/FeelingTabPanel.js
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

Ext.define('MySchool.view.monthly.summary.FeelingTabPanel', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.monthlyfeelingstabpanel',

    requires: [
        'Ext.toolbar.Toolbar',
        'Ext.button.Button',
        'Ext.form.Panel',
        'Ext.form.field.TextArea'
    ],

    itemId: 'monthlyfeelingstabpanel',
    layout: 'fit',
    title: 'Feelings',

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            dockedItems: [
                {
                    xtype: 'toolbar',
                    dock: 'bottom',
                    ui: 'footer',
                    items: [
                        {
                            xtype: 'component',
                            flex: 1
                        },
                        {
                            xtype: 'button',
                            itemId: 'editmonthlyfeelingstabpanel',
                            text: 'Edit',
                            tooltip: 'Click edit to modify'
                        }
                    ]
                },
                {
                    xtype: 'form',
                    dock: 'top',
                    minHeight: 273,
                    autoScroll: true,
                    layout: 'fit',
                    bodyPadding: 10,
                    dockedItems: [
                        {
                            xtype: 'textareafield',
                            dock: 'top',
                            disabled: true,
                            itemId: 'feelingstextbox',
                            minHeight: 273
                        }
                    ]
                }
            ]
        });

        me.callParent(arguments);
    }

});