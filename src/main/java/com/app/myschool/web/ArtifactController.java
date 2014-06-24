package com.app.myschool.web;

import com.app.myschool.model.Artifact;
import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/artifacts")
@Controller
@RooWebScaffold(path = "artifacts", formBackingObject = Artifact.class)
@RooWebJson(jsonObject = Artifact.class)
public class ArtifactController {

    @RequestMapping(value = "/json/{id}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<java.lang.String> showJson(@PathVariable("id") Long id) {
        ControllerHelper controllerHelper = new ControllerHelper();
        return controllerHelper.showJson(Artifact.class, id);
    }

    @RequestMapping(value = "/json", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<java.lang.String> listJson() {
        ControllerHelper controllerHelper = new ControllerHelper();
        return controllerHelper.listJson(Artifact.class);
    }

    @RequestMapping(value = "/json",method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> createFromJson(@RequestBody String json) {
        ControllerHelper controllerHelper = new ControllerHelper();
        return controllerHelper.createFromJson(Artifact.class, json);
    }

    @RequestMapping(value = "/json//jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> createFromJsonArray(@RequestBody String json) {
        for (Artifact artifact : Artifact.fromJsonArrayToArtifacts(json)) {
            artifact.persist();
        }
        ControllerHelper controllerHelper = new ControllerHelper();
        return controllerHelper.createFromJsonArray(Artifact.class, json);
    }

    @RequestMapping(value = "/json/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> updateFromJson(@RequestBody String json) {
        ControllerHelper controllerHelper = new ControllerHelper();
        return controllerHelper.updateFromJson(Artifact.class, json);
    }

    @RequestMapping(value = "/json/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> updateFromJsonArray(@RequestBody String json) {
        ControllerHelper controllerHelper = new ControllerHelper();
        return controllerHelper.updateFromJsonArray(Artifact.class, json);
    }

    @RequestMapping(value = "/json/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> deleteFromJson(@PathVariable("id") Long id) {
        ControllerHelper controllerHelper = new ControllerHelper();
        return controllerHelper.deleteFromJson(Artifact.class, id);
    }
}
