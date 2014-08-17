/*
 * File: app/store/student/StudentPasswordStore.js
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

Ext.define('MySchool.store.student.StudentPasswordStore', {
	extend: 'Ext.data.Store',

	requires: [
		'MySchool.model.student.PasswordModel',
		'Ext.data.proxy.Rest',
		'Ext.data.reader.Json',
		'Ext.data.writer.Json'
	],

	constructor: function(cfg) {
		var me = this;
		cfg = cfg || {};
		me.callParent([Ext.apply({
			autoLoad: true,
			model: 'MySchool.model.student.PasswordModel',
			storeId: 'student.StudentPasswordStore',
			proxy: {
				type: 'rest',
				url: 'studentpasswordviews/json',
				headers: {
					Accept: 'application/json'
				},
				reader: {
					type: 'json',
					root: 'data'
				},
				writer: {
					type: 'json',
					dateFormat: 'm/d/Y',
					encode: true,
					root: 'data'
				}
			},
			listeners: {
				write: {
					fn: me.onJsonstoreWrite,
					scope: me
				}
			}
		}, cfg)]);
	},

	onJsonstoreWrite: function(store, operation, eOpts) {
		store.reload();
	}

});