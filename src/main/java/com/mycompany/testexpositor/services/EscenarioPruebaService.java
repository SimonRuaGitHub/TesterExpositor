/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testexpositor.services;

import com.museumapp.ScenariosProcess.OpenSessionExecution;
import com.mycompany.testexpositor.contract.EscenarioPruebaContract;
import com.mycompany.testexpositor.dao.CasosPruebasJpaController;
import com.mycompany.testexpositor.dao.ParametrosJpaController;
import com.mycompany.testexpositor.dao.ProyectosJpaController;
import com.mycompany.testexpositor.entities.CasosPruebas;
import com.mycompany.testexpositor.entities.Parametros;
import com.mycompany.testexpositor.entities.Proyectos;
import com.mycompany.testexpositor.objects.Estados;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONObject;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
/**
 *
 * @author simon.rua
 */
public class EscenarioPruebaService implements EscenarioPruebaContract 
{
    private static final Logger LOGGER = Logger.getLogger(EscenarioPruebaService.class.getName());
    private CasosPruebasJpaController cpDao;
    private ProyectosJpaController proyDao;
    private ParametrosJpaController paramDao;

    public String dummy() {
        return "Conexion a backend pruebas exitosa";
    }

    public String testLogginSession(String casoPrueba) 
    {
          OpenSessionExecution openSessionExecution = new OpenSessionExecution();
         
          boolean result =  openSessionExecution.executeAutomatedOpeningSession(casoPrueba);
                  
          if(result == true)
              return "exitoso";
          else 
              return "fallido";
    }
    
    public List<Parametros> searchAllParametersByTc(JSONObject cpJson)
    {
           LOGGER.info("Ingresando a servicio de consulta de parametros para caso de prueba: "+cpJson);
           
           String cpCodigo = null;
           cpCodigo = (String) cpJson.get("codigoCaso");
           
           cpDao = new CasosPruebasJpaController();
           CasosPruebas cp = cpDao.findCasosPruebas(cpCodigo);
           
           paramDao = new ParametrosJpaController();
           List<Parametros> paramList = paramDao.findParametrosByCasoPrueba(cp);
           
           return paramList;
    }

    public List<CasosPruebas> searchAllTestCasesByProject(JSONObject proyecto) 
    {
           LOGGER.info("Ingresando a servicio de consula de casos de prueba por proyecto "+proyecto);
           
           String codProyecto = null;

           codProyecto = (String) proyecto.get("codigo");

           proyDao = new ProyectosJpaController();
           Proyectos proyectoEntity = proyDao.findProyectos(codProyecto);
           
           LOGGER.info("Realizando consulta de casos prueba");
           cpDao = new CasosPruebasJpaController();
           List<CasosPruebas> cpsList = cpDao.findCasosPruebasByProyecto(proyectoEntity);
                      
           LOGGER.info("Retornando resultado consulta casos de prueba para proyecto: "+codProyecto);
           return cpsList;
    }

    public List<CasosPruebas> searchAllTestCases() 
    {
           LOGGER.info("Ingresando a servicio de consula de casos de prueba");
           
           cpDao = new CasosPruebasJpaController();
           
           LOGGER.info("Realizando consulta de casos prueba");
           List<CasosPruebas> cpsList = cpDao.findCasosPruebasEntities();

           return cpsList;
    }
    

    public List<CasosPruebas> executeTestAutomation(JSONArray casosPrueba)
    {
           LOGGER.info("Ingresando a servicio de ejecucion automatizada");   
           Iterator iterator = casosPrueba.iterator();
           Map<String,String> mapCasosPrueba = new HashMap();
           List<CasosPruebas> cpsList = new ArrayList();
           
           while(iterator.hasNext())
           {
                LinkedHashMap hashMap = (LinkedHashMap) iterator.next();
                LOGGER.info((String) hashMap.get("codigoCaso"));
                LOGGER.info((String) hashMap.get("nombre"));
                mapCasosPrueba.put((String) hashMap.get("codigoCaso"),(String) hashMap.get("nombre"));
           }
           
        for(String cp:mapCasosPrueba.keySet())
        {
           LOGGER.info("Descripcion: "+mapCasosPrueba.get(cp));
           String escenario = mapCasosPrueba.get(cp).toUpperCase();
           CasosPruebas casoPrueba = new CasosPruebas();
           String resultStr = "";
           boolean result = false;
           boolean executed = false;
           
           if(escenario.trim().equalsIgnoreCase("validacion ingreso GC"))
           {
               LOGGER.info("Ejecutando prueba automatizada de ingreso de gerente de catalogo");
               OpenSessionExecution openSessionExecution = new OpenSessionExecution();
               result = openSessionExecution.executeAutomatedOpeningSession(cp);
               executed = true;
           }
           else if(escenario.trim().equalsIgnoreCase("validacion de usuario director museo"))
           {
                 LOGGER.info("Ejecutando prueba automatizada de director de museo");
                 OpenSessionExecution openSessionExecution = new OpenSessionExecution();
                 result = openSessionExecution.executeAutomatedOpeningSession(cp);  
                 executed = true;
           }
           else if(escenario.trim().equalsIgnoreCase("validacion de registro de obra"))
           {
               LOGGER.info("Ejecutando prueba automatizada de registro de obra");
               OpenSessionExecution openSessionExecution = new OpenSessionExecution();
               result = openSessionExecution.executeAutomatedOpeningSession(cp); 
               executed = true;
           }
           else if(escenario.trim().equalsIgnoreCase("validacion de consulta de obras"))
           {
               LOGGER.info("Ejecutando prueba automatizada de consulta de obras");
               OpenSessionExecution openSessionExecution = new OpenSessionExecution();
               result = openSessionExecution.executeAutomatedOpeningSession(cp);
               executed = true;
           }
           
           if(!executed)
               resultStr = Estados.IMPEDIDO.toString().toLowerCase();
           else           
               resultStr = establishStateResult(result);     
           
            casoPrueba.setCodigoCaso(cp);
            casoPrueba.setResultado(resultStr);
            casoPrueba.setNombre(mapCasosPrueba.get(cp));
            cpsList.add(casoPrueba);
        }
        
        return cpsList;
    }
    
    private String establishStateResult(boolean result)
    {
             String resultState;
             
             if(result)
                 resultState = Estados.EXITOSO.toString().toLowerCase();
             else 
                 resultState = Estados.FALLIDO.toString().toLowerCase();
              
             return resultState;
    }
    
    
    public List<CasosPruebas> resultTestCasesDummy() 
    {
           List<CasosPruebas> cpsList = new ArrayList();
           CasosPruebas cp = new CasosPruebas();
           cp.setCodigoCaso("CP_01");
           cp.setResultado("exitoso");
           
           cpsList.add(cp);
           
           return cpsList;
    }
}
