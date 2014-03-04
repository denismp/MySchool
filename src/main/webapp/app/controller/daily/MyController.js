/*
 * File: app/controller/daily/MyController.js
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

Ext.define('MySchool.controller.daily.MyController', {
    extend: 'Ext.app.Controller',
    alias: 'controller.dailycontroller',

    models: [
        'daily.DailyModel'
    ],
    stores: [
        'daily.MyJsonStore',
        'subject.SubjectStore',
        'student.StudentStore'
    ],

    refs: [
        {
            ref: 'DailyGridPanel',
            selector: '#dailygridpanel'
        },
        {
            ref: 'DailyResourcesUsedTabPanel',
            selector: '#dailyresourcesusedtabpanel'
        },
        {
            ref: 'DailyStudyDetailsTabPanel',
            selector: '#dailystudydetailstabpanel'
        },
        {
            ref: 'DailyDetailsEvaluationTabPanel',
            selector: '#dailydetailsevaluationtabpanel'
        },
        {
            ref: 'DailyDetailsActionsTabPanel',
            selector: '#dailydetailsactionstabpanel'
        },
        {
            ref: 'DailyCommentsTabPanel',
            selector: '#dailydetailscommentstabpanel'
        },
        {
            ref: 'DailyDetailsCorrectionTabPanel',
            selector: '#dailydetailscorrectiontabpanel'
        }
    ],

    onEditdailyhourstabClick: function(button, e, eOpts) {
        this.buttonHandler( button, e, eOpts );
    },

    onDailyrefreshtoolClick: function(tool, e, eOpts) {
        var myStore = Ext.getStore('daily.MyJsonStore');
        myStore.reload();
    },

    onDailysearchtoolClick: function(tool, e, eOpts) {

    },

    onDailynewtoolClick: function(tool, e, eOpts) {
        debugger;
        var studentStore				= Ext.getStore('student.StudentStore');
        var subjectStore				= Ext.getStore('subject.SubjectStore');
        var commonQuarterSubjectStore	= Ext.getStore( 'common.QuarterSubjectStore');
        var commonMonthStore			= Ext.getStore('common.MonthStore');

        var studentRecord	= studentStore.getAt(0);
        var studentId		= studentRecord.get( 'id' );
        var studentName		= studentRecord.get( 'userName' );

        var newDialog = Ext.create( 'MySchool.view.daily.NewDailyForm' );

        newDialog.down('#daily-studentid').setValue( studentId );
        newDialog.down('#daily-studentname').setValue( studentName );

        //commonQuarterSubjectStore.myLoad();
        commonMonthStore.myLoad();

        window.console.log( 'New Daily Dialog' );

        newDialog.render( Ext.getBody() );
        newDialog.show();
    },

    onDailysavetoolClick: function(tool, e, eOpts) {
        window.console.log( "daily.MyController.Save" );
        debugger;

        var mystore = Ext.getStore("daily.MyJsonStore");

        var records = mystore.getModifiedRecords();
        for( var i = 0; i < records.length; i++ )
        {
            records[i].set( 'lastUpdated', new Date() );
            records[i].set( 'whoUpdated', 'login');
        }

        mystore.sync();
    },

    onDailydeletetoolClick: function(tool, e, eOpts) {

    },

    onDailylocktoolClick: function(tool, e, eOpts) {

    },

    onDailygridpanelSelectionChange: function(model, selected, eOpts) {
        debugger;
        // in the onMyJsonStoreLoad we do a deselect so we need to test
        // if selected[0] has a value
        if ( Ext.isDefined( selected  ) && Ext.isDefined( selected[0]  )) {
            var tabPanel = this.getDailyResourcesUsedTabPanel();
                console.log( tabPanel );
            this.loadTabPanelForm( tabPanel, selected, 'resourcesUsed' );
            tabPanel = this.getDailyStudyDetailsTabPanel();
                console.log( tabPanel );
            this.loadTabPanelForm( tabPanel, selected, 'studyDetails' );
            tabPanel = this.getDailyDetailsEvaluationTabPanel();
                console.log( tabPanel );
            this.loadTabPanelForm( tabPanel, selected, 'evaluation' );
            tabPanel = this.getDailyDetailsCorrectionTabPanel();
                console.log( tabPanel );
            this.loadTabPanelForm( tabPanel, selected, 'correction' );
            tabPanel = this.getDailyDetailsActionsTabPanel();
                console.log( tabPanel );
            this.loadTabPanelForm( tabPanel, selected, 'dailyAction' );
            tabPanel = this.getDailyCommentsTabPanel();
                console.log( tabPanel );
            this.loadTabPanelForm( tabPanel, selected, 'comments' );

            console.log('onDailygridpanelSelectionChange()');
        }
    },

    onDailygridpanelViewReady: function(tablepanel, eOpts) {
        debugger;
        console.log('onDailygridpanelViewReady()');
        var myStore = Ext.getStore('daily.MyJsonStore');
        var myStudentStore = Ext.getStore('student.StudentStore');
        var studentRecord = myStudentStore.getAt(0);
        //        debugger
        if ( typeof( studentRecord ) != "undefined" ) {
            var studentName_ = studentRecord.get('firstName') + " " + studentRecord.get('middleName') + ' ' + studentRecord.get('lastName');
            //MonthlyDetailsGridPanel
            //var myGrid = Ext.ComponentQuery.query("#bodiesofworkssubjectsgrid")[0];
            var myGrid = this.getDailyGridPanel();

            myGrid.setTitle('[' + studentName_ + ']');
            myStore.load({
                callback: this.onMyJsonStoreLoad,
                scope: this,
                params: {
                    studentName: studentRecord.get('userName'),
                    studentId: studentRecord.get('studentId')
                }
            });
        }
        //grid.getSelectionModel().select( 0 );
        //tablepanel.getSelectionModel().select( 0 );

    },

    onDailysubmitClick: function(button, e, eOpts) {
        debugger;
        window.console.log( "Submit New Daily" );
        var myForm					= button.up().getForm();
        //var newDialog = button.up('monthlynewsummaryformpanel');

        //Get the values from the form and insert a new record into the MonthlySummaryView.

        var formValues				= myForm.getValues();

        //	Create an empty record
        var dailyRecord	= Ext.create('MySchool.model.daily.DailyModel');

        //	Get the stores that we will need
        var dailyStore		= this.getStore( 'daily.MyJsonStore' );

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
                dailyRecord.set('daily_month', formValues.combomonth);
                dailyRecord.set('daily_day', formValues.daily_day);
                dailyRecord.set('daily_hours', 0);

                dailyRecord.set('subjName', subjName );
                dailyRecord.set('subjId', subjId );
                dailyRecord.set('qtrName', qtrName );
                dailyRecord.set('qtrId', qtrId);
                dailyRecord.set('studentId', studentId);
                dailyRecord.set('studentUserName', studentName);
                //dailyRecord.set('qtrYear', qtrYear);
                dailyRecord.set('daily_year', qtrYear );
                dailyRecord.set('locked', 0 );
                dailyRecord.set('resourcesUsed', formValues.resourcesUsed);
                dailyRecord.set('studyDetails', formValues.studyDetails);
                dailyRecord.set('evaluation', formValues.evaluation);
                dailyRecord.set('correction', formValues.correction);
                dailyRecord.set('dailyAction', formValues.dailyAction);
                dailyRecord.set('comments', formValues.comments);

                dailyRecord.set('whoUpdated', 'login');
                dailyRecord.set('lastUpdated', new Date());
                dailyRecord.set('version', null);
                dailyRecord.set( 'dailyId', 0 );

                //add to the store

                dailyStore.add( dailyRecord );

                //sync the store.
                dailyStore.sync();

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

    onDailycancelClick: function(button, e, eOpts) {
        //debugger;
        window.console.log( "Cancel New Daily" );
        var myForm = button.up().getForm();
        myForm.reset();
        button.up().hide();
    },

    blurHandler: function(o, event, eOpts) {
        //debugger;
        var p_			= o.up('form').up('panel');
        var myForm		= o.up('form');
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
                var mystore		= Ext.getStore("daily.MyJsonStore");
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

    init: function(application) {
                this.control({
                    "#editdailytesttabpanel": {
                        click: this.buttonHandler
                    },
                    "#dailytesttabpaneltextbox": {
                        blur: this.blurHandler
                    },
                    "#editdailyresourcesusedtabpanel": {
                        click: this.buttonHandler
                    },
                    "#dailyresourcesusedtabpaneltextbox": {
                        blur: this.blurHandler
                    },
                    "#editdailystudydetailstabpanel": {
                        click: this.buttonHandler
                    },
                    "#dailystudydetailstabpaneltextbox": {
                        blur: this.blurHandler
                    },
                    "#editdailydetailsevaluationtabpanel": {
                        click: this.buttonHandler
                    },
                    "#dailydetailsevaluationtabpaneltextbox": {
                        blur: this.blurHandler
                    },
                    "#editdailydetailscorrectiontabpanel": {
                        click: this.buttonHandler
                    },
                    "#dailydetailscorrectiontabpaneltextbox": {
                        blur: this.blurHandler
                    },
                    "#editdailydetailsactionstabpanel": {
                        click: this.buttonHandler
                    },
                    "#dailydetailsactionstabpaneltextbox": {
                        blur: this.blurHandler
                    },
                    "#editdailydetailscommentstabpanel": {
                        click: this.buttonHandler
                    },
                    "#dailydetailscommentstabpaneltextbox": {
                        blur: this.blurHandler
                    }

                });

        this.control({
            "#editdailyhourstab": {
                click: this.onEditdailyhourstabClick
            },
            "#dailyrefreshtool": {
                click: this.onDailyrefreshtoolClick
            },
            "#dailysearchtool": {
                click: this.onDailysearchtoolClick
            },
            "#dailynewtool": {
                click: this.onDailynewtoolClick
            },
            "#dailysavetool": {
                click: this.onDailysavetoolClick
            },
            "#dailydeletetool": {
                click: this.onDailydeletetoolClick
            },
            "#dailylocktool": {
                click: this.onDailylocktoolClick
            },
            "#dailygridpanel": {
                selectionchange: this.onDailygridpanelSelectionChange,
                viewready: this.onDailygridpanelViewReady
            },
            "#dailysubmit": {
                click: this.onDailysubmitClick
            },
            "#dailycancel": {
                click: this.onDailycancelClick
            }
        });
    },

    onMyJsonStoreLoad: function() {
        //debugger;
        //var g_ = Ext.ComponentQuery.query("#monthlysummarygridpanel")[0];
        var g_ = this.getDailyGridPanel();

        if (g_.getStore().getCount() > 0) {
            g_.getSelectionModel().deselectAll();
            g_.getSelectionModel().select( 0 );
        }

        this.gridViewReady = true;
    },

    loadTabPanelForm: function(tabPanel, selected, fieldname) {
        debugger;
        var dockedItems = tabPanel.getDockedItems();
        var myForm = dockedItems[0];

        if( Ext.isDefined( myForm ) )
        {
            console.log( myForm );
            //var textBox = myForm.dockedItems.items[0];
            var textBox = myForm.down('textareafield');
            textBox.name = fieldname;
            myForm.loadRecord( selected[0] );
        }
        else
        {
            console.log( 'loadTabPanelForm(): No form' );
            //console.log( tabPanel );
        }
    }

});
