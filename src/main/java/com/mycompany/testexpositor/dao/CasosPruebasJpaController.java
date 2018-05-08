/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testexpositor.dao;

import com.mycompany.testexpositor.dao.exceptions.IllegalOrphanException;
import com.mycompany.testexpositor.dao.exceptions.NonexistentEntityException;
import com.mycompany.testexpositor.dao.exceptions.PreexistingEntityException;
import com.mycompany.testexpositor.entities.CasosPruebas;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.testexpositor.entities.Proyectos;
import com.mycompany.testexpositor.entities.Parametros;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author simon.rua
 */
public class CasosPruebasJpaController extends SuperiorJpaController implements Serializable {

    public CasosPruebasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public CasosPruebasJpaController() {
        super();
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CasosPruebas casosPruebas) throws PreexistingEntityException, Exception {
        if (casosPruebas.getParametrosList() == null) {
            casosPruebas.setParametrosList(new ArrayList<Parametros>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proyectos codProyecto = casosPruebas.getCodProyecto();
            if (codProyecto != null) {
                codProyecto = em.getReference(codProyecto.getClass(), codProyecto.getCodigo());
                casosPruebas.setCodProyecto(codProyecto);
            }
            List<Parametros> attachedParametrosList = new ArrayList<Parametros>();
            for (Parametros parametrosListParametrosToAttach : casosPruebas.getParametrosList()) {
                parametrosListParametrosToAttach = em.getReference(parametrosListParametrosToAttach.getClass(), parametrosListParametrosToAttach.getNombre());
                attachedParametrosList.add(parametrosListParametrosToAttach);
            }
            casosPruebas.setParametrosList(attachedParametrosList);
            em.persist(casosPruebas);
            if (codProyecto != null) {
                codProyecto.getCasosPruebasList().add(casosPruebas);
                codProyecto = em.merge(codProyecto);
            }
            for (Parametros parametrosListParametros : casosPruebas.getParametrosList()) {
                CasosPruebas oldCasoPruebacodigoOfParametrosListParametros = parametrosListParametros.getCasoPruebacodigo();
                parametrosListParametros.setCasoPruebacodigo(casosPruebas);
                parametrosListParametros = em.merge(parametrosListParametros);
                if (oldCasoPruebacodigoOfParametrosListParametros != null) {
                    oldCasoPruebacodigoOfParametrosListParametros.getParametrosList().remove(parametrosListParametros);
                    oldCasoPruebacodigoOfParametrosListParametros = em.merge(oldCasoPruebacodigoOfParametrosListParametros);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCasosPruebas(casosPruebas.getCodigoCaso()) != null) {
                throw new PreexistingEntityException("CasosPruebas " + casosPruebas + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CasosPruebas casosPruebas) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CasosPruebas persistentCasosPruebas = em.find(CasosPruebas.class, casosPruebas.getCodigoCaso());
            Proyectos codProyectoOld = persistentCasosPruebas.getCodProyecto();
            Proyectos codProyectoNew = casosPruebas.getCodProyecto();
            List<Parametros> parametrosListOld = persistentCasosPruebas.getParametrosList();
            List<Parametros> parametrosListNew = casosPruebas.getParametrosList();
            List<String> illegalOrphanMessages = null;
            for (Parametros parametrosListOldParametros : parametrosListOld) {
                if (!parametrosListNew.contains(parametrosListOldParametros)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Parametros " + parametrosListOldParametros + " since its casoPruebacodigo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (codProyectoNew != null) {
                codProyectoNew = em.getReference(codProyectoNew.getClass(), codProyectoNew.getCodigo());
                casosPruebas.setCodProyecto(codProyectoNew);
            }
            List<Parametros> attachedParametrosListNew = new ArrayList<Parametros>();
            for (Parametros parametrosListNewParametrosToAttach : parametrosListNew) {
                parametrosListNewParametrosToAttach = em.getReference(parametrosListNewParametrosToAttach.getClass(), parametrosListNewParametrosToAttach.getNombre());
                attachedParametrosListNew.add(parametrosListNewParametrosToAttach);
            }
            parametrosListNew = attachedParametrosListNew;
            casosPruebas.setParametrosList(parametrosListNew);
            casosPruebas = em.merge(casosPruebas);
            if (codProyectoOld != null && !codProyectoOld.equals(codProyectoNew)) {
                codProyectoOld.getCasosPruebasList().remove(casosPruebas);
                codProyectoOld = em.merge(codProyectoOld);
            }
            if (codProyectoNew != null && !codProyectoNew.equals(codProyectoOld)) {
                codProyectoNew.getCasosPruebasList().add(casosPruebas);
                codProyectoNew = em.merge(codProyectoNew);
            }
            for (Parametros parametrosListNewParametros : parametrosListNew) {
                if (!parametrosListOld.contains(parametrosListNewParametros)) {
                    CasosPruebas oldCasoPruebacodigoOfParametrosListNewParametros = parametrosListNewParametros.getCasoPruebacodigo();
                    parametrosListNewParametros.setCasoPruebacodigo(casosPruebas);
                    parametrosListNewParametros = em.merge(parametrosListNewParametros);
                    if (oldCasoPruebacodigoOfParametrosListNewParametros != null && !oldCasoPruebacodigoOfParametrosListNewParametros.equals(casosPruebas)) {
                        oldCasoPruebacodigoOfParametrosListNewParametros.getParametrosList().remove(parametrosListNewParametros);
                        oldCasoPruebacodigoOfParametrosListNewParametros = em.merge(oldCasoPruebacodigoOfParametrosListNewParametros);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = casosPruebas.getCodigoCaso();
                if (findCasosPruebas(id) == null) {
                    throw new NonexistentEntityException("The casosPruebas with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CasosPruebas casosPruebas;
            try {
                casosPruebas = em.getReference(CasosPruebas.class, id);
                casosPruebas.getCodigoCaso();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The casosPruebas with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Parametros> parametrosListOrphanCheck = casosPruebas.getParametrosList();
            for (Parametros parametrosListOrphanCheckParametros : parametrosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CasosPruebas (" + casosPruebas + ") cannot be destroyed since the Parametros " + parametrosListOrphanCheckParametros + " in its parametrosList field has a non-nullable casoPruebacodigo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Proyectos codProyecto = casosPruebas.getCodProyecto();
            if (codProyecto != null) {
                codProyecto.getCasosPruebasList().remove(casosPruebas);
                codProyecto = em.merge(codProyecto);
            }
            em.remove(casosPruebas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CasosPruebas> findCasosPruebasEntities() {
        return findCasosPruebasEntities(true, -1, -1);
    }

    public List<CasosPruebas> findCasosPruebasEntities(int maxResults, int firstResult) {
        return findCasosPruebasEntities(false, maxResults, firstResult);
    }

    private List<CasosPruebas> findCasosPruebasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CasosPruebas.class));
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

    public List<CasosPruebas> findCasosPruebasByProyecto(Proyectos proyecto)
    {
           EntityManager em = getEntityManager();
           Query query = em.createQuery("SELECT c FROM CasosPruebas c WHERE c.codProyecto = :codProyecto");           

           query.setParameter("codProyecto",proyecto);
           
           List<CasosPruebas> casosPruebaList = query.getResultList();
           
           return casosPruebaList;
    }

    public CasosPruebas findCasosPruebas(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CasosPruebas.class, id);
        } finally {
            em.close();
        }
    }

    public int getCasosPruebasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CasosPruebas> rt = cq.from(CasosPruebas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
