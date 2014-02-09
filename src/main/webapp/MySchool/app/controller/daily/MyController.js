/*
 * File: app/controller/daily/MyController.js
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

Ext.define('MySchool.controller.daily.MyController', {
    extend: 'Ext.app.Controller',
    alias: 'controller.dailycontroller',

    onEditdailyhourstabClick: function(button, e, eOpts) {
        this.buttonHandler( button, e, eOpts );
    },

    onDailydetailsformhournumberfieldBlur: function(component, e, eOpts) {
        this.blurHandler( component, e, this );
    },

    onDailyrefreshtoolClick: function(tool, e, eOpts) {

    },

    onDailysearchtoolClick: function(tool, e, eOpts) {

    },

    onDailynewtoolClick: function(tool, e, eOpts) {

    },

    onDailysavetoolClick: function(tool, e, eOpts) {

    },

    onDailydeletetoolClick: function(tool, e, eOpts) {

    },

    onDailylocktoolClick: function(tool, e, eOpts) {

    },

    buttonHandler: function(button, e, eOpts) {
        debugger;
        window.console.log(button);
        var b_		= button;
        var form	= b_.up('panel');
        var p_		= form.up();
        var pItemId_ = p_.getItemId();
        var field_;

        if (pItemId_ == 'dailyhourstab') {
            field_ = p_.down('numberfield');
        } else {
            field_ = p_.down('textareafield');
        }

        if (b_.getText().charAt(0) == 'D') {
            b_ = p_.down('#edit' + pItemId_);
            b_.setText('Edit');
            b_.setDisabled(false);
            field_.setDisabled(true);
        } else {
            b_.setText('Done');
            field_.setDisabled(false);
            field_.focus();
        }
    },

    blurHandler: function(o, event, eOpts) {
        //debugger;
        var p_ = o.up('form').up('panel');
        var topP_ = p_.up('panel');
        var pItemId_ = p_.getItemId();
        var edit_ = p_.down('#edit' + pItemId_);
        console.log( edit_ );
        console.log( topP_ );
        //topP_.buttonHandler(edit_);

        Ext.Msg.show({
            title:'Save Changes?',
            msg: 'Would you like to save your changes to ' + pItemId_ + ' ?',
            buttons: Ext.Msg.YESNO,
            icon: Ext.Msg.QUESTION,
            fn: function(buttonId) {
                if (buttonId == 'yes') {
                    Ext.Msg.show({
                        title: 'Save',
                        msg: 'record saved',
                        buttons: Ext.Msg.OK,
                        icon: Ext.window.MessageBox.INFO
                    });
                }
                else {
                    Ext.Msg.show({
                        title: 'Cancel',
                        msg: 'record restored',
                        buttons: Ext.Msg.OK,
                        icon: Ext.window.MessageBox.INFO
                    });
                }
                //topP_ = eOpts;
                //topP_.buttonHandler.call(edit_);
                //edit.setText('Edit');
                console.log( topP_ );
                topP_.buttonHandler(edit_);
                //p_.buttonHandler(edit_);
            }
        });
        debugger;
        this.buttonHandler( edit_ );
    },

    init: function(application) {
                this.control({
                    "#editdailytesttabpanel": {
                        click: this.buttonHandler
                    },
                    "#dailytesttabpaneltextbox": {
                        blur: this.blurHandler
                    },
                    "#editdailyresourcesusedtabpanel": {
                        click: this.buttonHandler
                    },
                    "#dailyresourcesusedtabpaneltextbox": {
                        blur: this.blurHandler
                    },
                    "#editdailystudydetailstabpanel": {
                        click: this.buttonHandler
                    },
                    "#dailystudydetailstabpaneltextbox": {
                        blur: this.blurHandler
                    },
                    "#editdailydetailsevaluationtabpanel": {
                        click: this.buttonHandler
                    },
                    "#dailydetailsevaluationtabpaneltextbox": {
                        blur: this.blurHandler
                    },
                    "#editdailydetailscorrectiontabpanel": {
                        click: this.buttonHandler
                    },
                    "#dailydetailscorrectiontabpaneltextbox": {
                        blur: this.blurHandler
                    },
                    "#editdailydetailsactionstabpanel": {
                        click: this.buttonHandler
                    },
                    "#dailydetailsactionstabpaneltextbox": {
                        blur: this.blurHandler
                    }

                });

        this.control({
            "#editdailyhourstab": {
                click: this.onEditdailyhourstabClick
            },
            "#dailydetailsformhournumberfield": {
                blur: this.onDailydetailsformhournumberfieldBlur
            },
            "#dailyrefreshtool": {
                click: this.onDailyrefreshtoolClick
            },
            "#dailysearchtool": {
                click: this.onDailysearchtoolClick
            },
            "#dailynewtool": {
                click: this.onDailynewtoolClick
            },
            "#dailysavetool": {
                click: this.onDailysavetoolClick
            },
            "#dailydeletetool": {
                click: this.onDailydeletetoolClick
            },
            "#dailylocktool": {
                click: this.onDailylocktoolClick
            }
        });
    }

});
