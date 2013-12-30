/*
 * File: app/store/subject/QuarterYearStore.js
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

Ext.define('MySchool.store.subject.QuarterYearStore', {
    extend: 'Ext.data.Store',

    requires: [
        'Ext.data.Field'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            autoLoad: true,
            storeId: 'subject.QuarterYearStore',
            data: [
                
            ],
            fields: [
                {
                    name: 'name',
                    type: 'string'
                },
                {
                    name: 'value',
                    type: 'int'
                }
            ]
        }, cfg)]);
    }
});