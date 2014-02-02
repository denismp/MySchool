/*
 * File: app/store/common/MonthStore.js
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

Ext.define('MySchool.store.common.MonthStore', {
    extend: 'Ext.data.Store',
    alias: 'store.commonmonthStore',

    requires: [
        'MySchool.model.common.MonthModel',
        'Ext.data.Field'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            autoLoad: true,
            model: 'MySchool.model.common.MonthModel',
            storeId: 'common.MonthStore',
            data: [
                
            ],
            fields: [
                {
                    name: 'id',
                    type: 'int'
                },
                {
                    name: 'name',
                    type: 'string'
                }
            ]
        }, cfg)]);
    },

    myLoad: function() {
        this.removeAll();

        this.add( { name: "Jan", id: 1});
        this.add( { name: "Feb", id: 2});
        this.add( { name: "Mar", id: 3});
        this.add( { name: "Apr", id: 4});
        this.add( { name: "May", id: 5});
        this.add( { name: "Jun", id: 6});
        this.add( { name: "Jul", id: 7});
        this.add( { name: "Aug", id: 8});
        this.add( { name: "Sep", id: 9});
        this.add( { name: "Oct", id: 10});
        this.add( { name: "Nov", id: 11});
        this.add( { name: "Dec", id: 12});

    }

});