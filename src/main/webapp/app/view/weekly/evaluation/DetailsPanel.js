/*
 * File: app/view/weekly/evaluation/DetailsPanel.js
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

Ext.define('MySchool.view.weekly.evaluation.DetailsPanel', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.weeklyevaluationdetailspanel',

    requires: [
        'MySchool.view.weekly.evaluation.GridPanel',
        'Ext.grid.Panel',
        'Ext.panel.Tool'
    ],

    height: 700,
    itemId: 'weeklyevaluationpanel',
    layout: 'fit',
    title: 'Weekly Evaluation Details',

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'weeklyevaluationgridpanel'
                }
            ],
            tools: [
                {
                    xtype: 'tool',
                    handler: function(event, toolEl, owner, tool) {
                        // Add refresh handler code here.  Use example from chapter 2 of book.
                    },
                    id: 'toolrefreshstudentsbysubject4',
                    tooltip: 'Refresh',
                    type: 'refresh'
                },
                {
                    xtype: 'tool',
                    handler: function(event, toolEl, owner, tool) {
                        // add search handler code here.
                    },
                    id: 'toolsearchstudentsbysubject4',
                    tooltip: 'Search',
                    type: 'search'
                },
                {
                    xtype: 'tool',
                    handler: function(event, toolEl, owner, tool) {
                        // add new/insert handler code here.
                    },
                    id: 'toolnewstudentsbysubject4',
                    tooltip: 'New',
                    type: 'plus'
                },
                {
                    xtype: 'tool',
                    handler: function(event, toolEl, owner, tool) {
                        // Add save/udate handler code here.
                    },
                    id: 'toolsavestudentsbysubject4',
                    tooltip: 'Save',
                    type: 'save'
                },
                {
                    xtype: 'tool',
                    handler: function(event, toolEl, owner, tool) {
                        // Add delete handler code here.
                    },
                    id: 'tooldeletestudentsbysubject4',
                    tooltip: 'Delete',
                    type: 'minus'
                },
                {
                    xtype: 'tool',
                    handler: function(event, toolEl, owner, tool) {
                        // add lock handler code here.
                    },
                    id: 'toollockstudentsbysubject4',
                    tooltip: 'Lock?',
                    type: 'pin'
                }
            ]
        });

        me.callParent(arguments);
    }

});