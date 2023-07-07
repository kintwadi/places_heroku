package com.place;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.util.ResourceUtils;

public class Util {

	public static Properties loadResourceFile(String name) {
		Properties properties = new Properties();

		String fileName = "classpath:message_" + name.toLowerCase() + ".properties";
		try {
			File file = ResourceUtils.getFile(fileName);
			InputStream in = new FileInputStream(file);
			properties.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}
	
public static JSONArray loadJSON(String directory,String name) {
	
	JSONArray data = new JSONArray();
	try {
		
		String fileName = "classpath:"+directory +FileSystems.getDefault().getSeparator()+ name + ".json";
		File file = ResourceUtils.getFile(fileName);
        JSONParser parser = new JSONParser();
        //Use JSONObject for simple JSON and JSONArray for array of JSON.
        data = (JSONArray) parser.parse( new FileReader(file.getAbsolutePath()));

    } catch (IOException | ParseException e) 
	{
        e.printStackTrace();
    }
	return data;
}

	public static String getLanguage(HttpServletRequest request) {

		String lang = request.getParameter("lang");
		if (lang == null) 
		{
			lang = Constants.DEFAULT_LANGUAGE;
		}
		
		return lang;
	}
	
	public static long getId(HttpServletRequest request) {

		Long id = 0L; 
		String value = request.getParameter("id");
		if (value != null) {
			
			id = Long.parseLong(value);
		}
		return id;
	}
	
 public static void main(String [] args) {
		

			
			Path directory1 = Paths.get("uploads"+"/users");
			Path directory2 = Paths.get("uploads"+"/users/"+500);
			try {
			
				Files.createDirectories(directory1);
				Files.createDirectories(directory2);
			} catch (IOException e) {
				System.out.println("Create directory: "+e.getMessage());
			}
			
		
	}

}
