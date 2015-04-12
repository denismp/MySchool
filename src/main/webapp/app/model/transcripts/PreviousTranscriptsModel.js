/*
 * File: app/model/transcripts/PreviousTranscriptsModel.js
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

Ext.define('MySchool.model.transcripts.PreviousTranscriptsModel', {
	extend: 'Ext.data.Model',
	alias: 'model.previoustranscriptsmodel',

	requires: [
		'Ext.data.Field'
	],

	fields: [
		{
			name: 'id'
		},
		{
			name: 'schoolId',
			type: 'int'
		},
		{
			name: 'previoustranscriptId',
			type: 'int'
		},
		{
			name: 'schoolEmail',
			type: 'string'
		},
		{
			name: 'schoolName',
			type: 'string'
		},
		{
			name: 'district',
			type: 'string'
		},
		{
			name: 'custodianOfRecords'
		},
		{
			name: 'custodianTitle',
			type: 'string'
		},
		{
			name: 'schoolPhone1',
			type: 'string'
		},
		{
			name: 'schoolPhone2',
			type: 'string'
		},
		{
			name: 'schoolAddress1',
			type: 'string'
		},
		{
			name: 'schoolAddress2',
			type: 'string'
		},
		{
			name: 'schoolCity'
		},
		{
			name: 'schoolProvince',
			type: 'string'
		},
		{
			name: 'schoolPostalCode',
			type: 'string'
		},
		{
			name: 'schoolCountry',
			type: 'string'
		},
		{
			name: 'schoolComments',
			type: 'string'
		},
		{
			dateFormat: 'm/d/Y',
			name: 'createdDate',
			type: 'date'
		},
		{
			name: 'whoUpdated',
			type: 'string'
		},
		{
			dateFormat: 'm/d/Y',
			name: 'lastUpdated',
			type: 'date'
		},
		{
			name: 'studentId',
			type: 'int'
		},
		{
			name: 'studentUserName',
			type: 'string'
		},
		{
			name: 'schoolAdminId',
			type: 'int'
		},
		{
			name: 'schoolAdminUserName',
			type: 'string'
		},
		{
			name: 'schoolAdminEmail',
			type: 'string'
		},
		{
			name: 'name',
			type: 'string'
		},
		{
			name: 'type',
			type: 'int'
		},
		{
			name: 'pdfURL',
			type: 'string'
		},
		{
			name: 'comments',
			type: 'string'
		},
		{
			name: 'gradingScale',
			type: 'int'
		},
		{
			name: 'version',
			type: 'int'
		}
	]
});