package com.app.myschool.web;

import com.app.myschool.model.QuarterNames;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/quarternameses")
@Controller
@RooWebScaffold(path = "quarternameses", formBackingObject = QuarterNames.class)
@RooWebJson(jsonObject = QuarterNames.class)
public class QuarterNamesController {
}
