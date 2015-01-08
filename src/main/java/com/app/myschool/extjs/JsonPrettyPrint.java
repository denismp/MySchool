/**
 * 
 */
package com.app.myschool.extjs;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * @author denisputnam
 *
 */
public class JsonPrettyPrint
{
	
	public static String getPrettyString( Object myObject )
	{
		ObjectMapper mapper = new ObjectMapper();
		String responseString = myObject.toString();
		try
		{
			responseString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(myObject);
		}
		catch (JsonGenerationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (JsonMappingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return responseString;
	}

}
