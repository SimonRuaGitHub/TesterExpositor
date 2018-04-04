/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testexpositor.services;

import com.museumapp.ScenariosProcess.OpenSession;
import com.mycompany.testexpositor.contract.UsuarioContract;
import com.mycompany.testexpositor.dao.UsuariosJpaController;
import com.mycompany.testexpositor.entities.Usuarios;
import java.util.List;
import java.util.logging.Logger;
import org.json.simple.JSONObject;

/**
 *
 * @author simon.rua
 */
public class UsuarioServices implements UsuarioContract
{
    private static final Logger LOGGER = Logger.getLogger(UsuarioServices.class.getName());
    
    private UsuariosJpaController usuariosDAO;
    
    public String dummy() 
    {
           return "conexion BackEnd exitosa";
    }
   
    public List<Usuarios> getAllUsuarios()
    {
           usuariosDAO = new UsuariosJpaController();
           return usuariosDAO.findUsuariosEntities();
    }

    public String verifyUserExist(String user, String pass) 
    {
           int result;
           
           usuariosDAO = new UsuariosJpaController();
           
           LOGGER.info("credenciales: "+user+" ,"+pass);
           
           int size = usuariosDAO.verifyUserExistByCredentials(user, pass);

           if(size == 1)
           {
               result = size;
           }            
           else 
           {
               result = 2;
           }
           
           LOGGER.info("Verificacion de usuario: "+result);
           
           return String.valueOf(result);
    }

    public String verifyUserExist(JSONObject credentials) 
    {     
           JSONObject obj = new JSONObject();

           LOGGER.info("Usuario ingresado: "+credentials.get("username"));
           LOGGER.info("Contraseña ingresada: "+credentials.get("password"));
           
           return "1";
    }
    
    
}
