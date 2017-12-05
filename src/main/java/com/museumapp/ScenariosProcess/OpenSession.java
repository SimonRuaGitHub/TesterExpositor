/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.museumapp.ScenariosProcess;

import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
/**
 *
 * @author ACER
 */
public class OpenSession extends TestBase
{
       private static final Logger LOGGER = Logger.getLogger(OpenSession.class.getName());
       
       public OpenSession()
       {
            super();
       }
             
       public boolean loggingUserSessionBasicFlow(String codeTestCase, String username , String password) 
       {
              try
              {
                  LOGGER.info("Ejecutando caso prueba: "+codeTestCase);
                  driver.get("http://www.museumlucky.epizy.com");
                  driver.findElement(By.id("txtNombreUsuario")).sendKeys(username);
                  driver.findElement(By.id("pswContrasena")).sendKeys(password);
                  driver.findElement(By.id("btnRegistrar")).click();
                  new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.id("btnCerrarSesion")));
                  LOGGER.info("Flujo basico ejecutado exitosamente");
                  return true;
              }
              catch(Exception ex)
              {
                    LOGGER.info("Flujo basico ejecutado con errores"); 
                    LOGGER.warning(ex.getMessage());
                    return false;
              }    
       }
}
