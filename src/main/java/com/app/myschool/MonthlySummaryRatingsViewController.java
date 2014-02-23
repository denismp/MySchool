package com.app.myschool;

import com.app.myschool.model.MonthlySummaryRatingsView;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebJson(jsonObject = MonthlySummaryRatingsView.class)
@Controller
@RequestMapping("/monthlysummaryratingsviews")
public class MonthlySummaryRatingsViewController {
}
