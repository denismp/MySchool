// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.app.myschool.model;

import com.app.myschool.model.QuarterNames;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

privileged aspect QuarterNames_Roo_Json {
    
    public String QuarterNames.toJson() {
        return new JSONSerializer()
        .exclude("*.class").serialize(this);
    }
    
    public String QuarterNames.toJson(String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(this);
    }
    
    public static QuarterNames QuarterNames.fromJsonToQuarterNames(String json) {
        return new JSONDeserializer<QuarterNames>()
        .use(null, QuarterNames.class).deserialize(json);
    }
    
    public static String QuarterNames.toJsonArray(Collection<QuarterNames> collection) {
        return new JSONSerializer()
        .exclude("*.class").serialize(collection);
    }
    
    public static String QuarterNames.toJsonArray(Collection<QuarterNames> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(collection);
    }
    
    public static Collection<QuarterNames> QuarterNames.fromJsonArrayToQuarterNameses(String json) {
        return new JSONDeserializer<List<QuarterNames>>()
        .use("values", QuarterNames.class).deserialize(json);
    }
    
}
