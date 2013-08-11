// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.app.myschool.web;

import com.app.myschool.model.Faculty;
import com.app.myschool.model.Roles;
import com.app.myschool.model.Student;
import com.app.myschool.web.FacultyController;
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

privileged aspect FacultyController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String FacultyController.create(@Valid Faculty faculty, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, faculty);
            return "facultys/create";
        }
        uiModel.asMap().clear();
        faculty.persist();
        return "redirect:/facultys/" + encodeUrlPathSegment(faculty.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String FacultyController.createForm(Model uiModel) {
        populateEditForm(uiModel, new Faculty());
        return "facultys/create";
    }
    
    @RequestMapping(value = "/{id}", produces = "text/html")
    public String FacultyController.show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("faculty", Faculty.findFaculty(id));
        uiModel.addAttribute("itemId", id);
        return "facultys/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String FacultyController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("facultys", Faculty.findFacultyEntries(firstResult, sizeNo));
            float nrOfPages = (float) Faculty.countFacultys() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("facultys", Faculty.findAllFacultys());
        }
        addDateTimeFormatPatterns(uiModel);
        return "facultys/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String FacultyController.update(@Valid Faculty faculty, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, faculty);
            return "facultys/update";
        }
        uiModel.asMap().clear();
        faculty.merge();
        return "redirect:/facultys/" + encodeUrlPathSegment(faculty.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String FacultyController.updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, Faculty.findFaculty(id));
        return "facultys/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String FacultyController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Faculty faculty = Faculty.findFaculty(id);
        faculty.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/facultys";
    }
    
    void FacultyController.addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("faculty_lastupdated_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }
    
    void FacultyController.populateEditForm(Model uiModel, Faculty faculty) {
        uiModel.addAttribute("faculty", faculty);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("roleses", Roles.findAllRoleses());
        uiModel.addAttribute("students", Student.findAllStudents());
    }
    
    String FacultyController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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
