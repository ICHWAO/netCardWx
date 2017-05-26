package com.code.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
 
 

public class Responser {


	ObjectMapper mapper = new ObjectMapper();  
	ObjectNode rootNode;
	
	public Responser(){

		rootNode = mapper.createObjectNode();

 	}

	public void put(String key,String value){
		rootNode.put(key, value);
	}

	public void putObject(String key,Object value){
		  
		rootNode.putPOJO(String.valueOf(key), (Object)value); 
	}

	public void putArray(String key,Object value)	{
 		rootNode.putPOJO(String.valueOf(key), (Object)value); 
 		
	}

    public  String GetResponse() throws JsonProcessingException{
    	
    	return mapper.writeValueAsString(rootNode).toString();
    	
    }
	
}
