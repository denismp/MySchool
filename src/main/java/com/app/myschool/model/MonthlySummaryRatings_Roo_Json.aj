// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.app.myschool.model;

import com.app.myschool.model.MonthlySummaryRatings;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

privileged aspect MonthlySummaryRatings_Roo_Json {
    
    public String MonthlySummaryRatings.toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
    
    public static MonthlySummaryRatings MonthlySummaryRatings.fromJsonToMonthlySummaryRatings(String json) {
        return new JSONDeserializer<MonthlySummaryRatings>().use(null, MonthlySummaryRatings.class).deserialize(json);
    }
    
    public static String MonthlySummaryRatings.toJsonArray(Collection<MonthlySummaryRatings> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    public static Collection<MonthlySummaryRatings> MonthlySummaryRatings.fromJsonArrayToMonthlySummaryRatingses(String json) {
        return new JSONDeserializer<List<MonthlySummaryRatings>>().use(null, ArrayList.class).use("values", MonthlySummaryRatings.class).deserialize(json);
    }
    
}
