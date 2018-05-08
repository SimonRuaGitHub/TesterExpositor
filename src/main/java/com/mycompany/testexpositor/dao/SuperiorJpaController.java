/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testexpositor.dao;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author simon.rua
 */
public class SuperiorJpaController 
{    
    protected final static String PERSISTANCE_UNIT = "DB_REMOTESTER";
    protected EntityManagerFactory emf = null;
    
    public SuperiorJpaController()
    {
        this.emf = Persistence.createEntityManagerFactory(PERSISTANCE_UNIT);
    }
}
