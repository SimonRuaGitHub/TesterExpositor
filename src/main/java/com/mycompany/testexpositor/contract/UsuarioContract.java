/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testexpositor.contract;

import com.mycompany.testexpositor.entities.Usuarios;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author simon.rua
 */
@Path("expositor")
public interface UsuarioContract 
{
     @Path("/dummy")
     @GET
     @Produces(MediaType.TEXT_PLAIN)
     public String dummy();
     
    /**
     *
     * @return 
     */
     @Path("/allUsers")
     @GET
     @Produces(MediaType.APPLICATION_JSON)
     public List<Usuarios> getAllUsuarios();
     
     /**
      * 
     * @param username
     * @param password
      * @return flag
      */
     @Path("/verifyUser")
     @POST
     @Produces(MediaType.TEXT_PLAIN)
     public String verifyUserExist(@QueryParam("user")String username, @QueryParam("pass")String password);
}
