/*
 * File: app/controller/subject/SubjectsController.js
 *
 * This file was generated by Sencha Architect version 3.0.4.
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
		'subject.GradeTypeStore',
		'subject.AllSubjectStore',
		'subject.QuarterYearStore'
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
		if( false )
		{
		window.console.log( 'selectedIndex=' + this.selectedIndex );
		window.console.log( "onSubjectdescriptiontextareraChange() field=" + field );
		var mystore = Ext.getStore("subject.SubjectStore");
		var myrecord = mystore.getAt( this.selectedIndex );
		myrecord.set( 'subjDescription', newValue );
		}
	},

	onSubjectobjectivetextareaChange: function(field, newValue, oldValue, eOpts) {
		if( false )
		{
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
		//        debugger;
		window.console.log( 'New' );
		var newDialog = Ext.create( 'MySchool.view.subject.NewForm' );
		//window.console.log( "DEBUG" );
		//newDialog.show();

		var mystore = Ext.getStore("subject.SubjectStore");
		var myAllSubjStore = Ext.getStore("subject.AllSubjectStore");
		var subjAllEmpty_ = myAllSubjStore.getCount() < 1 ? true : false;
		var mynamestore = Ext.getStore( "subject.QuarterNameStore" );
		var mygradetypestore = Ext.getStore( "subject.GradeTypeStore" );
		var qtrYrStore_ = Ext.getStore( "subject.QuarterYearStore" );
		var myrecord = mystore.getAt( this.selectedIndex );

		//myrecord.set( 'description', newValue );
		//window.console.log( myrecord.data );
		//        newDialog.loadRecord(myrecord);
		var myForm = newDialog.getForm();
		var myFormFields = newDialog.getForm().getFields();
		//        var myuserName = myrecord.data.studentName;
		var qtrNameId_;
		var gradeType_;
		var subjId_ = null;
		var allSubjRec_;

		var subjNameCombo_ = newDialog.down('subjectnamecombobox');
		var qtrNameCombo_ = newDialog.down('quarternamescombobox');
		var gradeTypeCombo_ = newDialog.down('gradetypecombobox');
		var qtrYearCombo_ = newDialog.down('quarteryearcombobox');
		var studentName_ = newDialog.down('#newsubjectform-studentName');
		var newsubjectedit_ = newDialog.down('#newsubjectedit');
		var newsubjectcreate_ = newDialog.down('#newsubjectcreate');
		var newsubjectsubmit_ = newDialog.down('#newsubjectsubmit');

		if (myrecord) {
		    window.console.log( myrecord.data );
		}

		studentName_.setValue(this.studentName);

		qtrYearCombo_.setValue(myrecord ? parseInt(myrecord.data.qtrYear) : new Date().getFullYear());

		if (myrecord) {
		    qtrNameId_ = mynamestore.findRecord( 'qtrName', myrecord.data.qtrName ).get( 'id' );
		}
		else {
		    qtrNameId_ = mynamestore.getAt(0).get( 'id' );
		}
		qtrNameCombo_.setValue( qtrNameId_ );

		if (myrecord) {
		    allSubjRec_ = myAllSubjStore.findRecord( 'subjName', myrecord.data.subjName );
		    subjId_ = allSubjRec_.get( 'subjId' );
		}
		else {
		    allSubjRec_ = myAllSubjStore.getAt(0);
		    if (allSubjRec_) {
		        subjId_ = allSubjRec_.get( 'subjId' );
		    }
		}

		if (subjId_) {
		    subjNameCombo_.setValue(subjId_);
		    this.onSubjComboSelect( subjNameCombo_, allSubjRec_, eOpts);
		}
		else {
		    // no subject records in database
		    newsubjectedit_.setDisabled(true);
		    newsubjectsubmit_.setDisabled(true);
		    subjNameCombo_.setDisabled(true);
		    qtrNameCombo_.setDisabled(true);
		    gradeTypeCombo_.setDisabled(true);
		    qtrYearCombo_.setDisabled(true);
		}

		if (myrecord) {
		    gradeType_ = myrecord.data.qtrGradeType;
		}
		else {
		    gradeType_ = mygradetypestore.getAt(0).get( 'value' );
		}
		gradeTypeCombo_.setValue( gradeType_ );

		//        newDialog.getForm().setValues( { userName: myuserName } );

		newDialog.subjEditMode = 'relate';

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
		var p_ = button.up('newsubjectform');
		var f_ = button.up().getForm();
		var cb_ = p_.down('subjectnamecombobox');
		var okToSync_ = true;
		var subjAllVal_ = cb_.getValue();
		var subjAllIdx_ = cb_.getStore().findExact('subjId', subjAllVal_);
		var subjAllRec_ = cb_.getStore().getAt(subjAllIdx_);
		var subjName_ = null;
		var subjAllEmpty_ = cb_.getStore().getCount() < 1 ? true : false;

		if (p_.subjEditMode.charAt(0) == 'r') {
		    subjName_ = subjAllRec_.data.subjName;
		}
		else {
		    subjName_ = p_.down('#newsubjectform-subjName');
		    subjName_ = Ext.String.trim(subjName_.getValue());
		}

		if (subjName_.length < 1) {
		    Ext.MessageBox.show({
		        title: 'Submit Exception',
		        msg: 'Subject name is empty.',
		        icon: Ext.MessageBox.ERROR,
		        buttons: Ext.Msg.OK
		    });
		    okToSync_ = false;
		}

		if (okToSync_ && p_.subjEditMode.charAt(0) == 'r') {
		    var qfCB_ = p_.down('#facultynamescombobox');
		    var qnCB_ = p_.down('quarternamescombobox');
		    var qyCB_ = p_.down('quarteryearcombobox');
		    var gtCB_ = p_.down('gradetypecombobox');
		    var gStore_ = Ext.getStore("subject.SubjectStore");
		    var recCnt_ = gStore_.getTotalCount();
		    //          var recs_ = gStore_.getRange( 0, recCnt_ );
		    var sv_ = qnCB_.getValue();
		    var idx_ = qnCB_.getStore().findExact('id', sv_);
		    var r_;
		    var qtrName_;
		    var qtrYear_;
		    var facultyId;

		    r_ = qnCB_.getStore().getAt(idx_);
		    qtrName_ = r_.data.qtrName;
		    qtrYear_ = qyCB_.getValue();
		    facultyId = qfCB_.getValue();

		    for( var i_ = 0; i_ < recCnt_; i_++ ) {
		        r_ = gStore_.getAt(i_);
		        if (r_ !== null						&&
		            subjName_ == r_.get('subjName')	&&
		            qtrName_ == r_.get('qtrName')	&&
		            qtrYear_ == r_.get('qtrYear'))
		        {
		            okToSync_ = false;
		            break;
		        }
		    }

		    if (okToSync_) {
				if (typeof(facultyId) != "undefined" && facultyId !== null )
				{
					r_ = Ext.create( 'MySchool.model.subject.SubjectsModel' );

					r_.set('subjId', subjAllRec_.get('subjId'));

					r_.set('facultyId', facultyId );

					r_.set('studentName', this.studentName);

					r_.set('qtrGrade', 0);
					r_.set('qtrGradeType', gtCB_.getValue());
					r_.set('qtrLastUpdated', new Date());
					r_.set('qtrName', qtrName_);
					r_.set('qtrWhoUpdated', 'login');
					r_.set('qtrYear', qtrYear_);

					gStore_.add(r_);
					gStore_.sync();
				}
				else
				{
					Ext.MessageBox.show({
						title: 'Submit Exception',
						msg: 'You must specify a faculty.',
						icon: Ext.MessageBox.ERROR,
						buttons: Ext.Msg.OK
					});
				}
		    }
		    else {
		        Ext.MessageBox.show({
		            title: 'Submit Exception',
		            msg: 'Subject and Quarter already related.',
		            icon: Ext.MessageBox.ERROR,
		            buttons: Ext.Msg.OK
		        });
		    }
		}
		else if (okToSync_) {
		    var subjDescription_ = p_.down('#newsubjectform-subjDescription');
		    var subjObjectives_ = p_.down('#newsubjectform-subjObjectives');
		    var subjGradeLevel_ = p_.down('#newsubjectform-subjGradeLevel');
		    var subjCreditHours_ = p_.down('#newsubjectform-subjCreditHours');
		    var oldIdx_ = subjAllIdx_;
		    var idx_ = cb_.getStore().findExact('subjName', subjName_);
		    var edit_ = p_.subjEditMode.charAt(0) == 'e';

		    if (! subjDescription_.isValid()) {
		        Ext.MessageBox.show({
		            title: 'Submit Exception',
		            msg: 'Subject description is not valid.',
		            icon: Ext.MessageBox.ERROR,
		            buttons: Ext.Msg.OK
		        });
		        okToSync_ = false;
		    }

		    if (! subjObjectives_.isValid()) {
		        Ext.MessageBox.show({
		            title: 'Submit Exception',
		            msg: 'Subject objectives is not valid.',
		            icon: Ext.MessageBox.ERROR,
		            buttons: Ext.Msg.OK
		        });
		        okToSync_ = false;
		    }

		    if (! subjGradeLevel_.isValid()) {
		        Ext.MessageBox.show({
		            title: 'Submit Exception',
		            msg: 'Subject Grade Level is not valid.',
		            icon: Ext.MessageBox.ERROR,
		            buttons: Ext.Msg.OK
		        });
		        okToSync_ = false;
		    }

		    if (! subjCreditHours_.isValid()) {
		        Ext.MessageBox.show({
		            title: 'Submit Exception',
		            msg: 'Subject Credit Hours is not valid.',
		            icon: Ext.MessageBox.ERROR,
		            buttons: Ext.Msg.OK
		        });
		        okToSync_ = false;
		    }

		    if (idx_ > -1 && (edit_ &&  oldIdx_ != idx_ || ! edit_)) {
		        Ext.MessageBox.show({
		            title: 'Submit Exception',
		            msg: 'Subject name is already in use.',
		            icon: Ext.MessageBox.ERROR,
		            buttons: Ext.Msg.OK
		        });
		        okToSync_ = false;
		    }

		    if (okToSync_) {
		        var r_;

		        if (edit_) {
		            r_ = subjAllRec_;
		        }
		        else {
		            r_ = Ext.create( 'MySchool.model.subject.SubjectsModel' );
		            cb_.getStore().add( r_ );
		        }

		        if (subjAllEmpty_ || oldIdx_ != idx_) {
		            r_.set('subjName', subjName_);
		        }

		        subjDescription_ = Ext.String.trim(subjDescription_.getValue());
		        subjObjectives_ = Ext.String.trim(subjObjectives_.getValue());

		        r_.set('subjCreditHours', subjCreditHours_.getValue());
		        r_.set('subjDescription', subjDescription_);
		        r_.set('subjGradeLevel', subjGradeLevel_.getValue());
		        r_.set('subjLastUpdated', new Date());
		        r_.set('subjObjectives', subjObjectives_);
		        r_.set('subjWhoUpdated', 'login');

		        if (edit_) {
		            cb_.getStore().sync({callback: this.onToolrefreshsubjectsClick});
		        }
		        else {
		            cb_.getStore().sync();
		        }
		    }
		}

		f_.reset();
		button.up().hide();

	},

	onNewsubjectcancelClick: function(button, e, eOpts) {
		debugger;
		window.console.log( "Cancel New Subject" );
		var myForm = button.up().getForm();
		myForm.reset();
		button.up().hide();
	},

	onSubjectformeditClick: function(button, e, eOpts) {
		debugger;
		window.console.log( 'Subject Edit' );
		var newDialog = Ext.create( 'MySchool.view.subject.EditForm' );
		var mystore = Ext.getStore("subject.SubjectStore");
		var myrecord = mystore.getAt( this.selectedIndex );
		newDialog.loadRecord(myrecord);

		newDialog.render( Ext.getBody() );
		newDialog.show();
	},

	onEditsubjectcancelbuttonClick: function(button, e, eOpts) {
		//debugger;
		window.console.log( "Cancel Edit Subject" );
		var myForm = button.up().getForm();
		myForm.reset();
		button.up().hide();
	},

	onEditsubjectsubmitbuttonClick: function(button, e, eOpts) {
		debugger;
		window.console.log( "Submit Edit Subject" );
		var myForm = button.up().getForm();


		var formValues = myForm.getValues();
		//var myrecords;
		//myrecords[this.selectedIndex].set( 'subjDescription', formValues.subjDescription );
		//myrecords[this.selectedIndex].set( 'subjObjectives', formValues.subjObjectives );
		window.console.log( 'objectives=' + formValues.subjObjectives );
		window.console.log( 'description=' + formValues.subjDescription );

		var mystore = Ext.getStore("subject.SubjectStore");
		var value = mystore.getAt( this.selectedIndex ).get( 'subjDescription' );
		window.console.log( "Store subjDescription is ", value );
		value = mystore.getAt( this.selectedIndex ).get( 'subjObjectives' );
		window.console.log( "Store subjObjectives are ", value );

		mystore.getAt( this.selectedIndex ).set( 'subjLastUpdated', new Date() );
		mystore.getAt( this.selectedIndex ).set( 'subjDescription', formValues.subjDescription );
		mystore.getAt( this.selectedIndex ).set( 'subjObjectives', formValues.subjObjectives );

		value = mystore.getAt( this.selectedIndex ).get( 'subjDescription' );
		window.console.log( "NEW STORE subjDescription=" + value );
		value = mystore.getAt( this.selectedIndex ).get( 'subjObjectives' );
		window.console.log( "NEW STORE subjObjectives=" + value );


		mystore.sync();

		myForm.reset();
		button.up().hide();
	},

	onEditsubjectdescriptionChange: function(field, newValue, oldValue, eOpts) {
		window.console.log( "oldValue=" + oldValue );
		window.console.log( "newValue=" + newValue );
	},

	onEditsubjectobjectivesChange: function(field, newValue, oldValue, eOpts) {

	},

	onNewsubjecteditClick: function(button, e, eOpts) {
		debugger;
		var myPanel_ = button.up('newsubjectform');
		var myForm_ = button.up().getForm();
		var subjectnamecombobox_ = myPanel_.down('subjectnamecombobox');
		var quarternamescombobox = myPanel_.down('quarternamescombobox');
		var quarteryearcombobox = myPanel_.down('quarteryearcombobox');
		var gradetypecombobox = myPanel_.down('gradetypecombobox');
		var facultynamescombobox = myPanel_.down('#facultynamescombobox');
		var newsubjectformname_ = myPanel_.down('#newsubjectform-subjName');
		var studentName_ = myPanel_.down('#newsubjectform-studentName');
		var subjGradeLevel_ = myPanel_.down('#newsubjectform-subjGradeLevel');
		var subjCreditHours_ = myPanel_.down('#newsubjectform-subjCreditHours');
		var subjDescription_ = myPanel_.down('#newsubjectform-subjDescription');
		var subjObjectives_ = myPanel_.down('#newsubjectform-subjObjectives');
		var newsubjectedit_ = myPanel_.down('#newsubjectedit');
		var newsubjectcreate_ = myPanel_.down('#newsubjectcreate');
		var newsubjectsubmit_ = myPanel_.down('#newsubjectsubmit');

		newsubjectsubmit_.setText('Submit');
		newsubjectsubmit_.setDisabled(false);

		newsubjectedit_.setDisabled(true);
		newsubjectcreate_.setDisabled(true);

		subjectnamecombobox_.setVisible(false);
		newsubjectformname_.setVisible(true);
		subjGradeLevel_.setDisabled(false);
		subjCreditHours_.setDisabled(false);
		subjDescription_.setDisabled(false);
		subjObjectives_.setDisabled(false);
		quarternamescombobox.setDisabled(true);
		quarteryearcombobox.setDisabled(true);
		gradetypecombobox.setDisabled(true);
		facultynamescombobox.setDisabled(true);

		if (button.getItemId().indexOf("create") > 0) {
		    newsubjectformname_.setValue("replace_me_with_name");
		    subjDescription_.setValue("replace_me_with_description");
		    subjObjectives_.setValue("replace_me_with_objectives");

		    myPanel_.subjEditMode = 'create';
		}
		else {
		    myPanel_.subjEditMode = 'edit';
		}

	},

	onNewsubjectcreateClick: function(button, e, eOpts) {
		this.onNewsubjecteditClick(button, e, eOpts);


	},

	onSubjComboSelect: function(combo, records, eOpts) {
		//    	debugger;
		window.console.log("onSubjComboSelect");
		var p_ = combo.up('newsubjectform');
		var subjName_ = p_.down('#newsubjectform-subjName');
		var subjGradeLevel_ = p_.down('#newsubjectform-subjGradeLevel');
		var subjCreditHours_ = p_.down('#newsubjectform-subjCreditHours');
		var subjDescription_ = p_.down('#newsubjectform-subjDescription');
		var subjObjectives_ = p_.down('#newsubjectform-subjObjectives');
		var r_ = Ext.isArray(records) ? records[0] : records;

		subjName_.setValue(r_.data.subjName);
		subjGradeLevel_.setValue(r_.data.subjGradeLevel);
		subjCreditHours_.setValue(r_.data.subjCreditHours);
		subjDescription_.setValue(r_.data.subjDescription);
		subjObjectives_.setValue(r_.data.subjObjectives);
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
			},
			"#subjectformedit": {
				click: this.onSubjectformeditClick
			},
			"#editsubjectcancelbutton": {
				click: this.onEditsubjectcancelbuttonClick
			},
			"#editsubjectsubmitbutton": {
				click: this.onEditsubjectsubmitbuttonClick
			},
			"#editsubjectdescription": {
				change: this.onEditsubjectdescriptionChange
			},
			"#editsubjectobjectives": {
				change: this.onEditsubjectobjectivesChange
			},
			"#newsubjectedit": {
				click: this.onNewsubjecteditClick
			},
			"#newsubjectcreate": {
				click: this.onNewsubjectcreateClick
			},
			"#subjectnamecombobox": {
				select: this.onSubjComboSelect
			}
		});
	},

	onViewReady: function(grid) {
		grid.getSelectionModel().select( 0 );
	},

	onSubjectsavetoolidClick: function(tool, e, eOpts) {
		window.console.log( "Save" );
		//debugger;

		var mystore = Ext.getStore("subject.SubjectStore");

		var records = mystore.getModifiedRecords();
		for( var i = 0; i < records.length; i++ )
		{
		    records[i].set( 'qtrLastUpdated', new Date() );
		    //records[i].set( 'quarter.lastUpdated', new Date() );
		    records[i].set( 'qtrWhoUpdated', 'login');
		    if( false )
		    {
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
		debugger;
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
		debugger;
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
		debugger;
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
		//debugger;
		// Use the automatically generated getter to get the store
		//        debugger;
		var studentStore_ = Ext.getStore('student.StudentStore');
		var codeStore_ = Ext.getStore( "subject.QuarterNameStore" );
		var allSubjectStore_ = Ext.getStore( "subject.AllSubjectStore" );
		var qtrYrStore_ = Ext.getStore( "subject.QuarterYearStore" );
		var yr_ = new Date().getFullYear();

		for (var i_ = yr_ - 5; i_ < yr_ + 5; i_++) {
		    qtrYrStore_.add({name: i_, value: i_});
		}

		this.studentName = 'denis';

		studentStore_.load({
		    callback: this.onStudentStoreLoad,
		    scope: this
		    //params: {
		    //    studentName: this.studentName
		    //}
		});

		allSubjectStore_.load({
		    callback: this.onAllSubjectStoreLoad,
		    scope: this
		});

		codeStore_.load({
		    callback: this.onCodeStoreLoad,
		    scope: this,
		    params: {
		        filterOn: 'QuarterNames'
		    }
		});

	},

	onStudentStoreLoad: function() {
		debugger;
		var studentStore_ = Ext.getStore('student.StudentStore');
		var r_ = studentStore_.getAt(0);
		//        debugger
		if ( typeof( r_ ) != "undefined" ) {
		    var subjectStore_ = Ext.getStore( 'subject.SubjectStore' );
		    var studentName_ = r_.get('firstName') + " " + r_.get('middleName') + ' ' + r_.get('lastName');
		    var g_ = Ext.ComponentQuery.query("#subjectsgrid")[0];

		    g_.setTitle('[' + studentName_ + '] Subjects');
			this.studentName = r_.get('userName');
		    subjectStore_.load({
		        callback: this.onSubjectStoreLoad,
		        scope: this,
		        params: {
		            studentName: r_.get('userName'),
		            studentId: r_.get('studentId')
		        }
		    });
		}
	},

	onSubjectStoreLoad: function() {
		debugger;
		var g_ = Ext.ComponentQuery.query("#subjectsgrid")[0];

		if (g_.getStore().getCount() > 0) {
		    g_.getSelectionModel().deselectAll();
		    g_.getSelectionModel().select( 0 );
		}
	},

	onAllSubjectStoreLoad: function() {

	},

	onCodeStoreLoad: function() {

	}

});
