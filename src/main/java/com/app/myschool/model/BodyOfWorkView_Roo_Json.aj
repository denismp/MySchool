// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.app.myschool.model;

import com.app.myschool.model.BodyOfWorkView;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

privileged aspect BodyOfWorkView_Roo_Json {
    
    public String BodyOfWorkView.toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
    
    public static BodyOfWorkView BodyOfWorkView.fromJsonToBodyOfWorkView(String json) {
        return new JSONDeserializer<BodyOfWorkView>().use(null, BodyOfWorkView.class).deserialize(json);
    }
    
    public static String BodyOfWorkView.toJsonArray(Collection<BodyOfWorkView> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    public static Collection<BodyOfWorkView> BodyOfWorkView.fromJsonArrayToBodyOfWorkViews(String json) {
        return new JSONDeserializer<List<BodyOfWorkView>>().use(null, ArrayList.class).use("values", BodyOfWorkView.class).deserialize(json);
    }
    
}