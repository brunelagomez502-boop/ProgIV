/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

/**
 *
 * @author bgsof
 */
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class GestorDb {
     @PersistenceContext(name = "com.mycompany_ProgIV_war_1.0-SNAPSHOTPU")
    
    EntityManager em;
    
    @RequestScoped
    @Produces
    public EntityManager generarEM(){
        return em;
    }
    
}
