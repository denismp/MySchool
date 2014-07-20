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

import com.app.myschool.model.Student;

@RooWebJson(jsonObject = Student.class )
@Controller
@RequestMapping("/securityviews")
public class SecurityViewController {
    @RequestMapping(value = "/json/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<java.lang.String> showJson(@PathVariable("id") Long id) {
        SecurityViewControllerHelper controllerHelper = new SecurityViewControllerHelper();
        return controllerHelper.showJson(id);
    }

    @RequestMapping(value = "/json", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<java.lang.String> listJson(@SuppressWarnings("rawtypes") @RequestParam Map params) {
        ResponseEntity<java.lang.String> ret_ = null;
        SecurityViewControllerHelper controllerHelper = new SecurityViewControllerHelper();
        if (params.containsKey("studentName")) {
            ret_ = controllerHelper.listJson(params);
        } else {
            ret_ = controllerHelper.listJson();
        }
        return ret_;
    }

    @RequestMapping(value = "/json", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> createFromJson(@RequestBody String json) {
        SecurityViewControllerHelper controllerHelper = new SecurityViewControllerHelper();
        return controllerHelper.createFromJson(json);
    }

    @RequestMapping(value = "/json/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> deleteFromJson(@PathVariable("id") Long id) {
        SecurityViewControllerHelper controllerHelper = new SecurityViewControllerHelper();
        return controllerHelper.deleteFromJson(id);
    }

    @RequestMapping(value = "/json/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> updateFromJson(@RequestBody String json) {
        SecurityViewControllerHelper controllerHelper = new SecurityViewControllerHelper();
        return controllerHelper.updateFromJson(json);
    }

    @RequestMapping(value = "/json/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> updateFromJsonArray(@RequestBody String json) {
        SecurityViewControllerHelper controllerHelper = new SecurityViewControllerHelper();
        return controllerHelper.updateFromJsonArray(json);
    }

    @RequestMapping(value = "/json/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> createFromJsonArray(@RequestBody String json) {
        SecurityViewControllerHelper controllerHelper = new SecurityViewControllerHelper();
        return controllerHelper.createFromJsonArray(json);
    }

    /*
    @RequestMapping(value = "/json", params = "find=ByUserNameEquals", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<java.lang.String> jsonFindStudentsByUserNameEquals(@RequestParam("userName") String userName) {
        SecurityViewControllerHelper controllerHelper = new SecurityViewControllerHelper();
        return controllerHelper.jsonFindStudentsByUserNameEquals(userName);
    }
    */
}
