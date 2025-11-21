/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package controladores;

import entidades.Comercio;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Model;
import jakarta.enterprise.inject.Produces;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import repositorios.repositorioComercio;

/**
 *
 * @author bgsof
 */
@Named(value = "controladorComercio")
@ViewScoped
public class controladorComercio implements Serializable {

    @Inject
    repositorioComercio repoComercio;

    private Comercio comercio;
    private Integer id;

    public controladorComercio() {
    }

    @Model
    @Produces
    public Comercio comercio() {
        System.out.println("id" + id);
        if (id != null && id > 0) {
            repoComercio.PorId(id).ifPresent(c -> {
                comercio = c;
            });
        } else {
            comercio = new Comercio();
        }
        return comercio;
    }

    public void cargarComercio() {
        System.out.println(" Entró a cargarComercio con id = " + id);
        if (id != null && id > 0) {
            repoComercio.PorId(id).ifPresentOrElse(
                    c -> {
                        comercio = c;
                        System.out.println(" Comercio cargado: " + comercio.getNombre());
                    },
                    () -> {
                        comercio = new Comercio();
                        System.out.println("️ No se encontró el comercio con ese ID, se creó uno nuevo.");
                    }
            );
        } else {
            comercio = new Comercio();
            System.out.println(" Creando nuevo comercio vacío");
        }
    }

//    public String guardar() {
//        System.out.println("💾 Guardando comercio con id: " + comercio.getIdComercios());
//        repoComercio.Guardar(comercio);
//        return "/comercios/comercios.xhtml?faces-redirect=true";
//    }
//     public void cargarComercio() {
//        
//        if (id != null && id > 0) {
//            repoComercio.PorId(id).ifPresent(c -> comercio = c);
//        } else {
//            comercio = new Comercio();
//        }
//    }
// public Comercio getComercio() {
//        if (comercio == null) {
//            comercio = new Comercio();
//        }
//        return comercio;
//    }
//    public Comercio getComercio() {
//        System.out.println("id" + id);
//        if (id != null && id > 0) {
//            repoComercio.PorId(id).ifPresent(c -> {
//                comercio = c;
//            });
//        } else {
//            comercio = new Comercio();
//        }
//        return comercio;
//    }
    public repositorioComercio getRepoComercio() {
        return repoComercio;
    }

    public void setRepoComercio(repositorioComercio repoComercio) {
        this.repoComercio = repoComercio;
    }

    public Comercio getComercio() {
        return comercio;
    }

    public void setComercio(Comercio comercio) {
        this.comercio = comercio;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String guardar() {
        repoComercio.Guardar(comercio);
        return "/comercios/comercios.xhtml?faces-redirect=true";
    }

    public String eliminar(Integer id) {
        if (id != null) {
            repoComercio.eliminar(id);
        }

        return "/comercios/comercios.xhtml?faces-redirect=true";
    }

    public List<Comercio> comercios() {
        return repoComercio.Listar();
    }

    public List<Comercio> getComercios() {
        return repoComercio.Listar();
    }
public String bajaLogica(Integer id) {
        repoComercio.BajaLogica(id);
        return "/comercios/comercios.xhtml?faces-redirect=true";
    }

    public String reactivar(Integer id) {
        repoComercio.Reactivar(id);
        return "/comercios/comercios.xhtml?faces-redirect=true";
    }
}
