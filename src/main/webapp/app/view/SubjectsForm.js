/*
 * File: app/view/SubjectsForm.js
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

Ext.define('MySchool.view.SubjectsForm', {
    extend: 'Ext.form.Panel',
    alias: 'widget.subjectsform',

    id: 'subjectdetailsform',
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
                    id: 'subjectdescriptiontextarea',
                    fieldLabel: 'Description',
                    name: 'description',
                    listeners: {
                        change: {
                            fn: me.onSubjectdescriptiontextareaChange,
                            scope: me
                        }
                    }
                },
                {
                    xtype: 'textareafield',
                    anchor: '100%',
                    id: 'subjectobjectivetextarea',
                    fieldLabel: 'Objectives',
                    name: 'objectives'
                }
            ]
        });

        me.callParent(arguments);
    },

    onSubjectdescriptiontextareaChange: function(field, newValue, oldValue, eOpts) {

    }

});