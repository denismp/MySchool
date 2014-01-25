/*
 * File: app/view/monthly/summary/ReflectionsTabPanel.js
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

Ext.define('MySchool.view.monthly.summary.ReflectionsTabPanel', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.monthlyrelectionstabpanel',

    requires: [
        'Ext.toolbar.Toolbar',
        'Ext.button.Button',
        'Ext.form.Panel',
        'Ext.form.field.TextArea'
    ],

    itemId: 'monthlyreflectiontabpanel',
    layout: 'fit',
    title: 'Reflections',

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
                            itemId: 'editmonthlyreflectiontabpanel',
                            text: 'Edit'
                        }
                    ]
                },
                {
                    xtype: 'form',
                    dock: 'top',
                    bodyPadding: 10,
                    dockedItems: [
                        {
                            xtype: 'textareafield',
                            dock: 'top',
                            disabled: true,
                            itemId: 'reflectionstextbox',
                            minHeight: 273,
                            name: 'reflections'
                        }
                    ]
                }
            ]
        });

        me.callParent(arguments);
    }

});