package mx.unam.banunam.system.controller.useradministration;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import mx.unam.banunam.auth.usuario.dto.TipoUsuarioDTO;
import mx.unam.banunam.auth.usuario.dto.UsuarioDTO;
import mx.unam.banunam.auth.usuario.service.TipoUsuarioService;
import mx.unam.banunam.auth.usuario.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping(path = "/user-administration/")
@PreAuthorize("hasRole(${perfil.usuario.tipo1})")
public class AdminController {
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private TipoUsuarioService tipoUsuarioService;

    private final String URI_BASE = "/user-administration/";

    @GetMapping("/")
    public String home(Model model, @RequestParam(required = false) String tipoUsuario) {
        log.info("Entra a raíz");
        List<TipoUsuarioDTO> listaTipos = tipoUsuarioService.listarTiposUsuario();
        model.addAttribute("listaTipos", listaTipos);
        if(tipoUsuario != null && !tipoUsuario.isEmpty()){
            model.addAttribute("usuarios", usuarioService.listarUsuariosPorTipoUsuarioAlias(tipoUsuario));
            return URI_BASE + "home";
        }
        model.addAttribute("usuarios" ,usuarioService.listarUsuarios());
        return URI_BASE + "home";
    }


    @PostMapping("/")
    public String buscarUsuario(@ModelAttribute("usuarioBusqueda") String usuarioBusqueda, Model model){
        List<TipoUsuarioDTO> listaTipos = tipoUsuarioService.listarTiposUsuario();
        model.addAttribute("listaTipos", listaTipos);
        List<UsuarioDTO> usuarios = new ArrayList<>();
        UsuarioDTO usuarioDTO = usuarioService.buscarUsuarioPorUsuario(usuarioBusqueda, false);
        if(usuarioDTO != null)
            usuarios.add(usuarioDTO);
        model.addAttribute("usuarios", usuarios);
        log.info("########## JEEM: usuarioBusqueda = {}", usuarioBusqueda);
        return URI_BASE + "home";
    }

    @GetMapping("/usuarios/{id}")
    public String detalleUsuario(@PathVariable Integer id, Model model){
        UsuarioDTO usuarioDTO = usuarioService.buscarUsuarioPorId(id);
        model.addAttribute("usuario", usuarioDTO);
        return URI_BASE + "usuario-detalle";
    }

    @GetMapping("/usuarios/{id}/editar")
    public String editarUsuario(@PathVariable Integer id, Model model){
        UsuarioDTO usuarioDTO = usuarioService.buscarUsuarioPorId(id);
        List<TipoUsuarioDTO> listaTipos = tipoUsuarioService.listarTiposUsuario();
        String contrasenaRep = "";
        model.addAttribute("usuario", usuarioDTO);
        model.addAttribute("listaTipos", listaTipos);
        model.addAttribute("contrasenaRep", contrasenaRep);
        model.addAttribute("tituloCard", "Editar usuario");
        return URI_BASE + "usuario-agregar";
    }

    @GetMapping("/usuarios/agregar")
    public String agregarUsuario(Model model){
        List<TipoUsuarioDTO> listaTipos = tipoUsuarioService.listarTiposUsuario();
        String contrasenaRep = "";
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        model.addAttribute("usuario", usuarioDTO);
        model.addAttribute("listaTipos", listaTipos);
        model.addAttribute("contrasenaRep", contrasenaRep);
        model.addAttribute("tituloCard", "Agregar usuario");
        return URI_BASE + "usuario-agregar";
    }

    @PostMapping("/usuarios/crear")
    public String crearUsuario(@Valid @ModelAttribute("usuario") UsuarioDTO usuarioRecibido, BindingResult result,
                               @ModelAttribute("contrasenaRep") String contrasenaRep, Model model, RedirectAttributes flash){
        log.info("########## JEEM: AdminController: Entra a crearUsuario");
        log.info("########## JEEM: AdminController: Información recibida: ");
        log.info("########## JEEM: {}", usuarioRecibido);
        log.info("########## JEEM: {}", contrasenaRep);
        String errorMsg = "Error en la entrada de datos. Valide lo siguiente: ";
        List<String> listaErrores = new ArrayList<>();
        Boolean update = usuarioRecibido.getIdUsuario() != null;
        log.info("########## JEEM: update = {}", update);

        if(result.hasErrors()){
            for(ObjectError e: result.getAllErrors()){
                listaErrores.add(e.getDefaultMessage());
                log.info("########## JEEM: Lista errores: " + listaErrores);
            }

            flash.addFlashAttribute("danger", errorMsg);
            flash.addFlashAttribute("listaErrores", listaErrores);
            flash.addFlashAttribute("usuario", usuarioRecibido);
            return "redirect:" + URI_BASE + "usuarios/agregar";
        }
        if(!usuarioRecibido.getContrasena().equals(contrasenaRep)){
            listaErrores.add("Las contraseñas no coinciden");
            flash.addFlashAttribute("danger", errorMsg);
            flash.addFlashAttribute("listaErrores", listaErrores);
            flash.addFlashAttribute("usuario", usuarioRecibido);
            return "redirect:" + URI_BASE + "usuarios/agregar";
        }

        log.info("########## JEEM: Se guarda el usuario");
        UsuarioDTO usuarioCreado = usuarioService.salvar(usuarioRecibido, update);
        //flash.addFlashAttribute("usuario", usuarioCreado);
        flash.addFlashAttribute("success", "Usuario " + usuarioCreado.getUsuario() + " guardado con éxito");

        return "redirect:" + URI_BASE + "usuarios/" + usuarioCreado.getIdUsuario();
    }

    @GetMapping("/usuarios/{id}/delete")
    public String eliminarUsuario(@PathVariable Integer id, HttpSession session, RedirectAttributes flash) {
        String usuario = usuarioService.buscarUsuarioPorId(id).getUsuario();
        String usuarioFirmado = (String) session.getAttribute("usuarioFirmado");
        log.info("########## JEEM: Usuario firmado {}", usuarioFirmado);
        if(usuarioService.buscarUsuarioPorId(id).getUsuario().equals(usuarioFirmado)) {
            log.info("########## JEEM: El usuario intentó eliminarse a sí mismo");
            flash.addFlashAttribute("danger", "Error: ¡No puedes eliminarte a ti mismo!");
        } else{
            log.info("########## JEEM: Se elimina al usuario {} con id {}", usuario, id);
            usuarioService.eliminarUsuarioPorId(id);
            flash.addFlashAttribute("success", "El usuario " + usuario + " fue eliminado");
        }
        return "redirect:" + URI_BASE;
    }


}
