/*
 * File: app.js
 *
 * This file was generated by Sencha Architect version 3.0.2.
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

// @require @packageOverrides
Ext.Loader.setConfig({
    enabled: true
});


Ext.application({
    models: [
        'subject.SubjectsModel',
        'subject.QuarterNamesModel',
        'quarters.QuarterModel',
        'student.StudentModel',
        'bodiesofwork.BodiesOfWorkModel',
        'daily.DailyModel',
        'monthly.EvaluationRatings',
        'monthly.SummaryRatings'
    ],
    stores: [
        'subject.SubjectStore',
        'subject.QuarterNameStore',
        'quarter.QuarterStore',
        'student.StudentStore',
        'subject.GradeTypeStore',
        'subject.AllSubjectStore',
        'subject.QuarterYearStore',
        'bodiesofwork.MyJsonStore',
        'daily.MyJsonStore',
        'monthly.SummaryRatingsStore',
        'monthly.EvaluationRatingsStore'
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
        'subject.QuarterNamesComboBox',
        'bodiesofwork.BodiesOfWorkPanel',
        'bodiesofwork.GridPanel',
        'subject.EditForm',
        'subject.SubjectNameComboBox',
        'subject.QuarterYearComboBox',
        'bodiesofwork.BodyOfWorkForm',
        'daily.DailyPanel',
        'daily.DailyGridPanel',
        'daily.DetailsTabPanel',
        'weekly.skills.TabPanel',
        'weekly.skills.GridPanel',
        'weekly.evaluation.DetailsPanel',
        'weekly.evaluation.GridPanel',
        'monthly.summary.DetailsPanel',
        'monthly.summary.DetailsGridPanel',
        'monthly.summary.DetailsTabPanel',
        'monthly.summary.FeelingTabPanel',
        'monthly.summary.ReflectionsTabPanel',
        'monthly.summary.PatternsOfCorrectonsTabPanel',
        'monthly.summary.EffectivenessOfActionsTabPanel',
        'monthly.summary.ActionResultsTabPanel',
        'monthly.summary.ReflectionTabPanel',
        'monthly.summary.PlannedChangesTabPanel',
        'monthly.summary.CommentsTabPanel',
        'monthly.evaluation.DetailsPanel',
        'monthly.evaluation.GridPanel',
        'student.ProfileGridPanel',
        'student.ProfileForm',
        'faculty.ProfilePanel',
        'faculty.ProfileGridPanel',
        'faculty.ProfileForm',
        'bodiesofwork.RefreshTool',
        'bodiesofwork.SearchTool',
        'bodiesofwork.NewTool',
        'bodiesofwork.DeleteTool',
        'bodiesofwork.SaveTool',
        'bodiesofwork.LockTool',
        'daily.DailyHoursTabPanel',
        'daily.ResourcesUsedTabPanel',
        'daily.StudyDetailsTabPanel',
        'daily.EvaluationsTabPanel',
        'daily.CorrectionsTabPanel',
        'daily.ActionsTabPanel',
        'daily.RefreshTool',
        'daily.SearchTool',
        'daily.NewTool',
        'daily.SaveTool',
        'daily.DeleteTool',
        'daily.LockTool',
        'monthly.summary.RefreshTool',
        'monthly.summary.SearchTool',
        'monthly.summarry.NewTool',
        'monthly.summary.SaveTool',
        'monthly.summary.DeleteTool',
        'monthly.summary.LockTool'
    ],
    controllers: [
        'subject.SubjectsController',
        'bodiesofwork.MyController',
        'daily.MyController',
        'monthly.SummaryRatingsController'
    ],
    name: 'MySchool',

    launch: function() {
        Ext.create('MySchool.view.MainPanel', {renderTo: Ext.getBody()});
    }

});
