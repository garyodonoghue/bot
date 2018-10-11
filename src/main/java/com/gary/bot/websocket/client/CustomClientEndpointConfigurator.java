package com.gary.bot.websocket.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.websocket.ClientEndpointConfig;

public class CustomClientEndpointConfigurator extends ClientEndpointConfig.Configurator {

  @Override
  public void beforeRequest(Map<String,List<String>> headers){
	  List<String> values = new ArrayList<String>();
	    values.add(System.getProperty("authToken"));  
      headers.put("Authorization" , values);
      
      List<String> values2 = new ArrayList<String>();
	  values2.add("nl-NL,en;q=0.8");
      headers.put("Accept-Language", values2);
  }
}
