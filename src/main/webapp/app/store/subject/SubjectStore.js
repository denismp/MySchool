/*
 * File: app/store/subject/SubjectStore.js
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

Ext.define('MySchool.store.subject.SubjectStore', {
	extend: 'Ext.data.Store',
	alias: 'store.subjectstore',

	requires: [
		'MySchool.model.subject.SubjectsModel',
		'Ext.data.proxy.Rest',
		'Ext.data.reader.Json'
	],

	constructor: function(cfg) {
		var me = this;
		cfg = cfg || {};
		me.callParent([Ext.apply({
			autoLoad: false,
			model: 'MySchool.model.subject.SubjectsModel',
			storeId: 'subject.SubjectStore',
			proxy: {
				type: 'rest',
				url: 'subjectviews/json',
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
				beforesync: {
					fn: me.onJsonstoreBeforeSync,
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
		//debugger;
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
		if( response.responseText.indexOf( 'Please specify a valid student user name' ) > -1 ){
			this.reload();
		}
		//if( this.getCount() > 0 )
		//{
		//    this.reload();
		//}
	},

	onJsonstoreBeforeSync: function(options, eOpts) {
		//debugger;
		var smsg = 'Before sync()';
		//Ext.MessageBox.show({
		//    title: 'REMOTE EXCEPTION',
		//    msg: smsg,
		//    icon: Ext.MessageBox.ERROR,
		//    buttons: Ext.Msg.OK
		//});
		window.console.log( smsg );
	},

	onJsonstoreWrite: function(store, operation, eOpts) {
		window.console.log( "SubjectStore reload...");
		store.reload();
	}

});