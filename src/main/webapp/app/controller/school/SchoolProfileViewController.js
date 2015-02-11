/*
 * File: app/controller/school/SchoolProfileViewController.js
 *
 * This file was generated by Sencha Architect version 3.2.0.
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

Ext.define('MySchool.controller.school.SchoolProfileViewController', {
	extend: 'Ext.app.Controller',

	stores: [
		'school.SchoolProfileStore',
		'subject.SchoolsStore',
		'admin.AdminStore'
	],

	refs: [
		{
			ref: 'SchoolProfileForm',
			selector: '#schoolprofileform'
		},
		{
			ref: 'SchoolGridPanel',
			selector: '#schoolprofilesgridpanel'
		}
	],

	onSchoolprofilesgridpanelViewReady: function(tablepanel, eOpts) {
		debugger;
		console.log('onSchoolprofilesgridpanelViewReady()');
		var myStore = Ext.getStore('school.SchoolProfileStore');

		var securityStore = Ext.getStore('security.SecurityStore');
		var securityRecord = securityStore.getAt(0);
		this.userName = securityRecord.get('userName');
		this.userRole = securityRecord.get('userRole');
		var ss_ = Ext.getStore('student.StudentStore');

		var studentName_;


		var r_ = ss_.getAt(0);
		var title;
		var myGrid = this.getSchoolGridPanel();


		if( this.userRole === 'ROLE_FACULTY'){
			title = this.userName + '/' + this.userRole;


			myGrid.setTitle('[' + title + ']');
			myStore.load({
				callback: this.onMyJsonStoreLoad,
				scope: this,
				params: {
					studentName: this.userName
				}
			});
		}
		else if( this.userRole === 'ROLE_ADMIN' || this.userRole === 'ROLE_SCHOOL' ){
			title = this.userName + '/' + this.userRole;


			myGrid.setTitle('[' + title + ']');
			myStore.load({
				callback: this.onMyJsonStoreLoad,
				scope: this
			});
		}
		else{
			title = r_.get('firstName') + " " + r_.get('middleName') + ' ' + r_.get('lastName');

			myGrid.setTitle('[' + title + ']' + ' Guardians');
			myStore.load({
				callback: this.onMyJsonStoreLoad,
				scope: this,
				params: {
					studentName: this.userName
				}
			});
		}
	},

	onSchoolprofilesgridpanelSelectionChange: function(model, selected, eOpts) {
		debugger;
		// in the onMyJsonStoreLoad we do a deselect so we need to test
		// if selected[0] has a value
		if ( Ext.isDefined( selected  ) && Ext.isDefined( selected[0]  )) {
		    var myForm = this.getSchoolProfileForm();

		    this.loadForm( myForm, selected );


		    console.log('onSchoolprofilesgridpanelSelectionChanged()');
		}
	},

	onSchoolrefreshtoolClick: function(tool, e, eOpts) {
		debugger;
		var myStore = Ext.getStore('school.SchoolProfileStore');
		var myGrid = this.getSchoolGridPanel();
		//myGrid.removeAll();
		//myStore.reload();
		this.onSchoolprofilesgridpanelViewReady( myGrid, null );

	},

	onSchoolsavetoolClick: function(tool, e, eOpts) {
		window.console.log( "school.SchoolProfileStore.Save" );
		debugger;
		if( this.roleUser === 'ROLE_SCHOOL' || this.roleUser === 'ROLE_ADMIN'){
			var mystore = Ext.getStore("school.SchoolProfileStore");

			var records = mystore.getModifiedRecords();
			for( var i = 0; i < records.length; i++ )
			{
				records[i].set( 'lastUpdated', new Date() );
				records[i].set( 'whoUpdated', 'login');
			}
		}
		mystore.sync();
	},

	onSchoolprofiletabActivate: function(component, eOpts) {
		debugger;
		//subjectsgrid
		// catch the tab activate but only reload if we have processed
		// the viewready indicated by this.gridViewReady
		console.log('tab.activate()');

		if ( Ext.isDefined( this.gridViewReady  ) ) {
		    //var g_ = Ext.ComponentQuery.query("#subjectsgrid")[0];
			var g_ = this.getSchoolGridPanel();

		    g_.getStore().reload();
		}

	},

	onSchoolnewtoolClick: function() {
		debugger;

		//var schoolStore = Ext.getStore('school.SchoolProfileStore');
		//var guardianTypesStore = Ext.getStore('guardian.GuardianTypeStore');
		//guardianTypesStore.myLoad();

		if( this.userRole === 'ROLE_ADMIN' || this.userRole === 'ROLE_SCHOOL' ){
			var newDialog = Ext.create( 'MySchool.view.school.NewDialog' );

			window.console.log( 'New School Dialog' );

			newDialog.render( Ext.getBody() );
			newDialog.show();
		}
	},

	onSchoolsubmitClick: function(button, e, eOpts) {
		debugger;
		window.console.log( "Submit New School" );
		var myForm					= button.up().getForm();
		var myPanel					= button.up();
		var myGrid					= this.getSchoolGridPanel();

		//Get the values from the form and insert a new record into the SchoolProfileStore.

		var formValues				= myForm.getValues();

		//	Create an empty record
		var myRecord	= Ext.create('MySchool.model.school.SchoolProfileModel');

		//	Get the stores that we will need

		var schoolname	= formValues.name;
		var district	= formValues.district;
		var custodianofrecords	= formValues.custodianOfRecords;
		var custodiantitle	= formValues.custodianTitle;
		var phone1		= formValues.phone1;
		var phone2		= formValues.phone2;
		var address1	= formValues.address1;
		var address2	= formValues.address2;
		var city		= formValues.city;
		var province	= formValues.province;
		var postalcode	= formValues.postalcode;
		var country		= formValues.country;
		var email		= formValues.email;
		var comments	= formValues.comments;
		var adminUser	= formValues.adminUserName;


		var myStore		= this.getStore( 'school.SchoolProfileStore' );

		//debugger;


		myRecord.set( 'id', null );
		//myRecord.set( 'version', null );

		myRecord.set('whoUpdated', 'login');
		myRecord.set('lastUpdated', new Date());
		myRecord.set('email', email );
		myRecord.set('name', schoolname );
		myRecord.set('district', district );
		myRecord.set('comments', comments);
		myRecord.set('phone1', phone1 );
		myRecord.set('phone2', phone2 );
		myRecord.set('address1', address1);
		myRecord.set('address2', address2);
		myRecord.set('city', city);
		myRecord.set('province', province);
		myRecord.set('postalCode',postalcode);
		myRecord.set('country',country);
		myRecord.set('custodianOfRecords',custodianofrecords);
		myRecord.set('custodianTitle', custodiantitle);
		myRecord.set('adminUserName', adminUser);

		myRecord.set('enabled', true);


		//add to the store

		myStore.add( myRecord );

		//sync the store.
		myStore.sync();

		myForm.reset();
		button.up().hide();
		button.up().up().close();

	},

	onSchoolcancelClick: function(button, e, eOpts) {
		debugger;
		window.console.log( "Cancel New School" );
		var myForm = button.up().getForm();
		myForm.reset();
		button.up().hide();
		button.up().up().close();
	},

	onSchooldeletetoolClick: function(button, e, eOpts) {

	},

	onSchoollocktoolClick: function(button, e, eOpts) {

	},

	onSchoolprofileformeditbuttonClick: function(button, e, eOpts) {
		debugger;
		if( this.roleUser === 'ROLE_SCHOOL' || this.roleUser === 'ROLE_ADMIN'){
			var myForm = this.getSchoolProfileForm();
			var myFields = myForm.getForm().getFields();
			for( var i = 0; i < myFields.length; i++ )
			{
				myFields.items[i].enable();
			}
			//myForm.getForm().focus();studentprofileformeditbutton
			var cancelButton	= button.up().down('#schoolprofileformcanelbutton');
			var saveButton		= button.up().down('#schoolprofileformsavebutton');
			cancelButton.enable();
			saveButton.enable();
			button.disable();
		}

	},

	onSchoolprofileformcanelbuttonClick: function(button, e, eOpts) {
		var myForm = this.getSchoolProfileForm();
		var myFields = myForm.getForm().getFields();
		for( var i = 0; i < myFields.length; i++ )
		{
			myFields.items[i].disable();
		}
		//myForm.getForm().focus();
		//var cancelButton	= button.up().down('#schoolprofileformcanelbutton');
		var saveButton		= button.up().down('#schoolprofileformsavebutton');
		var editButton		= button.up().down('#schoolprofileformeditbutton');
		editButton.enable();
		saveButton.disable();
		button.disable();
	},

	onSchoolprofileformsavebuttonClick: function(button, e, eOpts) {
		debugger;
		var myForm = this.getSchoolProfileForm();
		var myFields = myForm.getForm().getFields();
		for( var i = 0; i < myFields.length; i++ )
		{
			myFields.items[i].disable();
		}
		//myForm.getForm().focus();
		var cancelButton	= button.up().down('#schoolprofileformcanelbutton');
		//var saveButton		= button.up().down('#schoolprofileformsavebutton');
		var editButton		= button.up().down('#schoolprofileformeditbutton');
		editButton.enable();
		cancelButton.disable();
		button.disable();
		this.saveProfileForm();
	},

	loadForm: function(form, selected) {
		debugger;

		console.log( form );

		form.loadRecord( selected[0] );

		var myFields = form.getForm().getFields();
		for( var i = 0; i < myFields.length; i++ )
		{
		    myFields.items[i].disable();
		}
		console.log('school.SchoolViewController.loadForm(): completed');
	},

	onMyJsonStoreLoad: function() {
		//debugger;
		//var g_ = Ext.ComponentQuery.query("#monthlysummarygridpanel")[0];
		var g_ = this.getSchoolGridPanel();

		if (g_.getStore().getCount() > 0) {
		    g_.getSelectionModel().deselectAll();
		    g_.getSelectionModel().select( 0 );
		}

		this.gridViewReady = true;
	},

	saveProfileForm: function() {
		debugger;
		window.console.log( "Save School Profile Form" );
		//var myForm					= button.up().getForm();
		var myForm = this.getSchoolProfileForm();


		//	Get the stores that we will need
		var myStore		= this.getStore( 'school.SchoolProfileStore' );

		//var rForm = myForm.getForm();

		//var formValues1	= myForm.getValues();
		//var formValues	= myForm.getForm().getValues();
		var formFields	= myForm.getForm().getFields();
		//var name	= formFields.items[0].name;
		//var value	= formFields.items[0].lastValue;
		//var test	= this.getFormValue( formFields, 'middleName');
		//debugger;

		//	Create the form record.
		var myRecord	= myForm.getRecord();

		myRecord.set('firstName', this.getFormValue( formFields, 'firstName' ) );
		myRecord.set('middleName', this.getFormValue( formFields, 'middleName' ));
		myRecord.set('lastName', this.getFormValue( formFields, 'lastName' ));
		myRecord.set('phone1', this.getFormValue( formFields, 'phone1' ));
		myRecord.set('phone2', this.getFormValue( formFields, 'phone2' ));
		myRecord.set('address1', this.getFormValue( formFields, 'address1' ));
		myRecord.set('address2', this.getFormValue( formFields, 'address2' ));
		myRecord.set('city', this.getFormValue( formFields, 'city' ));
		myRecord.set('province', this.getFormValue( formFields, 'province' ));
		myRecord.set('postalCode', this.getFormValue( formFields, 'postalCode' ));
		myRecord.set('country', this.getFormValue( formFields, 'country' ));
		myRecord.set('email', this.getFormValue( formFields, 'email' ));
		myRecord.set('dob', this.getFormValue( formFields, 'dob' ));

		myRecord.set('whoUpdated', 'login');
		myRecord.set('lastUpdated', new Date());

		debugger;

		//sync the store.
		myStore.sync();
	},

	getFormValue: function(formFields, name) {
		//var name	= formFields.items[0].name;
		//var value	= formFields.items[0].lastValue;
		for( var i = 0; i < formFields.length; i++ )
		{
		    if( formFields.items[i].name === name )
		    {
		        return formFields.items[i].lastValue;
		    }
		}
		return "";
	},

	init: function(application) {
		this.control({
			"#schoolprofilesgridpanel": {
				viewready: this.onSchoolprofilesgridpanelViewReady,
				selectionchange: this.onSchoolprofilesgridpanelSelectionChange
			},
			"#schoolrefreshtool": {
				click: this.onSchoolrefreshtoolClick
			},
			"#schoolsavetool": {
				click: this.onSchoolsavetoolClick
			},
			"#schoolprofiletab": {
				activate: this.onSchoolprofiletabActivate
			},
			"#schoolnewtool": {
				click: this.onSchoolnewtoolClick
			},
			"#schoolsubmit": {
				click: this.onSchoolsubmitClick
			},
			"#schoolcancel": {
				click: this.onSchoolcancelClick
			},
			"#schooldeletetool": {
				click: this.onSchooldeletetoolClick
			},
			"#schoollocktool": {
				click: this.onSchoollocktoolClick
			},
			"#schoolprofileformeditbutton": {
				click: this.onSchoolprofileformeditbuttonClick
			},
			"#schoolprofileformcanelbutton": {
				click: this.onSchoolprofileformcanelbuttonClick
			},
			"#schoolprofileformsavebutton": {
				click: this.onSchoolprofileformsavebuttonClick
			}
		});
	}

});
