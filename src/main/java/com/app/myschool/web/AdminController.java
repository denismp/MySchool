package com.app.myschool.web;

import com.app.myschool.model.Admin;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admins")
@Controller
@RooWebScaffold(path = "admins", formBackingObject = Admin.class)
@RooWebJson(jsonObject = Admin.class)
public class AdminController {
}
