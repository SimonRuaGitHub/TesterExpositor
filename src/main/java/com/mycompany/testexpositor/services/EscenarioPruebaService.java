/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testexpositor.services;

import com.mycompany.testexpositor.contract.EscenarioPruebaContract;
/**
 *
 * @author simon.rua
 */
public class EscenarioPruebaService implements EscenarioPruebaContract 
{
    public String dummy() {
        return "Conexion a backend pruebas exitosa"; //To change body of generated methods, choose Tools | Templates.
    }

    public String testLogginSession(String casoPrueba) 
    {
          boolean result = true;
                  
          if(result == true)
              return "exitoso";
          else 
              return "fallido";
    }
    
}
