package com.app.myschool;

import com.app.myschool.model.BodyOfWorkView;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebJson(jsonObject = BodyOfWorkView.class)
@Controller
@RequestMapping("/bodyofworkviews")
public class BodyOfWorkViewController {
}
