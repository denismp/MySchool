// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.app.myschool.model;

import com.app.myschool.model.Guardian;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

privileged aspect Guardian_Roo_Json {
    
    public String Guardian.toJson() {
        return new JSONSerializer()
        .exclude("*.class").serialize(this);
    }
    
    public String Guardian.toJson(String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(this);
    }
    
    public static Guardian Guardian.fromJsonToGuardian(String json) {
        return new JSONDeserializer<Guardian>()
        .use(null, Guardian.class).deserialize(json);
    }
    
    public static String Guardian.toJsonArray(Collection<Guardian> collection) {
        return new JSONSerializer()
        .exclude("*.class").serialize(collection);
    }
    
    public static String Guardian.toJsonArray(Collection<Guardian> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(collection);
    }
    
    public static Collection<Guardian> Guardian.fromJsonArrayToGuardians(String json) {
        return new JSONDeserializer<List<Guardian>>()
        .use("values", Guardian.class).deserialize(json);
    }
    
}
