package com.app.myschool.web;
import com.app.myschool.model.Admin;


import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;

@RequestMapping("/adminviews")
@Controller
@RooWebScaffold(path = "admins", formBackingObject = Admin.class)
@RooWebJson(jsonObject = Admin.class)
public class AdminViewController {


	@RequestMapping(value = "/json", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json, UriComponentsBuilder uriBuilder) {
    	AdminViewControllerHelper controllerHelper = new AdminViewControllerHelper();
        return controllerHelper.createFromJson(json);
   }

	@RequestMapping(value = "/json", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listJson() {
        ResponseEntity<java.lang.String> ret_ = null;
        AdminViewControllerHelper controllerHelper = new AdminViewControllerHelper();
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
		AdminViewControllerHelper controllerHelper = new AdminViewControllerHelper();
        return controllerHelper.updateFromJson(json);
    }
}
