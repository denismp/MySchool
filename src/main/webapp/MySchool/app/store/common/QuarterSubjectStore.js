/*
 * File: app/store/common/QuarterSubjectStore.js
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

Ext.define('MySchool.store.common.QuarterSubjectStore', {
    extend: 'Ext.data.Store',
    alias: 'store.commonquartersubjectstore',

    requires: [
        'MySchool.model.common.QuarterSubjectModel',
        'Ext.data.Field'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            autoLoad: true,
            model: 'MySchool.model.common.QuarterSubjectModel',
            storeId: 'common.QuarterSubjectStore',
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
                },
                {
                    name: 'qtrId',
                    type: 'int'
                },
                {
                    name: 'qtrName',
                    type: 'string'
                },
                {
                    name: 'subjId',
                    type: 'int'
                },
                {
                    name: 'subjName',
                    type: 'string'
                }
            ]
        }, cfg)]);
    },

    myLoad: function() {
        //commonQuarterSubjectStore.removeAll();
        this.removeAll();
        var subjectStore = Ext.getStore( 'subject.SubjectStore');
        var numSubjects = subjectStore.count();


        for( var i = 0; i < numSubjects; i++ )
        {
            var record = subjectStore.getAt(i);
            var qtrName = record.get( 'qtrName' );
            var qtrYear = record.get( 'qtrYear' );
            var subjName = record.get( 'subjName' );
            var subjId = record.get( 'subjId' );
            var qtrId = record.get( 'qtrId' );
            var name = qtrName + '/' + qtrYear + '-' + subjName;
            console.log( 'name=' + name );
            this.add({
                name: name,
                id: i,
                qtrName: qtrName,
                subjName: subjName,
                qtrId: qtrId,
                subjId: subjId
            });
        }
    }

});