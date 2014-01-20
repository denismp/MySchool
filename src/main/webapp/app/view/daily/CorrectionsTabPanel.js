/*
 * File: app/view/daily/CorrectionsTabPanel.js
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

Ext.define('MySchool.view.daily.CorrectionsTabPanel', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.dailycorrectionstabpanel',

    requires: [
        'Ext.form.Panel',
        'Ext.form.field.TextArea',
        'Ext.toolbar.Toolbar',
        'Ext.button.Button'
    ],

    itemId: 'dailydetailscorrectiontab',
    title: 'Correction',

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            dockedItems: [
                {
                    xtype: 'form',
                    dock: 'top',
                    layout: 'fit',
                    bodyPadding: 10,
                    dockedItems: [
                        {
                            xtype: 'textareafield',
                            dock: 'top',
                            disabled: true,
                            itemId: 'dailydetailscorrectiontextarea',
                            minHeight: 273
                        }
                    ]
                },
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
                            itemId: 'editdailydetailscorrectiontab',
                            text: 'Edit'
                        }
                    ]
                }
            ]
        });

        me.callParent(arguments);
    }

});