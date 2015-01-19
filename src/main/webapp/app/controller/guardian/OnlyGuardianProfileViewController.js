/*
 * File: app/controller/guardian/OnlyGuardianProfileViewController.js
 *
 * This file was generated by Sencha Architect version 3.1.0.
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

Ext.define('MySchool.controller.guardian.OnlyGuardianProfileViewController', {
	extend: 'Ext.app.Controller',

	stores: [
		'guardian.GuardianTypeStore'
	],

	refs: [
		{
			ref: 'OnlyGuardianForm',
			selector: '#guardianonlyprofileform'
		},
		{
			ref: 'OnlyGuardianGridPanel',
			selector: '#guardianonlygridpanel'
		}
	],

	onGuardianonlygridpanelSelectionChange: function(model, selected, eOpts) {
		debugger;
		// in the onMyJsonStoreLoad we do a deselect so we need to test
		// if selected[0] has a value
		if ( Ext.isDefined( selected  ) && Ext.isDefined( selected[0]  )) {
		    var myForm = this.getOnlyGuardianForm();

		    this.loadForm( myForm, selected );


		    console.log('onGuardianonlygridpanelSelectionChange()');
		}
	},

	onGuardianonlygridpanelViewReady: function(tablepanel, eOpts) {
		debugger;
		console.log('onGuardianprofilerefreshtoolClick()');
		var myStore = Ext.getStore('guardian.GuardianProfileStore');

		var securityStore = Ext.getStore('security.SecurityStore');
		var securityRecord = securityStore.getAt(0);
		this.userName = securityRecord.get('userName');
		this.userRole = securityRecord.get('userRole');
		var ss_ = Ext.getStore('student.StudentStore');

		var studentName_;


		var r_ = ss_.getAt(0);
		var title;
		var myGrid = this.getOnlyGuardianGridPanel();


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
		else if( this.userRole === 'ROLE_ADMIN'){
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

	onOnlyguardianrefreshtoolClick: function(tool, e, eOpts) {
		debugger;
		var myStore = Ext.getStore('guardian.GuardianProfileStore');
		myStore.reload();

		if( false ){
		//debugger;
		console.log('onGuardianprofilerefreshtoolClick()');
		var myStore = Ext.getStore('guardian.GuardianProfileStore');

		var securityStore = Ext.getStore('security.SecurityStore');
		var securityRecord = securityStore.getAt(0);
		this.userName = securityRecord.get('userName');
		this.userRole = securityRecord.get('userRole');
		var title;
		var myGrid = this.getOnlyGuardianGridPanel();


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
		else if( this.userRole === 'ROLE_ADMIN'){
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
		}

	},

	onOnlyguardianprofileformeditbuttonClick: function(button, e, eOpts) {
		debugger;

		var myForm = this.getOnlyGuardianForm();
		var myFields = myForm.getForm().getFields();
		for( var i = 0; i < myFields.length; i++ )
		{
			myFields.items[i].enable();
		}
		//myForm.getForm().focus();studentprofileformeditbutton
		var cancelButton	= button.up().down('#onlyguardianprofileformcanelbutton');
		var saveButton		= button.up().down('#onlyguardianprofileformsavebutton');
		cancelButton.enable();
		saveButton.enable();
		button.disable();

	},

	onOnlyguardianprofileformcanelbuttonClick: function(button, e, eOpts) {
		var myForm = this.getOnlyGuardianForm();
		var myFields = myForm.getForm().getFields();
		for( var i = 0; i < myFields.length; i++ )
		{
			myFields.items[i].disable();
		}
		//myForm.getForm().focus();
		//var cancelButton	= button.up().down('#onlyguardianprofileformcanelbutton');
		var saveButton		= button.up().down('#onlyguardianprofileformsavebutton');
		var editButton		= button.up().down('#onlyguardianprofileformeditbutton');
		editButton.enable();
		saveButton.disable();
		button.disable();
	},

	onOnlyguardianprofileformsavebuttonClick: function(button, e, eOpts) {
		debugger;
		var myForm = this.getOnlyGuardianForm();
		var myFields = myForm.getForm().getFields();
		for( var i = 0; i < myFields.length; i++ )
		{
			myFields.items[i].disable();
		}
		//myForm.getForm().focus();
		var cancelButton	= button.up().down('#onlyguardianprofileformcanelbutton');
		//var saveButton		= button.up().down('#onlyguardianprofileformsavebutton');
		var editButton		= button.up().down('#onlyguardianprofileformeditbutton');
		editButton.enable();
		cancelButton.disable();
		button.disable();
		this.saveOnlyGuardianProfileForm();
	},

	onOnlyfguardiansavetoolClick: function(tool, e, eOpts) {
		window.console.log( "guardian.GuardianProfileStore.Save" );
		debugger;

		var mystore = Ext.getStore("guardian.GuardianProfileStore");

		var records = mystore.getModifiedRecords();
		for( var i = 0; i < records.length; i++ )
		{
		    records[i].set( 'lastUpdated', new Date() );
		    records[i].set( 'whoUpdated', 'login');
		}

		mystore.sync();
	},

	onOnlyguardiannewtoolClick: function(tool, e, eOpts) {
		debugger;

		var guardianStore = Ext.getStore('guardian.GuardianProfileStore');
		var guardianTypesStore = Ext.getStore('guardian.GuardianTypeStore');
		guardianTypesStore.myLoad();

		if( this.userRole === 'ROLE_ADMIN'){
			var newDialog = Ext.create( 'MySchool.view.guardian.NewDialog' );

			window.console.log( 'New Guardian Dialog' );

			newDialog.render( Ext.getBody() );
			newDialog.show();
		}


	},

	onGuardiancancelClick: function(button, e, eOpts) {
		debugger;
		window.console.log( "Cancel New Guardian" );
		var myForm = button.up().getForm();
		myForm.reset();
		button.up().hide();
		button.up().up().close();
	},

	onGuardiansubmitClick: function(button, e, eOpts) {
		debugger;
		window.console.log( "Submit New Guardian" );
		var myForm					= button.up().getForm();
		var myPanel					= button.up();
		var myGrid					= this.getOnlyGuardianGridPanel();

		//Get the values from the form and insert a new record into the GuardianProfileStore.

		var formValues				= myForm.getValues();

		//	Create an empty record
		var myRecord	= Ext.create('MySchool.model.guardian.GuardianProfileModel');

		//	Get the stores that we will need

		var firstname	= formValues.firstname;
		var middlename	= formValues.middlename;
		var lastname	= formValues.lastname;
		var phone1		= formValues.phone1;
		var phone2		= formValues.phone2;
		var address1	= formValues.address1;
		var address2	= formValues.address2;
		var city		= formValues.city;
		var state		= formValues.state;
		var postalcode	= formValues.postalcode;
		var country		= formValues.country;
		var email		= formValues.email;
		var username	= formValues.username;

		var type		= formValues.combotype;

		var dob			= formValues.dob;


		var myStore		= this.getStore( 'guardian.GuardianProfileStore' );

		//debugger;


		myRecord.set( 'id', null );
		//myRecord.set( 'version', null );

		myRecord.set('whoUpdated', 'login');
		myRecord.set('lastUpdated', new Date());
		myRecord.set('email', email );
		myRecord.set('firstName', firstname );
		myRecord.set('middleName', middlename );
		myRecord.set('lastName', lastname);
		myRecord.set('phone1', phone1 );
		myRecord.set('phone2', phone2 );
		myRecord.set('address1', address1);
		myRecord.set('address2', address2);
		myRecord.set('city', city);
		myRecord.set('province', state);
		myRecord.set('postalCode',postalcode);
		myRecord.set('country',country);
		myRecord.set('userName',username);

		myRecord.set('enabled', true);
		myRecord.set('dob', dob);

		myRecord.set('type', type);

		//add to the store

		myStore.add( myRecord );

		//sync the store.
		myStore.sync();

		myForm.reset();
		button.up().hide();
		button.up().up().close();

	},

	onGuardianprofiletabActivate: function(component, eOpts) {
		debugger;
		//subjectsgrid
		// catch the tab activate but only reload if we have processed
		// the viewready indicated by this.gridViewReady
		console.log('tab.activate()');

		if ( Ext.isDefined( this.gridViewReady  ) ) {
		    //var g_ = Ext.ComponentQuery.query("#subjectsgrid")[0];
			var g_ = this.getOnlyGuardianGridPanel();

		    g_.getStore().reload();
		}

	},

	onOnlyguardiannewchildtoolClick: function(button, e, eOpts) {
		debugger;

		var guardianStore = Ext.getStore('guardian.GuardianProfileStore');
		var guardianTypesStore = Ext.getStore('guardian.GuardianTypeStore');
		guardianTypesStore.myLoad();

		if( this.userRole === 'ROLE_ADMIN'){
			var newDialog = Ext.create( 'MySchool.view.guardian.AddChildDialog' );

			window.console.log( 'Add Child Dialog' );

			newDialog.render( Ext.getBody() );
			newDialog.show();
		}

	},

	onGuardianaddchildcancelClick: function(button, e, eOpts) {
		debugger;
		window.console.log( "Cancel Add Child" );
		var myForm = button.up().getForm();
		myForm.reset();
		button.up().hide();
		button.up().up().close();
	},

	onGuardianaddchildsubmitClick: function(button, e, eOpts) {
		debugger;
		window.console.log( "Submit Add Child" );
		var myForm					= button.up().getForm();
		var myPanel					= button.up();
		var myGrid					= this.getOnlyGuardianGridPanel();

		//Get the values from the form and insert a new record into the GuardianProfileStore.

		var formValues				= myForm.getValues();

		//	Create an empty record
		var myRecord	= myGrid.getSelectionModel().getSelection()[0];
		//var myRecord	= Ext.create('MySchool.model.guardian.GuardianProfileModel');

		//	Get the stores that we will need

		var username	= formValues.username;

		var myStore		= this.getStore( 'guardian.GuardianProfileStore' );

		//debugger;

		//myRecord.set( 'quardianId', myRecord.get('guardianId') );
		myRecord.set( 'studentUserName', username );
		myRecord.set( 'id', null );

		//add to the store.  For a normal update, only the sync() is called.
		//In this case we are expecting the create method on the backend to
		//perform an update instead of a create.  This will allow updated from
		//the grid to work as expected while allowing us to add a new student
		//child to the existing guardian relation.  This will actually create
		//another record in the guardian table with the new studentId.
		//The backend checks to see if the studentId in this records is
		//different from the one with the specified studentUserName.  If so,
		//then the new record will be created.
		myStore.add( myRecord );

		//sync the store.
		myStore.sync();

		myForm.reset();
		button.up().hide();
		button.up().up().close();

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
		console.log('guardian.OnlyGuardianProfileViewController.loadForm(): completed');
	},

	onMyJsonStoreLoad: function() {
		//debugger;
		//var g_ = Ext.ComponentQuery.query("#monthlysummarygridpanel")[0];
		var g_ = this.getOnlyGuardianGridPanel();

		if (g_.getStore().getCount() > 0) {
		    g_.getSelectionModel().deselectAll();
		    g_.getSelectionModel().select( 0 );
		}

		this.gridViewReady = true;
	},

	saveOnlyGuardianProfileForm: function() {
		debugger;
		window.console.log( "Save Guardian Profile Form" );
		//var myForm					= button.up().getForm();
		var myForm = this.getOnlyGuardianForm();


		//	Get the stores that we will need
		var myStore		= this.getStore( 'guardian.GuardianProfileStore' );

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
			"#guardianonlygridpanel": {
				selectionchange: this.onGuardianonlygridpanelSelectionChange,
				viewready: this.onGuardianonlygridpanelViewReady
			},
			"#onlyguardianrefreshtool": {
				click: this.onOnlyguardianrefreshtoolClick
			},
			"#onlyguardianprofileformeditbutton": {
				click: this.onOnlyguardianprofileformeditbuttonClick
			},
			"#onlyguardianprofileformcanelbutton": {
				click: this.onOnlyguardianprofileformcanelbuttonClick
			},
			"#onlyguardianprofileformsavebutton": {
				click: this.onOnlyguardianprofileformsavebuttonClick
			},
			"#onlyfguardiansavetool": {
				click: this.onOnlyfguardiansavetoolClick
			},
			"#onlyguardiannewtool": {
				click: this.onOnlyguardiannewtoolClick
			},
			"#guardiancancel": {
				click: this.onGuardiancancelClick
			},
			"#guardiansubmit": {
				click: this.onGuardiansubmitClick
			},
			"#guardianprofiletab": {
				activate: this.onGuardianprofiletabActivate
			},
			"#onlyguardiannewchildtool": {
				click: this.onOnlyguardiannewchildtoolClick
			},
			"#guardianaddchildcancel": {
				click: this.onGuardianaddchildcancelClick
			},
			"#guardianaddchildsubmit": {
				click: this.onGuardianaddchildsubmitClick
			}
		});
	}

});
