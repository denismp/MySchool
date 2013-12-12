/*
 * File: app/controller/subject/SubjectsController.js
 *
 * This file was generated by Sencha Architect version 3.0.0.
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

Ext.define('MySchool.controller.subject.SubjectsController', {
    extend: 'Ext.app.Controller',

    selectedIndex: '0',
    models: [
        'subject.SubjectsModel',
        'quarters.QuarterModel',
        'student.StudentModel',
        'subject.QuarterNamesModel'
    ],
    stores: [
        'subject.SubjectStore',
        'quarter.QuarterStore',
        'student.StudentStore',
        'subject.GradeTypeStore'
    ],
    views: [
        'MainPanel',
        'subject.SubjectsGridPanel',
        'subject.SubjectsForm',
        'subject.SubjectsPanel',
        'subject.GradeTypeComboBox',
        'subject.QuarterNamesComboBox'
    ],

    refs: [
        {
            ref: 'subjectsForm',
            selector: 'form'
        }
    ],

    constructor: function(cfg) {
        cfg = cfg || {};
        this.callParent(this.processSubjectSubjectsController(cfg));
    },

    processSubjectSubjectsController: function(config) {
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
    	if (false) {
            window.console.log( 'selectedIndex=' + this.selectedIndex );
            window.console.log( "onSubjectdescriptiontextareraChange() field=" + field );
            var mystore = Ext.getStore("subject.SubjectStore");
            var myrecord = mystore.getAt( this.selectedIndex );
            myrecord.set( 'subjDescription', newValue );
    	}
    },

    onSubjectobjectivetextareaChange: function(field, newValue, oldValue, eOpts) {
    	if (false) {
            window.console.log( 'selectedIndex=' + this.selectedIndex );
            window.console.log( "onSubjectobjectivetextareraChange() field=" + field );
            var mystore = Ext.getStore("subject.SubjectStore");
            var myrecord = mystore.getAt( this.selectedIndex );
            myrecord.set( 'subjObjectives', newValue );
    	}
    },

    onTooldeletestudentsbysubjectClick: function(tool, e, eOpts) {
        window.console.log( 'Delete' );
    },

    onToolnewsubjectsClick: function(tool, e, eOpts) {
        //debugger;
        window.console.log( 'New' );
        var newDialog = Ext.create( 'MySchool.view.subject.NewForm' );
        //window.console.log( "DEBUG" );
        //newDialog.show();

        var mystore = Ext.getStore("subject.SubjectStore");
        var mynamestore = Ext.getStore( "subject.QuarterNameStore" );
        var myrecord = mystore.getAt( this.selectedIndex );
        //myrecord.set( 'description', newValue );
        window.console.log( myrecord.data );
        newDialog.loadRecord(myrecord);
        var myFormFields = newDialog.getForm().getFields();
        //        var myuserName = myrecord.data.studentName;
        var mygradeType = myrecord.data.qtrGradeType;
        var myquarterName = myrecord.data.qtrName;
        var myQtrNameRec = mynamestore.findRecord( 'qtrName', myquarterName );

        if (mygradeType) {
            var mycomboview = myFormFields.getAt( 2 );  // Grade type selection for combobox.
            mycomboview.setValue( mygradeType );
        }

        if (myQtrNameRec) {
            var qtrId = myQtrNameRec.get( 'id' );
            var mycomboview = myFormFields.getAt( 7 ); // Quarter Name selection for combobox.
            mycomboview.setValue( qtrId );
        }

        //        newDialog.getForm().setValues( { userName: myuserName } );

        newDialog.render( Ext.getBody() );
        newDialog.show();
    },

    onToolsearchsubjectsClick: function(tool, e, eOpts) {
        window.console.log( 'Search' );
    },

    onToolrefreshsubjectsClick: function(tool, e, eOpts) {
        // Add refresh handler code here.  Use example from chapter 2 of book.
        //debugger;
        window.console.log( 'Refresh' );
        var mystore = Ext.getStore("subject.SubjectStore");
        mystore.reload();
        //pnl.setTitle( 'Denis' );
    },

    onNewsubjectsubmitClick: function(button, e, eOpts) {
        debugger;
        //var mystore = this.getSubjectStoreStore();
        window.console.log( "Submit New Subject" );
        var mysubjectstore = Ext.getStore("subject.SubjectStore");
        var myqtrstore = Ext.getStore( "subject.QuarterNameStore" );
        var myquarterstore = Ext.getStore( "quarter.QuarterStore" );
        var mystudentstore = Ext.getStore( "student.StudentStore" );
        var myrecords = myqtrstore.getRange( 0, 3 );
        //var currentDate = Ext.Date.parse(new Date(),'m/d/Y');
        //var currentDate = new Date();
        //currentDate = Ext.Date.format( currentDate, 'm/d/Y' );
        var currentDate = '10/20/2013';
        for( var i = 0; i < 4; i++ )
        {
            window.console.log( "qtrName=" + myrecords[i].get( 'qtrName' ) );
        }
        var myForm = button.up().getForm();
        debugger;
        // Get the data from the form and add a new subject record to the datbase.
        var myvalues	= myForm.getFieldValues();
        var myfields	= myForm.getFields();
        var creditHours = myvalues.creditHours;
        var description = myvalues.description;
        var gradeLevel	= myvalues.gradeLevel;
        var subjectName = myvalues.name;
        var objectives	= myvalues.objectives;
        var qtr_year	= myvalues.qtr_year;
        var userName	= myvalues.userName;


        var gradeTypeIntValue = myfields.getAt( 2 ).getValue();  // Grade type selection for combobox.

        var quarterNameIntValue = myfields.getAt( 7 ).getValue(); // Quarter Name selection for combobox.


        //	Now we need to put together the data to be inserted(and/or possibly updated).
        //debugger;
        var subjectRecord, studentRecord, myQtrName, qtrRecord;
        subjectRecord	= this.getSubjectRecord( subjectName );
        studentRecord	= this.getStudentRecord( userName );
        myQtrName		= this.getQuarterName( quarterNameIntValue );

        qtrRecord		= this.getQuarterRecord( myQtrName, qtr_year);


        window.console.log( "subjectRecord type is " + typeof subjectRecord );
        window.console.log( "studentRecord type is " + typeof studentRecord );
        window.console.log( "qtrRecord type is " + typeof qtrRecord );

        var qtrFlag = 1;
        var subjectFlag = 1;
        //debugger;
        if( typeof qtrRecord == "undefined" )
        {
            // A new quarter is being requested.
            qtrFlag = 0;
            qtrRecord = Ext.create('MySchool.model.quarters.QuarterModel' );
            qtrRecord.set( 'id', null );
            qtrRecord.set( 'qtrName', myQtrName );
            qtrRecord.set( 'qtr_year', qtr_year );
            qtrRecord.set( 'gradeType', gradeTypeIntValue );
            qtrRecord.set( 'grade', 0 );
            qtrRecord.set( 'locked', 0 );
            qtrRecord.set( 'whoUpdated', 'application' );
            qtrRecord.set( 'lastUpdated', new Date() );
            qtrRecord.set( 'version', 0 );
            qtrRecord.set( 'student', studentRecord.data );
            myquarterstore.add( qtrRecord );
            //	Let's try sync() ing the qtrRecord and then getting
            //	the id of the record before assigning it to the subject record.
        }
        else
        {
            qtrRecord.set( 'student', studentRecord.data );
        }
        myquarterstore.sync();
        if( typeof subjectRecord == "undefined" )
        {
            // A new subject is being requested.
            subjectFlag = 0;
            subjectRecord = Ext.create( 'MySchool.model.subject.SubjectsModel' );
            subjectRecord.set( 'id', null );
            subjectRecord.set( 'name', subjectName );
            subjectRecord.set( 'gradeLevel', gradeLevel );
            subjectRecord.set( 'creditHours', creditHours );
            subjectRecord.set( 'completed', 0 );
            subjectRecord.set( 'whoUpdated', 'application' );
            subjectRecord.set( 'lastUpdated', new Date() );
            subjectRecord.set( 'description', description );
            subjectRecord.set( 'objectives', objectives );
            subjectRecord.set( 'version', 0 );
            subjectRecord.set( 'quarter', qtrRecord.data );
            subjectRecord.set( 'userName', userName );
            mysubjectstore.add( subjectRecord );
        }
        if( qtrFlag === 0 || subjectFlag !== 0 )
        {
            //  Here we add the new quarter record to the existing subject record.
            subjectRecord.set( 'quarter', qtrRecord.data );
        }

        mysubjectstore.sync();

        myForm.reset();
        button.up().hide();
    },

    onNewsubjectcancelClick: function(button, e, eOpts) {
        //debugger;
        window.console.log( "Cancel New Subject" );
        var myForm = button.up().getForm();
        myForm.reset();
        button.up().hide();
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
            },
            "#newsubjectsubmit": {
                click: this.onNewsubjectsubmitClick
            },
            "#newsubjectcancel": {
                click: this.onNewsubjectcancelClick
            }
        });
    },

    onViewReady: function(grid) {

        grid.getSelectionModel().select( 0 );
    },

    onSubjectsavetoolidClick: function(tool, e, eOpts) {
        window.console.log( "Save" );
        debugger;

        var mystore = Ext.getStore("subject.SubjectStore");

        var records = mystore.getModifiedRecords();
        for( var i = 0; i < records.length; i++ )
        {
            records[i].set( 'qtrLastUpdated', new Date() );
            records[i].set( 'qtrWhoUpdated', 'app'); // replace with login id

            if (false) {
                var form = this.getSubjectsForm().getForm();
                var formValues = form.getValues();
                records[i].set( 'subjDescription', formValues.subjDescription );
                records[i].set( 'subjObjectives', formValues.subjObjectives );
                window.console.log( 'objectives=' + formValues.subjObjectives );
                window.console.log( 'description=' + formValues.subjDescription );
            }
        }

        mystore.sync();

    },

    gridSelectionChange: function(model, records) {
        //debugger;
        if ( records[0] ) {
            this.getSubjectsForm().getForm().loadRecord(records[0]);
        }
    },

    findQuarterIdByName: function(name) {
        var myqtrstore = Ext.getStore( "subject.QuarterNameStore" );
        var index = myqtrstore.findRecord( 'qtrName', name ).get( 'id' );
        return index;
    },

    getQuarterName: function(quarterNameID) {
        var mynamestore = Ext.getStore( "subject.QuarterNameStore" );
        var recCount = mynamestore.getTotalCount();
        var records = mynamestore.getRange( 0, recCount );
        for( var i = 0; i < recCount; i++ )
        {
            window.console.log( "qtrName=" + records[i].get( 'qtrName' ) );
            window.console.log( "id=" + records[i].get( 'id' ) );
            var myId = records[i].get( 'id' );
            var myName = records[i].get( 'qtrName' );
            if( myId === quarterNameID )
            {
                return myName;
            }
        }
        return 'NONE';
    },

    getQuarterRecord: function(name, year) {
        var qtrStore = Ext.getStore( 'quarter.QuarterStore' );
        var qtrCount = qtrStore.getTotalCount();
        var records = qtrStore.getRange( 0, qtrCount );
        for( var i = 0; i < qtrCount; i++ )
        {
            window.console.log( "qtrName=" + records[i].get( 'qtrName' ) );
            window.console.log( "id=" + records[i].get( 'id' ) );
            window.console.log( "qtr_year=" + records[i].get( 'qtr_year' ) );
            if( name == records[i].get( 'qtrName' ) && year == records[i].get( 'qtr_year' ) )
            {
                return records[i];
            }
        }
    },

    getSubjectRecord: function(name) {
        var myStore = Ext.getStore( 'subject.SubjectStore' );
        var recCount = myStore.getTotalCount();
        var records = myStore.getRange( 0, recCount );
        for( var i = 0; i < recCount; i++ )
        {
            window.console.log( "name=" + records[i].get( 'name' ) );
            window.console.log( "id=" + records[i].get( 'id' ) );
            if( name == records[i].get( 'name' ) )
            {
                return records[i];
            }
        }
    },

    getStudentRecord: function(userName) {
        var myStore = Ext.getStore( 'student.StudentStore' );
        var recCount = myStore.getTotalCount();
        var records = myStore.getRange( 0, recCount );
        for( var i = 0; i < recCount; i++ )
        {
            window.console.log( "userName=" + records[i].get( 'userName' ) );
            window.console.log( "id=" + records[i].get( 'id' ) );
            if( userName == records[i].get( 'userName' ) )
            {
                return records[i];
            }
        }
    },

    onLaunch: function() {
        // Use the automatically generated getter to get the store
        //    	debugger;
        var studentStore_ = Ext.getStore('student.StudentStore');

        studentStore_.load({
            callback: this.onStudentStoreLoad,
            scope: this,
            params: {
                studentName: 'denis'
            }
        });
    },

    onStudentStoreLoad: function() {
        var studentStore_ = Ext.getStore('student.StudentStore');
        var r_ = studentStore_.getAt(0);
        //        debugger
        if ( typeof( r_ ) != "undefined" ) {
            var subjectStore_ = Ext.getStore( 'subject.SubjectStore' );
            var studentName_ = r_.get('firstName') + " " + r_.get('middleName') + ' ' + r_.get('lastName');
            var g_ = Ext.ComponentQuery.query("#subjectsgrid")[0];

            g_.setTitle('[' + studentName_ + '] Subjects');
            subjectStore_.load({
                callback: this.onSubjectStoreLoad,
                scope: this,
                params: {
                    studentName: r_.get('userName'),
                    studentId: r_.get('id')
                }
            });
        }
    },

    onSubjectStoreLoad: function() {
        var g_ = Ext.ComponentQuery.query("#subjectsgrid")[0];

        if (g_.getStore().getCount() > 0) {
            g_.getSelectionModel().select( 0 );
        }
    }

});
