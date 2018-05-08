/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.dao;

import com.mycompany.testexpositor.services.ProyectosService;
import java.util.List;
import org.json.simple.JSONObject;
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
//@Ignore
public class ProyectosServiceTest {
    
    private ProyectosService proyServ;
    
    public ProyectosServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
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

     @Test
     public void testSearchAllProjectsByUser() {
             
        proyServ = new ProyectosService();
        JSONObject usuario = new JSONObject();
        usuario.put("username","simon");
        
        List proyList = proyServ.searchAllProjectsByUser(usuario);
        
         assertEquals(true, !proyList.isEmpty());
     }
} 
