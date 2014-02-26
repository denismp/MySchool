package com.app.myschool.web;

import java.util.Map;

import com.app.myschool.model.Faculty;
import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/facultys")
@Controller
@RooWebScaffold(path = "facultys", formBackingObject = Faculty.class)
@RooWebJson(jsonObject = Faculty.class)
public class FacultyController {

    @RequestMapping(value = "/{id}", headers = "Accept=application/json")
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
    @RequestMapping(headers = "Accept=application/json")
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

    @RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> createFromJson(@RequestBody String json) {
        FacultyByStudentControllerHelper controllerHelper = new FacultyByStudentControllerHelper();
        return controllerHelper.createFromJson(json);
    }

    @RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> createFromJsonArray(@RequestBody String json) {
        FacultyByStudentControllerHelper controllerHelper = new FacultyByStudentControllerHelper();
        return controllerHelper.createFromJsonArray(json);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> updateFromJson(@RequestBody String json) {
        FacultyByStudentControllerHelper controllerHelper = new FacultyByStudentControllerHelper();
        return controllerHelper.updateFromJson(json);
    }

    @RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> updateFromJsonArray(@RequestBody String json) {
        FacultyByStudentControllerHelper controllerHelper = new FacultyByStudentControllerHelper();
        return controllerHelper.updateFromJsonArray(json);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> deleteFromJson(@PathVariable("id") Long id) {
        FacultyByStudentControllerHelper controllerHelper = new FacultyByStudentControllerHelper();
        return controllerHelper.deleteFromJson(id);
    }
}
