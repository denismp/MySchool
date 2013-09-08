// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.app.myschool.web;

import com.app.myschool.model.Artifact;
import com.app.myschool.model.BodyOfWork;
import com.app.myschool.model.Daily;
import com.app.myschool.model.EvaluationRatings;
import com.app.myschool.model.Faculty;
import com.app.myschool.model.GraduateTracking;
import com.app.myschool.model.MonthlyEvaluationRatings;
import com.app.myschool.model.MonthlySummaryRatings;
import com.app.myschool.model.PreviousTranscripts;
import com.app.myschool.model.Quarter;
import com.app.myschool.model.QuarterNames;
import com.app.myschool.model.Roles;
import com.app.myschool.model.SkillRatings;
import com.app.myschool.model.Student;
import com.app.myschool.model.Subject;
import com.app.myschool.model.Weekly;
import com.app.myschool.web.ApplicationConversionServiceFactoryBean;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;

privileged aspect ApplicationConversionServiceFactoryBean_Roo_ConversionService {
    
    declare @type: ApplicationConversionServiceFactoryBean: @Configurable;
    
    public Converter<Artifact, String> ApplicationConversionServiceFactoryBean.getArtifactToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.app.myschool.model.Artifact, java.lang.String>() {
            public String convert(Artifact artifact) {
                return new StringBuilder().append(artifact.getArtifactName()).append(' ').append(artifact.getFileName()).append(' ').append(artifact.getComments()).append(' ').append(artifact.getWhoUpdated()).toString();
            }
        };
    }
    
    public Converter<Long, Artifact> ApplicationConversionServiceFactoryBean.getIdToArtifactConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.app.myschool.model.Artifact>() {
            public com.app.myschool.model.Artifact convert(java.lang.Long id) {
                return Artifact.findArtifact(id);
            }
        };
    }
    
    public Converter<String, Artifact> ApplicationConversionServiceFactoryBean.getStringToArtifactConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.app.myschool.model.Artifact>() {
            public com.app.myschool.model.Artifact convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Artifact.class);
            }
        };
    }
    
    public Converter<BodyOfWork, String> ApplicationConversionServiceFactoryBean.getBodyOfWorkToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.app.myschool.model.BodyOfWork, java.lang.String>() {
            public String convert(BodyOfWork bodyOfWork) {
                return new StringBuilder().append(bodyOfWork.getWorkName()).append(' ').append(bodyOfWork.getDescription()).append(' ').append(bodyOfWork.getWhat()).append(' ').append(bodyOfWork.getObjective()).toString();
            }
        };
    }
    
    public Converter<Long, BodyOfWork> ApplicationConversionServiceFactoryBean.getIdToBodyOfWorkConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.app.myschool.model.BodyOfWork>() {
            public com.app.myschool.model.BodyOfWork convert(java.lang.Long id) {
                return BodyOfWork.findBodyOfWork(id);
            }
        };
    }
    
    public Converter<String, BodyOfWork> ApplicationConversionServiceFactoryBean.getStringToBodyOfWorkConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.app.myschool.model.BodyOfWork>() {
            public com.app.myschool.model.BodyOfWork convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), BodyOfWork.class);
            }
        };
    }
    
    public Converter<Daily, String> ApplicationConversionServiceFactoryBean.getDailyToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.app.myschool.model.Daily, java.lang.String>() {
            public String convert(Daily daily) {
                return new StringBuilder().append(daily.getDaily_year()).append(' ').append(daily.getDaily_month()).append(' ').append(daily.getDaily_week()).append(' ').append(daily.getDaily_day()).toString();
            }
        };
    }
    
    public Converter<Long, Daily> ApplicationConversionServiceFactoryBean.getIdToDailyConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.app.myschool.model.Daily>() {
            public com.app.myschool.model.Daily convert(java.lang.Long id) {
                return Daily.findDaily(id);
            }
        };
    }
    
    public Converter<String, Daily> ApplicationConversionServiceFactoryBean.getStringToDailyConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.app.myschool.model.Daily>() {
            public com.app.myschool.model.Daily convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Daily.class);
            }
        };
    }
    
    public Converter<EvaluationRatings, String> ApplicationConversionServiceFactoryBean.getEvaluationRatingsToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.app.myschool.model.EvaluationRatings, java.lang.String>() {
            public String convert(EvaluationRatings evaluationRatings) {
                return new StringBuilder().append(evaluationRatings.getMotivation()).append(' ').append(evaluationRatings.getOrganization()).append(' ').append(evaluationRatings.getEffectiveUseOfStudyTime()).append(' ').append(evaluationRatings.getQualityOfWork()).toString();
            }
        };
    }
    
    public Converter<Long, EvaluationRatings> ApplicationConversionServiceFactoryBean.getIdToEvaluationRatingsConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.app.myschool.model.EvaluationRatings>() {
            public com.app.myschool.model.EvaluationRatings convert(java.lang.Long id) {
                return EvaluationRatings.findEvaluationRatings(id);
            }
        };
    }
    
    public Converter<String, EvaluationRatings> ApplicationConversionServiceFactoryBean.getStringToEvaluationRatingsConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.app.myschool.model.EvaluationRatings>() {
            public com.app.myschool.model.EvaluationRatings convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), EvaluationRatings.class);
            }
        };
    }
    
    public Converter<Faculty, String> ApplicationConversionServiceFactoryBean.getFacultyToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.app.myschool.model.Faculty, java.lang.String>() {
            public String convert(Faculty faculty) {
                return new StringBuilder().append(faculty.getPersonID()).append(' ').append(faculty.getFirstName()).append(' ').append(faculty.getLastName()).append(' ').append(faculty.getMiddleName()).toString();
            }
        };
    }
    
    public Converter<Long, Faculty> ApplicationConversionServiceFactoryBean.getIdToFacultyConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.app.myschool.model.Faculty>() {
            public com.app.myschool.model.Faculty convert(java.lang.Long id) {
                return Faculty.findFaculty(id);
            }
        };
    }
    
    public Converter<String, Faculty> ApplicationConversionServiceFactoryBean.getStringToFacultyConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.app.myschool.model.Faculty>() {
            public com.app.myschool.model.Faculty convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Faculty.class);
            }
        };
    }
    
    public Converter<GraduateTracking, String> ApplicationConversionServiceFactoryBean.getGraduateTrackingToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.app.myschool.model.GraduateTracking, java.lang.String>() {
            public String convert(GraduateTracking graduateTracking) {
                return "(no displayable fields)";
            }
        };
    }
    
    public Converter<Long, GraduateTracking> ApplicationConversionServiceFactoryBean.getIdToGraduateTrackingConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.app.myschool.model.GraduateTracking>() {
            public com.app.myschool.model.GraduateTracking convert(java.lang.Long id) {
                return GraduateTracking.findGraduateTracking(id);
            }
        };
    }
    
    public Converter<String, GraduateTracking> ApplicationConversionServiceFactoryBean.getStringToGraduateTrackingConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.app.myschool.model.GraduateTracking>() {
            public com.app.myschool.model.GraduateTracking convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), GraduateTracking.class);
            }
        };
    }
    
    public Converter<MonthlyEvaluationRatings, String> ApplicationConversionServiceFactoryBean.getMonthlyEvaluationRatingsToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.app.myschool.model.MonthlyEvaluationRatings, java.lang.String>() {
            public String convert(MonthlyEvaluationRatings monthlyEvaluationRatings) {
                return new StringBuilder().append(monthlyEvaluationRatings.getMonth_year()).append(' ').append(monthlyEvaluationRatings.getMonth_number()).append(' ').append(monthlyEvaluationRatings.getWeek_number()).append(' ').append(monthlyEvaluationRatings.getLevelOfDifficulty()).toString();
            }
        };
    }
    
    public Converter<Long, MonthlyEvaluationRatings> ApplicationConversionServiceFactoryBean.getIdToMonthlyEvaluationRatingsConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.app.myschool.model.MonthlyEvaluationRatings>() {
            public com.app.myschool.model.MonthlyEvaluationRatings convert(java.lang.Long id) {
                return MonthlyEvaluationRatings.findMonthlyEvaluationRatings(id);
            }
        };
    }
    
    public Converter<String, MonthlyEvaluationRatings> ApplicationConversionServiceFactoryBean.getStringToMonthlyEvaluationRatingsConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.app.myschool.model.MonthlyEvaluationRatings>() {
            public com.app.myschool.model.MonthlyEvaluationRatings convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), MonthlyEvaluationRatings.class);
            }
        };
    }
    
    public Converter<MonthlySummaryRatings, String> ApplicationConversionServiceFactoryBean.getMonthlySummaryRatingsToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.app.myschool.model.MonthlySummaryRatings, java.lang.String>() {
            public String convert(MonthlySummaryRatings monthlySummaryRatings) {
                return new StringBuilder().append(monthlySummaryRatings.getYear_number()).append(' ').append(monthlySummaryRatings.getMonth_number()).append(' ').append(monthlySummaryRatings.getWeek_number()).append(' ').append(monthlySummaryRatings.getFeelings()).toString();
            }
        };
    }
    
    public Converter<Long, MonthlySummaryRatings> ApplicationConversionServiceFactoryBean.getIdToMonthlySummaryRatingsConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.app.myschool.model.MonthlySummaryRatings>() {
            public com.app.myschool.model.MonthlySummaryRatings convert(java.lang.Long id) {
                return MonthlySummaryRatings.findMonthlySummaryRatings(id);
            }
        };
    }
    
    public Converter<String, MonthlySummaryRatings> ApplicationConversionServiceFactoryBean.getStringToMonthlySummaryRatingsConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.app.myschool.model.MonthlySummaryRatings>() {
            public com.app.myschool.model.MonthlySummaryRatings convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), MonthlySummaryRatings.class);
            }
        };
    }
    
    public Converter<PreviousTranscripts, String> ApplicationConversionServiceFactoryBean.getPreviousTranscriptsToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.app.myschool.model.PreviousTranscripts, java.lang.String>() {
            public String convert(PreviousTranscripts previousTranscripts) {
                return new StringBuilder().append(previousTranscripts.getType()).toString();
            }
        };
    }
    
    public Converter<Long, PreviousTranscripts> ApplicationConversionServiceFactoryBean.getIdToPreviousTranscriptsConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.app.myschool.model.PreviousTranscripts>() {
            public com.app.myschool.model.PreviousTranscripts convert(java.lang.Long id) {
                return PreviousTranscripts.findPreviousTranscripts(id);
            }
        };
    }
    
    public Converter<String, PreviousTranscripts> ApplicationConversionServiceFactoryBean.getStringToPreviousTranscriptsConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.app.myschool.model.PreviousTranscripts>() {
            public com.app.myschool.model.PreviousTranscripts convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), PreviousTranscripts.class);
            }
        };
    }
    
    public Converter<Quarter, String> ApplicationConversionServiceFactoryBean.getQuarterToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.app.myschool.model.Quarter, java.lang.String>() {
            public String convert(Quarter quarter) {
                return new StringBuilder().append(quarter.getQtrName()).append(' ').append(quarter.getGradeType()).append(' ').append(quarter.getGrade()).append(' ').append(quarter.getWhoUpdated()).toString();
            }
        };
    }
    
    public Converter<Long, Quarter> ApplicationConversionServiceFactoryBean.getIdToQuarterConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.app.myschool.model.Quarter>() {
            public com.app.myschool.model.Quarter convert(java.lang.Long id) {
                return Quarter.findQuarter(id);
            }
        };
    }
    
    public Converter<String, Quarter> ApplicationConversionServiceFactoryBean.getStringToQuarterConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.app.myschool.model.Quarter>() {
            public com.app.myschool.model.Quarter convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Quarter.class);
            }
        };
    }
    
    public Converter<QuarterNames, String> ApplicationConversionServiceFactoryBean.getQuarterNamesToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.app.myschool.model.QuarterNames, java.lang.String>() {
            public String convert(QuarterNames quarterNames) {
                return new StringBuilder().append(quarterNames.getQtrName()).toString();
            }
        };
    }
    
    public Converter<Long, QuarterNames> ApplicationConversionServiceFactoryBean.getIdToQuarterNamesConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.app.myschool.model.QuarterNames>() {
            public com.app.myschool.model.QuarterNames convert(java.lang.Long id) {
                return QuarterNames.findQuarterNames(id);
            }
        };
    }
    
    public Converter<String, QuarterNames> ApplicationConversionServiceFactoryBean.getStringToQuarterNamesConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.app.myschool.model.QuarterNames>() {
            public com.app.myschool.model.QuarterNames convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), QuarterNames.class);
            }
        };
    }
    
    public Converter<Roles, String> ApplicationConversionServiceFactoryBean.getRolesToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.app.myschool.model.Roles, java.lang.String>() {
            public String convert(Roles roles) {
                return new StringBuilder().append(roles.getRoleName()).append(' ').append(roles.getRoleType()).toString();
            }
        };
    }
    
    public Converter<Long, Roles> ApplicationConversionServiceFactoryBean.getIdToRolesConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.app.myschool.model.Roles>() {
            public com.app.myschool.model.Roles convert(java.lang.Long id) {
                return Roles.findRoles(id);
            }
        };
    }
    
    public Converter<String, Roles> ApplicationConversionServiceFactoryBean.getStringToRolesConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.app.myschool.model.Roles>() {
            public com.app.myschool.model.Roles convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Roles.class);
            }
        };
    }
    
    public Converter<SkillRatings, String> ApplicationConversionServiceFactoryBean.getSkillRatingsToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.app.myschool.model.SkillRatings, java.lang.String>() {
            public String convert(SkillRatings skillRatings) {
                return new StringBuilder().append(skillRatings.getRemembering()).append(' ').append(skillRatings.getUnderstanding()).append(' ').append(skillRatings.getApplying()).append(' ').append(skillRatings.getAnalyzing()).toString();
            }
        };
    }
    
    public Converter<Long, SkillRatings> ApplicationConversionServiceFactoryBean.getIdToSkillRatingsConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.app.myschool.model.SkillRatings>() {
            public com.app.myschool.model.SkillRatings convert(java.lang.Long id) {
                return SkillRatings.findSkillRatings(id);
            }
        };
    }
    
    public Converter<String, SkillRatings> ApplicationConversionServiceFactoryBean.getStringToSkillRatingsConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.app.myschool.model.SkillRatings>() {
            public com.app.myschool.model.SkillRatings convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), SkillRatings.class);
            }
        };
    }
    
    public Converter<Student, String> ApplicationConversionServiceFactoryBean.getStudentToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.app.myschool.model.Student, java.lang.String>() {
            public String convert(Student student) {
                return new StringBuilder().append(student.getPersonID()).append(' ').append(student.getFirstName()).append(' ').append(student.getLastName()).append(' ').append(student.getMiddleName()).toString();
            }
        };
    }
    
    public Converter<Long, Student> ApplicationConversionServiceFactoryBean.getIdToStudentConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.app.myschool.model.Student>() {
            public com.app.myschool.model.Student convert(java.lang.Long id) {
                return Student.findStudent(id);
            }
        };
    }
    
    public Converter<String, Student> ApplicationConversionServiceFactoryBean.getStringToStudentConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.app.myschool.model.Student>() {
            public com.app.myschool.model.Student convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Student.class);
            }
        };
    }
    
    public Converter<Subject, String> ApplicationConversionServiceFactoryBean.getSubjectToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.app.myschool.model.Subject, java.lang.String>() {
            public String convert(Subject subject) {
                return new StringBuilder().append(subject.getName()).append(' ').append(subject.getGradeLevel()).append(' ').append(subject.getCreditHours()).append(' ').append(subject.getDescription()).toString();
            }
        };
    }
    
    public Converter<Long, Subject> ApplicationConversionServiceFactoryBean.getIdToSubjectConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.app.myschool.model.Subject>() {
            public com.app.myschool.model.Subject convert(java.lang.Long id) {
                return Subject.findSubject(id);
            }
        };
    }
    
    public Converter<String, Subject> ApplicationConversionServiceFactoryBean.getStringToSubjectConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.app.myschool.model.Subject>() {
            public com.app.myschool.model.Subject convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Subject.class);
            }
        };
    }
    
    public Converter<Weekly, String> ApplicationConversionServiceFactoryBean.getWeeklyToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.app.myschool.model.Weekly, java.lang.String>() {
            public String convert(Weekly weekly) {
                return new StringBuilder().append(weekly.getWeek_year()).append(' ').append(weekly.getWeek_month()).append(' ').append(weekly.getWeek_number()).append(' ').append(weekly.getComments()).toString();
            }
        };
    }
    
    public Converter<Long, Weekly> ApplicationConversionServiceFactoryBean.getIdToWeeklyConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.app.myschool.model.Weekly>() {
            public com.app.myschool.model.Weekly convert(java.lang.Long id) {
                return Weekly.findWeekly(id);
            }
        };
    }
    
    public Converter<String, Weekly> ApplicationConversionServiceFactoryBean.getStringToWeeklyConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.app.myschool.model.Weekly>() {
            public com.app.myschool.model.Weekly convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Weekly.class);
            }
        };
    }
    
    public void ApplicationConversionServiceFactoryBean.installLabelConverters(FormatterRegistry registry) {
        registry.addConverter(getArtifactToStringConverter());
        registry.addConverter(getIdToArtifactConverter());
        registry.addConverter(getStringToArtifactConverter());
        registry.addConverter(getBodyOfWorkToStringConverter());
        registry.addConverter(getIdToBodyOfWorkConverter());
        registry.addConverter(getStringToBodyOfWorkConverter());
        registry.addConverter(getDailyToStringConverter());
        registry.addConverter(getIdToDailyConverter());
        registry.addConverter(getStringToDailyConverter());
        registry.addConverter(getEvaluationRatingsToStringConverter());
        registry.addConverter(getIdToEvaluationRatingsConverter());
        registry.addConverter(getStringToEvaluationRatingsConverter());
        registry.addConverter(getFacultyToStringConverter());
        registry.addConverter(getIdToFacultyConverter());
        registry.addConverter(getStringToFacultyConverter());
        registry.addConverter(getGraduateTrackingToStringConverter());
        registry.addConverter(getIdToGraduateTrackingConverter());
        registry.addConverter(getStringToGraduateTrackingConverter());
        registry.addConverter(getMonthlyEvaluationRatingsToStringConverter());
        registry.addConverter(getIdToMonthlyEvaluationRatingsConverter());
        registry.addConverter(getStringToMonthlyEvaluationRatingsConverter());
        registry.addConverter(getMonthlySummaryRatingsToStringConverter());
        registry.addConverter(getIdToMonthlySummaryRatingsConverter());
        registry.addConverter(getStringToMonthlySummaryRatingsConverter());
        registry.addConverter(getPreviousTranscriptsToStringConverter());
        registry.addConverter(getIdToPreviousTranscriptsConverter());
        registry.addConverter(getStringToPreviousTranscriptsConverter());
        registry.addConverter(getQuarterToStringConverter());
        registry.addConverter(getIdToQuarterConverter());
        registry.addConverter(getStringToQuarterConverter());
        registry.addConverter(getQuarterNamesToStringConverter());
        registry.addConverter(getIdToQuarterNamesConverter());
        registry.addConverter(getStringToQuarterNamesConverter());
        registry.addConverter(getRolesToStringConverter());
        registry.addConverter(getIdToRolesConverter());
        registry.addConverter(getStringToRolesConverter());
        registry.addConverter(getSkillRatingsToStringConverter());
        registry.addConverter(getIdToSkillRatingsConverter());
        registry.addConverter(getStringToSkillRatingsConverter());
        registry.addConverter(getStudentToStringConverter());
        registry.addConverter(getIdToStudentConverter());
        registry.addConverter(getStringToStudentConverter());
        registry.addConverter(getSubjectToStringConverter());
        registry.addConverter(getIdToSubjectConverter());
        registry.addConverter(getStringToSubjectConverter());
        registry.addConverter(getWeeklyToStringConverter());
        registry.addConverter(getIdToWeeklyConverter());
        registry.addConverter(getStringToWeeklyConverter());
    }
    
    public void ApplicationConversionServiceFactoryBean.afterPropertiesSet() {
        super.afterPropertiesSet();
        installLabelConverters(getObject());
    }
    
}
