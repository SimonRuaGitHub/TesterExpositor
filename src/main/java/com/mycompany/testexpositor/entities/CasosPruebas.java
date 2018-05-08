/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testexpositor.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author simon.rua
 */
@Entity
@Table(name = "casos_pruebas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CasosPruebas.findAll", query = "SELECT c FROM CasosPruebas c"),
    @NamedQuery(name = "CasosPruebas.findByCodigoCaso", query = "SELECT c FROM CasosPruebas c WHERE c.codigoCaso = :codigoCaso"),
    @NamedQuery(name = "CasosPruebas.findByNombre", query = "SELECT c FROM CasosPruebas c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "CasosPruebas.findByDescripcion", query = "SELECT c FROM CasosPruebas c WHERE c.descripcion = :descripcion"),
    @NamedQuery(name = "CasosPruebas.findByResultado", query = "SELECT c FROM CasosPruebas c WHERE c.resultado = :resultado")})
public class CasosPruebas implements Serializable {

    @Basic(optional = false)
    @Column(name = "escenario")
    private String escenario;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "casoPruebacodigo")
    private List<Parametros> parametrosList;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "codigoCaso")
    private String codigoCaso;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "resultado")
    private String resultado;
    @JoinColumn(name = "cod_proyecto", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private Proyectos codProyecto;
    
    

    public CasosPruebas() {
    }

    public CasosPruebas(String codigoCaso) {
        this.codigoCaso = codigoCaso;
    }

    public CasosPruebas(String codigoCaso, String nombre, String descripcion) {
        this.codigoCaso = codigoCaso;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getCodigoCaso() {
        return codigoCaso;
    }

    public void setCodigoCaso(String codigoCaso) {
        this.codigoCaso = codigoCaso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public Proyectos getCodProyecto() {
        return codProyecto;
    }

    public void setCodProyecto(Proyectos codProyecto) {
        this.codProyecto = codProyecto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoCaso != null ? codigoCaso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CasosPruebas)) {
            return false;
        }
        CasosPruebas other = (CasosPruebas) object;
        if ((this.codigoCaso == null && other.codigoCaso != null) || (this.codigoCaso != null && !this.codigoCaso.equals(other.codigoCaso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.testexpositor.entities.CasosPruebas[ codigoCaso=" + codigoCaso + " ]";
    }

    @XmlTransient
    @JsonIgnore
    public List<Parametros> getParametrosList() {
        return parametrosList;
    }

    public void setParametrosList(List<Parametros> parametrosList) {
        this.parametrosList = parametrosList;
    }

    public String getEscenario() {
        return escenario;
    }

    public void setEscenario(String escenario) {
        this.escenario = escenario;
    }
    
}
