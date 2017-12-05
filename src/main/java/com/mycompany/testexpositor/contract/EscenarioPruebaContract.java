/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testexpositor.contract;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author simon.rua
 */
@Path("executionTest")
public interface EscenarioPruebaContract 
{
     @Path("/dummy")
     @GET
     @Produces(MediaType.TEXT_PLAIN)
     public String dummy();
     
     @Path("/automationTest")
     @POST
     @Produces(MediaType.TEXT_PLAIN)
     public String testLogginSession(@QueryParam("testCase")String casoPrueba);
     
}
