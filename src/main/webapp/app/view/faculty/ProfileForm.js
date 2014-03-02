/*
 * File: app/view/faculty/ProfileForm.js
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

Ext.define('MySchool.view.faculty.ProfileForm', {
    extend: 'Ext.form.Panel',
    alias: 'widget.facultyprofileform',

    requires: [
        'Ext.form.field.Text',
        'Ext.button.Button'
    ],

    itemId: 'facultyprofileform',
    minHeight: 500,
    autoScroll: true,
    layout: 'auto',
    bodyPadding: 10,
    collapseFirst: false,
    frameHeader: false,
    title: 'Faculty Profile Form',

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'textfield',
                    itemId: 'faculty-firstname',
                    width: 300,
                    fieldLabel: 'First Name',
                    name: 'firstName'
                },
                {
                    xtype: 'textfield',
                    itemId: 'faculty-middlename',
                    width: 300,
                    fieldLabel: 'Middle Name',
                    name: 'middleName'
                },
                {
                    xtype: 'textfield',
                    itemId: 'faculty-lastname',
                    width: 300,
                    fieldLabel: 'Last Name',
                    name: 'lastName'
                },
                {
                    xtype: 'textfield',
                    itemId: 'faculty-phone1',
                    fieldLabel: 'Phone1',
                    name: 'phone1'
                },
                {
                    xtype: 'textfield',
                    itemId: 'faculty-phone2',
                    fieldLabel: 'Phone2',
                    name: 'phone2'
                },
                {
                    xtype: 'textfield',
                    itemId: 'faculty-address1',
                    width: 300,
                    fieldLabel: 'Address1',
                    name: 'address1'
                },
                {
                    xtype: 'textfield',
                    itemId: 'faculty-address2',
                    width: 300,
                    fieldLabel: 'Address2',
                    name: 'address2'
                },
                {
                    xtype: 'textfield',
                    itemId: 'faculty-street',
                    width: 300,
                    fieldLabel: 'Street',
                    name: 'street'
                },
                {
                    xtype: 'textfield',
                    itemId: 'faculty-city',
                    width: 300,
                    fieldLabel: 'City',
                    name: 'city'
                },
                {
                    xtype: 'textfield',
                    itemId: 'faculty-province',
                    width: 300,
                    fieldLabel: 'Province/State',
                    name: 'province'
                },
                {
                    xtype: 'textfield',
                    itemId: 'faculty-postalcode',
                    fieldLabel: 'Postal Code',
                    name: 'postalCode'
                },
                {
                    xtype: 'textfield',
                    itemId: 'faculty-country',
                    fieldLabel: 'Country',
                    name: 'country'
                },
                {
                    xtype: 'textfield',
                    itemId: 'faculty-email',
                    width: 300,
                    fieldLabel: 'Email',
                    name: 'email'
                },
                {
                    xtype: 'button',
                    itemId: 'facultyprofileformeditbutton',
                    text: 'Edit'
                },
                {
                    xtype: 'button',
                    disabled: true,
                    itemId: 'facultyprofileformcanelbutton',
                    text: 'Cancel'
                },
                {
                    xtype: 'button',
                    disabled: true,
                    itemId: 'facultyprofileformsavebutton',
                    text: 'Save'
                }
            ]
        });

        me.callParent(arguments);
    }

});