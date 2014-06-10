// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.app.myschool.web;

import com.app.myschool.model.MonthlyEvaluationRatings;
import com.app.myschool.model.Quarter;
import com.app.myschool.web.MonthlyEvaluationRatingsController;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.joda.time.format.DateTimeFormat;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

privileged aspect MonthlyEvaluationRatingsController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String MonthlyEvaluationRatingsController.create(@Valid MonthlyEvaluationRatings monthlyEvaluationRatings, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, monthlyEvaluationRatings);
            return "monthlyevaluationratingses/create";
        }
        uiModel.asMap().clear();
        monthlyEvaluationRatings.persist();
        return "redirect:/monthlyevaluationratingses/" + encodeUrlPathSegment(monthlyEvaluationRatings.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String MonthlyEvaluationRatingsController.createForm(Model uiModel) {
        populateEditForm(uiModel, new MonthlyEvaluationRatings());
        return "monthlyevaluationratingses/create";
    }
    
    @RequestMapping(value = "/{id}", produces = "text/html")
    public String MonthlyEvaluationRatingsController.show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("monthlyevaluationratings", MonthlyEvaluationRatings.findMonthlyEvaluationRatings(id));
        uiModel.addAttribute("itemId", id);
        return "monthlyevaluationratingses/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String MonthlyEvaluationRatingsController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("monthlyevaluationratingses", MonthlyEvaluationRatings.findMonthlyEvaluationRatingsEntries(firstResult, sizeNo));
            float nrOfPages = (float) MonthlyEvaluationRatings.countMonthlyEvaluationRatingses() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("monthlyevaluationratingses", MonthlyEvaluationRatings.findAllMonthlyEvaluationRatingses());
        }
        addDateTimeFormatPatterns(uiModel);
        return "monthlyevaluationratingses/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String MonthlyEvaluationRatingsController.update(@Valid MonthlyEvaluationRatings monthlyEvaluationRatings, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, monthlyEvaluationRatings);
            return "monthlyevaluationratingses/update";
        }
        uiModel.asMap().clear();
        monthlyEvaluationRatings.merge();
        return "redirect:/monthlyevaluationratingses/" + encodeUrlPathSegment(monthlyEvaluationRatings.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String MonthlyEvaluationRatingsController.updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, MonthlyEvaluationRatings.findMonthlyEvaluationRatings(id));
        return "monthlyevaluationratingses/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String MonthlyEvaluationRatingsController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        MonthlyEvaluationRatings monthlyEvaluationRatings = MonthlyEvaluationRatings.findMonthlyEvaluationRatings(id);
        monthlyEvaluationRatings.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/monthlyevaluationratingses";
    }
    
    void MonthlyEvaluationRatingsController.addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("monthlyEvaluationRatings_lastupdated_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }
    
    void MonthlyEvaluationRatingsController.populateEditForm(Model uiModel, MonthlyEvaluationRatings monthlyEvaluationRatings) {
        uiModel.addAttribute("monthlyEvaluationRatings", monthlyEvaluationRatings);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("quarters", Quarter.findAllQuarters());
    }
    
    String MonthlyEvaluationRatingsController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }
    
}
