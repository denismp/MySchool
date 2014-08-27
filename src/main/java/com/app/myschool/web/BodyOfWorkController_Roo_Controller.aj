// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.app.myschool.web;

import com.app.myschool.model.Artifact;
import com.app.myschool.model.BodyOfWork;
import com.app.myschool.model.Quarter;
import com.app.myschool.web.BodyOfWorkController;
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

privileged aspect BodyOfWorkController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String BodyOfWorkController.create(@Valid BodyOfWork bodyOfWork, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, bodyOfWork);
            return "bodyofworks/create";
        }
        uiModel.asMap().clear();
        bodyOfWork.persist();
        return "redirect:/bodyofworks/" + encodeUrlPathSegment(bodyOfWork.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String BodyOfWorkController.createForm(Model uiModel) {
        populateEditForm(uiModel, new BodyOfWork());
        return "bodyofworks/create";
    }
    
    @RequestMapping(value = "/{id}", produces = "text/html")
    public String BodyOfWorkController.show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("bodyofwork", BodyOfWork.findBodyOfWork(id));
        uiModel.addAttribute("itemId", id);
        return "bodyofworks/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String BodyOfWorkController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("bodyofworks", BodyOfWork.findBodyOfWorkEntries(firstResult, sizeNo, sortFieldName, sortOrder));
            float nrOfPages = (float) BodyOfWork.countBodyOfWorks() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("bodyofworks", BodyOfWork.findAllBodyOfWorks(sortFieldName, sortOrder));
        }
        addDateTimeFormatPatterns(uiModel);
        return "bodyofworks/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String BodyOfWorkController.update(@Valid BodyOfWork bodyOfWork, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, bodyOfWork);
            return "bodyofworks/update";
        }
        uiModel.asMap().clear();
        bodyOfWork.merge();
        return "redirect:/bodyofworks/" + encodeUrlPathSegment(bodyOfWork.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String BodyOfWorkController.updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, BodyOfWork.findBodyOfWork(id));
        return "bodyofworks/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String BodyOfWorkController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        BodyOfWork bodyOfWork = BodyOfWork.findBodyOfWork(id);
        bodyOfWork.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/bodyofworks";
    }
    
    void BodyOfWorkController.addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("bodyOfWork_lastupdated_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("bodyOfWork_createddate_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }
    
    void BodyOfWorkController.populateEditForm(Model uiModel, BodyOfWork bodyOfWork) {
        uiModel.addAttribute("bodyOfWork", bodyOfWork);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("artifacts", Artifact.findAllArtifacts());
        uiModel.addAttribute("quarters", Quarter.findAllQuarters());
    }
    
    String BodyOfWorkController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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
