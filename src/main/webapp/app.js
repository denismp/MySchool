/*
 * File: app.js
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
		'monthly.SummaryRatings',
		'common.QuarterSubjectModel',
		'common.MonthModel',
		'weekly.SkillRatingsModel',
		'weekly.EvaluationsModel',
		'faculty.ByStudentModel',
		'student.StudentProfileModel',
		'faculty.FacultyTableModel',
		'student.PasswordModel',
		'security.SecurityModel',
		'subject.SchoolsModel'
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
		'monthly.EvaluationRatingsStore',
		'common.QuarterSubjectStore',
		'common.MonthStore',
		'weekly.SkillRatingsStore',
		'weekly.EvaluationsRatingsStore',
		'faculty.ByStudentStore',
		'student.StudentProfileStore',
		'faculty.FacultyTableStore',
		'student.StudentPasswordStore',
		'security.SecurityStore',
		'subject.SchoolsStore'
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
		'daily.RefreshTool',
		'daily.SearchTool',
		'daily.NewTool',
		'daily.SaveTool',
		'daily.DeleteTool',
		'daily.LockTool',
		'monthly.summary.RefreshTool',
		'monthly.summary.SearchTool',
		'monthly.summary.NewTool',
		'monthly.summary.SaveTool',
		'monthly.summary.DeleteTool',
		'monthly.summary.LockTool',
		'monthly.summary.NewSummaryFormPanel',
		'common.QuarterSubjectComboBox',
		'common.MonthComboBox',
		'common.MyTabPanel',
		'bodiesofwork.NewForm',
		'daily.NewDailyForm',
		'weekly.skills.DetailsTabPanel',
		'weekly.skills.RefreshTool',
		'weekly.skills.SearchTool',
		'weekly.skills.NewTool',
		'weekly.skills.SaveTool',
		'weekly.skills.DeleteTool',
		'weekly.skills.LockTool',
		'weekly.skills.NewForm',
		'weekly.evaluation.DetailsTabPanel',
		'weekly.evaluation.RefreshTool',
		'weekly.evaluation.SearchTool',
		'weekly.evaluation.NewTool',
		'weekly.evaluation.SaveTool',
		'weekly.evaluation.DeleteTool',
		'weekly.evaluation.LockTool',
		'weekly.evaluations.NewForm',
		'monthly.evaluation.NewFormPanel',
		'monthly.evaluation.TabPanel',
		'monthly.evaluation.RefreshTool',
		'monthly.evaluation.SearchTool',
		'monthly.evaluation.NewTool',
		'monthly.evaluation.SaveTool',
		'monthly.evaluation.DeleteTool',
		'monthly.evaluation.LockTool',
		'bodiesofwork.DetailsTabPanel',
		'faculty.RefreshTool',
		'faculty.SearchTool',
		'faculty.NewTool',
		'faculty.SaveTool',
		'faculty.DeleteTool',
		'faculty.LockTool',
		'student.ProfileRefreshTool',
		'student.ProfileSearchTool',
		'student.ProfileNewTool',
		'student.ProfileSaveTool',
		'student.ProfileDeleteTool',
		'student.ProfileLockTool',
		'subject.FacultyNamesComboBox',
		'student.PasswordTool',
		'student.PasswordDialog',
		'student.NewDialog',
		'faculty.OnlyFacultyProfilePanel',
		'faculty.ProfileGridFacultyOnlyPanel',
		'faculty.FacultyOnlyProfileForm',
		'faculty.NewDialog',
		'faculty.PasswordDialog',
		'subject.SchoolNameComboBox'
	],
	controllers: [
		'subject.SubjectsController',
		'bodiesofwork.MyController',
		'daily.MyController',
		'monthly.SummaryRatingsController',
		'weekly.SkillRatingsController',
		'weekly.EvaluationsController',
		'monthly.EvaluationRatingsController',
		'faculty.ByStudentController',
		'student.ProfileViewController',
		'faculty.OnlyFacultyProfileViewController'
	],
	name: 'MySchool',

	launch: function() {
		Ext.create('MySchool.view.MainPanel', {renderTo: Ext.getBody()});
	}

});
