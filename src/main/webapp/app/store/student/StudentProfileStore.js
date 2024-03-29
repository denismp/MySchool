/*
 * File: app/store/student/StudentProfileStore.js
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

Ext.define('MySchool.store.student.StudentProfileStore', {
	extend: 'Ext.data.Store',
	alias: 'store.studentprofilestore',

	requires: [
		'MySchool.model.student.StudentProfileModel',
		'Ext.data.proxy.Rest',
		'Ext.data.reader.Json'
	],

	constructor: function(cfg) {
		var me = this;
		cfg = cfg || {};
		me.callParent([Ext.apply({
			autoLoad: false,
			model: 'MySchool.model.student.StudentProfileModel',
			storeId: 'student.StudentProfileStore',
			proxy: {
				type: 'rest',
				url: 'studentprofileviews/json',
				headers: {
					Accept: 'application/json'
				},
				reader: {
					type: 'json',
					root: 'data'
				},
				listeners: {
					exception: {
						fn: me.onRestException,
						scope: me
					}
				}
			},
			listeners: {
				load: {
					fn: me.onJsonstoreLoad,
					scope: me
				},
				write: {
					fn: me.onJsonstoreWrite,
					scope: me
				}
			}
		}, cfg)]);
	},

	onRestException: function(proxy, response, operation, eOpts) {
		debugger;
		var smsg = response.request.options.method + '<br>' + response.request.options.action + '<br>' + response.responseText + '<br>' + response.status + '<br>' + response.statusText + '<br>' + operation.params.data;
		var myStr = response.responseText;
		var n = myStr.search('No data for');
		var n2 = myStr.search('No records for');
		var displayMsg = true;
		if( n >= 0  ){
			displayMsg = false;
		}
		if( displayMsg === false || n2 >= 0 ){
			displayMsg = false;
		}

		window.console.log('n=' + n );

		if( displayMsg === true ){
			Ext.MessageBox.show({
				title: 'REMOTE EXCEPTION',
				msg: smsg,
				icon: Ext.MessageBox.ERROR,
				buttons: Ext.Msg.OK,
				resizeable: true
			});
		}
		window.console.log( smsg );
		if( this.getTotalCount() > 0 )
		{
		    this.reload();
		}
		//this.reload();

	},

	onJsonstoreLoad: function(store, records, successful, eOpts) {
		//debugger;
		console.log("student.StudentProfileStore.onJsonstoreLoad() called...");
	},

	onJsonstoreWrite: function(store, operation, eOpts) {
		//debugger;
		console.log("student.StudentProfileStore.onJsonstoreWrite(): called...");
		store.reload();
	}

});