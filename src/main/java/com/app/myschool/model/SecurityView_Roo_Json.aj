// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.app.myschool.model;

import com.app.myschool.model.SecurityView;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

privileged aspect SecurityView_Roo_Json {
    
    public String SecurityView.toJson() {
        return new JSONSerializer()
        .exclude("*.class").serialize(this);
    }
    
    public String SecurityView.toJson(String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(this);
    }
    
    public static SecurityView SecurityView.fromJsonToSecurityView(String json) {
        return new JSONDeserializer<SecurityView>()
        .use(null, SecurityView.class).deserialize(json);
    }
    
    public static String SecurityView.toJsonArray(Collection<SecurityView> collection) {
        return new JSONSerializer()
        .exclude("*.class").serialize(collection);
    }
    
    public static String SecurityView.toJsonArray(Collection<SecurityView> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(collection);
    }
    
    public static Collection<SecurityView> SecurityView.fromJsonArrayToSecurityViews(String json) {
        return new JSONDeserializer<List<SecurityView>>()
        .use("values", SecurityView.class).deserialize(json);
    }
    
}