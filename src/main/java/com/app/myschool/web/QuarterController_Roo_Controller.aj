// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.app.myschool.web;

import com.app.myschool.model.BodyOfWork;
import com.app.myschool.model.Daily;
import com.app.myschool.model.MonthlyEvaluationRatings;
import com.app.myschool.model.MonthlySummaryRatings;
import com.app.myschool.model.Quarter;
import com.app.myschool.model.Student;
import com.app.myschool.model.Subject;
import com.app.myschool.web.QuarterController;
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

privileged aspect QuarterController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String QuarterController.create(@Valid Quarter quarter, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, quarter);
            return "quarters/create";
        }
        uiModel.asMap().clear();
        quarter.persist();
        return "redirect:/quarters/" + encodeUrlPathSegment(quarter.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String QuarterController.createForm(Model uiModel) {
        populateEditForm(uiModel, new Quarter());
        return "quarters/create";
    }
    
    @RequestMapping(value = "/{id}", produces = "text/html")
    public String QuarterController.show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("quarter", Quarter.findQuarter(id));
        uiModel.addAttribute("itemId", id);
        return "quarters/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String QuarterController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("quarters", Quarter.findQuarterEntries(firstResult, sizeNo));
            float nrOfPages = (float) Quarter.countQuarters() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("quarters", Quarter.findAllQuarters());
        }
        addDateTimeFormatPatterns(uiModel);
        return "quarters/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String QuarterController.update(@Valid Quarter quarter, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, quarter);
            return "quarters/update";
        }
        uiModel.asMap().clear();
        quarter.merge();
        return "redirect:/quarters/" + encodeUrlPathSegment(quarter.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String QuarterController.updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, Quarter.findQuarter(id));
        return "quarters/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String QuarterController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Quarter quarter = Quarter.findQuarter(id);
        quarter.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/quarters";
    }
    
    void QuarterController.addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("quarter_lastupdated_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }
    
    void QuarterController.populateEditForm(Model uiModel, Quarter quarter) {
        uiModel.addAttribute("quarter", quarter);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("bodyofworks", BodyOfWork.findAllBodyOfWorks());
        uiModel.addAttribute("dailys", Daily.findAllDailys());
        uiModel.addAttribute("monthlyevaluationratingses", MonthlyEvaluationRatings.findAllMonthlyEvaluationRatingses());
        uiModel.addAttribute("monthlysummaryratingses", MonthlySummaryRatings.findAllMonthlySummaryRatingses());
        uiModel.addAttribute("students", Student.findAllStudents());
        uiModel.addAttribute("subjects", Subject.findAllSubjects());
    }
    
    String QuarterController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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
