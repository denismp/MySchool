package com.app.myschool.web;

import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.myschool.model.Student;

@RooWebJson(jsonObject = Student.class)
@Controller
@RequestMapping("/detailtranscript")
public class PDFDetailTranscriptController
{
    @RequestMapping(value = "/json", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> createFromJson(@RequestBody String json) {
    	PDFDetailTranscriptControllerHelper controllerHelper = new PDFDetailTranscriptControllerHelper();
        return controllerHelper.createFromJson(json);
    }	

    @RequestMapping(value = "/json/{id}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<java.lang.String> showJson(@PathVariable("id") Long id) {
        PDFDetailTranscriptControllerHelper controllerHelper = new PDFDetailTranscriptControllerHelper();
        return controllerHelper.showJson(id);
    }

    @RequestMapping(value = "/json", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<java.lang.String> listJson(@RequestParam Map<?, ?> params) {
    	PDFDetailTranscriptControllerHelper controllerHelper = new PDFDetailTranscriptControllerHelper();
        return controllerHelper.listJson(params);
    }
    
    @RequestMapping(value = "/json/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> updateFromJson(@RequestBody String json) {
    	PDFDetailTranscriptControllerHelper controllerHelper = new PDFDetailTranscriptControllerHelper();
        return controllerHelper.updateFromJson(json);
    }
}
