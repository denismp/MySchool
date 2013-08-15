package com.app.myschool.web;

import com.app.myschool.model.SkillRatings;
import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/skillratingses")
@Controller
@RooWebScaffold(path = "skillratingses", formBackingObject = SkillRatings.class)
@RooWebJson(jsonObject = SkillRatings.class)
public class SkillRatingsController {

    @RequestMapping(value = "/{id}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<java.lang.String> showJson(@PathVariable("id") Long id) {
        ControllerHelper controllerHelper = new ControllerHelper();
        return controllerHelper.showJson(SkillRatings.class, id);
    }

    @RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<java.lang.String> listJson() {
        ControllerHelper controllerHelper = new ControllerHelper();
        return controllerHelper.listJson(SkillRatings.class);
    }

    @RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> createFromJson(@RequestBody String json) {
        ControllerHelper controllerHelper = new ControllerHelper();
        return controllerHelper.createFromJson(SkillRatings.class, json);
    }

    @RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> createFromJsonArray(@RequestBody String json) {
        ControllerHelper controllerHelper = new ControllerHelper();
        return controllerHelper.createFromJsonArray(SkillRatings.class, json);
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> updateFromJson(@RequestBody String json) {
        ControllerHelper controllerHelper = new ControllerHelper();
        return controllerHelper.updateFromJson(SkillRatings.class, json);
    }

    @RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> updateFromJsonArray(@RequestBody String json) {
        ControllerHelper controllerHelper = new ControllerHelper();
        return controllerHelper.updateFromJsonArray(SkillRatings.class, json);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> deleteFromJson(@PathVariable("id") Long id) {
        ControllerHelper controllerHelper = new ControllerHelper();
        return controllerHelper.deleteFromJson(SkillRatings.class, id);
    }
}
