/*
 * File: app/view/subject/SubjectsGridPanel.js
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

Ext.define('MySchool.view.subject.SubjectsGridPanel', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.subjectsgridpanel',

    itemId: 'subjectsgrid',
    width: 1000,
    autoScroll: true,
    title: '[student name] Subjects',
    forceFit: true,
    store: 'subject.SubjectStore',

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            columns: [
                {
                    xtype: 'numbercolumn',
                    hidden: true,
                    dataIndex: 'id',
                    text: 'subjectid',
                    format: '000000'
                },
                {
                    xtype: 'gridcolumn',
                    width: 94,
                    dataIndex: 'name',
                    text: 'Subject Name'
                },
                {
                    xtype: 'numbercolumn',
                    width: 97,
                    dataIndex: 'gradeLevel',
                    text: 'Grade Level',
                    format: '00'
                },
                {
                    xtype: 'numbercolumn',
                    dataIndex: 'creditHours',
                    text: 'Credit Hours'
                },
                {
                    xtype: 'datecolumn',
                    dataIndex: 'lastUpdated',
                    text: 'Date',
                    format: 'm/d/Y'
                },
                {
                    xtype: 'checkcolumn',
                    dataIndex: 'completed',
                    text: 'Complete?',
                    editor: {
                        xtype: 'checkboxfield'
                    }
                },
                {
                    xtype: 'gridcolumn',
                    hidden: true,
                    dataIndex: 'version',
                    text: 'version'
                },
                {
                    xtype: 'gridcolumn',
                    renderer: function(value, metaData, record, rowIndex, colIndex, store, view) {
                        //debugger;
                        var myobj = record.get( 'quarter' );
                        var myQtrName = record.get( 'quarter' ).qtrName;
                        window.console.log( 'quarter=' + myQtrName );
                        return myQtrName;

                    },
                    dataIndex: 'quarter',
                    text: 'Quarter'
                },
                {
                    xtype: 'gridcolumn',
                    renderer: function(value, metaData, record, rowIndex, colIndex, store, view) {
                        var myQtrYear = record.data.quarter.qtr_year;
                        return myQtrYear;
                    },
                    text: 'QtrYear'
                },
                {
                    xtype: 'gridcolumn',
                    renderer: function(value, metaData, record, rowIndex, colIndex, store, view) {
                        //debugger;
                        //var myobj = record.get( 'quarter' ).student;
                        var myuser = record.data.quarter.student.userName;
                        window.console.log( 'userName=' + myuser );
                        return myuser;
                    },
                    dataIndex: 'userName',
                    text: 'UserName'
                }
            ],
            viewConfig: {
                id: 'subjectsgridview'
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