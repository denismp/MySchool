/*
 * File: app/view/weekly/skills/GridPanel.js
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

Ext.define('MySchool.view.weekly.skills.GridPanel', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.weeklyskillsgridpanel',

    requires: [
        'Ext.grid.column.Number',
        'Ext.grid.View',
        'Ext.form.field.Number',
        'Ext.grid.column.CheckColumn',
        'Ext.form.field.Checkbox',
        'Ext.grid.column.Date',
        'Ext.grid.plugin.CellEditing'
    ],

    id: 'weeklyskillsgridpanel',
    autoScroll: true,
    title: '[student name]Weekly Skills By Subject And Week',
    forceFit: true,
    store: 'weekly.SkillRatingsStore',

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            columns: [
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'subjName',
                    text: 'Subject Name'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'qtrName',
                    text: 'qtrName'
                },
                {
                    xtype: 'numbercolumn',
                    dataIndex: 'qtrYear',
                    text: 'Year',
                    format: '0000'
                },
                {
                    xtype: 'numbercolumn',
                    dataIndex: 'week_month',
                    text: 'Month',
                    format: '00'
                },
                {
                    xtype: 'numbercolumn',
                    dataIndex: 'week_number',
                    text: 'Week',
                    format: '0'
                },
                {
                    xtype: 'numbercolumn',
                    dataIndex: 'remembering',
                    text: 'Remembering',
                    format: '00',
                    editor: {
                        xtype: 'numberfield',
                        maxValue: 10,
                        minValue: 6
                    }
                },
                {
                    xtype: 'numbercolumn',
                    dataIndex: 'understanding',
                    text: 'Understanding',
                    format: '00',
                    editor: {
                        xtype: 'numberfield',
                        maxValue: 10,
                        minValue: 6
                    }
                },
                {
                    xtype: 'numbercolumn',
                    dataIndex: 'applying',
                    text: 'Applying',
                    format: '00',
                    editor: {
                        xtype: 'numberfield',
                        maxValue: 10,
                        minValue: 6
                    }
                },
                {
                    xtype: 'numbercolumn',
                    dataIndex: 'analyzing',
                    text: 'Analyzing',
                    format: '00',
                    editor: {
                        xtype: 'numberfield',
                        maxValue: 10,
                        minValue: 6
                    }
                },
                {
                    xtype: 'numbercolumn',
                    dataIndex: 'evaluating',
                    text: 'Evaluating',
                    format: '00',
                    editor: {
                        xtype: 'numberfield',
                        maxValue: 10,
                        minValue: 6
                    }
                },
                {
                    xtype: 'numbercolumn',
                    dataIndex: 'creating',
                    text: 'Creating',
                    format: '00',
                    editor: {
                        xtype: 'numberfield',
                        maxValue: 10,
                        minValue: 6
                    }
                },
                {
                    xtype: 'checkcolumn',
                    dataIndex: 'locked',
                    text: 'Locked?',
                    editor: {
                        xtype: 'checkboxfield'
                    }
                },
                {
                    xtype: 'numbercolumn',
                    hidden: true,
                    dataIndex: 'id',
                    text: 'id',
                    format: '000000'
                },
                {
                    xtype: 'numbercolumn',
                    hidden: true,
                    dataIndex: 'subjId',
                    text: 'subjectId',
                    format: '000000'
                },
                {
                    xtype: 'numbercolumn',
                    hidden: true,
                    dataIndex: 'weeklyskillId',
                    text: 'skillId',
                    format: '000000'
                },
                {
                    xtype: 'numbercolumn',
                    dataIndex: 'version',
                    text: 'version',
                    format: '000000'
                },
                {
                    xtype: 'numbercolumn',
                    hidden: true,
                    dataIndex: 'qtrId',
                    text: 'qtrId',
                    format: '000000'
                },
                {
                    xtype: 'numbercolumn',
                    hidden: true,
                    dataIndex: 'studentId',
                    text: 'studentId',
                    format: '000000'
                },
                {
                    xtype: 'gridcolumn',
                    hidden: true,
                    dataIndex: 'studentUserName',
                    text: 'studentUserName'
                },
                {
                    xtype: 'datecolumn',
                    hidden: true,
                    dataIndex: 'lastUpdated',
                    text: 'lastUpdated',
                    format: 'm/d/Y'
                },
                {
                    xtype: 'gridcolumn',
                    hidden: true,
                    dataIndex: 'whoUpdated',
                    text: 'whoUpdated'
                }
            ],
            viewConfig: {
                itemId: 'bodiesofworksubjectsview'
            },
            plugins: [
                Ext.create('Ext.grid.plugin.CellEditing', {
                    clicksToEdit: 1
                })
            ]
        });

        me.callParent(arguments);
    }

});