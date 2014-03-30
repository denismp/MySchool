/*
 * File: app/store/daily/MyJsonStore.js
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

Ext.define('MySchool.store.daily.MyJsonStore', {
    extend: 'Ext.data.Store',

    requires: [
        'MySchool.model.daily.DailyModel',
        'Ext.data.proxy.Rest',
        'Ext.data.reader.Json'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            model: 'MySchool.model.daily.DailyModel',
            storeId: 'daily.MyJsonStore',
            proxy: {
                type: 'rest',
                url: 'dailys',
                headers: {
                    Accept: 'application/json'
                },
                reader: {
                    type: 'json',
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
                load: {
                    fn: me.onJsonstoreLoad,
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
        debugger;
        var smsg = response.request.options.method + '<br>' + response.request.options.action + '<br>' + response.responseText + '<br>' + response.status + '<br>' + response.statusText + '<br>' + operation.params.data;
        Ext.MessageBox.show({
            title: 'REMOTE EXCEPTION',
            msg: smsg,
            icon: Ext.MessageBox.ERROR,
            buttons: Ext.Msg.OK,
            resizeable: true
        });
        window.console.log( smsg );
        if( this.getCount() > 0 )
        {
            this.reload();
        }
        //this.reload();

    },

    onJsonstoreLoad: function(store, records, successful, eOpts) {
        //debugger;
        console.log("daily.MyJsonStore.onJsonstoreLoad() called...");
    },

    onJsonstoreWrite: function(store, operation, eOpts) {
        //debugger;
        console.log("daily.MyJsonStore.onJsonstoreWrite(): called...");
        store.reload();
    }

});