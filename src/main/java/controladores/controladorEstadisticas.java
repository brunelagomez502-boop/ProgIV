package controladores;

import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import repositorios.repositorioAfiliado;
import repositorios.repositorioComercio;

import repositorios.repositorioBonosEmitidos;

@Named(value = "controladorEstadisticas")
@RequestScoped
public class controladorEstadisticas {

    @Inject
    private repositorioAfiliado repoAfi;

    @Inject
    private repositorioComercio repoCom;

    @Inject
    private repositorioBonosEmitidos repoBono; // <-- nuevo

    public long getTotalAfiliados() {
        return repoAfi.Listar().size();
    }

    public long getTotalComercios() {
        return repoCom.Listar().size();
    }

    public long getTotalBonos() {
        return repoBono.Listar().size(); // <-- cuenta los bonos emitidos
    }
}
