package com.CS6511_AI.maven.tictactoe;

import okhttp3.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class Requests {
	
	final static String baseUrl = "https://www.notexponential.com/aip2pgaming/api/index.php?";
	
	
	public static void main(String [] args) throws IOException
	{
		// testing
	    //System.out.println(get_board_map(3794));
	    
	    // testing
	    //System.out.println(get_board_string(3794));
	    
	    // testing
	    System.out.println(get_team_members(1343));
	}
	
	static String get_board_map(int gameId) throws IOException
	{
		OkHttpClient client = new OkHttpClient().newBuilder()
	    	      .build();
	    	    MediaType mediaType = MediaType.parse("text/plain");
	    	    Request request = new Request.Builder()
	    	      .url(baseUrl+"type=boardMap&gameId=" + gameId)
	    	      .method("GET", null)
	    	      .addHeader("x-api-key", "962b43699ecfed02ee31")
	    	      .addHeader("userid", "1134")
	    	      .build();
	    	    Response response = client.newCall(request).execute();
	    	    
	    	    // holds the response from the API call
	    	    String jsonResponse = response.body().string();
	    	    
	    	    
	    	    // Accessing the JSON values within the response string using the jsonNOde object
	    	    ObjectMapper objectMapper = new ObjectMapper();
	            JsonNode jsonNode = objectMapper.readTree(jsonResponse);
	            
	            String code = jsonNode.get("code").asText();
	            
	            if (code.equals("FAIL"))
	            {
	            	String errorMessage = jsonNode.get("message").asText();
	            	System.out.println("FAIL: " + errorMessage);
	            	return "-1";
	            }
	            else if (code.equals("OK"))
	            {
		            String output = jsonNode.get("output").asText();
		            int target = jsonNode.get("target").asInt();
		            
		            // only returning the board map
		            // Format: "{"4,4":"O","4,3":"X","3,3":"O","3,4":"X","5,4":"O"}"
		            return output;
	            }
	            else
	            {
	            	System.out.println("Unknown Error" );
	            	return "-1";
	            }
	}
	
	static String get_board_string(int gameId) throws IOException{
	    OkHttpClient client = new OkHttpClient().newBuilder()
	    	      .build();
	    	    MediaType mediaType = MediaType.parse("text/plain");
	    	    Request request = new Request.Builder()
	    	      .url(baseUrl+"type=boardString&gameId=" + gameId)
	    	      .method("GET", null)
	    	      .addHeader("x-api-key", "962b43699ecfed02ee31")
	    	      .addHeader("userid", "1134")
	    	      .build();
	    	    Response response = client.newCall(request).execute();
	    	    
	    	    // holds the response from the API call
	    	    String jsonResponse = response.body().string();
	    	    
	    	    
	    	    // Accessing the JSON values within the response string using the jsonNOde object
	    	    ObjectMapper objectMapper = new ObjectMapper();
	            JsonNode jsonNode = objectMapper.readTree(jsonResponse);
	            
	            String code = jsonNode.get("code").asText();
	            
	            if (code.equals("FAIL"))
	            {
	            	String errorMessage = jsonNode.get("message").asText();
	            	System.out.println("FAIL: " + errorMessage);
	            	return "-1";
	            }
	            else if (code.equals("OK"))
	            {
		            String output = jsonNode.get("output").asText();
		            int target = jsonNode.get("target").asInt();
		            
		            /*only returning the board string
		            Format: 
									------------
									------------
									------------
									---OX-------
									---XO-------
									----O-------
									------------
									------------
									------------
									------------
									------------
									------------

		            */
		            return output;
	            }
	            else
	            {
	            	System.out.println("Unknown Error" );
	            	return "-1";
	            }

	}
	
	static List <String> get_team_members(int teamId)throws IOException
	{
		 // Create a list to store the array elements
        List<String> myArray = new ArrayList<>();
        
	    OkHttpClient client = new OkHttpClient().newBuilder()
	    	      .build();
	    	    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
	    	    Request request = new Request.Builder()
	    	      .url(baseUrl+"type=team&teamId=" + teamId)
	    	      .method("GET", null)
	    	      .addHeader("x-api-key", "962b43699ecfed02ee31")
	    	      .addHeader("userid", "1134")
	    	      .addHeader("Content-Type", "application/x-www-form-urlencoded")
	    	      .build();
	    	    Response response = client.newCall(request).execute();
	    	 // holds the response from the API call
	    	    String jsonResponse = response.body().string();
	    	   // System.out.println(response.body().string());
	    	    
	    	    // Accessing the JSON values within the response string using the jsonNOde object
	    	    ObjectMapper objectMapper = new ObjectMapper();
	            JsonNode jsonNode = objectMapper.readTree(jsonResponse);
	            
	            String code = jsonNode.get("code").asText();
	            
	            if (code.equals("FAIL"))
	            {
	            	String errorMessage = jsonNode.get("message").asText();
	            	System.out.println("FAIL: " + errorMessage);
	            	myArray.add("-1");
	            	return myArray;
	            }
	            else if (code.equals("OK"))
	            {
	            	JsonNode userIdsNode = jsonNode.get("userIds");
		            
		            // only returning one UserID because we only have access to a single userID as a team
		            // Format: 
	            	

	                // Iterate over the array elements and add them to the list
	                for (JsonNode arrayElement : userIdsNode) {
	                    String element = arrayElement.asText();
	                    myArray.add(element);
	                }

	                
		            return myArray;
	            }
	            else
	            {
	            	System.out.println("Unknown Error" );
	            	myArray.add("-1");
	            	return myArray;
	            }
	    	  }

}

