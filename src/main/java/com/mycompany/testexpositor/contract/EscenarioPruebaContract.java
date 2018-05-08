/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testexpositor.contract;

import com.mycompany.testexpositor.entities.CasosPruebas;
import com.mycompany.testexpositor.entities.Parametros;
import java.util.List;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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
     
     @Path("/dummyResults")
     @GET
     @Produces(MediaType.APPLICATION_JSON)
     public List<CasosPruebas> resultTestCasesDummy();
     
     @Path("/automationTest")
     @POST
     @Produces(MediaType.TEXT_PLAIN)
     public String testLogginSession(@QueryParam("testCase")String casoPrueba);
     
     @Path("/allTcsByProject")
     @POST
     @Produces(MediaType.APPLICATION_JSON)
     public List<CasosPruebas> searchAllTestCasesByProject(JSONObject proyecto);
     
     @Path("/allTcs")
     @POST
     @Produces(MediaType.APPLICATION_JSON)
     public List<CasosPruebas> searchAllTestCases();
     
     @Path("/executeTestAuto")
     @POST
     @Produces(MediaType.APPLICATION_JSON)
     public List<CasosPruebas> executeTestAutomation(JSONArray casosPrueba);
     
     @Path("/allParamsByTc")
     @POST
     @Produces(MediaType.APPLICATION_JSON)
     public List<Parametros> searchAllParametersByTc(JSONObject cpJson);
}
