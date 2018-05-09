/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testexpositor.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author simon.rua
 */
@Entity
@Table(name = "parametros")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Parametros.findAll", query = "SELECT p FROM Parametros p"),
    @NamedQuery(name = "Parametros.findByNombre", query = "SELECT p FROM Parametros p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Parametros.findByValor", query = "SELECT p FROM Parametros p WHERE p.valor = :valor")})
public class Parametros implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "consecutivo")
    private Integer consecutivo;

    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "valor")
    private String valor;
    @JoinColumn(name = "casoPrueba_codigo", referencedColumnName = "codigoCaso")
    @ManyToOne(optional = false)
    private CasosPruebas casoPruebacodigo;

    public Parametros() {
    }

    public Parametros(String nombre) {
        this.nombre = nombre;
    }

    public Parametros(String nombre, String valor) {
        this.nombre = nombre;
        this.valor = valor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public CasosPruebas getCasoPruebacodigo() {
        return casoPruebacodigo;
    }

    public void setCasoPruebacodigo(CasosPruebas casoPruebacodigo) {
        this.casoPruebacodigo = casoPruebacodigo;
    }

    public Parametros(Integer consecutivo) {
        this.consecutivo = consecutivo;
    }

    public Integer getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(Integer consecutivo) {
        this.consecutivo = consecutivo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (consecutivo != null ? consecutivo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Parametros)) {
            return false;
        }
        Parametros other = (Parametros) object;
        if ((this.consecutivo == null && other.consecutivo != null) || (this.consecutivo != null && !this.consecutivo.equals(other.consecutivo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.testexpositor.entities.Parametros[ consecutivo=" + consecutivo + " ]";
    }
    
}
