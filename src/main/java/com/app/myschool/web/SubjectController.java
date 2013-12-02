package com.app.myschool.web;

import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.app.myschool.model.Quarter;
import com.app.myschool.model.Student;
import com.app.myschool.model.Subject;
import com.app.myschool.model.SubjectView;

import org.apache.commons.lang3.StringUtils;
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

@RequestMapping("/subjects")
@Controller
@RooWebScaffold(path = "subjects", formBackingObject = Subject.class)
@RooWebJson(jsonObject = Subject.class)
public class SubjectController {

    @RequestMapping(value = "/json/{id}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<java.lang.String> showJson(@PathVariable("id") Long id) {
        ControllerHelper controllerHelper = new ControllerHelper();
        return controllerHelper.showJson(Subject.class, id);
    }

    @RequestMapping(value = "/json", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<java.lang.String> listJson(@RequestParam Map params) {
    	ResponseEntity<java.lang.String> ret_ = null;
        ControllerHelper controllerHelper = new ControllerHelper();
    	
    	if (params.containsKey("studentName")) {
    		ret_ = controllerHelper.listJson(SubjectView.class, params);
    	}
    	else {
    		ret_ = controllerHelper.listJson(Subject.class);
    	}
        return ret_;
    }

    @RequestMapping(value = "/json", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> createFromJson(@RequestBody String json) {
        ControllerHelper controllerHelper = new ControllerHelper();
        return controllerHelper.createFromJson(SubjectView.class, json);
    }

    @RequestMapping(value = "/json/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> createFromJsonArray(@RequestBody String json) {
        ControllerHelper controllerHelper = new ControllerHelper();
        return controllerHelper.createFromJsonArray(Subject.class, json);
    }

    @RequestMapping(value = "/json/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> updateFromJson(@RequestBody String json) {
        ControllerHelper controllerHelper = new ControllerHelper();
        return controllerHelper.updateFromJson(Subject.class, json);
    }

    @RequestMapping(value = "/json/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> updateFromJsonArray(@RequestBody String json) {
        ControllerHelper controllerHelper = new ControllerHelper();
        return controllerHelper.updateFromJsonArray(Subject.class, json);
    }

    @RequestMapping(value = "/json/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> deleteFromJson(@PathVariable("id") Long id) {
        ControllerHelper controllerHelper = new ControllerHelper();
        return controllerHelper.deleteFromJson(Subject.class, id);
    }
}
