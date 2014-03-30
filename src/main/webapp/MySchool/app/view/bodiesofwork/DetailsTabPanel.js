/*
 * File: app/view/bodiesofwork/DetailsTabPanel.js
 *
 * This file was generated by Sencha Architect version 3.0.3.
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

Ext.define('MySchool.view.bodiesofwork.DetailsTabPanel', {
    extend: 'Ext.tab.Panel',
    alias: 'widget.bodiesofworkdetailstabpanel',

    requires: [
        'MySchool.view.common.MyTabPanel',
        'Ext.panel.Panel',
        'Ext.tab.Tab'
    ],

    height: 300,
    itemId: 'bottombodiesofworktabpanel',
    minHeight: 300,
    activeTab: 0,

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'commonmytabpanel',
                    itemId: 'bodiesofworkwhattabpanel',
                    title: 'What',
                    tabConfig: {
                        xtype: 'tab',
                        tooltip: 'Click edit to modify'
                    }
                },
                {
                    xtype: 'commonmytabpanel',
                    itemId: 'bodiesofworkdescriptiontabpanel',
                    title: 'Description',
                    tabConfig: {
                        xtype: 'tab',
                        tooltip: 'Click edit to modify'
                    }
                },
                {
                    xtype: 'commonmytabpanel',
                    itemId: 'bodiesofworkobjectivetabpanel',
                    title: 'Objective',
                    tabConfig: {
                        xtype: 'tab',
                        tooltip: 'Click edit to modify'
                    }
                }
            ]
        });

        me.callParent(arguments);
    }

});