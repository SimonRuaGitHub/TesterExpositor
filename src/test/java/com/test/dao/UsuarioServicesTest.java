/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.dao;

import com.mycompany.testexpositor.contract.UsuarioContract;
import com.mycompany.testexpositor.services.UsuarioServices;
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
//s@Ignore
public class UsuarioServicesTest {
    
    private static UsuarioContract usuarioServices;
    
    @BeforeClass
    public static void setUpClass() 
    {
        usuarioServices = new UsuarioServices();
    }
    
    @AfterClass
    public static void tearDownClass() 
    {
        
    }
    
     @Test
     public void TestVerifyUserService() 
     {
         usuarioServices.verifyUserExist("simon","1234");
     }
}
