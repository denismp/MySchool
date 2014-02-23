/*
 * File: app/controller/bodiesofwork/MyController.js
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

Ext.define('MySchool.controller.bodiesofwork.MyController', {
    extend: 'Ext.app.Controller',
    alias: 'controller.bodiesofworkcontroller',

    models: [
        'bodiesofwork.BodiesOfWorkModel'
    ],
    stores: [
        'bodiesofwork.MyJsonStore',
        'subject.SubjectStore'
    ],
    views: [
        'bodiesofwork.GridPanel'
    ],

    refs: [
        {
            ref: 'BodiesOfWorkWhatTabPanel',
            selector: '#bodiesofworkwhattabpanel'
        },
        {
            ref: 'BodiesOfWorkDescriptionTabPanel',
            selector: '#bodiesofworkdescriptiontabpanel'
        },
        {
            ref: 'BodiesOfWorkObjectiveTabPanel',
            selector: '#bodiesofworkobjectivetabpanel'
        }
    ],

    onBodiesofworkssubjectsgridViewReady: function(tablepanel, eOpts) {
        //debugger;
        console.log('onBodiesofworkssubjectsgridViewReady()');
        var bws_ = Ext.getStore('bodiesofwork.MyJsonStore');
        var ss_ = Ext.getStore('student.StudentStore');
        var r_ = ss_.getAt(0);
        //        debugger
        if ( typeof( r_ ) != "undefined" ) {
            var studentName_ = r_.get('firstName') + " " + r_.get('middleName') + ' ' + r_.get('lastName');
            var g_ = Ext.ComponentQuery.query("#bodiesofworkssubjectsgrid")[0];
            g_.setTitle('[' + studentName_ + '] Bodies Of Work');
            bws_.load({
                callback: this.onMyJsonStoreLoad,
                scope: this,
                params: {
                    studentName: r_.get('userName'),
                    studentId: r_.get('id')
                }
            });
        }
        //grid.getSelectionModel().select( 0 );
        //tablepanel.getSelectionModel().select( 0 );

    },

    onBodiesofworkssubjectsgridSelectionChange: function(model, selected, eOpts) {
        //debugger;
        // in the onMyJsonStoreLoad we do a deselect so we need to test
        // if selected[0] has a value
        if ( Ext.isDefined( selected  ) && Ext.isDefined( selected[0]  )) {
            this.loadTabPanelForm( this.getBodiesOfWorkWhatTabPanel(), selected, 'what' );
            this.loadTabPanelForm( this.getBodiesOfWorkDescriptionTabPanel(), selected, 'description' );
            this.loadTabPanelForm( this.getBodiesOfWorkObjectiveTabPanel(), selected, 'objective' );

            console.log('onBodiesofworssubjectsgridSelectionChange()');
        }

    },

    onBodyofworkformBoxReady: function(component, width, height, eOpts) {
        //debugger;
        console.log('onBodyofworkformBoxReady()');
    },

    onBodiesofworkstabActivate: function(component, eOpts) {
        // catch the tab activate but only reload if we have processed
        // the viewready indicated by this.gridViewReady
        console.log('#bodiesofworkstab.activate()');

        if ( Ext.isDefined( this.gridViewReady  ) ) {
            var g_ = Ext.ComponentQuery.query("#bodiesofworkssubjectsgrid")[0];

            g_.getStore().reload();
        }

    },

    onToolrefreshbodiesofworkClick: function(tool, e, eOpts) {

    },

    onToolsearchbodiesofworkClick: function(tool, e, eOpts) {

    },

    onToolnewbodiesofworkClick: function(tool, e, eOpts) {
        debugger;
        var qs_ = Ext.getStore('subject.SubjectStore');
        var recCnt_ = qs_.getTotalCount();

        if (recCnt_ < 1) {
            Ext.MessageBox.show({
                title : 'Body of Work Exception',
                msg : 'Subjects by Student is empty, please add a record there first.',
                icon : Ext.MessageBox.ERROR,
                buttons : Ext.Msg.OK
            });

            return;
        }

        var newDialog = Ext.create( 'MySchool.view.bodiesofwork.NewForm' );
        var bws_ = Ext.getStore('bodiesofwork.MyJsonStore');
        var ss_ = Ext.getStore('student.StudentStore');
        var r_ = bws_.getAt( this.selectedIndex );
        var studentName_ = newDialog.down('#newbodiesofworkform-studentName');
        var qsCB_ = newDialog.down('#newbodiesofworkform-quarter');

        studentName_.setValue(ss_.getAt(0).get('userName'));

        if (r_ === null) {
            r_ = bws_.getAt(0);
        }

        if (r_) {
            var qtrId_ = r_.get('qtrId');
            var subjId_ = r_.get('subjId');

            newDialog.loadRecord(r_);

            for( var i_ = 0; i_ < recCnt_; i_++ ) {
                r_ = qs_.getAt(i_);
                if (r_ !== null && qtrId_ == r_.get('qtrId') && subjId_ == r_.get('subjId')) {
                    qsCB_.setValue(r_.get('id'));
                    qsCB_.setDisabled(true);
                    break;
                }
            }
        }
        else {
        // no body of works records in database
            var b_ = newDialog.down('#newbodiesofworkcreate');
            var workName_ = newDialog.down('#newbodiesofworkform-workName');
            var what_ = newDialog.down('#newbodiesofworkform-what');
            var desc_ = newDialog.down('#newbodiesofworkform-description');
            var obj_ = newDialog.down('#newbodiesofworkform-objective');

            b_.setDisabled(true);
            qsCB_.setValue(qs_.getAt(0).get('id'));
            workName_.setValue("replace_me_with_name");
            what_.setValue("replace_me_with_what");
            desc_.setValue("replace_me_with_description");
            obj_.setValue("replace_me_with_objectives");
        }

        window.console.log( 'New' );
        newDialog.render( Ext.getBody() );
        newDialog.show();

    },

    onToolsavebodiesofworkClick: function(tool, e, eOpts) {

    },

    onTooldeletebodiesofworkClick: function(tool, e, eOpts) {

    },

    onToollockbodiesofworkClick: function(tool, e, eOpts) {

    },

    onNewbodiesofworkcancelClick: function(button, e, eOpts) {
            var bId_ = button.getItemId();
            var newDialog = button.up('newbodiesofworkform');
            var myForm = newDialog.getForm();
            var hide_ = true;
            var okToSync_ = false;

            if (bId_.indexOf("cancel") < 0) {
                debugger;
                var qsCB_ = newDialog.down('quartersubjectcombobox');

                if (bId_.indexOf("submit") > 0){
                    if (newDialog.down('#newbodiesofworkcreate').isDisabled()) {
                        var r_;
                        var qsId_ = qsCB_.getValue();

                        if (qsId_ === null) {
                            Ext.MessageBox.show({
                                title : 'Submit Exception',
                                msg : 'You must pick a Quarter and Subject.',
                                icon : Ext.MessageBox.ERROR,
                                buttons : Ext.Msg.OK
                            });
                        }
                        else {
                            Ext.MessageBox.show({
                                title : 'Submit BOW',
                                msg : 'CREATE',
                                icon : Ext.MessageBox.ERROR,
                                buttons : Ext.Msg.OK
                            });
                        }
                    }
                    else {
                        var r_ = myForm.getRecord();
                        myForm.updateRecord(r_);
                        Ext.MessageBox.show({
                            title : 'Submit BOW',
                            msg : 'UPDATE',
                            icon : Ext.MessageBox.ERROR,
                            buttons : Ext.Msg.OK
                        });
                    }
                }
                else {
                    var b_ = newDialog.down('#newbodiesofworkcreate');
                    var workName_ = newDialog.down('#newbodiesofworkform-workName');
                    var what_ = newDialog.down('#newbodiesofworkform-what');
                    var desc_ = newDialog.down('#newbodiesofworkform-description');
                    var obj_ = newDialog.down('#newbodiesofworkform-objective');

                    b_.setDisabled(true);
                    qsCB_.setDisabled(false);
                    hide_ = false;

                    workName_.setValue("replace_me_with_name");
                    what_.setValue("replace_me_with_what");
                    desc_.setValue("replace_me_with_description");
                    obj_.setValue("replace_me_with_objectives");
                }
            }

            if (hide_) {
                myForm.reset();
                button.up().hide();
            }
    },

    onNewbodiesofworksubmitClick: function(button, e, eOpts) {
            this.onNewbodiesofworkcancelClick(button, e, eOpts);
    },

    onNewbodiesofworkcreateClick: function(button, e, eOpts) {
            this.onNewbodiesofworkcancelClick(button, e, eOpts);
    },

    onBodiesofworkssubjectsgridSelect: function(rowmodel, record, index, eOpts) {
            window.console.log( "selected row in grid." );
            window.console.log( "index=" + index );
            this.selectedIndex = index;
    },

    init: function(application) {
                this.selectedIndex = 0;

        this.control({
            "#bodiesofworkssubjectsgrid": {
                viewready: this.onBodiesofworkssubjectsgridViewReady,
                selectionchange: this.onBodiesofworkssubjectsgridSelectionChange,
                select: this.onBodiesofworkssubjectsgridSelect
            },
            "#bodyofworkform": {
                boxready: this.onBodyofworkformBoxReady
            },
            "#bodiesofworkstab": {
                activate: this.onBodiesofworkstabActivate
            },
            "#toolrefreshbodiesofwork": {
                click: this.onToolrefreshbodiesofworkClick
            },
            "#toolsearchbodiesofwork": {
                click: this.onToolsearchbodiesofworkClick
            },
            "#toolnewbodiesofwork": {
                click: this.onToolnewbodiesofworkClick
            },
            "#toolsavebodiesofwork": {
                click: this.onToolsavebodiesofworkClick
            },
            "#tooldeletebodiesofwork": {
                click: this.onTooldeletebodiesofworkClick
            },
            "#toollockbodiesofwork": {
                click: this.onToollockbodiesofworkClick
            },
            "#newbodiesofworkcancel": {
                click: this.onNewbodiesofworkcancelClick
            },
            "#newbodiesofworksubmit": {
                click: this.onNewbodiesofworksubmitClick
            },
            "#newbodiesofworkcreate": {
                click: this.onNewbodiesofworkcreateClick
            }
        });
    },

    onMyJsonStoreLoad: function() {
        //debugger;
        var g_ = Ext.ComponentQuery.query("#bodiesofworkssubjectsgrid")[0];

        if (g_.getStore().getCount() > 0) {
            g_.getSelectionModel().deselectAll();
            g_.getSelectionModel().select( this.selectedIndex );
        }

        this.gridViewReady = true;

    },

    loadTabPanelForm: function(tabPanel, selected, fieldname) {
        debugger;
        var dockedItems = tabPanel.getDockedItems();
        var myForm = dockedItems[0];

        if( Ext.isDefined( myForm ) )
        {
            console.log( myForm );
            //var textBox = myForm.dockedItems.items[0];
            var textBox = myForm.down('textareafield');
            textBox.name = fieldname;
            myForm.loadRecord( selected[0] );
        }
        else
        {
            console.log( 'loadTabPanelForm(): No form' );
            //console.log( tabPanel );
        }
    }

});
