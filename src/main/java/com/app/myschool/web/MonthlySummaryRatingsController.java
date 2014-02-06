package com.app.myschool.web;

import java.util.Map;

import com.app.myschool.model.MonthlySummaryRatings;

import org.apache.log4j.Logger;
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

@RequestMapping("/monthlysummaryratingses")
@Controller
@RooWebScaffold(path = "monthlysummaryratingses", formBackingObject = MonthlySummaryRatings.class)
@RooWebJson(jsonObject = MonthlySummaryRatings.class)
public class MonthlySummaryRatingsController {
	private static final Logger logger = Logger.getLogger(MonthlySummaryRatingsController.class);

    @RequestMapping(value = "/{id}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<java.lang.String> showJson(@PathVariable("id") Long id) {
    	MonthlySummaryRatingsControllerHelper controllerHelper = new MonthlySummaryRatingsControllerHelper();
        return controllerHelper.showJson(id);
    }

    //@RequestMapping(headers = "Accept=application/json")
   // @ResponseBody
    //public ResponseEntity<java.lang.String> listJson() {
   // 	MonthlySummaryRatingsControllerHelper controllerHelper = new MonthlySummaryRatingsControllerHelper();
   //     return controllerHelper.listJson();
    //}
    
    @RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<java.lang.String> listJson(@RequestParam Map<?, ?> params) {
    	//MonthlySummaryRatingsControllerHelper controllerHelper = new MonthlySummaryRatingsControllerHelper();
        //return controllerHelper.listJson(params);
        ResponseEntity<java.lang.String> ret_ = null;
        MonthlySummaryRatingsControllerHelper controllerHelper = new MonthlySummaryRatingsControllerHelper();
        if (params.containsKey("studentName")) {
            ret_ = controllerHelper.listJson( params);
        } else {
            ret_ = controllerHelper.listJson();
        }
        return ret_;

    }

    @RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> createFromJson(@RequestBody String json) {
    	MonthlySummaryRatingsControllerHelper controllerHelper = new MonthlySummaryRatingsControllerHelper();
        return controllerHelper.createFromJson(json);
    }

    @RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> createFromJsonArray(@RequestBody String json) {
    	MonthlySummaryRatingsControllerHelper controllerHelper = new MonthlySummaryRatingsControllerHelper();
        return controllerHelper.createFromJsonArray(json);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> updateFromJson(@RequestBody String json) {
    	logger.info("updateFromJson() json=" + json );
    	MonthlySummaryRatingsControllerHelper controllerHelper = new MonthlySummaryRatingsControllerHelper();
        return controllerHelper.updateFromJson(json);
    }

    @RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> updateFromJsonArray(@RequestBody String json) {
    	MonthlySummaryRatingsControllerHelper controllerHelper = new MonthlySummaryRatingsControllerHelper();
        return controllerHelper.updateFromJsonArray(json);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<java.lang.String> deleteFromJson(@PathVariable("id") Long id) {
    	MonthlySummaryRatingsControllerHelper controllerHelper = new MonthlySummaryRatingsControllerHelper();
        return controllerHelper.deleteFromJson(id);
    }
}
