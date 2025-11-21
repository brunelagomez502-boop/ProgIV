/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositorios;

import entidades.Comercio;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author bgsof
 */
@Stateless
public class repositorioComercio {
     @Inject
    private EntityManager em;

    public void Guardar(Comercio c) {
        if (c.getIdComercios()!= null && c.getIdComercios() > 0) {
            em.merge(c);
        } else {
            em.persist(c);
        }
    }

   
    
    
    public Optional<Comercio> PorId(Integer id) {
        return Optional.ofNullable(em.find(Comercio.class, id));
    }

    public List<Comercio> Listar() {
        return em.createQuery("SELECT c FROM Comercio c", Comercio.class).getResultList();
    }
    
        @Transactional
public void eliminar(Integer id) {
    PorId(id).ifPresent(p -> {
        em.remove(em.contains(p) ? p : em.merge(p));
    });
}
public void BajaLogica(Integer id) {
        PorId(id).ifPresent(p -> {
            p.setEstadoCom(false);
            em.merge(p);
        });
    }

public void Reactivar(Integer id) {
        PorId(id).ifPresent(p -> {
            p.setEstadoCom(true);
            em.merge(p);
        });
    }
}
