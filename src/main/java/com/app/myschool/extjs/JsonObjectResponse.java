/**
 * 
 */
package com.app.myschool.extjs;

import java.util.Date;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

/**  
 * Class to be used for returning data back to the client.  
 *   
 * Data should be set on this object then serialized into JSON format  
 * to be returned to the client.  
 *   
 * @author nvujasin  
 */   
public class JsonObjectResponse {   
       
    private long total;   
    private boolean success;   
    private Object data;   
    private String message;  
    private boolean excludeData;
    private static final DateTransformer DATE_TRANSFORMER = new DateTransformer("MM/dd/yyyy");
       
    public long getTotal() {   
        return total;   
    }   
    public void setTotal(long total) {   
        this.total = total;   
    }   
    public boolean isSuccess() {   
        return success;   
    }   
    public void setSuccess(boolean success) {   
        this.success = success;   
    }   
    public Object getData() {   
        return data;   
    }   
    public void setData(Object data) {   
        this.data = data;   
    }   
    public String getMessage() {   
        return message;   
    }   
    public void setMessage(String message) {   
        this.message = message;   
    }  

    public boolean getExcludeData() {
        return excludeData;
    }

    public void setExcludeData(boolean excludeData) {
        this.excludeData = excludeData;
    }

    public String toString() {
        if (excludeData)
            return new JSONSerializer().exclude("*.class").include("data.id")
                    .exclude("data.*")
                    //.transform(new DateTransformer("MM/dd/yyyy"), Date.class)
                    .transform(DATE_TRANSFORMER, Date.class)
                    .serialize(this);
        else
            return new JSONSerializer().exclude("*.class", "excludeData").include( "data.id" )
                    //.transform(new DateTransformer("MM/dd/yyyy"), Date.class)
            		.transform(DATE_TRANSFORMER, Date.class)
                    .serialize(this);
    }
}   
