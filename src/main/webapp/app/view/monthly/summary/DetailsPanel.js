/*
 * File: app/view/monthly/summary/DetailsPanel.js
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

Ext.define('MySchool.view.monthly.summary.DetailsPanel', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.monthlysummarydetailspanel',

    requires: [
        'MySchool.view.monthly.summary.DetailsGridPanel',
        'MySchool.view.monthly.summary.DetailsTabPanel',
        'MySchool.view.monthly.summary.RefreshTool',
        'MySchool.view.monthly.summary.SearchTool',
        'MySchool.view.monthly.summary.NewTool',
        'MySchool.view.monthly.summary.SaveTool',
        'MySchool.view.monthly.summary.DeleteTool',
        'MySchool.view.monthly.summary.LockTool',
        'Ext.grid.Panel',
        'Ext.tab.Panel',
        'Ext.panel.Tool'
    ],

    height: 673,
    itemId: 'monthlysummarydetailspanel',
    title: 'Monthly Summary Details',

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            dockedItems: [
                {
                    xtype: 'monthlydetailsgridpanel',
                    height: 334,
                    dock: 'top'
                },
                {
                    xtype: 'monthlysummarydetailstabpanel',
                    dock: 'bottom'
                }
            ],
            tools: [
                {
                    xtype: 'monthlysummaryrefreshtool'
                },
                {
                    xtype: 'monthlysummarysearchtool'
                },
                {
                    xtype: 'monthlysummarynewtool'
                },
                {
                    xtype: 'monthlysummarysavetool'
                },
                {
                    xtype: 'monthlysummarydeletetool'
                },
                {
                    xtype: 'monthlysummarylocktool'
                }
            ]
        });

        me.callParent(arguments);
    }

});