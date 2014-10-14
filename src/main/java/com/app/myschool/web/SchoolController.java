package com.app.myschool.web;
import com.app.myschool.model.School;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/schools")
@Controller
@RooWebScaffold(path = "schools", formBackingObject = School.class)
public class SchoolController {
}
