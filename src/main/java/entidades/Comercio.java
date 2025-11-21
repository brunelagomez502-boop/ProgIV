/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import entidades.Bonosemitidos;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author roble
 */
@Entity
@Table(name = "comercios", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"mail"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Comercio.findAll", query = "SELECT c FROM Comercio c"),
    @NamedQuery(name = "Comercio.findByIdComercios", query = "SELECT c FROM Comercio c WHERE c.idComercios = :idComercios"),
    @NamedQuery(name = "Comercio.findByNombre", query = "SELECT c FROM Comercio c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "Comercio.findByDireccion", query = "SELECT c FROM Comercio c WHERE c.direccion = :direccion"),
    @NamedQuery(name = "Comercio.findByResponsable", query = "SELECT c FROM Comercio c WHERE c.responsable = :responsable"),
    @NamedQuery(name = "Comercio.findByTelefono", query = "SELECT c FROM Comercio c WHERE c.telefono = :telefono"),
    @NamedQuery(name = "Comercio.findByMail", query = "SELECT c FROM Comercio c WHERE c.mail = :mail"),
    @NamedQuery(name = "Comercio.findByDescuento", query = "SELECT c FROM Comercio c WHERE c.descuento = :descuento"),
    @NamedQuery(name = "Comercio.findByFecIniAct", query = "SELECT c FROM Comercio c WHERE c.fecIniAct = :fecIniAct"),
    @NamedQuery(name = "Comercio.findByEstadoCom", query = "SELECT c FROM Comercio c WHERE c.estadoCom = :estadoCom")})
public class Comercio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer idComercios;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false, length = 255)
    private String nombre;
    @Size(max = 255)
    @Column(length = 255)
    private String direccion;
    @Size(max = 255)
    @Column(length = 255)
    private String responsable;
    @Size(max = 20)
    @Column(length = 20)
    private String telefono;
    @Size(max = 255)
    @Column(length = 255)
    private String mail;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private int descuento;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fecIniAct;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private boolean estadoCom=true;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idComercios")
    private List<Bonosemitidos> bonoemitidoList;

    public Comercio() {
    }

    public Comercio(Integer idComercios) {
        this.idComercios = idComercios;
    }

    public Comercio(Integer idComercios, String nombre, int descuento, Date fecIniAct, boolean estadoCom) {
        this.idComercios = idComercios;
        this.nombre = nombre;
        this.descuento = descuento;
        this.fecIniAct = fecIniAct;
        this.estadoCom = estadoCom;
    }

    public Integer getIdComercios() {
        return idComercios;
    }

    public void setIdComercios(Integer idComercios) {
        this.idComercios = idComercios;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getDescuento() {
        return descuento;
    }

    public void setDescuento(int descuento) {
        this.descuento = descuento;
    }

    public Date getFecIniAct() {
        return fecIniAct;
    }

    public void setFecIniAct(Date fecIniAct) {
        this.fecIniAct = fecIniAct;
    }

    public boolean getEstadoCom() {
        return estadoCom;
    }

    public void setEstadoCom(boolean estadoCom) {
        this.estadoCom = estadoCom;
    }

    @XmlTransient
    public List<Bonosemitidos> getBonoemitidoList() {
        return bonoemitidoList;
    }

    public void setBonoemitidoList(List<Bonosemitidos> bonoemitidoList) {
        this.bonoemitidoList = bonoemitidoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idComercios != null ? idComercios.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comercio)) {
            return false;
        }
        Comercio other = (Comercio) object;
        if ((this.idComercios == null && other.idComercios != null) || (this.idComercios != null && !this.idComercios.equals(other.idComercios))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sistemamutual.Comercio[ idComercios=" + idComercios + " ]";
    }
    
}
