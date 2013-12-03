package com.app.myschool.web;

import com.app.myschool.model.Grades;
import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/gradeses")
@Controller
@RooWebScaffold(path = "gradeses", formBackingObject = Grades.class)
@RooWebJson(jsonObject = Grades.class)
public class GradesController {

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> showJson(@PathVariable("id") Long id) {
        ControllerHelper controllerHelper = new ControllerHelper();
        return controllerHelper.showJson(Grades.class, id);
    }

	@RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listJson() {
        ControllerHelper controllerHelper = new ControllerHelper();
        return controllerHelper.listJson(Grades.class);
    }

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json) {
        ControllerHelper controllerHelper = new ControllerHelper();
        return controllerHelper.createFromJson(Grades.class, json);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
        ControllerHelper controllerHelper = new ControllerHelper();
        return controllerHelper.createFromJsonArray(Grades.class, json);
    }

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJson(@RequestBody String json) {
        ControllerHelper controllerHelper = new ControllerHelper();
        return controllerHelper.updateFromJson(Grades.class, json);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
        ControllerHelper controllerHelper = new ControllerHelper();
        return controllerHelper.updateFromJsonArray(Grades.class, json);
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") Long id) {
        ControllerHelper controllerHelper = new ControllerHelper();
        return controllerHelper.deleteFromJson(Grades.class, id);
    }
}
