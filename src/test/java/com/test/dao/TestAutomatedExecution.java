/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.dao;

import com.museumapp.ScenariosProcess.OpenSession;
import com.museumapp.ScenariosProcess.OpenSessionExecution;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
/**
 *
 * @author ACER
 */
public class TestAutomatedExecution 
{
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    OpenSessionExecution openSessionExeution;
     
    @Before
    public void initWebDriver()
    {
         openSessionExeution = new OpenSessionExecution();
    }
    
     @Test
     public void testAutomationOpenSession() 
     {
            boolean result = openSessionExeution.executeAutomatedOpeningSession("CP_01");
            
            assertEquals(result, true);
     }
}