/*
 * File: app/view/MainPanel.js
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

Ext.define('MySchool.view.MainPanel', {
    extend: 'Ext.panel.Panel',

    requires: [
        'MySchool.view.subject.SubjectsPanel',
        'MySchool.view.bodiesofwork.BodiesOfWorkPanel',
        'MySchool.view.daily.DailyPanel',
        'MySchool.view.weekly.skills.TabPanel',
        'MySchool.view.weekly.evaluation.DetailsPanel',
        'MySchool.view.monthly.summary.DetailsPanel',
        'MySchool.view.monthly.evaluation.DetailsPanel',
        'MySchool.view.faculty.ProfilePanel',
        'MySchool.view.student.ProfilePanel',
        'Ext.tab.Panel',
        'Ext.tab.Tab'
    ],

    itemId: 'mainpanel',
    maxHeight: 700,
    maxWidth: 1300,
    minHeight: 700,
    minWidth: 1300,
    autoScroll: true,
    layout: 'fit',

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'tabpanel',
                    id: 'maintabpanel',
                    minHeight: 700,
                    activeTab: 0,
                    items: [
                        {
                            xtype: 'panel',
                            itemId: 'subjecttab',
                            width: 1000,
                            title: 'SubjectsByStudent',
                            layout: {
                                type: 'hbox',
                                align: 'stretch'
                            },
                            items: [
                                {
                                    xtype: 'subjectspanel',
                                    autoScroll: true,
                                    flex: 1
                                }
                            ]
                        },
                        {
                            xtype: 'panel',
                            height: 700,
                            itemId: 'bodiesofworkstab',
                            title: 'Bodies Of Work',
                            layout: {
                                type: 'hbox',
                                align: 'stretch'
                            },
                            dockedItems: [
                                {
                                    xtype: 'mybodiesofworkpanel',
                                    itemId: 'bodiesofworkpaneel',
                                    flex: 1,
                                    dock: 'top'
                                }
                            ]
                        },
                        {
                            xtype: 'panel',
                            height: 700,
                            itemId: 'dailytab',
                            title: 'Daily',
                            layout: {
                                type: 'hbox',
                                align: 'stretch'
                            },
                            dockedItems: [
                                {
                                    xtype: 'dailypanel',
                                    flex: 1,
                                    dock: 'top'
                                }
                            ]
                        },
                        {
                            xtype: 'panel',
                            itemId: 'weeklyskillstab',
                            minHeight: 700,
                            title: 'Weekly Skills',
                            layout: {
                                type: 'vbox',
                                align: 'stretch'
                            },
                            dockedItems: [
                                {
                                    xtype: 'weeklydetailspanel',
                                    itemId: 'weeklyskillstabpanel',
                                    flex: 1,
                                    dock: 'top'
                                }
                            ]
                        },
                        {
                            xtype: 'panel',
                            itemId: 'weeklyevaluatontab',
                            minHeight: 700,
                            title: 'Weekly Evaluation',
                            layout: {
                                type: 'hbox',
                                align: 'stretch'
                            },
                            dockedItems: [
                                {
                                    xtype: 'weeklyevaluationdetailspanel',
                                    flex: 1,
                                    dock: 'top'
                                }
                            ]
                        },
                        {
                            xtype: 'panel',
                            itemId: 'monthlysummarytab',
                            minHeight: 700,
                            title: 'Monthly Summary',
                            layout: {
                                type: 'hbox',
                                align: 'stretch'
                            },
                            dockedItems: [
                                {
                                    xtype: 'monthlysummarydetailspanel',
                                    flex: 1,
                                    dock: 'top'
                                }
                            ]
                        },
                        {
                            xtype: 'panel',
                            itemId: 'monthlyevaluationtab',
                            minHeight: 700,
                            title: 'Monthly Evaluation',
                            layout: {
                                type: 'hbox',
                                align: 'stretch'
                            },
                            dockedItems: [
                                {
                                    xtype: 'monthlyevaluationdetailspanel',
                                    flex: 1,
                                    dock: 'top'
                                }
                            ]
                        },
                        {
                            xtype: 'panel',
                            itemId: 'facultyprofilesbystudenttab',
                            title: 'Faculty Profiles by Student',
                            dockedItems: [
                                {
                                    xtype: 'facultyprofilepanel',
                                    dock: 'top'
                                }
                            ]
                        },
                        {
                            xtype: 'panel',
                            itemId: 'studentprofilestab',
                            minHeight: 600,
                            title: 'Student Profiles',
                            dockedItems: [
                                {
                                    xtype: 'studentprofilepanel',
                                    itemId: 'studentprofilepanel',
                                    dock: 'top'
                                }
                            ]
                        }
                    ]
                }
            ]
        });

        me.callParent(arguments);
    }

});