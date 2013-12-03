package com.app.myschool;

import com.app.myschool.model.SubjectView;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebJson(jsonObject = SubjectView.class)
@Controller
@RequestMapping("/subjectviews")
public class SubjectViewController {
}
