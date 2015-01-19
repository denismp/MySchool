package com.app.myschool.web;
import com.app.myschool.model.School;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

@RequestMapping("/schools")
@Controller
@RooWebScaffold(path = "schools", formBackingObject = School.class)
@RooWebJson(jsonObject = School.class)
public class SchoolController {

    @RequestMapping(value = "/json", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listJson(@RequestParam Map<?, ?> params) {
        //public ResponseEntity<java.lang.String> listJson() {
        ResponseEntity<java.lang.String> ret_ = null;
        SchoolControllerHelper controllerHelper = new SchoolControllerHelper();
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

    @RequestMapping(value = "/json/{id}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> showJson(@PathVariable("id") Long id) {
        SchoolControllerHelper controllerHelper = new SchoolControllerHelper();
        return controllerHelper.showJson(id);
    }
}
