/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositorios;
import entidades.Afiliado;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author bgsof
 */@Stateless
public class repositorioAfiliado {

    @Inject
    private EntityManager em;

    public void Guardar(Afiliado a) {
        if (a.getNroTarjeta() != null && a.getNroTarjeta() > 0) {
            em.merge(a);
        } else {
            em.persist(a);
        }
    }

    public void Eliminar(Integer id) {
        PorId(id).ifPresent(a -> {
            em.remove(a);
        });
    }

    public Optional<Afiliado> PorId(Integer id) {
        return Optional.ofNullable(em.find(Afiliado.class, id));
    }

    public List<Afiliado> Listar() {
        return em.createQuery("SELECT a FROM Afiliado a", Afiliado.class).getResultList();
    }
    
    public void BajaLogica(Integer id) {
        PorId(id).ifPresent(p -> {
            p.setEstadoAfi(false);
            em.merge(p);
        });
    }
    public void Reactivar(Integer id) {
        PorId(id).ifPresent(p -> {
            p.setEstadoAfi(true);
            em.merge(p);
        });
    }
}

