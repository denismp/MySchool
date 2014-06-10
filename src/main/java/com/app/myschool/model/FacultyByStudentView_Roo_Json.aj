// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.app.myschool.model;

import com.app.myschool.model.FacultyByStudentView;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

privileged aspect FacultyByStudentView_Roo_Json {
    
    public String FacultyByStudentView.toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
    
    public static FacultyByStudentView FacultyByStudentView.fromJsonToFacultyByStudentView(String json) {
        return new JSONDeserializer<FacultyByStudentView>().use(null, FacultyByStudentView.class).deserialize(json);
    }
    
    public static String FacultyByStudentView.toJsonArray(Collection<FacultyByStudentView> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    public static Collection<FacultyByStudentView> FacultyByStudentView.fromJsonArrayToFacultyByStudentViews(String json) {
        return new JSONDeserializer<List<FacultyByStudentView>>().use(null, ArrayList.class).use("values", FacultyByStudentView.class).deserialize(json);
    }
    
}
