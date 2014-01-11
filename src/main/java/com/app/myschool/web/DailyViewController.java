package com.app.myschool.web;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.myschool.model.DailyView;

@RooWebJson(jsonObject = DailyView.class )
@Controller
@RequestMapping("/dailyviews")
public class DailyViewController {

}
