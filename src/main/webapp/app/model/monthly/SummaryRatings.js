/*
 * File: app/model/monthly/SummaryRatings.js
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

Ext.define('MySchool.model.monthly.SummaryRatings', {
	extend: 'Ext.data.Model',
	alias: 'model.monthlysummaryratingsmodel',

	requires: [
		'Ext.data.Field'
	],

	fields: [
		{
			name: 'id'
		},
		{
			name: 'month_number',
			type: 'int'
		},
		{
			name: 'feelings',
			type: 'string'
		},
		{
			name: 'reflections',
			type: 'string'
		},
		{
			name: 'patternsOfCorrections',
			type: 'string'
		},
		{
			name: 'effectivenessOfActions',
			type: 'string'
		},
		{
			name: 'actionResults',
			type: 'string'
		},
		{
			name: 'realizations',
			type: 'string'
		},
		{
			name: 'plannedChanges',
			type: 'string'
		},
		{
			name: 'comments',
			type: 'string'
		},
		{
			name: 'locked',
			type: 'boolean'
		},
		{
			dateFormat: 'm/d/Y',
			name: 'lastUpdated',
			type: 'date'
		},
		{
			name: 'whoUpdated',
			type: 'string'
		},
		{
			name: 'qtrId',
			type: 'int'
		},
		{
			name: 'qtrName',
			type: 'string'
		},
		{
			name: 'qtrYear',
			type: 'int'
		},
		{
			name: 'studentId'
		},
		{
			name: 'studentUserName',
			type: 'string'
		},
		{
			name: 'facultyUserName',
			type: 'string'
		},
		{
			name: 'subjId',
			type: 'int'
		},
		{
			name: 'subjName',
			type: 'string'
		},
		{
			name: 'version',
			type: 'int'
		},
		{
			name: 'summaryId',
			type: 'int'
		}
	]
});