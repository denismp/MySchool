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
import com.app.myschool.model.Admin;

@RooWebJson(jsonObject = Admin.class )
@Controller
@RequestMapping("/adminpasswordviews")
public class AdminPasswordViewController {
    @RequestMapping(value = "/json", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<java.lang.String> listJson(@SuppressWarnings("rawtypes") @RequestParam Map params) {
        ResponseEntity<java.lang.String> ret_ = null;
        AdminPasswordViewControllerHelper controllerHelper = new AdminPasswordViewControllerHelper();
        if (params.containsKey("studentName")) {
            ret_ = controllerHelper.listJson(params);
        } else {
            ret_ = controllerHelper.listJson();
        }
        return ret_;
    }

    @RequestMapping(value = "/json", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> createFromJson(@RequestBody String json) {
    	AdminPasswordViewControllerHelper controllerHelper = new AdminPasswordViewControllerHelper();
        return controllerHelper.createFromJson(json);
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJson(@RequestBody String json, @PathVariable("id") Long id) {
		AdminPasswordViewControllerHelper controllerHelper = new AdminPasswordViewControllerHelper();
        return controllerHelper.updateFromJson(json);
    }
}
