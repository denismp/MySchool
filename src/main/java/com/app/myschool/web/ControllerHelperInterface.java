/**
 * 
 */
package com.app.myschool.web;

import java.util.Map;
import org.springframework.http.ResponseEntity;


/**
 * @author denisputnam
 *
 */
public interface ControllerHelperInterface {
	public String getParam(@SuppressWarnings("rawtypes") Map m, String p);
	
	public ResponseEntity<String> listJson(@SuppressWarnings("rawtypes") Map params);

	public ResponseEntity<String> listJson();
	
    public ResponseEntity<String> showJson(Long id);
    
    public ResponseEntity<String> createFromJson(String json);

    public ResponseEntity<String> deleteFromJson(Long id);

    public ResponseEntity<String> updateFromJson(String json);

    public ResponseEntity<String> updateFromJsonArray(String json);

    public ResponseEntity<String> createFromJsonArray(String json);
    
	//public ResponseEntity<String> jsonFindStudentsByUserNameEquals(String student);
}
