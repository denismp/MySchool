/*
 * File: app/model/subject/SubjectsModel.js
 *
 * This file was generated by Sencha Architect version 3.0.0.
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

    requires: [
        'Ext.data.Field'
    ],

    fields: [
        {
            name: 'id'
        },
        {
            name: 'studentName',
            type: 'string'
        },
        {
            name: 'subjCreditHours',
            type: 'int'
        },
        {
            name: 'subjDescription',
            type: 'string'
        },
        {
            name: 'subjGradeLevel',
            type: 'int'
        },
        {
            name: 'subjId',
            type: 'int'
        },
        {
            dateFormat: 'm/d/Y',
            name: 'subjLastUpdated',
            type: 'date'
        },
        {
            name: 'subjName',
            type: 'string'
        },
        {
            name: 'subjObjectives',
            type: 'string'
        },
        {
            name: 'subjVersion',
            type: 'int'
        },
        {
            name: 'subjWhoUpdated',
            type: 'string'
        },
        {
            name: 'qtrGrade',
            type: 'float'
        },
        {
            name: 'qtrGradeType',
            type: 'int'
        },
        {
            name: 'qtrId',
            type: 'int'
        },
        {
            dateFormat: 'm/d/Y',
            name: 'qtrLastUpdated',
            type: 'date'
        },
        {
            name: 'qtrCompleted',
            type: 'boolean'
        },
        {
            name: 'qtrLocked',
            type: 'boolean'
        },
        {
            name: 'qtrName',
            type: 'string'
        },
        {
            name: 'qtrVersion',
            type: 'int'
        },
        {
            name: 'qtrWhoUpdated',
            type: 'string'
        },
        {
            name: 'qtrYear',
            type: 'string'
        }
    ]
});