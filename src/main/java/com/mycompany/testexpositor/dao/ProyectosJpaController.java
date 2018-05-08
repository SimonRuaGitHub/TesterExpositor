/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testexpositor.dao;

import com.mycompany.testexpositor.dao.exceptions.IllegalOrphanException;
import com.mycompany.testexpositor.dao.exceptions.NonexistentEntityException;
import com.mycompany.testexpositor.dao.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.testexpositor.entities.Usuarios;
import com.mycompany.testexpositor.entities.CasosPruebas;
import com.mycompany.testexpositor.entities.Proyectos;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author simon.rua
 */
public class ProyectosJpaController extends SuperiorJpaController implements Serializable {
    
    public ProyectosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public ProyectosJpaController()
    {
           super();
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Proyectos proyectos) throws PreexistingEntityException, Exception {
        if (proyectos.getCasosPruebasList() == null) {
            proyectos.setCasosPruebasList(new ArrayList<CasosPruebas>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios codUsuario = proyectos.getCodUsuario();
            if (codUsuario != null) {
                codUsuario = em.getReference(codUsuario.getClass(), codUsuario.getCodigo());
                proyectos.setCodUsuario(codUsuario);
            }
            List<CasosPruebas> attachedCasosPruebasList = new ArrayList<CasosPruebas>();
            for (CasosPruebas casosPruebasListCasosPruebasToAttach : proyectos.getCasosPruebasList()) {
                casosPruebasListCasosPruebasToAttach = em.getReference(casosPruebasListCasosPruebasToAttach.getClass(), casosPruebasListCasosPruebasToAttach.getCodigoCaso());
                attachedCasosPruebasList.add(casosPruebasListCasosPruebasToAttach);
            }
            proyectos.setCasosPruebasList(attachedCasosPruebasList);
            em.persist(proyectos);
            if (codUsuario != null) {
                codUsuario.getProyectosList().add(proyectos);
                codUsuario = em.merge(codUsuario);
            }
            for (CasosPruebas casosPruebasListCasosPruebas : proyectos.getCasosPruebasList()) {
                Proyectos oldCodProyectoOfCasosPruebasListCasosPruebas = casosPruebasListCasosPruebas.getCodProyecto();
                casosPruebasListCasosPruebas.setCodProyecto(proyectos);
                casosPruebasListCasosPruebas = em.merge(casosPruebasListCasosPruebas);
                if (oldCodProyectoOfCasosPruebasListCasosPruebas != null) {
                    oldCodProyectoOfCasosPruebasListCasosPruebas.getCasosPruebasList().remove(casosPruebasListCasosPruebas);
                    oldCodProyectoOfCasosPruebasListCasosPruebas = em.merge(oldCodProyectoOfCasosPruebasListCasosPruebas);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProyectos(proyectos.getCodigo()) != null) {
                throw new PreexistingEntityException("Proyectos " + proyectos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Proyectos proyectos) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proyectos persistentProyectos = em.find(Proyectos.class, proyectos.getCodigo());
            Usuarios codUsuarioOld = persistentProyectos.getCodUsuario();
            Usuarios codUsuarioNew = proyectos.getCodUsuario();
            List<CasosPruebas> casosPruebasListOld = persistentProyectos.getCasosPruebasList();
            List<CasosPruebas> casosPruebasListNew = proyectos.getCasosPruebasList();
            List<String> illegalOrphanMessages = null;
            for (CasosPruebas casosPruebasListOldCasosPruebas : casosPruebasListOld) {
                if (!casosPruebasListNew.contains(casosPruebasListOldCasosPruebas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CasosPruebas " + casosPruebasListOldCasosPruebas + " since its codProyecto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (codUsuarioNew != null) {
                codUsuarioNew = em.getReference(codUsuarioNew.getClass(), codUsuarioNew.getCodigo());
                proyectos.setCodUsuario(codUsuarioNew);
            }
            List<CasosPruebas> attachedCasosPruebasListNew = new ArrayList<CasosPruebas>();
            for (CasosPruebas casosPruebasListNewCasosPruebasToAttach : casosPruebasListNew) {
                casosPruebasListNewCasosPruebasToAttach = em.getReference(casosPruebasListNewCasosPruebasToAttach.getClass(), casosPruebasListNewCasosPruebasToAttach.getCodigoCaso());
                attachedCasosPruebasListNew.add(casosPruebasListNewCasosPruebasToAttach);
            }
            casosPruebasListNew = attachedCasosPruebasListNew;
            proyectos.setCasosPruebasList(casosPruebasListNew);
            proyectos = em.merge(proyectos);
            if (codUsuarioOld != null && !codUsuarioOld.equals(codUsuarioNew)) {
                codUsuarioOld.getProyectosList().remove(proyectos);
                codUsuarioOld = em.merge(codUsuarioOld);
            }
            if (codUsuarioNew != null && !codUsuarioNew.equals(codUsuarioOld)) {
                codUsuarioNew.getProyectosList().add(proyectos);
                codUsuarioNew = em.merge(codUsuarioNew);
            }
            for (CasosPruebas casosPruebasListNewCasosPruebas : casosPruebasListNew) {
                if (!casosPruebasListOld.contains(casosPruebasListNewCasosPruebas)) {
                    Proyectos oldCodProyectoOfCasosPruebasListNewCasosPruebas = casosPruebasListNewCasosPruebas.getCodProyecto();
                    casosPruebasListNewCasosPruebas.setCodProyecto(proyectos);
                    casosPruebasListNewCasosPruebas = em.merge(casosPruebasListNewCasosPruebas);
                    if (oldCodProyectoOfCasosPruebasListNewCasosPruebas != null && !oldCodProyectoOfCasosPruebasListNewCasosPruebas.equals(proyectos)) {
                        oldCodProyectoOfCasosPruebasListNewCasosPruebas.getCasosPruebasList().remove(casosPruebasListNewCasosPruebas);
                        oldCodProyectoOfCasosPruebasListNewCasosPruebas = em.merge(oldCodProyectoOfCasosPruebasListNewCasosPruebas);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = proyectos.getCodigo();
                if (findProyectos(id) == null) {
                    throw new NonexistentEntityException("The proyectos with id " + id + " no longer exists.");
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
            Proyectos proyectos;
            try {
                proyectos = em.getReference(Proyectos.class, id);
                proyectos.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The proyectos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<CasosPruebas> casosPruebasListOrphanCheck = proyectos.getCasosPruebasList();
            for (CasosPruebas casosPruebasListOrphanCheckCasosPruebas : casosPruebasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Proyectos (" + proyectos + ") cannot be destroyed since the CasosPruebas " + casosPruebasListOrphanCheckCasosPruebas + " in its casosPruebasList field has a non-nullable codProyecto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuarios codUsuario = proyectos.getCodUsuario();
            if (codUsuario != null) {
                codUsuario.getProyectosList().remove(proyectos);
                codUsuario = em.merge(codUsuario);
            }
            em.remove(proyectos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Proyectos> findProyectosEntities() {
        return findProyectosEntities(true, -1, -1);
    }

    public List<Proyectos> findProyectosEntities(int maxResults, int firstResult) {
        return findProyectosEntities(false, maxResults, firstResult);
    }

    private List<Proyectos> findProyectosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Proyectos.class));
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

    public Proyectos findProyectos(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Proyectos.class, id);
        } finally {
            em.close();
        }
    }

    public int getProyectosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Proyectos> rt = cq.from(Proyectos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<Proyectos> searchProjectsByUser(Usuarios usuarioObj) 
    {
         EntityManager em = getEntityManager();
         Query query = em.createQuery("SELECT p FROM Proyectos p WHERE p.codUsuario = :codUsuario");
         query.setParameter("codUsuario",usuarioObj);
         
         List<Proyectos> proyectosList = query.getResultList();

         return proyectosList;
    }
    
}
