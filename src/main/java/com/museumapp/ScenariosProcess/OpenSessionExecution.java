/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.museumapp.ScenariosProcess;

/**
 *
 * @author ACER
 */
public class OpenSessionExecution 
{
       private OpenSession openSessionProcess;
     
       public OpenSessionExecution()
       {
              openSessionProcess = new OpenSession();
       }
       
       public boolean executeAutomatedOpeningSession(String testCase)
       {
              openSessionProcess.settingWebDriver();
              boolean result = openSessionProcess.loggingUserSessionBasicFlow(testCase,"ramirezGC","123654");
              openSessionProcess.closingWebDriver();
              
              return result;
       }
}
