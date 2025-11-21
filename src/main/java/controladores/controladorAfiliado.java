/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package controladores;

import entidades.Afiliado;


import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Model;
import jakarta.enterprise.inject.Produces;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import java.io.Serializable;
import java.util.List;
import repositorios.repositorioAfiliado;

/**
 *
 * @author bgsof
 */
@Named(value = "controladorAfiliado")
@ViewScoped
public class controladorAfiliado implements Serializable {

    @Inject
    repositorioAfiliado repoAfi;

    private Afiliado afiliado;
    private Integer id;

    public controladorAfiliado() {
    }

    @Model
    @Produces
    public Afiliado afiliado() {
        System.out.println("nroTarj" + id);
        if (id != null && id > 0) {
            repoAfi.PorId(id).ifPresent(a -> {
                afiliado = a;
            });
        } else {
            afiliado = new Afiliado();
        }
        return afiliado;
    }
public Afiliado getAfiliado() {
        System.out.println("id" + id);
        if (id != null && id > 0) {
            repoAfi.PorId(id).ifPresent(c -> {
                afiliado= c;
            });
        } else {
            afiliado = new Afiliado();
        }
        return afiliado;
    }
    public repositorioAfiliado getRepoAfi() {
        return repoAfi;
    }

    public void setRepoAfi(repositorioAfiliado repoAfi) {
        this.repoAfi = repoAfi;
    }

    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNroTarj(Integer nroTarj) {
        this.id = nroTarj;
    }

    public String guardar() {
        repoAfi.Guardar(afiliado);
        return "/afiliados/afiliados.xhtml?faces-redirect=true";
    }

    public String eliminar(Integer id) {
        repoAfi.Eliminar(id);
        return "/afiliados/afiliados.xhtml?faces-redirect=true";
    }

    public List<Afiliado> listar() {
        return repoAfi.Listar();
    }
    public List<Afiliado> getAfiliados() {
    return repoAfi.Listar();
}
 public void cargarAfiliado() {
        System.out.println(" Entró a cargarComercio con id = " + id);
        if (id != null && id > 0) {
            repoAfi.PorId(id).ifPresentOrElse(
                c -> {
                   afiliado= c;
                    System.out.println(" Comercio cargado: " + afiliado.getNombre());
                },
                () -> {
                  afiliado = new Afiliado();
                    System.out.println("️ No se encontró el afiliado con ese ID, se creó uno nuevo.");
                }
            );
        } else {
           afiliado = new Afiliado();
            System.out.println(" Creando nuevo afiliado");
        }
    }
public String bajaLogica(Integer id) {
        repoAfi.BajaLogica(id);
        return "/afiliados/afiliados.xhtml?faces-redirect=true";
    }
 public String reactivar(Integer id) {
        repoAfi.Reactivar(id);
        return "/afiliados/afiliados.xhtml?faces-redirect=true";
    }
}
