/*
 * File: app/view/subject/SubjectsForm.js
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

Ext.define('MySchool.view.subject.SubjectsForm', {
    extend: 'Ext.form.Panel',
    alias: 'widget.subjectsform',

    requires: [
        'Ext.form.field.TextArea'
    ],

    itemId: 'subjectdetailsform',
    autoScroll: true,
    bodyPadding: 10,
    title: 'Subject Details',

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'textareafield',
                    anchor: '100%',
                    itemId: 'subjectdescriptiontextarea',
                    fieldLabel: 'Description',
                    name: 'subjDescription',
                    readOnly: true
                },
                {
                    xtype: 'textareafield',
                    anchor: '100%',
                    itemId: 'subjectobjectivetextarea',
                    fieldLabel: 'Objectives',
                    name: 'subjObjectives',
                    readOnly: true
                }
            ]
        });

        me.callParent(arguments);
    }

});