/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.dao;

import com.mycompany.testexpositor.entities.CasosPruebas;
import com.mycompany.testexpositor.entities.Parametros;
import com.mycompany.testexpositor.services.EscenarioPruebaService;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;


/**
 *
 * @author simon.rua
 */
@Ignore
public class CasosPruebaServiceTest {

    private static EscenarioPruebaService epServ;
    private static final Logger LOGGER = Logger.getLogger(CasosPruebaServiceTest.class.getName());
    
    @BeforeClass
    public static void setUpClass() 
    {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    
     @Test
     public void testSearchNoExistingTestCasesByProject() throws JSONException 
     {
            epServ = new EscenarioPruebaService();
            
            JSONObject proyecto = new JSONObject();
            proyecto.put("codigo","PMO");
            
            List testCases = epServ.searchAllTestCasesByProject(proyecto);
            
            assertEquals(true, testCases.isEmpty());
     }
     
     @Test
     public void testSearchExistingTestCasesByProject() throws JSONException 
     {
            epServ = new EscenarioPruebaService();
            
            JSONObject proyecto = new JSONObject();
            proyecto.put("codigo","PMO23");
            
            List testCases = epServ.searchAllTestCasesByProject(proyecto);
            
            assertEquals(true, !testCases.isEmpty());
     }
     
     @Test
     public void testSearchExistingParametersByTestCaseWithOutService()
     {
            epServ = new EscenarioPruebaService();
            String cpReference = "CP_01";
            
            JSONObject proyecto = new JSONObject();
            proyecto.put("codigo","PMO23");
            
            List<CasosPruebas> cpsList = epServ.searchAllTestCasesByProject(proyecto);
            List<Parametros> parametersList = new ArrayList();
            
            for(CasosPruebas cp:cpsList)
            {
                if(cp.getCodigoCaso().equals("CP_01"))
                {
                   parametersList = cp.getParametrosList();
                   break;
                }
            }

            for(Parametros p:parametersList)
            {
                LOGGER.info(p.getCasoPruebacodigo()+":"+p.getNombre()+":"+p.getValor());
            }   
            
            assertEquals(true, !parametersList.isEmpty());
     }
     
     @Test
     public void testSearchExistingParametersByTestCase()
     {
             epServ = new EscenarioPruebaService();
             JSONObject cpJson = new JSONObject();
             cpJson.put("codigoCaso","CP_03");

             List<Parametros> paramList = epServ.searchAllParametersByTc(cpJson);
             
            for(Parametros p:paramList)
            {
                LOGGER.info(p.getCasoPruebacodigo()+":"+p.getNombre()+":"+p.getValor());
            }   
             
             assertEquals(true, !paramList.isEmpty());
     }
     
      @Test
     public void testExecuteAutomationTcsSuite() throws ParseException
     {
             LinkedHashMap lhmOne = new LinkedHashMap();
             lhmOne.put("codigoCaso","CP_01");
             lhmOne.put("escenario","INGRESO_ROL");
             
             LinkedHashMap lhmTwo = new LinkedHashMap();
             lhmTwo.put("codigoCaso","CP_03");
             lhmTwo.put("escenario","REGISTRO_OBRA");

             JSONArray cpsJsonArr = new JSONArray();
             cpsJsonArr.add(lhmOne);
             cpsJsonArr.add(lhmTwo);
             
             epServ = new EscenarioPruebaService();
             List<CasosPruebas> cpsList = epServ.executeTestAutomation(cpsJsonArr);
             
             assertEquals("exitoso", cpsList.get(0).getResultado());
     }
     
     @Test
     public void testEditParamsTcs()
     {
            JSONObject cpJson = new JSONObject();
            cpJson.put("codigoCaso","CP_01");
            cpJson.put("username","ramirezGC");
            cpJson.put("password","123654");
            
            epServ = new EscenarioPruebaService();
            epServ.updateAllParametersByTc(cpJson);
            
            Map paramsMap = epServ.searchAllParametersMaps(cpJson.get("codigoCaso").toString());

            assertEquals(cpJson.get("username"),paramsMap.get("username"));
     }

}
