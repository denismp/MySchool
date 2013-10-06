/*
 * File: app/model/subject/SubjectsModel.js
 *
 * This file was generated by Sencha Architect version 2.2.3.
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

Ext.define('MySchool.model.subject.SubjectsModel', {
    extend: 'Ext.data.Model',

    uses: [
        'MySchool.model.quarters.QuarterModel'
    ],

    fields: [
        {
            name: 'id',
            type: 'int'
        },
        {
            name: 'name',
            type: 'string'
        },
        {
            name: 'gradeLevel',
            type: 'int'
        },
        {
            name: 'creditHours',
            type: 'int'
        },
        {
            name: 'completed',
            type: 'boolean'
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
            name: 'description',
            type: 'string'
        },
        {
            name: 'objectives',
            type: 'string'
        },
        {
            name: 'version',
            type: 'string'
        },
        {
            name: 'quarter',
            type: 'auto'
        },
        {
            name: 'userName',
            type: 'string'
        }
    ],

    hasOne: {
        model: 'MySchool.model.quarters.QuarterModel',
        reader: {
            type: 'json',
            root: 'data'
        }
    },

    belongsTo: {
        model: 'MySchool.model.quarters.QuarterModel'
    }
});