/*
 * File: app/view/SubjectsPanel.js
 *
 * This file was generated by Sencha Architect version 2.2.2.
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

Ext.define('MySchool.view.SubjectsPanel', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.subjectspanel',

    requires: [
        'MySchool.view.SubjectsGridPanel',
        'MySchool.view.SubjectsForm'
    ],

    id: 'subjectspanel',
    layout: {
        align: 'stretch',
        type: 'hbox'
    },
    title: 'Subjects By Student',

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'subjectsgridpanel',
                    flex: 1
                },
                {
                    xtype: 'subjectsform',
                    flex: 1
                }
            ],
            tools: [
                {
                    xtype: 'tool',
                    handler: function(event, toolEl, owner, tool) {
                        // Add refresh handler code here.  Use example from chapter 2 of book.
                        //debugger;

                        var pnl = this.up('subjectspanel').down('subjectsgridpanel');
                        var store = pnl.getStore();
                        store.reload();
                        //pnl.setTitle( 'Denis' );

                    },
                    id: 'toolrefreshstudentsbysubject',
                    tooltip: 'Refresh',
                    type: 'refresh'
                },
                {
                    xtype: 'tool',
                    handler: function(event, toolEl, owner, tool) {
                        // add search handler code here.
                    },
                    id: 'toolsearchstudentsbysubject',
                    tooltip: 'Search',
                    type: 'search'
                },
                {
                    xtype: 'tool',
                    handler: function(event, toolEl, owner, tool) {
                        // add new/insert handler code here.
                    },
                    id: 'toolnewstudentsbysubject',
                    tooltip: 'New',
                    type: 'plus'
                },
                {
                    xtype: 'tool',
                    handler: function(event, toolEl, owner, tool) {
                        window.console.log( "Save..." );
                        //debugger;
                        var pnl = this.up('subjectspanel').down('subjectsgridpanel');
                        var mystore = pnl.getStore();
                        var myProxy = mystore.getProxy();
                        var mymodel = mystore.getProxy().getModel();
                        var myid = tool.id;
                        var myeventtype = event.type;
                        //tool.fireEvent( event.type, mystore, myProxy, mymodel, myid );
                        //mymodel.save();
                        //mystore.commitChanges();
                        mystore.sync();
                        mystore.load();

                    },
                    id: 'mytoolsavesubjects',
                    type: 'save'
                },
                {
                    xtype: 'tool',
                    handler: function(event, toolEl, owner, tool) {
                        // Add delete handler code here.
                    },
                    id: 'tooldeletestudentsbysubject',
                    tooltip: 'Delete',
                    type: 'minus'
                }
            ]
        });

        me.callParent(arguments);
    }

});