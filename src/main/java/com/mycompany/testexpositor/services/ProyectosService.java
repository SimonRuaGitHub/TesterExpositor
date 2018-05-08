/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testexpositor.services;

import com.mycompany.testexpositor.contract.ProyectosContract;
import com.mycompany.testexpositor.dao.UsuariosJpaController;
import com.mycompany.testexpositor.dao.ProyectosJpaController;
import com.mycompany.testexpositor.entities.Proyectos;
import com.mycompany.testexpositor.entities.Usuarios;
import java.util.List;
import java.util.logging.Logger;
import org.json.simple.JSONObject;

/**
 *
 * @author simon.rua
 */
public class ProyectosService implements ProyectosContract
{
       private static final Logger LOGGER = Logger.getLogger(ProyectosService.class.getName());
       private ProyectosJpaController proDao;
       private UsuariosJpaController usDao;

    public List<Proyectos> searchAllProjectsByUser(JSONObject usuario) 
    {
          LOGGER.info("Consultando proyectos para usuario: "+usuario);
          
          String username = (String) usuario.get("username");
          
          LOGGER.info("Verificando existencia usuario");
          usDao = new UsuariosJpaController();         
          Usuarios usuarioObj = usDao.searchUserByCredentials(username);
          
          LOGGER.info("Consultando proyectos para usuario");
          proDao = new ProyectosJpaController();
          List<Proyectos> proyectosList = proDao.searchProjectsByUser(usuarioObj);
          
          return proyectosList;  
    }
       
      
}
