package com.app.myschool.web;

import com.app.myschool.model.Grades;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/gradeses")
@Controller
@RooWebScaffold(path = "gradeses", formBackingObject = Grades.class)
public class GradesController {
}
