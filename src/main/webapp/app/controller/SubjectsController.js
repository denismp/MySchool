/*
 * File: app/controller/SubjectsController.js
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

Ext.define('MySchool.controller.SubjectsController', {
    extend: 'Ext.app.Controller',

    selectedIndex: '0',
    models: [
        'SubjectsModel'
    ],
    stores: [
        'SubjectStore'
    ],
    views: [
        'MainPanel',
        'SubjectsGridPanel',
        'SubjectsForm',
        'SubjectsPanel'
    ],

    refs: [
        {
            ref: 'subjectsForm',
            selector: 'form'
        }
    ],

    constructor: function(cfg) {
        cfg = cfg || {};
        this.callParent(this.processSubjectsController(cfg));
    },

    processSubjectsController: function(config) {
        return config;
    },

    onSubjectsgridSelect: function(rowmodel, record, index, eOpts) {
        window.console.log( "selected row in grid." );
        window.console.log( "index=" + index );
        //debugger;
        //if ( record ) {
        //    this.getSubjectsForm().getForm().loadRecord(record);
        //}
        this.selectedIndex = index;
    },

    onSubjectdescriptiontextareaChange: function(field, newValue, oldValue, eOpts) {
        // NEED TO MARK THE GRID's RECORD DIRTY HERE BUT I DON'T HOW TO GET A HOLD OF THE RECORD INDEX OR THE RECORD.
        window.console.log( 'selectedIndex=' + this.selectedIndex );
        window.console.log( "onSubjectdescriptiontextareraChange() field=" + field );
        var mystore = Ext.getStore("SubjectStore");
        var myrecord = mystore.getAt( this.selectedIndex );
        myrecord.set( 'description', newValue );


    },

    onSubjectobjectivetextareaChange: function(field, newValue, oldValue, eOpts) {
        // NEED TO MARK THE GRID's RECORD DIRTY HERE BUT I DON'T HOW TO GET A HOLD OF THE RECORD INDEX OR THE RECORD.
        window.console.log( 'selectedIndex=' + this.selectedIndex );
        window.console.log( "onSubjectobjectivetextareraChange() field=" + field );
        var mystore = Ext.getStore("SubjectStore");
        var myrecord = mystore.getAt( this.selectedIndex );
        myrecord.set( 'objectives', newValue );
    },

    onTooldeletestudentsbysubjectClick: function(tool, e, eOpts) {
        window.console.log( 'Delete' );
    },

    onToolnewsubjectsClick: function(tool, e, eOpts) {
        window.console.log( 'New' );

    },

    onToolsearchsubjectsClick: function(tool, e, eOpts) {
        window.console.log( 'Search' );
    },

    onToolrefreshsubjectsClick: function(tool, e, eOpts) {
        // Add refresh handler code here.  Use example from chapter 2 of book.
        //debugger;
        window.console.log( 'Refresh' );
        var mystore = Ext.getStore("SubjectStore");
        mystore.reload();
        //pnl.setTitle( 'Denis' );
    },

    init: function(application) {

        this.control({
            'subjectsgridpanel': {
                selectionchange: this.gridSelectionChange,
                viewready: this.onViewReady
            },
            'subjectsavetool': {
                click: this.onSubjectsavetoolidClick
            }
        });


        this.control({
            "#subjectsgrid": {
                select: this.onSubjectsgridSelect
            },
            "#subjectdescriptiontextarea": {
                change: this.onSubjectdescriptiontextareaChange
            },
            "#subjectobjectivetextarea": {
                change: this.onSubjectobjectivetextareaChange
            },
            "#tooldeletestudentsbysubject": {
                click: this.onTooldeletestudentsbysubjectClick
            },
            "#toolnewsubjects": {
                click: this.onToolnewsubjectsClick
            },
            "#toolsearchsubjects": {
                click: this.onToolsearchsubjectsClick
            },
            "#toolrefreshsubjects": {
                click: this.onToolrefreshsubjectsClick
            }
        });
    },

    onViewReady: function(grid) {

        grid.getSelectionModel().select( 0 );
    },

    onSubjectsavetoolidClick: function(tool, e, eOpts) {
        window.console.log( "Save" );
        //debugger;

        var mystore = Ext.getStore("SubjectStore");

        var records = mystore.getModifiedRecords();
        for( var i = 0; i < records.length; i++ )
        {
            records[i].set( 'lastUpdated', new Date() );
            var form = this.getSubjectsForm().getForm();
            var formValues = form.getValues();
            records[i].set( 'description', formValues.description );
            records[i].set( 'objectives', formValues.objectives );
            window.console.log( 'objectives=' + formValues.objectives );
            window.console.log( 'description=' + formValues.description );
        }

        mystore.sync();

    },

    gridSelectionChange: function(model, records) {
        //debugger;
        if ( records[0] ) {
            this.getSubjectsForm().getForm().loadRecord(records[0]);
        }
    }

});
