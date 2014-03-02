/*
 * File: app/controller/faculty/ByStudentController.js
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

Ext.define('MySchool.controller.faculty.ByStudentController', {
    extend: 'Ext.app.Controller',
    alias: 'controller.facultybystudentcontroller',

    models: [
        'monthly.EvaluationRatings'
    ],
    stores: [
        'monthly.EvaluationRatingsStore'
    ],

    refs: [
        {
            ref: 'FacultyByStudentGridPanel',
            selector: '#facultyprofilesbystudentgridpanel'
        },
        {
            ref: 'FacultyByStudentForm',
            selector: '#facultyprofileform'
        }
    ],

    onNewfacultybystudentcanelClick: function(button, e, eOpts) {
        //debugger;
        window.console.log( "Cancel New Monthly Evaluation" );
        var myForm = button.up().getForm();
        myForm.reset();
        button.up().hide();
    },

    onNewfacultybystudentsubmitClick: function(button, e, eOpts) {
        debugger;
        window.console.log( "Submit New Monthly Evaluations" );
        var myForm					= button.up().getForm();
        //var newDialog = button.up('monthlynewsummaryformpanel');

        //Get the values from the form and insert a new record into the MonthlySummaryView.

        var formValues				= myForm.getValues();

        //	Create an empty record
        var myRecord	= Ext.create('MySchool.model.monthly.EvaluationRatings');

        //	Get the stores that we will need
        var myStore		= this.getStore( 'monthly.EvaluationRatingsStore' );

        var studentStore = Ext.getStore('student.StudentStore');
        var subjectStore = Ext.getStore( 'subject.SubjectStore' );

        //	Get the student info
        var studentRecord	= studentStore.getAt(0);
        var studentId		= studentRecord.get( 'id' );
        var studentName		= studentRecord.get( 'userName' );

        //	Get the quarterSubject record from the form.
        var quarterSubjectId		= formValues.comboquartersubject;
        var quarterSubjectRecord;
        for( var i = 0; i < subjectStore.count(); i++ )
        {
            if( subjectStore.getAt(i).get('id') === quarterSubjectId )
            {
                quarterSubjectRecord = subjectStore.getAt(i);
                break;
            }
        }

        if( typeof quarterSubjectRecord !== 'undefined')
        {
            //	Get the other information that we need for the new record.
            var subjName	= quarterSubjectRecord.get('subjName');
            var subjId		= quarterSubjectRecord.get('subjId');
            var qtrName		= quarterSubjectRecord.get('qtrName');
            var qtrId		= quarterSubjectRecord.get('qtrId');
            var qtrYear		= quarterSubjectRecord.get('qtrYear');
            //var month_number = formValues.combomonth;

            //var allSubjRec_ = myAllSubjStore.findRecord( 'subjName', subjName );
            //var subjId_ = allSubjRec_.get( 'subjId' );


            //Add the data to the new record.
            if( formValues.combomonth > 0 )
            {
                myRecord.set('month_number', formValues.combomonth);
                //myRecord.set('week_number', formValues.week_number);

                myRecord.set('subjName', subjName );
                myRecord.set('subjId', subjId );
                myRecord.set('qtrName', qtrName );
                myRecord.set('qtrId', qtrId);
                myRecord.set('studentId', studentId);
                myRecord.set('studentUserName', studentName);
                myRecord.set('qtrYear', qtrYear);

                myRecord.set('locked', 0 );
                myRecord.set('levelOfDifficulty', formValues.levelOfDifficulty);
                myRecord.set('criticalThinkingSkills', formValues.criticalThinkingSkills);
                myRecord.set('effectiveCorrectionActions', formValues.effectiveCorrectionActions);
                myRecord.set('completingCourseObjectives', formValues.completingCourseObjectives);
                myRecord.set('accuratelyIdCorrections', formValues.accuratelyIdCorrections);
                myRecord.set('thoughtfulnessOfReflections', formValues.thoughtfulnessOfReflections);
                myRecord.set('workingEffectivelyWithAdvisor', formValues.workingEffectivelyWithAdvisor);
                myRecord.set('responsibilityOfProgress', formValues.responsibilityOfProgress);
                myRecord.set('comments', formValues.comments);

                myRecord.set('whoUpdated', 'login');
                myRecord.set('lastUpdated', new Date());
                myRecord.set('version', null);
                myRecord.set('monthlyevaluationId', 0 );

                //add to the store

                myStore.add( myRecord );

                //sync the store.
                myStore.sync();

                myForm.reset();
                button.up().hide();
            }
            else
            {
                var smsg = "You must enter a value for month";
                Ext.MessageBox.show({
                    title: 'REMOTE EXCEPTION',
                    msg: smsg,
                    icon: Ext.MessageBox.ERROR,
                    buttons: Ext.Msg.OK
                });
            }
        }
        else
        {
            var msg = "You must have student/faculty/subject/quarter records.";
                    Ext.MessageBox.show({
                    title: 'NO DATA',
                    msg: smsg,
                    icon: Ext.MessageBox.ERROR,
                    buttons: Ext.Msg.OK
                });
        }
    },

    onFacultyprofilerefreshtoolClick: function(tool, e, eOpts) {
        var myStore = Ext.getStore('faculty.ByStudentStore');
        myStore.reload();
    },

    onFacultyprofilesearchtoolClick: function(tool, e, eOpts) {

    },

    onFacultyprofilenewtoolClick: function(tool, e, eOpts) {
        debugger;
        var studentStore				= Ext.getStore('student.StudentStore');
        var subjectStore				= Ext.getStore('subject.SubjectStore');
        var commonQuarterSubjectStore	= Ext.getStore( 'common.QuarterSubjectStore');
        var commonMonthStore			= Ext.getStore('common.MonthStore');

        var studentRecord	= studentStore.getAt(0);
        var studentId		= studentRecord.get( 'id' );
        var studentName		= studentRecord.get( 'userName' );

        var newDialog = Ext.create( 'MySchool.view.faculty.NewFormPanel' );

        newDialog.down('#studentid').setValue( studentId );
        newDialog.down('#studentname').setValue( studentName );

        //commonQuarterSubjectStore.myLoad();
        commonMonthStore.myLoad();

        window.console.log( 'New Faculty By Student Dialog' );

        newDialog.render( Ext.getBody() );
        newDialog.show();
    },

    onFacultyprofilesavetoolClick: function(tool, e, eOpts) {
        window.console.log( "faculty.ByStudentStore.Save" );
        debugger;

        var mystore = Ext.getStore("faculty.ByStudentStore");

        var records = mystore.getModifiedRecords();
        for( var i = 0; i < records.length; i++ )
        {
            records[i].set( 'lastUpdated', new Date() );
            records[i].set( 'whoUpdated', 'login');
        }

        mystore.sync();
    },

    onFacultyprofiledeletetoolClick: function(tool, e, eOpts) {

    },

    onFacultyprofilelocktoolClick: function(tool, e, eOpts) {

    },

    onFacultyprofilesbystudentgridpanelViewReady: function(tablepanel, eOpts) {
        debugger;
        console.log('onFacultyprofilesbystudnegridpanelViewReady()');
        var myStore = Ext.getStore('faculty.ByStudentStore');
        var myStudentStore = Ext.getStore('student.StudentStore');
        var studentRecord = myStudentStore.getAt(0);
        //        debugger
        if ( typeof( studentRecord ) != "undefined" ) {
            var studentName_ = studentRecord.get('firstName') + " " + studentRecord.get('middleName') + ' ' + studentRecord.get('lastName');
            //MonthlyDetailsGridPanel
            //var myGrid = Ext.ComponentQuery.query("#bodiesofworkssubjectsgrid")[0];
            var myGrid = this.getFacultyByStudentGridPanel();

            myGrid.setTitle('[' + studentName_ + ']');
            myStore.load({
                callback: this.onMyJsonStoreLoad,
                scope: this,
                params: {
                    studentName: studentRecord.get('userName'),
                    studentId: studentRecord.get('id')
                }
            });
        }
        //grid.getSelectionModel().select( 0 );
        //tablepanel.getSelectionModel().select( 0 );
    },

    onFacultyprofilesbystudentgridpanelSelectionChange: function(model, selected, eOpts) {
        debugger;
        // in the onMyJsonStoreLoad we do a deselect so we need to test
        // if selected[0] has a value
        if ( Ext.isDefined( selected  ) && Ext.isDefined( selected[0]  )) {
            var myForm = this.getFacultyByStudentForm();
            var myPanel = myForm.up();
            this.loadForm( myForm, selected );
            //myForm.focus();

            console.log('onFacultyprofielsbystudengridpanelSelectionChange()');
        }
    },

    onFacultyprofilegridpanelenabledCheckChange: function(checkcolumn, rowIndex, checked, eOpts) {
        debugger;
        var myGrid = this.getFacultyByStudentGridPanel();
        var myStore = this.getStore( 'faculty.ByStudentStore');
        var record = myStore.getAt(rowIndex);
        record.set( 'enabled', !checked );
    },

    onFacultyprofileformBlur: function(component, e, eOpts) {
        debugger;
        console.log( 'faculty.ByStudentController.onFacultyprofileformBlur()');
    },

    onFacultyprofileformFocus: function(component, e, eOpts) {
        debugger;
        console.log('faculty.ByStudentController.onFacultyprofileformFocus()');
    },

    onFacultyprofileformeditbuttonClick: function(button, e, eOpts) {
        debugger;

        var myForm = this.getFacultyByStudentForm();
        var myFields = myForm.getForm().getFields();
        for( var i = 0; i < myFields.length; i++ )
        {
            myFields.items[i].enable();
        }
        //myForm.getForm().focus();
        var cancelButton	= button.up().down('#facultyprofileformcanelbutton');
        var saveButton		= button.up().down('#facultyprofileformsavebutton');
        cancelButton.enable();
        saveButton.enable();
        button.disable();
    },

    onFacultyprofileformcanelbuttonClick: function(button, e, eOpts) {
        var myForm = this.getFacultyByStudentForm();
        var myFields = myForm.getForm().getFields();
        for( var i = 0; i < myFields.length; i++ )
        {
            myFields.items[i].disable();
        }
        //myForm.getForm().focus();
        //var cancelButton	= button.up().down('#facultyprofileformcanelbutton');
        var saveButton		= button.up().down('#facultyprofileformsavebutton');
        var editButton		= button.up().down('#facultyprofileformeditbutton');
        editButton.enable();
        saveButton.disable();
        button.disable();
    },

    onFacultyprofileformsavebuttonClick: function(button, e, eOpts) {
        var myForm = this.getFacultyByStudentForm();
        var myFields = myForm.getForm().getFields();
        for( var i = 0; i < myFields.length; i++ )
        {
            myFields.items[i].disable();
        }
        //myForm.getForm().focus();
        var cancelButton	= button.up().down('#facultyprofileformcanelbutton');
        //var saveButton		= button.up().down('#facultyprofileformsavebutton');
        var editButton		= button.up().down('#facultyprofileformeditbutton');
        editButton.enable();
        cancelButton.disable();
        button.disable();
    },

    buttonHandler: function(button, e, eOpts) {
        debugger;
        window.console.log(button);
        var b_		= button;
        var form	= b_.up('panel');
        var p_		= form.up();
        var pItemId_ = p_.getItemId();
        var field_;

        if (pItemId_ == 'dailyhourstab') {
            field_ = p_.down('numberfield');
        } else {
            field_ = p_.down('textareafield');
        }

        if (b_.getText().charAt(0) == 'D') {
            b_ = p_.down('#edit' + pItemId_);
            b_.setText('Edit');
            b_.setDisabled(false);
            field_.setDisabled(true);
        } else {
            b_.setText('Done');
            field_.setDisabled(false);
            field_.focus();
        }
    },

    blurHandler: function(o, event, eOpts) {
        debugger;
        var p_			= o.up('form').up('panel');
        //var myForm		= o.up('form');
        var myForm		= this.getFacultyByStudentForm();
        //var topP_		= p_.up('panel');
        var pItemId_	= p_.getItemId();
        var edit_		= p_.down('#edit' + pItemId_);
        var myTitle		= p_.title;
        console.log( edit_ );
        //console.log( topP_ );
        console.log( myForm );
        console.log( "pItemId_=" + pItemId_);
        //var myController = this;

        console.log( 'title=' + myTitle );

        //topP_.buttonHandler(edit_);

        Ext.Msg.show({
            title:'Save Changes?',
            //msg: 'Would you like to save your changes to ' + pItemId_ + ' ?',
            msg: 'Would you like to save your changes to ' + myTitle + ' ?',
            buttons: Ext.Msg.YESNO,
            icon: Ext.Msg.QUESTION,
            fn: function(buttonId) {
                var mystore		= Ext.getStore("faculty.ByStudentStore");
                if (buttonId == 'yes') {
                    Ext.Msg.show({
                        title: 'Save',
                        msg: 'record saved',
                        buttons: Ext.Msg.OK,
                        icon: Ext.window.MessageBox.INFO
                    });
                    //debugger;

                    var myTextArea	= myForm.down('textareafield');
                    var myName		= myTextArea.getName();
                    var myValue		= myTextArea.getValue();
                    var record		= myForm.getRecord();
                    record.set( myName, myValue );
                    record.set( 'lastUpdated', new Date() );
                    record.set( 'whoUpdated', 'login' );

                    mystore.sync();
                }
                else {
                    Ext.Msg.show({
                        title: 'Cancel',
                        msg: 'record restored',
                        buttons: Ext.Msg.OK,
                        icon: Ext.window.MessageBox.INFO
                    });
                    mystore.reload();
                }

            }
        });
        //debugger;
        this.buttonHandler( edit_ );
    },

    loadForm: function(form, selected) {
        debugger;

        console.log( form );
        //var textBox = myForm.dockedItems.items[0];
        //var textBox = myForm.down('textareafield');
        //textBox.name = fieldname;
        form.loadRecord( selected[0] );
        var myFields = form.getForm().getFields();
        for( var i = 0; i < myFields.length; i++ )
        {
            myFields.items[i].disable();
        }
        console.log('faculty.ByStudentController.loadForm(): completed');
    },

    onMyJsonStoreLoad: function() {
        //debugger;
        //var g_ = Ext.ComponentQuery.query("#monthlysummarygridpanel")[0];
        var g_ = this.getFacultyByStudentGridPanel();

        if (g_.getStore().getCount() > 0) {
            g_.getSelectionModel().deselectAll();
            g_.getSelectionModel().select( 0 );
        }

        this.gridViewReady = true;
    },

    init: function(application) {
        this.control({
            "#newfacultybystudentcanel": {
                click: this.onNewfacultybystudentcanelClick
            },
            "#newfacultybystudentsubmit": {
                click: this.onNewfacultybystudentsubmitClick
            },
            "#facultyprofilerefreshtool": {
                click: this.onFacultyprofilerefreshtoolClick
            },
            "#facultyprofilesearchtool": {
                click: this.onFacultyprofilesearchtoolClick
            },
            "#facultyprofilenewtool": {
                click: this.onFacultyprofilenewtoolClick
            },
            "#facultyprofilesavetool": {
                click: this.onFacultyprofilesavetoolClick
            },
            "#facultyprofiledeletetool": {
                click: this.onFacultyprofiledeletetoolClick
            },
            "#facultyprofilelocktool": {
                click: this.onFacultyprofilelocktoolClick
            },
            "#facultyprofilesbystudentgridpanel": {
                viewready: this.onFacultyprofilesbystudentgridpanelViewReady,
                selectionchange: this.onFacultyprofilesbystudentgridpanelSelectionChange
            },
            "#facultyprofilegridpanelenabled": {
                checkchange: this.onFacultyprofilegridpanelenabledCheckChange
            },
            "#facultyprofileform": {
                blur: this.onFacultyprofileformBlur,
                focus: this.onFacultyprofileformFocus
            },
            "#facultyprofileformeditbutton": {
                click: this.onFacultyprofileformeditbuttonClick
            },
            "#facultyprofileformcanelbutton": {
                click: this.onFacultyprofileformcanelbuttonClick
            },
            "#facultyprofileformsavebutton": {
                click: this.onFacultyprofileformsavebuttonClick
            }
        });
    }

});
