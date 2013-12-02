// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.app.myschool.model;

import com.app.myschool.model.Grades;
import com.app.myschool.model.GradesDataOnDemand;
import com.app.myschool.model.Quarter;
import com.app.myschool.model.Student;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.stereotype.Component;

privileged aspect GradesDataOnDemand_Roo_DataOnDemand {
    
    declare @type: GradesDataOnDemand: @Component;
    
    private Random GradesDataOnDemand.rnd = new SecureRandom();
    
    private List<Grades> GradesDataOnDemand.data;
    
    public Grades GradesDataOnDemand.getNewTransientGrades(int index) {
        Grades obj = new Grades();
        setGrade(obj, index);
        setGrade_type(obj, index);
        setLastUpdated(obj, index);
        setQuarter(obj, index);
        setStudent(obj, index);
        setWhoUpdated(obj, index);
        return obj;
    }
    
    public void GradesDataOnDemand.setGrade(Grades obj, int index) {
        double grade = new Integer(index).doubleValue();
        obj.setGrade(grade);
    }
    
    public void GradesDataOnDemand.setGrade_type(Grades obj, int index) {
        int grade_type = index;
        if (grade_type < 0 || grade_type > 2) {
            grade_type = 2;
        }
        obj.setGrade_type(grade_type);
    }
    
    public void GradesDataOnDemand.setLastUpdated(Grades obj, int index) {
        Date lastUpdated = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), Calendar.getInstance().get(Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime();
        obj.setLastUpdated(lastUpdated);
    }
    
    public void GradesDataOnDemand.setQuarter(Grades obj, int index) {
        Quarter quarter = null;
        obj.setQuarter(quarter);
    }
    
    public void GradesDataOnDemand.setStudent(Grades obj, int index) {
        Student student = null;
        obj.setStudent(student);
    }
    
    public void GradesDataOnDemand.setWhoUpdated(Grades obj, int index) {
        String whoUpdated = "whoUpdated_" + index;
        obj.setWhoUpdated(whoUpdated);
    }
    
    public Grades GradesDataOnDemand.getSpecificGrades(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Grades obj = data.get(index);
        Long id = obj.getId();
        return Grades.findGrades(id);
    }
    
    public Grades GradesDataOnDemand.getRandomGrades() {
        init();
        Grades obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return Grades.findGrades(id);
    }
    
    public boolean GradesDataOnDemand.modifyGrades(Grades obj) {
        return false;
    }
    
    public void GradesDataOnDemand.init() {
        int from = 0;
        int to = 10;
        data = Grades.findGradesEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Grades' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Grades>();
        for (int i = 0; i < 10; i++) {
            Grades obj = getNewTransientGrades(i);
            try {
                obj.persist();
            } catch (ConstraintViolationException e) {
                StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getConstraintDescriptor()).append(":").append(cv.getMessage()).append("=").append(cv.getInvalidValue()).append("]");
                }
                throw new RuntimeException(msg.toString(), e);
            }
            obj.flush();
            data.add(obj);
        }
    }
    
}