package com.app.myschool.web;

import com.app.myschool.model.Admin;
import com.app.myschool.model.BodyOfWorkView;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

@RequestMapping("/adminviews")
@Controller
@RooWebScaffold(path = "admins", formBackingObject = Admin.class)
@RooWebJson(jsonObject = Admin.class)
public class AdminController {

	@RequestMapping(value = "/json", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json, UriComponentsBuilder uriBuilder) {
		AdminControllerHelper controllerHelper = new AdminControllerHelper();
        return controllerHelper.createFromJson(json);
    }

	@RequestMapping(value = "/json", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listJson() {
        AdminControllerHelper controllerHelper = new AdminControllerHelper();
        return controllerHelper.listJson();
    }
	
	@RequestMapping(value = "/json/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> showJson(@PathVariable("id") Long id) {
        AdminControllerHelper controllerHelper = new AdminControllerHelper();
        return controllerHelper.showJson(id);
    }

	@RequestMapping(value = "/json/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJson(@RequestBody String json, @PathVariable("id") Long id) {
		AdminControllerHelper controllerHelper = new AdminControllerHelper();
        return controllerHelper.updateFromJson(json);
    }
}
