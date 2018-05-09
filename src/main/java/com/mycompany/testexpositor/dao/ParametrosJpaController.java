/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testexpositor.dao;

import com.mycompany.testexpositor.dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.testexpositor.entities.CasosPruebas;
import com.mycompany.testexpositor.entities.Parametros;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author simon.rua
 */
public class ParametrosJpaController extends SuperiorJpaController implements Serializable {

    public ParametrosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public ParametrosJpaController() {
        super();
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Parametros parametros) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CasosPruebas casoPruebacodigo = parametros.getCasoPruebacodigo();
            if (casoPruebacodigo != null) {
                casoPruebacodigo = em.getReference(casoPruebacodigo.getClass(), casoPruebacodigo.getCodigoCaso());
                parametros.setCasoPruebacodigo(casoPruebacodigo);
            }
            em.persist(parametros);
            if (casoPruebacodigo != null) {
                casoPruebacodigo.getParametrosList().add(parametros);
                casoPruebacodigo = em.merge(casoPruebacodigo);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Parametros parametros) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Parametros persistentParametros = em.find(Parametros.class, parametros.getConsecutivo());
            CasosPruebas casoPruebacodigoOld = persistentParametros.getCasoPruebacodigo();
            CasosPruebas casoPruebacodigoNew = parametros.getCasoPruebacodigo();
            if (casoPruebacodigoNew != null) {
                casoPruebacodigoNew = em.getReference(casoPruebacodigoNew.getClass(), casoPruebacodigoNew.getCodigoCaso());
                parametros.setCasoPruebacodigo(casoPruebacodigoNew);
            }
            parametros = em.merge(parametros);
            if (casoPruebacodigoOld != null && !casoPruebacodigoOld.equals(casoPruebacodigoNew)) {
                casoPruebacodigoOld.getParametrosList().remove(parametros);
                casoPruebacodigoOld = em.merge(casoPruebacodigoOld);
            }
            if (casoPruebacodigoNew != null && !casoPruebacodigoNew.equals(casoPruebacodigoOld)) {
                casoPruebacodigoNew.getParametrosList().add(parametros);
                casoPruebacodigoNew = em.merge(casoPruebacodigoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = parametros.getConsecutivo();
                if (findParametros(id) == null) {
                    throw new NonexistentEntityException("The parametros with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Parametros parametros;
            try {
                parametros = em.getReference(Parametros.class, id);
                parametros.getConsecutivo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The parametros with id " + id + " no longer exists.", enfe);
            }
            CasosPruebas casoPruebacodigo = parametros.getCasoPruebacodigo();
            if (casoPruebacodigo != null) {
                casoPruebacodigo.getParametrosList().remove(parametros);
                casoPruebacodigo = em.merge(casoPruebacodigo);
            }
            em.remove(parametros);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Parametros> findParametrosEntities() {
        return findParametrosEntities(true, -1, -1);
    }

    public List<Parametros> findParametrosEntities(int maxResults, int firstResult) {
        return findParametrosEntities(false, maxResults, firstResult);
    }

    private List<Parametros> findParametrosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Parametros.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Parametros findParametros(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Parametros.class, id);
        } finally {
            em.close();
        }
    }

    public int getParametrosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Parametros> rt = cq.from(Parametros.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<Parametros> findParametrosByCasoPrueba(CasosPruebas cp)
    {
           EntityManager em = getEntityManager();
           Query query = em.createQuery("SELECT p FROM Parametros p WHERE p.casoPruebacodigo = :casoPruebacodigo");           

           query.setParameter("casoPruebacodigo",cp);
           
           List parametrosList = query.getResultList();
           
           return parametrosList;
    }
    
}
