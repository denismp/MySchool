// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.app.myschool.model;

import com.app.myschool.model.MonthlySummaryRatingsView;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

privileged aspect MonthlySummaryRatingsView_Roo_Json {
    
    public String MonthlySummaryRatingsView.toJson() {
        return new JSONSerializer()
        .exclude("*.class").serialize(this);
    }
    
    public String MonthlySummaryRatingsView.toJson(String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(this);
    }
    
    public static MonthlySummaryRatingsView MonthlySummaryRatingsView.fromJsonToMonthlySummaryRatingsView(String json) {
        return new JSONDeserializer<MonthlySummaryRatingsView>()
        .use(null, MonthlySummaryRatingsView.class).deserialize(json);
    }
    
    public static String MonthlySummaryRatingsView.toJsonArray(Collection<MonthlySummaryRatingsView> collection) {
        return new JSONSerializer()
        .exclude("*.class").serialize(collection);
    }
    
    public static String MonthlySummaryRatingsView.toJsonArray(Collection<MonthlySummaryRatingsView> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(collection);
    }
    
    public static Collection<MonthlySummaryRatingsView> MonthlySummaryRatingsView.fromJsonArrayToMonthlySummaryRatingsViews(String json) {
        return new JSONDeserializer<List<MonthlySummaryRatingsView>>()
        .use("values", MonthlySummaryRatingsView.class).deserialize(json);
    }
    
}
