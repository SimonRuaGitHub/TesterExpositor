/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testexpositor.services;

import com.mycompany.testexpositor.contract.UsuarioContract;
import com.mycompany.testexpositor.dao.UsuariosJpaController;
import com.mycompany.testexpositor.entities.Usuarios;
import java.util.List;

/**
 *
 * @author simon.rua
 */
public class UsuarioServices implements UsuarioContract
{
    UsuariosJpaController usuariosDAO;
    
    public String dummy() 
    {
           return "conexion BackEnd exitosa";
    }
   
    public List<Usuarios> getAllUsuarios()
    {
           usuariosDAO = new UsuariosJpaController();
           return usuariosDAO.findUsuariosEntities();
    }
}
