/*
 * File: app/controller/school/SchoolProfileViewController.js
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

Ext.define('MySchool.controller.school.SchoolProfileViewController', {
	extend: 'Ext.app.Controller',

	stores: [
		'school.SchoolProfileStore',
		'subject.SchoolsStore'
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
		myStore.reload();

	},

	onSchoolsavetoolClick: function(tool, e, eOpts) {
		window.console.log( "school.SchoolProfileStore.Save" );
		debugger;

		var mystore = Ext.getStore("school.SchoolProfileStore");

		var records = mystore.getModifiedRecords();
		for( var i = 0; i < records.length; i++ )
		{
		    records[i].set( 'lastUpdated', new Date() );
		    records[i].set( 'whoUpdated', 'login');
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
			}
		});
	}

});
