/*
 * File: app/view/monthly/evaluation/DetailsPanel.js
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

Ext.define('MySchool.view.monthly.evaluation.DetailsPanel', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.monthlyevaluationdetailspanel',

    requires: [
        'MySchool.view.monthly.evaluation.GridPanel',
        'Ext.grid.Panel',
        'Ext.panel.Tool'
    ],

    itemId: 'monthlyevaluatondetailspanel',
    layout: 'fit',
    title: 'Monthly Evaluation Details',

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'monthlyevaluationgridpanel'
                }
            ],
            tools: [
                {
                    xtype: 'tool',
                    handler: function(event, toolEl, owner, tool) {
                        // Add refresh handler code here.  Use example from chapter 2 of book.
                    },
                    id: 'toolrefreshstudentsbysubject7',
                    tooltip: 'Refresh',
                    type: 'refresh'
                },
                {
                    xtype: 'tool',
                    handler: function(event, toolEl, owner, tool) {
                        // add search handler code here.
                    },
                    id: 'toolsearchstudentsbysubject7',
                    tooltip: 'Search',
                    type: 'search'
                },
                {
                    xtype: 'tool',
                    handler: function(event, toolEl, owner, tool) {
                        // add new/insert handler code here.
                    },
                    id: 'toolnewstudentsbysubject7',
                    tooltip: 'New',
                    type: 'plus'
                },
                {
                    xtype: 'tool',
                    handler: function(event, toolEl, owner, tool) {
                        // Add save/udate handler code here.
                    },
                    id: 'toolsavestudentsbysubject7',
                    tooltip: 'Save',
                    type: 'save'
                },
                {
                    xtype: 'tool',
                    handler: function(event, toolEl, owner, tool) {
                        // Add delete handler code here.
                    },
                    id: 'tooldeletestudentsbysubject7',
                    tooltip: 'Delete',
                    type: 'minus'
                },
                {
                    xtype: 'tool',
                    handler: function(event, toolEl, owner, tool) {
                        // add lock handler code here.
                    },
                    id: 'toollockstudentsbysubject7',
                    tooltip: 'Lock?',
                    type: 'pin'
                }
            ]
        });

        me.callParent(arguments);
    }

});