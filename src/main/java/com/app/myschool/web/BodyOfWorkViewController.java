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

import com.app.myschool.model.BodyOfWork;
import com.app.myschool.model.BodyOfWorkView;

@RooWebJson(jsonObject = BodyOfWorkView.class)
@Controller
@RequestMapping("/bodyofworkviews")
public class BodyOfWorkViewController {

    @RequestMapping(value = "/json/{id}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<java.lang.String> showJson(@PathVariable("id") Long id) {
        BodyOfWorkViewControllerHelper controllerHelper = new BodyOfWorkViewControllerHelper();
        return controllerHelper.showJson(id);
    }

    @RequestMapping(value = "/json", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<java.lang.String> listJson(@RequestParam Map<?, ?> params) {
        BodyOfWorkViewControllerHelper controllerHelper = new BodyOfWorkViewControllerHelper();
        return controllerHelper.listJson(params);
    }

    @RequestMapping(value = "/json", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> createFromJson(@RequestBody String json) {
        BodyOfWorkViewControllerHelper controllerHelper = new BodyOfWorkViewControllerHelper();
        return controllerHelper.createFromJson(json);
    }

    @RequestMapping(value = "/json/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> createFromJsonArray(@RequestBody String json) {
        for (BodyOfWork bodyOfWork : BodyOfWork.fromJsonArrayToBodyOfWorks(json)) {
            bodyOfWork.persist();
        }
        BodyOfWorkViewControllerHelper controllerHelper = new BodyOfWorkViewControllerHelper();
        return controllerHelper.createFromJsonArray(json);
    }

    @RequestMapping(value = "/json/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> updateFromJson(@RequestBody String json) {
        BodyOfWorkViewControllerHelper controllerHelper = new BodyOfWorkViewControllerHelper();
        return controllerHelper.updateFromJson(json);
    }

    @RequestMapping(value = "/json/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> updateFromJsonArray(@RequestBody String json) {
        BodyOfWorkViewControllerHelper controllerHelper = new BodyOfWorkViewControllerHelper();
        return controllerHelper.updateFromJsonArray(json);
    }

    @RequestMapping(value = "/json/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> deleteFromJson(@PathVariable("id") Long id) {
        BodyOfWorkViewControllerHelper controllerHelper = new BodyOfWorkViewControllerHelper();
        return controllerHelper.deleteFromJson(id);
    }
}
