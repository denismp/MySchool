// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.app.myschool.model;

import com.app.myschool.model.MonthlyEvaluationRatings;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

privileged aspect MonthlyEvaluationRatings_Roo_Json {
    
    public String MonthlyEvaluationRatings.toJson() {
        return new JSONSerializer()
        .exclude("*.class").serialize(this);
    }
    
    public String MonthlyEvaluationRatings.toJson(String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(this);
    }
    
    public static MonthlyEvaluationRatings MonthlyEvaluationRatings.fromJsonToMonthlyEvaluationRatings(String json) {
        return new JSONDeserializer<MonthlyEvaluationRatings>()
        .use(null, MonthlyEvaluationRatings.class).deserialize(json);
    }
    
    public static String MonthlyEvaluationRatings.toJsonArray(Collection<MonthlyEvaluationRatings> collection) {
        return new JSONSerializer()
        .exclude("*.class").serialize(collection);
    }
    
    public static String MonthlyEvaluationRatings.toJsonArray(Collection<MonthlyEvaluationRatings> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(collection);
    }
    
    public static Collection<MonthlyEvaluationRatings> MonthlyEvaluationRatings.fromJsonArrayToMonthlyEvaluationRatingses(String json) {
        return new JSONDeserializer<List<MonthlyEvaluationRatings>>()
        .use("values", MonthlyEvaluationRatings.class).deserialize(json);
    }
    
}
