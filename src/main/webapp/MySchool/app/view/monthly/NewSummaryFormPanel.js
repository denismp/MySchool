/*
 * File: app/view/monthly/NewSummaryFormPanel.js
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

Ext.define('MySchool.view.monthly.NewSummaryFormPanel', {
    extend: 'Ext.form.Panel',
    alias: 'widget.monthlynewsummaryformpanel',

    requires: [
        'Ext.form.FieldSet',
        'Ext.form.field.Number',
        'Ext.button.Button'
    ],

    draggable: true,
    floating: true,
    frame: true,
    height: 620,
    itemId: 'monthlynewsummaryformpanel',
    width: 700,
    bodyPadding: 10,
    title: 'New Monthly Summary Form',

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'fieldset',
                    itemId: 'monthlysummaryfieldset',
                    title: 'Monthly Summary Fields',
                    items: [
                        {
                            xtype: 'numberfield',
                            anchor: '100%',
                            itemId: 'newmonthlysummary-month_number',
                            fieldLabel: 'Month Number(1-12)',
                            name: 'month_number',
                            maxValue: 12,
                            minValue: 1
                        },
                        {
                            xtype: 'textfield',
                            anchor: '100%',
                            itemId: 'newmonthlysummary-feelings',
                            minHeight: 50,
                            fieldLabel: 'Feelings',
                            name: 'feelings'
                        },
                        {
                            xtype: 'textfield',
                            anchor: '100%',
                            itemId: 'newmonthlysummary-reflections',
                            minHeight: 50,
                            fieldLabel: 'Reflections',
                            name: 'reflections'
                        },
                        {
                            xtype: 'textfield',
                            anchor: '100%',
                            itemId: 'newmonthlysummary-patternsofcorrections',
                            minHeight: 50,
                            fieldLabel: 'Patterns Of Corrections',
                            name: 'patternsofcorrections'
                        },
                        {
                            xtype: 'textfield',
                            anchor: '100%',
                            itemId: 'newmonthlysummary-effectivenesofactions',
                            minHeight: 50,
                            fieldLabel: 'Effectiveness Of Actions',
                            name: 'effectivenesofactions'
                        },
                        {
                            xtype: 'textfield',
                            anchor: '100%',
                            itemId: 'newmonthlysummary-actionresults',
                            minHeight: 50,
                            fieldLabel: 'Action Results',
                            name: 'actionresults'
                        },
                        {
                            xtype: 'textfield',
                            anchor: '100%',
                            itemId: 'newmonthlysummary-realizations',
                            minHeight: 50,
                            fieldLabel: 'Realizations',
                            name: 'realizations'
                        },
                        {
                            xtype: 'textfield',
                            anchor: '100%',
                            itemId: 'newmonthlysummary-plannedchanges',
                            minHeight: 50,
                            fieldLabel: 'Planned Changes',
                            name: 'plannedchanges'
                        },
                        {
                            xtype: 'textfield',
                            anchor: '100%',
                            itemId: 'newmonthlysummary-comments',
                            minHeight: 50,
                            fieldLabel: 'Comments',
                            name: 'comments'
                        }
                    ]
                },
                {
                    xtype: 'button',
                    itemId: 'newmonthlysummarycanel',
                    text: 'Canel'
                }
            ]
        });

        me.callParent(arguments);
    }

});