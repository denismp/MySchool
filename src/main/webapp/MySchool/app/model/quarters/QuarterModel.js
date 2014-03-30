/*
 * File: app/model/quarters/QuarterModel.js
 *
 * This file was generated by Sencha Architect version 3.0.3.
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

Ext.define('MySchool.model.quarters.QuarterModel', {
    extend: 'Ext.data.Model',

    requires: [
        'Ext.data.Field',
        'Ext.data.association.HasMany',
        'Ext.data.reader.Json',
        'Ext.data.association.HasOne',
        'Ext.data.association.BelongsTo'
    ],
    uses: [
        'MySchool.model.subject.SubjectsModel',
        'MySchool.model.student.StudentModel'
    ],

    fields: [
        {
            name: 'qtrName',
            type: 'string'
        },
        {
            name: 'qtr_year',
            type: 'int'
        },
        {
            name: 'gradeType',
            type: 'int'
        },
        {
            name: 'grade',
            type: 'int'
        },
        {
            name: 'locked',
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
        }
    ],

    hasMany: {
        model: 'MySchool.model.subject.SubjectsModel',
        autoLoad: true,
        reader: {
            type: 'json',
            root: 'data'
        }
    },

    hasOne: {
        model: 'MySchool.model.student.StudentModel',
        reader: {
            type: 'json',
            root: 'data'
        }
    },

    belongsTo: {
        model: 'MySchool.model.student.StudentModel',
        reader: {
            type: 'json',
            root: 'data'
        }
    }
});