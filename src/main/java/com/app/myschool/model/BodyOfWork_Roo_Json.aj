// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.app.myschool.model;

import com.app.myschool.model.BodyOfWork;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

privileged aspect BodyOfWork_Roo_Json {
    
    public String BodyOfWork.toJson() {
        return new JSONSerializer()
        .exclude("*.class").serialize(this);
    }
    
    public String BodyOfWork.toJson(String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(this);
    }
    
    public static BodyOfWork BodyOfWork.fromJsonToBodyOfWork(String json) {
        return new JSONDeserializer<BodyOfWork>()
        .use(null, BodyOfWork.class).deserialize(json);
    }
    
    public static String BodyOfWork.toJsonArray(Collection<BodyOfWork> collection) {
        return new JSONSerializer()
        .exclude("*.class").serialize(collection);
    }
    
    public static String BodyOfWork.toJsonArray(Collection<BodyOfWork> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(collection);
    }
    
    public static Collection<BodyOfWork> BodyOfWork.fromJsonArrayToBodyOfWorks(String json) {
        return new JSONDeserializer<List<BodyOfWork>>()
        .use("values", BodyOfWork.class).deserialize(json);
    }
    
}
