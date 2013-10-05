/*
 * File: app.js
 *
 * This file was generated by Sencha Architect version 2.2.2.
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

//@require @packageOverrides
Ext.Loader.setConfig({
    enabled: true
});

Ext.application({
    models: [
        'subject.SubjectsModel',
        'subject.QuarterNamesModel',
        'quarters.QuarterModel',
        'student.StudentModel'
    ],
    stores: [
        'subject.SubjectStore',
        'subject.QuarterNameStore',
        'quarter.QuarterStore',
        'student.StudentStore',
        'subject.GradeTypeStore'
    ],
    views: [
        'MainPanel',
        'subject.SubjectsGridPanel',
        'subject.SubjectsForm',
        'subject.SubjectsPanel',
        'subject.SubjectRefreshTool',
        'subject.SubjectDeleteTool',
        'subject.SubjectSearchTool',
        'subject.SubjectNewTool',
        'subject.SubjectSaveTool',
        'subject.NewForm',
        'subject.GradeTypeComboBox',
        'subject.QuarterNamesComboBox'
    ],
    autoCreateViewport: true,
    controllers: [
        'subject.SubjectsController'
    ],
    name: 'MySchool'
});
