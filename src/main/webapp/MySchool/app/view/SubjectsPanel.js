/*
 * File: app/view/SubjectsPanel.js
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

Ext.define('MySchool.view.SubjectsPanel', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.subjectspanel',

    requires: [
        'MySchool.view.SubjectsGridPanel',
        'MySchool.view.SubjectsForm',
        'MySchool.view.SubjectRefreshTool',
        'MySchool.view.SubjectSearchTool',
        'MySchool.view.SubjectNewTool',
        'MySchool.view.SubjectSaveTool',
        'MySchool.view.SubjectDeleteTool'
    ],

    id: 'subjectspanel',
    layout: {
        align: 'stretch',
        type: 'hbox'
    },
    title: 'Subjects By Student',

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'subjectsgridpanel',
                    flex: 1
                },
                {
                    xtype: 'subjectsform',
                    flex: 1
                }
            ],
            tools: [
                {
                    xtype: 'subjectrefreshtool'
                },
                {
                    xtype: 'subjectsearchtool'
                },
                {
                    xtype: 'subjectnewtool'
                },
                {
                    xtype: 'subjectsavetool'
                },
                {
                    xtype: 'subjectdeletetool',
                    id: 'tooldeletesubjects'
                }
            ]
        });

        me.callParent(arguments);
    }

});