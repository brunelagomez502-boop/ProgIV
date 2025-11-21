/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

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
@Table(name = "afiliados", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"mail"}),
    @UniqueConstraint(columnNames = {"dni"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Afiliado.findAll", query = "SELECT a FROM Afiliado a"),
    @NamedQuery(name = "Afiliado.findByNroTarjeta", query = "SELECT a FROM Afiliado a WHERE a.nroTarjeta = :nroTarjeta"),
    @NamedQuery(name = "Afiliado.findByNombre", query = "SELECT a FROM Afiliado a WHERE a.nombre = :nombre"),
    @NamedQuery(name = "Afiliado.findByApellido", query = "SELECT a FROM Afiliado a WHERE a.apellido = :apellido"),
    @NamedQuery(name = "Afiliado.findByTelefono", query = "SELECT a FROM Afiliado a WHERE a.telefono = :telefono"),
    @NamedQuery(name = "Afiliado.findByDni", query = "SELECT a FROM Afiliado a WHERE a.dni = :dni"),
    @NamedQuery(name = "Afiliado.findByDomicilio", query = "SELECT a FROM Afiliado a WHERE a.domicilio = :domicilio"),
    @NamedQuery(name = "Afiliado.findBySexo", query = "SELECT a FROM Afiliado a WHERE a.sexo = :sexo"),
    @NamedQuery(name = "Afiliado.findByFecNac", query = "SELECT a FROM Afiliado a WHERE a.fecNac = :fecNac"),
    @NamedQuery(name = "Afiliado.findByFecIngMut", query = "SELECT a FROM Afiliado a WHERE a.fecIngMut = :fecIngMut"),
    @NamedQuery(name = "Afiliado.findByMail", query = "SELECT a FROM Afiliado a WHERE a.mail = :mail"),
    @NamedQuery(name = "Afiliado.findByEstadoAfi", query = "SELECT a FROM Afiliado a WHERE a.estadoAfi = :estadoAfi")})
public class Afiliado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer nroTarjeta;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false, length = 255)
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false, length = 255)
    private String apellido;
  
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false, length = 255)
    private String telefono;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private int dni;
    @Size(max = 255)
    @Column(length = 255)
    private String domicilio;
    private Character sexo;
    @Temporal(TemporalType.DATE)
    private Date fecNac;
    @Temporal(TemporalType.DATE)
    private Date fecIngMut;
    @Size(max = 255)
    @Column(length = 255)
    private String mail;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private boolean estadoAfi=true;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "nroTarjeta")
    private List<Bonosemitidos> bonoemitidoList;

    public Afiliado() {
    }

    public Afiliado(Integer nroTarjeta) {
        this.nroTarjeta = nroTarjeta;
    }

    public Afiliado(Integer nroTarjeta, String nombre, String apellido, int dni, boolean estadoAfi) {
        this.nroTarjeta = nroTarjeta;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.estadoAfi = estadoAfi;
    }

    public Integer getNroTarjeta() {
        return nroTarjeta;
    }

    public void setNroTarjeta(Integer nroTarjeta) {
        this.nroTarjeta = nroTarjeta;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public Character getSexo() {
        return sexo;
    }

    public void setSexo(Character sexo) {
        this.sexo = sexo;
    }

    public Date getFecNac() {
        return fecNac;
    }

    public void setFecNac(Date fecNac) {
        this.fecNac = fecNac;
    }

    public Date getFecIngMut() {
        return fecIngMut;
    }

    public void setFecIngMut(Date fecIngMut) {
        this.fecIngMut = fecIngMut;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public boolean getEstadoAfi() {
        return estadoAfi;
    }

    public void setEstadoAfi(boolean estadoAfi) {
        this.estadoAfi = estadoAfi;
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
        hash += (nroTarjeta != null ? nroTarjeta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Afiliado)) {
            return false;
        }
        Afiliado other = (Afiliado) object;
        if ((this.nroTarjeta == null && other.nroTarjeta != null) || (this.nroTarjeta != null && !this.nroTarjeta.equals(other.nroTarjeta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sistemamutual.Afiliado[ nroTarjeta=" + nroTarjeta + " ]";
    }
    
}
