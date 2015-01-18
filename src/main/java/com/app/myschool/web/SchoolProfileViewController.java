package com.app.myschool.web;
import com.app.myschool.model.School;

import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;


@RequestMapping("/schoolprofileviews")
@Controller

@RooWebJson(jsonObject = School.class)
public class SchoolProfileViewController {


	@RequestMapping(value = "/json", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json, UriComponentsBuilder uriBuilder) {
    	SchoolProfileViewControllerHelper controllerHelper = new SchoolProfileViewControllerHelper();
        return controllerHelper.createFromJson(json);
   }

	@RequestMapping(value = "/json", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listJson() {
        ResponseEntity<java.lang.String> ret_ = null;
        SchoolProfileViewControllerHelper controllerHelper = new SchoolProfileViewControllerHelper();
        ret_ = controllerHelper.listJson();
        /*
        if (params.containsKey("studentName")) {
            ret_ = controllerHelper.listJson(params);
        } else {
            ret_ = controllerHelper.listJson();
        }
        */
        return ret_;
     }

	@RequestMapping(value = "/json/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJson(@RequestBody String json, @PathVariable("id") Long id) {
    	SchoolProfileViewControllerHelper controllerHelper = new SchoolProfileViewControllerHelper();
        return controllerHelper.updateFromJson(json);
    }
}
