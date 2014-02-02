/*
 * File: app/controller/monthly/SummaryRatingsController.js
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

Ext.define('MySchool.controller.monthly.SummaryRatingsController', {
    extend: 'Ext.app.Controller',
    alias: 'controller.monthlysummaryratingscontroller',

    models: [
        'monthly.SummaryRatings'
    ],
    stores: [
        'monthly.SummaryRatingsStore',
        'student.StudentStore',
        'subject.SubjectStore'
    ],

    refs: [
        {
            ref: 'MonthlyDetailsTabPanel',
            selector: 'monthlydetailstabpanel'
        },
        {
            ref: 'MonthlyFeelingsTabPanel',
            selector: 'monthlyfeelingstabpanel'
        },
        {
            ref: 'MonthlyReflectionsTabPanel',
            selector: 'monthlyrelectionstabpanel'
        },
        {
            ref: 'MonthlyPatternsOfCorrectionsTabPanel',
            selector: 'monthlypatternsofcorrectionstabpanel'
        },
        {
            ref: 'MonthlyEffectivenessOfActionsTabPanel',
            selector: 'monthlyeffectivenessofactionstabpanel'
        },
        {
            ref: 'MonthlyActionResultsTabPanel',
            selector: 'monthlyactionresultstabpanel'
        },
        {
            ref: 'MonthlyRealizationsTabPanel',
            selector: 'monthlyrealizationstabpanel'
        },
        {
            ref: 'MonthlyPlannedChangesTabPanel',
            selector: 'monthlyplannedchangestabpanel'
        },
        {
            ref: 'MonthlyCommentsTabPanel',
            selector: 'monthlycommentstabpanel'
        },
        {
            ref: 'EditMonthlyPlannedChangesTabPanel',
            selector: '#editmonthlyplannedchangestabpanel'
        },
        {
            ref: 'MonthlyDetailsGridPanel',
            selector: 'monthlydetailsgridpanel'
        }
    ],

    onMonthlysummarygridpanelSelectionChange: function(model, selected, eOpts) {
        debugger;
        // in the onMyJsonStoreLoad we do a deselect so we need to test
        // if selected[0] has a value
        if ( Ext.isDefined( selected  ) && Ext.isDefined( selected[0]  )) {
            var tabPanel = this.getMonthlyFeelingsTabPanel();
                console.log( tabPanel );
            this.loadTabPanelForm( tabPanel, selected );
            tabPanel = this.getMonthlyReflectionsTabPanel();
                console.log( tabPanel );
            this.loadTabPanelForm( tabPanel, selected );
            tabPanel = this.getMonthlyPatternsOfCorrectionsTabPanel();
                console.log( tabPanel );
            this.loadTabPanelForm( tabPanel, selected );
            tabPanel = this.getMonthlyEffectivenessOfActionsTabPanel();
                console.log( tabPanel );
            this.loadTabPanelForm( tabPanel, selected );
            tabPanel = this.getMonthlyActionResultsTabPanel();
                console.log( tabPanel );
            this.loadTabPanelForm( tabPanel, selected );
            tabPanel = this.getMonthlyRealizationsTabPanel();
                console.log( tabPanel );
            this.loadTabPanelForm( tabPanel, selected );
            tabPanel = this.getMonthlyPlannedChangesTabPanel();
                console.log( tabPanel );
            this.loadTabPanelForm( tabPanel, selected );
            tabPanel = this.getMonthlyCommentsTabPanel();
                console.log( tabPanel );
            this.loadTabPanelForm( tabPanel, selected );

            console.log('onMonthlysummarygridpanelSelectionChange()');
        }
    },

    onMonthlysummarydetailspanelActivate: function(component, eOpts) {

    },

    onMonthlyfeelingstabpanelActivate: function(component, eOpts) {
        //debugger;
        console.log( "onMonthlyfeelingstabpanelActivate");
    },

    onMonthlysummarygridviewViewReady: function(dataview, eOpts) {
        debugger;
        console.log('onMonthlysummarygridviewViewReady()');
        var myStore = Ext.getStore('monthly.SummaryRatingsStore');
        var myStudentStore = Ext.getStore('student.StudentStore');
        var studentRecord = myStudentStore.getAt(0);
        //        debugger
        if ( typeof( studentRecord ) != "undefined" ) {
            var studentName_ = studentRecord.get('firstName') + " " + studentRecord.get('middleName') + ' ' + studentRecord.get('lastName');
            //MonthlyDetailsGridPanel
            //var myGrid = Ext.ComponentQuery.query("#bodiesofworkssubjectsgrid")[0];
            var myGrid = this.getMonthlyDetailsGridPanel();

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

    onFeelingstextboxBlur: function(component, e, eOpts) {
        this.blurHandler( component, e, this );
    },

    onEditmonthlyfeelingstabpanelClick: function(button, e, eOpts) {
        this.buttonHandler( button, e, eOpts );
    },

    onEditmonthlyreflectiontabpanelClick: function(button, e, eOpts) {
        this.buttonHandler( button, e, eOpts );
    },

    onReflectionstextboxBlur: function(component, e, eOpts) {
        this.blurHandler( component, e, this );
    },

    onEditmonthlypatternsofcorrectionstabpanelClick: function(button, e, eOpts) {
        this.buttonHandler( button, e, eOpts );
    },

    onPatternofcorrectionstextpadBlur: function(component, e, eOpts) {
        this.blurHandler( component, e, this );
    },

    onEffectivenestextboxBlur: function(component, e, eOpts) {
        this.blurHandler( component, e, this );
    },

    onEditmonthlyeffectivenessofactionstabpanelClick: function(button, e, eOpts) {
        this.buttonHandler( button, e, eOpts );
    },

    onActionresultstextboxBlur: function(component, e, eOpts) {
        this.blurHandler( component, e, this );
    },

    onEditmonthyactionresultstabpanelClick: function(button, e, eOpts) {
        this.buttonHandler( button, e, eOpts );
    },

    onEditmonthlyrealizationstabpanelClick: function(button, e, eOpts) {
        this.buttonHandler( button, e, eOpts );
    },

    onRealizationstextboxBlur: function(component, e, eOpts) {
        this.blurHandler( component, e, this );
    },

    onPlannedchangestextboxBlur: function(component, e, eOpts) {
        this.blurHandler( component, e, this );
    },

    onEditmonthlyplannedchangestabpanelClick: function(button, e, eOpts) {
        this.buttonHandler( button, e, eOpts );
    },

    onCommentstextboxBlur: function(component, e, eOpts) {
        this.blurHandler( component, e, this );
    },

    onRefreshmonthlysummariestoolClick: function(tool, e, eOpts) {
        var myStore = Ext.getStore('monthly.SummaryRatingsStore');
        myStore.reload();
    },

    onEditmonthlycommentstabpanelClick: function(button, e, eOpts) {
        this.buttonHandler( button, e, eOpts );
    },

    onSavemonthlysummariestoolClick: function(tool, e, eOpts) {
        window.console.log( "monthly.SummaryRatingsController.Save" );
        debugger;

        var mystore = Ext.getStore("monthly.SummaryRatingsStore");

        var records = mystore.getModifiedRecords();
        for( var i = 0; i < records.length; i++ )
        {
            records[i].set( 'lastUpdated', new Date() );
            records[i].set( 'whoUpdated', 'login');
        }

        mystore.sync();
    },

    onNewmonthlysummariestoolClick: function(tool, e, eOpts) {
        debugger;
        var studentStore = Ext.getStore('student.StudentStore');
        var subjectStore = Ext.getStore( 'subject.SubjectStore');
        var commonQuarterSubjectStore = Ext.getStore( 'common.QuarterSubjectStore');
        var commonMonthStore = Ext.getStore('common.MonthStore');

        var studentRecord = studentStore.getAt(0);
        var studentId = studentRecord.get( 'id' );
        var studentName = studentRecord.get( 'userName' );

        var newDialog = Ext.create( 'MySchool.view.monthly.summary.NewSummaryFormPanel' );

        newDialog.down('#newmonthlysummary-studentid').setValue( studentId );
        newDialog.down('#newmonthlysummary-studentname').setValue( studentName );

        commonQuarterSubjectStore.myLoad();
        commonMonthStore.myLoad();

        //commonQuarterSubjectStore.removeAll();


        //var numSubjects = subjectStore.count();


        //for( var i = 0; i < numSubjects; i++ )
        //{
        //    var record = subjectStore.getAt(i);
        //    var qtrName = record.get( 'qtrName' );
        //    var qtrYear = record.get( 'qtrYear' );
        //    var subjName = record.get( 'subjName' );
        //    var subjId = record.get( 'subjId' );
        //    var qtrId = record.get( 'qtrId' );
        //    var name = qtrName + '/' + qtrYear + '-' + subjName;
        //    console.log( 'name=' + name );
        //    commonQuarterSubjectStore.add({
        //        name: name,
        //        id: i,
        //        qtrName: qtrName,
        //        subjName: subjName,
        //        qtrId: qtrId,
        //        subjId: subjId
        //    });
        //}

        //commonMonthStore.removeAll();

        //commonMonthStore.add( { name: "Jan", id: 1});
        //commonMonthStore.add( { name: "Feb", id: 2});
        //commonMonthStore.add( { name: "Mar", id: 3});
        //commonMonthStore.add( { name: "Apr", id: 4});
        //commonMonthStore.add( { name: "May", id: 5});
        //commonMonthStore.add( { name: "Jun", id: 6});
        //commonMonthStore.add( { name: "Jul", id: 7});
        //commonMonthStore.add( { name: "Aug", id: 8});
        //commonMonthStore.add( { name: "Sep", id: 9});
        //commonMonthStore.add( { name: "Oct", id: 10});
        //commonMonthStore.add( { name: "Nov", id: 11});
        //commonMonthStore.add( { name: "Dec", id: 12});


        window.console.log( 'New Monthly Summary Dialog' );


        newDialog.render( Ext.getBody() );
        newDialog.show();
    },

    onNewmonthlysummarycanelClick: function(button, e, eOpts) {
        //debugger;
        window.console.log( "Cancel New Monthly Summary" );
        var myForm = button.up().getForm();
        myForm.reset();
        button.up().hide();
    },

    onNewmonthlysummarysubmitClick: function(button, e, eOpts) {
        debugger;
        window.console.log( "Submit New Monthly Summary" );
        var myForm = button.up().getForm();

        //Get the values from the form and insert a new record into the MonthlySummaryView.

        var formValues = myForm.getValues();
        var record = Ext.create('MySchool.model.monthly.SummaryRatings');
        var myStore = this.getStore( 'monthly.SummaryRatingsStore' );

        var studentStore_ = Ext.getStore('student.StudentStore');
        var subjectStore_ = Ext.getStore( 'subject.SubjectStore' );
        var myAllSubjStore = Ext.getStore("subject.AllSubjectStore");
        var subjAllEmpty_ = myAllSubjStore.getCount() < 1 ? true : false;
        var mynamestore = Ext.getStore( "subject.QuarterNameStore" );

        var qtrYrStore_ = Ext.getStore( "subject.QuarterYearStore" );

        var r_ = studentStore_.getAt(0);
        var studentId = r_.get( 'id' );
        var studentName = r_.get( 'userName' );

        var myFormFields = newDialog.getForm().getFields();
        //        var myuserName = myrecord.data.studentName;
        var qtrNameId_;
        var gradeType_;
        var subjId_ = null;
        var allSubjRec_;

        var subjNameCombo_ = newDialog.down('subjectnamecombobox');
        var qtrNameCombo_ = newDialog.down('quarternamescombobox');
        var qtrYearCombo_ = newDialog.down('quarteryearcombobox');

        var myGrid = this.getMonthlyDetailsGridPanel();
        //var mySelected = myGrid.getSelectionModel().getLastSelected();
        var subjName = subjNameCombo_.getValue();
        var qtrName = qtrNameCombo_.getValue();
        var qtrYear = qtrYearCombo_.getValue();
        //var qtrId = mySelected.get( 'qtrId');
        //var subjId = mySelected.get( 'subjId');

        var allSubjRec_ = myAllSubjStore.findRecord( 'subjName', subjName );
        var subjId_ = allSubjRec_.get( 'subjId' );


        //Add the data to the new record.
        if( formValues.month_number > 0 )
        {
            record.set('month_number', formValues.month_number);

            record.set('subjName', subjName );
            record.set('subjId', subjId_ );
            record.set('qtrName', qtrName );
            record.set('qtrId', formValues.quarterId);
            record.set('studentId', studentId);
            record.set('studentUserName', studentName);
            record.set('qtrYear', qtrYear);
            record.set('locked', 0 );
            record.set('feelings', formValues.feelings);
            record.set('patternsOfCorrections', formValues.patternsofcorrections);
            record.set('effectivenessOfActions', formValues.effectivenessofactions);
            record.set('realizations', formValues.realizations);
            record.set('reflections', formValues.reflections);
            record.set('plannedChanges', formValues.plannedchanges);
            record.set('comments', formValues.comments);
            record.set('actionResults', formValues.actionresults);
            record.set('whoUpdated', 'login');
            record.set('lastUpdated', new Date());

            //add to the store

            myStore.add( record );

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

    },

    blurHandler: function(o, event, eOpts) {
        debugger;
        var p_ = o.up('form').up('panel');
        var topP_ = p_.up('panel');
        var pItemId_ = p_.getItemId();
        var edit_ = p_.down('#edit' + pItemId_);

        Ext.Msg.show({
            title:'Save Changes?',
            msg: 'Would you like to save your changes to ' + pItemId_ + ' ?',
            buttons: Ext.Msg.YESNO,
            icon: Ext.Msg.QUESTION,
            fn: function(buttonId) {
                if (buttonId == 'yes') {
                    Ext.Msg.show({
                        title: 'Save',
                        msg: 'record saved',
                        buttons: Ext.Msg.OK,
                        icon: Ext.window.MessageBox.INFO
                    });
                }
                else {
                    Ext.Msg.show({
                        title: 'Cancel',
                        msg: 'record restored',
                        buttons: Ext.Msg.OK,
                        icon: Ext.window.MessageBox.INFO
                    });
                }
                topP_ = eOpts;
                //topP_.buttonHandler.call(edit_);
                topP_.buttonHandler(edit_);
            }
        });
    },

    buttonHandler: function(button, e, eOpts) {
        debugger;
        window.console.log(button);
        var b_ = button;
        var p_ = b_.up('panel');
        var pItemId_ = p_.getItemId();
        var field_;

        //if (pItemId_ == 'monthlyfeelingstabpanel') {
        //    field_ = p_.down('numberfield');
        //} else {
        //    field_ = p_.down('textareafield');
        //}
        field_ = p_.down('textareafield');

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

    onMyJsonStoreLoad: function() {
        //debugger;
        //var g_ = Ext.ComponentQuery.query("#monthlysummarygridpanel")[0];
        var g_ = this.getMonthlyDetailsGridPanel();

        if (g_.getStore().getCount() > 0) {
            g_.getSelectionModel().deselectAll();
            g_.getSelectionModel().select( 0 );
        }

        this.gridViewReady = true;
    },

    loadTabPanelForm: function(tabPanel, selected) {
        var dockedItems = tabPanel.getDockedItems();
        var myForm = dockedItems[0].getForm();
        if( Ext.isDefined( myForm ) === false )
        {
            myForm = dockedItems[1].getForm();
        }
        if( Ext.isDefined( myForm ) )
        {
            console.log( myForm );
            myForm.loadRecord( selected[0] );
        }
        else
        {
            console.log( 'No form' );
            console.log( tabPenel );
        }
    },

    init: function(application) {
        this.control({
            "#monthlysummarygridpanel": {
                selectionchange: this.onMonthlysummarygridpanelSelectionChange
            },
            "#monthlysummarydetailspanel": {
                activate: this.onMonthlysummarydetailspanelActivate
            },
            "#monthlyfeelingstabpanel": {
                activate: this.onMonthlyfeelingstabpanelActivate
            },
            "#monthlysummarygridview": {
                viewready: this.onMonthlysummarygridviewViewReady
            },
            "#feelingstextbox": {
                blur: this.onFeelingstextboxBlur
            },
            "#editmonthlyfeelingstabpanel": {
                click: this.onEditmonthlyfeelingstabpanelClick
            },
            "#editmonthlyreflectiontabpanel": {
                click: this.onEditmonthlyreflectiontabpanelClick
            },
            "#reflectionstextbox": {
                blur: this.onReflectionstextboxBlur
            },
            "#editmonthlypatternsofcorrectionstabpanel": {
                click: this.onEditmonthlypatternsofcorrectionstabpanelClick
            },
            "#patternofcorrectionstextpad": {
                blur: this.onPatternofcorrectionstextpadBlur
            },
            "#effectivenestextbox": {
                blur: this.onEffectivenestextboxBlur
            },
            "#editmonthlyeffectivenessofactionstabpanel": {
                click: this.onEditmonthlyeffectivenessofactionstabpanelClick
            },
            "#actionresultstextbox": {
                blur: this.onActionresultstextboxBlur
            },
            "#editmonthyactionresultstabpanel": {
                click: this.onEditmonthyactionresultstabpanelClick
            },
            "#editmonthlyrealizationstabpanel": {
                click: this.onEditmonthlyrealizationstabpanelClick
            },
            "#realizationstextbox": {
                blur: this.onRealizationstextboxBlur
            },
            "#plannedchangestextbox": {
                blur: this.onPlannedchangestextboxBlur
            },
            "#editmonthlyplannedchangestabpanel": {
                click: this.onEditmonthlyplannedchangestabpanelClick
            },
            "#commentstextbox": {
                blur: this.onCommentstextboxBlur
            },
            "#refreshmonthlysummariestool": {
                click: this.onRefreshmonthlysummariestoolClick
            },
            "#editmonthlycommentstabpanel": {
                click: this.onEditmonthlycommentstabpanelClick
            },
            "#savemonthlysummariestool": {
                click: this.onSavemonthlysummariestoolClick
            },
            "#newmonthlysummariestool": {
                click: this.onNewmonthlysummariestoolClick
            },
            "#newmonthlysummarycanel": {
                click: this.onNewmonthlysummarycanelClick
            },
            "#newmonthlysummarysubmit": {
                click: this.onNewmonthlysummarysubmitClick
            }
        });
    }

});
