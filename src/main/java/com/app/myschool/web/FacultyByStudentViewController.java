package com.app.myschool.web;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.myschool.model.Faculty;

@RooWebJson(jsonObject = Faculty.class )
@Controller
@RequestMapping("/facultybystudentviews")
public class FacultyByStudentViewController {

    @RequestMapping(value = "/json/{id}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<java.lang.String> showJson(@PathVariable("id") Long id) {
    	FacultyByStudentControllerHelper controllerHelper = new FacultyByStudentControllerHelper();
        return controllerHelper.showJson(id);
    }

    /*
    @RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<java.lang.String> listJson() {
        FacultyByStudentControllerHelper controllerHelper = new FacultyByStudentControllerHelper();
        return controllerHelper.listJson(Faculty.class);
    }
    */
    @RequestMapping(value = "/json", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<java.lang.String> listJson(@RequestParam Map<?, ?> params) {
        ResponseEntity<java.lang.String> ret_ = null;
        FacultyByStudentControllerHelper controllerHelper = new FacultyByStudentControllerHelper();
        if (params.containsKey("studentName")) {
            ret_ = controllerHelper.listJson(params);
        } else {
            ret_ = controllerHelper.listJson();
        }
        return ret_;
    }

    @RequestMapping(value = "/json", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> createFromJson(@RequestBody String json) {
        FacultyByStudentControllerHelper controllerHelper = new FacultyByStudentControllerHelper();
        return controllerHelper.createFromJson(json);
    }

    @RequestMapping(value = "/json/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> createFromJsonArray(@RequestBody String json) {
        FacultyByStudentControllerHelper controllerHelper = new FacultyByStudentControllerHelper();
        return controllerHelper.createFromJsonArray(json);
    }

    @RequestMapping(value = "/json/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> updateFromJson(@RequestBody String json) {
        FacultyByStudentControllerHelper controllerHelper = new FacultyByStudentControllerHelper();
        return controllerHelper.updateFromJson(json);
    }

    @RequestMapping(value = "/json/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> updateFromJsonArray(@RequestBody String json) {
        FacultyByStudentControllerHelper controllerHelper = new FacultyByStudentControllerHelper();
        return controllerHelper.updateFromJsonArray(json);
    }

    @RequestMapping(value = "/json/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> deleteFromJson(@PathVariable("id") Long id) {
        FacultyByStudentControllerHelper controllerHelper = new FacultyByStudentControllerHelper();
        return controllerHelper.deleteFromJson(id);
    }

}
