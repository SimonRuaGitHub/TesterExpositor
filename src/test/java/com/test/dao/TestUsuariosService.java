/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.dao;

import com.mycompany.testexpositor.entities.Usuarios;
import com.mycompany.testexpositor.services.UsuarioServices;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author simon.rua
 */
public class TestUsuariosService {
    
    public TestUsuariosService() {
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

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    //@Test
    public void testGetAllUsuarios() 
    {
        UsuarioServices usuarioServ = new UsuarioServices();
        List<Usuarios> usuariosList = usuarioServ.getAllUsuarios();
                
        System.out.println(usuariosList.size());
    }
}
