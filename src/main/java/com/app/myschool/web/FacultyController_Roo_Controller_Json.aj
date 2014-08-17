// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.app.myschool.web;

import com.app.myschool.model.Faculty;
import com.app.myschool.web.FacultyController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

privileged aspect FacultyController_Roo_Controller_Json {
    
    @RequestMapping(params = "find=ByUserNameEquals", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> FacultyController.jsonFindFacultysByUserNameEquals(@RequestParam("userName") String userName) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        return new ResponseEntity<String>(Faculty.toJsonArray(Faculty.findFacultysByUserNameEquals(userName).getResultList()), headers, HttpStatus.OK);
    }
    
}