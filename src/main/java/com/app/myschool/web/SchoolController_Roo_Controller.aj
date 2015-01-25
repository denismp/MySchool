// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.app.myschool.web;

import com.app.myschool.model.Admin;
import com.app.myschool.model.School;
import com.app.myschool.model.Student;
import com.app.myschool.model.Subject;
import com.app.myschool.web.SchoolController;
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

privileged aspect SchoolController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String SchoolController.create(@Valid School school, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, school);
            return "schools/create";
        }
        uiModel.asMap().clear();
        school.persist();
        return "redirect:/schools/" + encodeUrlPathSegment(school.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String SchoolController.createForm(Model uiModel) {
        populateEditForm(uiModel, new School());
        return "schools/create";
    }
    
    @RequestMapping(value = "/{id}", produces = "text/html")
    public String SchoolController.show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("school", School.findSchool(id));
        uiModel.addAttribute("itemId", id);
        return "schools/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String SchoolController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("schools", School.findSchoolEntries(firstResult, sizeNo, sortFieldName, sortOrder));
            float nrOfPages = (float) School.countSchools() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("schools", School.findAllSchools(sortFieldName, sortOrder));
        }
        addDateTimeFormatPatterns(uiModel);
        return "schools/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String SchoolController.update(@Valid School school, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, school);
            return "schools/update";
        }
        uiModel.asMap().clear();
        school.merge();
        return "redirect:/schools/" + encodeUrlPathSegment(school.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String SchoolController.updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, School.findSchool(id));
        return "schools/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String SchoolController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        School school = School.findSchool(id);
        school.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/schools";
    }
    
    void SchoolController.addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("school_createddate_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("school_lastupdated_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }
    
    void SchoolController.populateEditForm(Model uiModel, School school) {
        uiModel.addAttribute("school", school);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("admins", Admin.findAllAdmins());
        uiModel.addAttribute("students", Student.findAllStudents());
        uiModel.addAttribute("subjects", Subject.findAllSubjects());
    }
    
    String SchoolController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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
