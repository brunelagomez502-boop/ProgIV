/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package convertidores;

/**
 *
 * @author bgsof
 */


import entidades.Comercio;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import repositorios.repositorioComercio;

@FacesConverter(value = "comercioConverter", managed = true)
public class comercioConverter implements Converter<Comercio> {

    @Inject
    private repositorioComercio repoComercio;

    @Override
    public Comercio getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        try {
            Integer id = Integer.valueOf(value);
            return repoComercio.PorId(id).orElse(null);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Comercio comercio) {
        if (comercio == null) {
            return "";
        }
        return String.valueOf(comercio.getIdComercios());
    }
}
