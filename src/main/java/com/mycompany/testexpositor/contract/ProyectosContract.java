/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testexpositor.contract;

import com.mycompany.testexpositor.entities.Proyectos;
import java.util.List;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.json.simple.JSONObject;

/**
 *
 * @author simon.rua
 */
@Path("expositorProjects")
public interface ProyectosContract 
{
       @Path("/allProjectsByUser")
       @POST
       @Produces(MediaType.APPLICATION_JSON)
       public List<Proyectos> searchAllProjectsByUser(JSONObject usuario);
}
