package com.app.myschool.web;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.myschool.model.MonthlyEvaluationRatingsView;

@RooWebJson(jsonObject = MonthlyEvaluationRatingsView.class)
@Controller
@RequestMapping("/monthlyevaluationratingviews")
public class MonthlyEvaluationRatingsViewController {

}
