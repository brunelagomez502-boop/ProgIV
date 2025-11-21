/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author bgsof
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bonosemitidos.findAll", query = "SELECT b FROM Bonosemitidos b"),
    @NamedQuery(name = "Bonosemitidos.findByIdBonos", query = "SELECT b FROM Bonosemitidos b WHERE b.idBonos = :idBonos"),
    @NamedQuery(name = "Bonosemitidos.findByFecEmision", query = "SELECT b FROM Bonosemitidos b WHERE b.fecEmision = :fecEmision"),
    @NamedQuery(name = "Bonosemitidos.findByFecVencimiento", query = "SELECT b FROM Bonosemitidos b WHERE b.fecVencimiento = :fecVencimiento")})
public class Bonosemitidos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer idBonos;
    @Basic(optional = false)
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date fecEmision;
    @Basic(optional = false)
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date fecVencimiento;
    @JoinColumn(name = "NroTarjeta", referencedColumnName = "NroTarjeta")
    @ManyToOne(optional = false)
    private Afiliado nroTarjeta;
    @JoinColumn(name = "IdComercios", referencedColumnName = "IdComercios")
    @ManyToOne(optional = false)
    private Comercio idComercios;

    public Bonosemitidos() {
    }

    public Bonosemitidos(Integer idBonos) {
        this.idBonos = idBonos;
    }

    public Bonosemitidos(Integer idBonos, Date fecEmision, Date fecVencimiento) {
        this.idBonos = idBonos;
        this.fecEmision = fecEmision;
        this.fecVencimiento = fecVencimiento;
    }

    public Integer getIdBonos() {
        return idBonos;
    }

    public void setIdBonos(Integer idBonos) {
        this.idBonos = idBonos;
    }

    public Date getFecEmision() {
        return fecEmision;
    }

    public void setFecEmision(Date fecEmision) {
        this.fecEmision = fecEmision;
    }

    public Date getFecVencimiento() {
        return fecVencimiento;
    }

    public void setFecVencimiento(Date fecVencimiento) {
        this.fecVencimiento = fecVencimiento;
    }

    public Afiliado getNroTarjeta() {
        return nroTarjeta;
    }

    public void setNroTarjeta(Afiliado nroTarjeta) {
        this.nroTarjeta = nroTarjeta;
    }

    public Comercio getIdComercios() {
        return idComercios;
    }

    public void setIdComercios(Comercio idComercios) {
        this.idComercios = idComercios;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBonos != null ? idBonos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bonosemitidos)) {
            return false;
        }
        Bonosemitidos other = (Bonosemitidos) object;
        if ((this.idBonos == null && other.idBonos != null) || (this.idBonos != null && !this.idBonos.equals(other.idBonos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Bonosemitidos[ idBonos=" + idBonos + " ]";
    }

    
}
