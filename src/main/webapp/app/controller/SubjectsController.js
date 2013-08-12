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

    init: function(application) {

        this.control({
            'subjectsgridpanel': {
                selectionchange: this.gridSelectionChange, 
                viewready: this.onViewReady
            },

            'subjectspanel': { mytoolsavesubjects: this.saveSubjects }

        });
    },

    gridSelectionChange: function(model, records) {

        if ( records[0] ) {
            this.getSubjectsForm().getForm().loadRecord(records[0]);
        }
    },

    onViewReady: function(grid) {

        grid.getSelectionModel().select( 0 );
    },

    saveSubjects: function() {
        window.console( 'saveSubjects() called...' );
    }

});
