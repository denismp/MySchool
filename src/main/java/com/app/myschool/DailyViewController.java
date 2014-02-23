package com.app.myschool;

import com.app.myschool.model.DailyView;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebJson(jsonObject = DailyView.class)
@Controller
@RequestMapping("/dailyviews")
public class DailyViewController {
}
