/*
 * File: app/store/student/StudentStore.js
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

Ext.define('MySchool.store.student.StudentStore', {
    extend: 'Ext.data.Store',

    requires: [
        'MySchool.model.student.StudentModel'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            autoLoad: true,
            model: 'MySchool.model.student.StudentModel',
            storeId: 'student.StudentStore',
            proxy: {
                type: 'rest',
                url: 'students',
                headers: {
                    Accept: 'application/json'
                },
                reader: {
                    type: 'json',
                    root: 'data'
                },
                writer: {
                    type: 'json',
                    dateFormat: 'm/d/Y',
                    encode: true,
                    root: 'data'
                }
            }
        }, cfg)]);
    }
});