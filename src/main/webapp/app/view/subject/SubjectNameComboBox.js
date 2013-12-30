/*
 * File: app/view/subject/SubjectNameComboBox.js
 *
 * This file was generated by Sencha Architect version 3.0.1.
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

Ext.define('MySchool.view.subject.SubjectNameComboBox', {
    extend: 'Ext.form.field.ComboBox',
    alias: 'widget.subjectnamecombobox',

    itemId: 'subjectnamecombobox',
    fieldLabel: 'Subject Name',
    emptyText: 'Select one.',
    displayField: 'subjName',
    forceSelection: true,
    store: 'subject.AllSubjectStore',
    valueField: 'subjId',

    initComponent: function() {
        var me = this;

        me.callParent(arguments);
    }

});