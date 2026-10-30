package com.relesys.usuario.view;

import com.relesys.organizacion.model.Empresa;
import com.relesys.organizacion.model.Sector;
import com.relesys.organizacion.model.Ubicacion;
import com.relesys.organizacion.repository.EmpresaRepository;
import com.relesys.organizacion.repository.SectorRepository;
import com.relesys.organizacion.repository.UbicacionRepository;
import com.relesys.usuario.model.Usuario;
import com.relesys.usuario.service.UsuarioService;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.List;

@Component
@SessionScope
public class UsuarioView {

    private Usuario usuarioEditando;
    private boolean modoEdicion;

    private Integer empresaSeleccionadaId;
    private Integer ubicacionSeleccionadaId;
    private Integer sectorSeleccionadoId;

    private final UsuarioService usuarioService;
    private final EmpresaRepository empresaRepository;
    private final UbicacionRepository ubicacionRepository;
    private final SectorRepository sectorRepository;

    private Usuario nuevoUsuario = new Usuario();

    public UsuarioView(
            UsuarioService usuarioService,
            EmpresaRepository empresaRepository,
            UbicacionRepository ubicacionRepository,
            SectorRepository sectorRepository) {

        this.usuarioService = usuarioService;
        this.empresaRepository = empresaRepository;
        this.ubicacionRepository = ubicacionRepository;
        this.sectorRepository = sectorRepository;
    }

    public List<Usuario> getUsuarios() {
        return usuarioService.listarUsuarios();
    }

    public List<Empresa> getEmpresas() {
        return empresaRepository.findAll();
    }

    public List<Ubicacion> getUbicaciones() {
        return ubicacionRepository.findAll();
    }

    public List<Sector> getSectores() {
        return sectorRepository.findAll();
    }

    public Integer getEmpresaSeleccionadaId() {
        return empresaSeleccionadaId;
    }

    public void setEmpresaSeleccionadaId(Integer id) {
        this.empresaSeleccionadaId = id;
    }

    public Integer getUbicacionSeleccionadaId() {
        return ubicacionSeleccionadaId;
    }

    public void setUbicacionSeleccionadaId(Integer id) {
        this.ubicacionSeleccionadaId = id;
    }

    public Integer getSectorSeleccionadoId() {
        return sectorSeleccionadoId;
    }

    public void setSectorSeleccionadoId(Integer id) {
        this.sectorSeleccionadoId = id;
    }

    public Usuario getNuevoUsuario() {
        return nuevoUsuario;
    }

    public void editar(Usuario usuario) {
        this.usuarioEditando = usuario;
        this.modoEdicion = true;

        this.nuevoUsuario = new Usuario();

        this.nuevoUsuario.setNumero(usuario.getNumero());
        this.nuevoUsuario.setInterno(usuario.getInterno());
        this.nuevoUsuario.setNombre(usuario.getNombre());
        this.nuevoUsuario.setApellido(usuario.getApellido());

        this.nuevoUsuario.setEmpresa(usuario.getEmpresa());
        this.nuevoUsuario.setUbicacion(usuario.getUbicacion());
        this.nuevoUsuario.setSector(usuario.getSector());

        this.empresaSeleccionadaId =
                usuario.getEmpresa() != null
                        ? usuario.getEmpresa().getId()
                        : null;

        this.ubicacionSeleccionadaId =
                usuario.getUbicacion() != null
                        ? usuario.getUbicacion().getId()
                        : null;

        this.sectorSeleccionadoId =
                usuario.getSector() != null
                        ? usuario.getSector().getId()
                        : null;
    }

    public boolean isModoEdicion() {
        return modoEdicion;
    }

    public String getTituloDialogo() {
        return modoEdicion ? "Editar usuario" : "Nuevo usuario";
    }

    public void prepararNuevo() {
        nuevoUsuario = new Usuario();
        usuarioEditando = null;
        modoEdicion = false;

        empresaSeleccionadaId = null;
        ubicacionSeleccionadaId = null;
        sectorSeleccionadoId = null;
    }

    public void cancelar() {
        prepararNuevo();
    }

    public void guardar() {
        System.out.println(">>> SE EJECUTÓ guardar()");

        try {
            Empresa empresa = empresaSeleccionadaId != null
                    ? empresaRepository.findById(empresaSeleccionadaId)
                    .orElseThrow(() ->
                            new RuntimeException("Empresa no encontrada"))
                    : null;

            Ubicacion ubicacion = ubicacionSeleccionadaId != null
                    ? ubicacionRepository.findById(ubicacionSeleccionadaId)
                    .orElseThrow(() ->
                            new RuntimeException("Ubicación no encontrada"))
                    : null;

            Sector sector = sectorSeleccionadoId != null
                    ? sectorRepository.findById(sectorSeleccionadoId)
                    .orElseThrow(() ->
                            new RuntimeException("Sector no encontrado"))
                    : null;

            nuevoUsuario.setEmpresa(empresa);
            nuevoUsuario.setUbicacion(ubicacion);
            nuevoUsuario.setSector(sector);

            Usuario usuarioAGuardar =
                    modoEdicion ? usuarioEditando : nuevoUsuario;

            usuarioAGuardar.setNumero(nuevoUsuario.getNumero());
            usuarioAGuardar.setInterno(nuevoUsuario.getInterno());
            usuarioAGuardar.setNombre(nuevoUsuario.getNombre());
            usuarioAGuardar.setApellido(nuevoUsuario.getApellido());

            usuarioAGuardar.setEmpresa(empresa);
            usuarioAGuardar.setUbicacion(ubicacion);
            usuarioAGuardar.setSector(sector);

            usuarioService.guardarUsuario(usuarioAGuardar);

            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(
                            FacesMessage.SEVERITY_INFO,
                            "Éxito",
                            modoEdicion
                                    ? "Usuario actualizado correctamente"
                                    : "Usuario registrado correctamente"
                    )
            );

            prepararNuevo();

        } catch (Exception e) {
            e.printStackTrace();

            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "Error",
                            "No se pudo guardar el usuario: " + e.getMessage()
                    )
            );
        }
    }

    public void eliminar(Usuario usuario) {
        try {
            usuarioService.eliminarUsuario(usuario.getId());

            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(
                            FacesMessage.SEVERITY_INFO,
                            "Éxito",
                            "Usuario eliminado correctamente"
                    )
            );

        } catch (Exception e) {
            e.printStackTrace();

            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "Error",
                            "No se pudo eliminar el usuario: " + e.getMessage()
                    )
            );
        }
    }
}