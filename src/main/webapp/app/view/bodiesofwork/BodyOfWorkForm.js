/*
 * File: app/view/bodiesofwork/BodyOfWorkForm.js
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

Ext.define('MySchool.view.bodiesofwork.BodyOfWorkForm', {
    extend: 'Ext.form.Panel',
    alias: 'widget.bodyofworkform',

    requires: [
        'Ext.form.field.TextArea'
    ],

    itemId: 'bodyofworkform',
    width: 508,
    bodyPadding: 10,
    title: 'Body Of Work',

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'textareafield',
                    itemId: 'bodyofworkwhattextbox',
                    width: 492,
                    fieldLabel: 'What',
                    name: 'what',
                    readOnly: true,
                    size: 5
                },
                {
                    xtype: 'textareafield',
                    anchor: '100%',
                    itemId: 'bodyofworkdescriptiontextbox',
                    fieldLabel: 'Description',
                    name: 'description',
                    readOnly: true
                },
                {
                    xtype: 'textareafield',
                    anchor: '100%',
                    itemId: 'bodyofworkobjectivetextbox',
                    fieldLabel: 'Objective',
                    name: 'objective',
                    readOnly: true
                }
            ]
        });

        me.callParent(arguments);
    }

});