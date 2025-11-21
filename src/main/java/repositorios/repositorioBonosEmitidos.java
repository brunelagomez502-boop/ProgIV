/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositorios;

import entidades.Bonosemitidos;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author bgsof
 */
@Stateless
public class repositorioBonosEmitidos {
    @Inject
    private EntityManager em;

    public void Guardar(Bonosemitidos b) {
        if (b.getIdBonos()!= null && b.getIdBonos() > 0) {
            em.merge(b);
        } else {
            em.persist(b);
        }
    }

    public void Eliminar(Integer id) {
        PorId(id).ifPresent(b -> {
            em.remove(b);
        });
    }

    public Optional<Bonosemitidos> PorId(Integer id) {
        return Optional.ofNullable(em.find(Bonosemitidos.class, id));
    }

    public List<Bonosemitidos> Listar() {
        return em.createQuery("SELECT b FROM Bonosemitidos b", Bonosemitidos.class).getResultList();
    }
    
    public boolean existeBonoDelMes(Integer nroTarjeta, Integer idComercio) {
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.DAY_OF_MONTH, 1);
    Date inicioMes = cal.getTime();

    cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
    Date finMes = cal.getTime();

    List<Bonosemitidos> bonos = em.createQuery(
        "SELECT b FROM Bonosemitidos b WHERE b.nroTarjeta.nroTarjeta = :nroTarjeta " +
        "AND b.idComercios.idComercios = :idComercio " +
        "AND b.fecEmision BETWEEN :inicioMes AND :finMes", Bonosemitidos.class)
        .setParameter("nroTarjeta", nroTarjeta)
        .setParameter("idComercio", idComercio)
        .setParameter("inicioMes", inicioMes)
        .setParameter("finMes", finMes)
        .getResultList();

    return !bonos.isEmpty();
}
}