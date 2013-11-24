/*
 * File: app/store/subject/SubjectStore.js
 *
 * This file was generated by Sencha Architect version 3.0.0.
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

Ext.define('MySchool.store.subject.SubjectStore', {
    extend: 'Ext.data.Store',
    alias: 'store.subjectstore',

    requires: [
        'MySchool.model.subject.SubjectsModel',
        'Ext.data.proxy.Rest',
        'Ext.data.reader.Json',
        'Ext.data.writer.Json'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            autoLoad: true,
            autoSync: true,
            model: 'MySchool.model.subject.SubjectsModel',
            storeId: 'subject.SubjectStore',
            proxy: {
                type: 'rest',
                url: 'subjects/json',
                headers: {
                    Accept: 'application/json'
                },
                reader: {
                    type: 'json',
                    root: 'data'
                },
                writer: {
                    type: 'json',
                    write: function(request) {
                        debugger;
                        var operation = request.operation;
                        var records   = operation.records || [];
                        var len       = records.length;
                        var i         = 0;
                        var data      = [];

                        for (; i < len; i++) {
                            data.push(this.getRecordData(records[i], operation));
                            if( data[i].quarter.lastUpdated !== "" )
                            {
                                data[i].quarter.lastUpdated = Ext.Date.format(data[i].quarter.lastUpdated, 'm/d/Y' );
                            }
                            if( data[i].quarter.student.lastUpdated !== "" )
                            {
                                data[i].quarter.student.lastUpdated = Ext.Date.format( data[i].quarter.student.lastUpdated, 'm/d/Y' );
                            }
                        }
                        return this.writeRecords(request, data);

                    },
                    dateFormat: 'm/d/Y',
                    encode: true,
                    root: 'data'
                },
                listeners: {
                    exception: {
                        fn: me.onRestException,
                        scope: me
                    }
                }
            },
            listeners: {
                beforesync: {
                    fn: me.onJsonstoreBeforeSync,
                    scope: me
                },
                write: {
                    fn: me.onJsonstoreWrite,
                    scope: me
                }
            }
        }, cfg)]);
    },

    onRestException: function(proxy, response, operation, eOpts) {
        //debugger;
        var smsg = response.request.options.method + ':' + response.request.options.action + ':' + response.responseText + ':' + response.status + ':' + response.statusText + ':' + operation.params.data;
        Ext.MessageBox.show({
            title: 'REMOTE EXCEPTION',
            msg: smsg,
            icon: Ext.MessageBox.ERROR,
            buttons: Ext.Msg.OK
        });
        window.console.log( smsg );
    },

    onJsonstoreBeforeSync: function(options, eOpts) {
        //debugger;
        var smsg = 'Before sync()';
        //Ext.MessageBox.show({
        //    title: 'REMOTE EXCEPTION',
        //    msg: smsg,
        //    icon: Ext.MessageBox.ERROR,
        //    buttons: Ext.Msg.OK
        //});
        window.console.log( smsg );
    },

    onJsonstoreWrite: function(store, operation, eOpts) {
        store.reload();
    }

});