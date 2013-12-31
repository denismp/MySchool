/*
 * File: app/view/daily/DetailsTabPanel.js
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

Ext.define('MySchool.view.daily.DetailsTabPanel', {
    extend: 'Ext.tab.Panel',
    alias: 'widget.dailydetailstabpanel',

    requires: [
        'Ext.tab.Tab',
        'Ext.form.Panel',
        'Ext.form.field.Number',
        'Ext.form.field.TextArea'
    ],

    height: 300,
    itemId: 'bottomdailytabpanel',
    minHeight: 300,
    activeTab: 0,

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'panel',
                    height: 100,
                    itemId: 'dailyhourstab',
                    title: 'Hours',
                    dockedItems: [
                        {
                            xtype: 'form',
                            dock: 'top',
                            id: 'hours',
                            minHeight: 300,
                            autoScroll: true,
                            layout: 'auto',
                            bodyPadding: 10,
                            items: [
                                {
                                    xtype: 'numberfield',
                                    id: 'dailydetailsformhournumberfield',
                                    labelWidth: 50
                                }
                            ]
                        }
                    ]
                },
                {
                    xtype: 'panel',
                    itemId: 'dailyresourcesusedtab',
                    title: 'Resources Used',
                    dockedItems: [
                        {
                            xtype: 'textareafield',
                            dock: 'top',
                            id: 'dailydetailsresoucesusedtextarea',
                            minHeight: 273
                        }
                    ]
                },
                {
                    xtype: 'panel',
                    itemId: 'dailystudydetailstab',
                    minHeight: 300,
                    title: 'Study Details',
                    dockedItems: [
                        {
                            xtype: 'textareafield',
                            dock: 'top',
                            id: 'dailydetailsstudydetailstextarea',
                            minHeight: 273
                        }
                    ]
                },
                {
                    xtype: 'panel',
                    itemId: 'dailydetailsevaluationtab',
                    minHeight: 300,
                    layout: 'fit',
                    bodyPadding: 10,
                    title: 'Evaluation',
                    dockedItems: [
                        {
                            xtype: 'textareafield',
                            dock: 'top',
                            id: 'dailydetailsevaluationtextarea',
                            minHeight: 273
                        }
                    ]
                },
                {
                    xtype: 'panel',
                    itemId: 'dailydetailscorrectiontab',
                    title: 'Correction',
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
                                    id: 'dailydetailscorrectiontextarea',
                                    minHeight: 273
                                }
                            ]
                        }
                    ]
                },
                {
                    xtype: 'panel',
                    itemId: 'dailydetailsactionstab',
                    title: 'Actions',
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
                                    id: 'dailydetailsactiontextarea',
                                    minHeight: 273
                                }
                            ]
                        }
                    ]
                }
            ]
        });

        me.callParent(arguments);
    }

});