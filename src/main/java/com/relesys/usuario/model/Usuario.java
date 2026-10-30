package com.relesys.usuario.model;

import com.relesys.organizacion.model.Sector;
import com.relesys.organizacion.model.Ubicacion;
import com.relesys.organizacion.model.Empresa;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String numero;
    private String interno;
    private String nombre;
    private String apellido;

    @ManyToOne
    @JoinColumn(name = "ubicacion_id")
    private Ubicacion ubicacion;

    @ManyToOne
    @JoinColumn(name = "sector_id")
    private Sector sector;

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

    public Usuario() {
    }

    public Integer getId() {
        return id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getInterno() {
        return interno;
    }

    public void setInterno(String interno) {
        this.interno = interno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Sector getSector() {
        return sector;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }

    public String getSectorNombre() {
        return sector != null ? sector.getNombre() : "";
    }

    public String getUbicacionDireccion() {
        return ubicacion != null ? ubicacion.getDireccion() : "";
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public String getEmpresaNombre() {
        return empresa != null ? empresa.getNombre() : "";
    }
}
