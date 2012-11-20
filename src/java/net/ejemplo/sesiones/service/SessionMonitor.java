package net.ejemplo.sesiones.service;

import java.util.concurrent.ConcurrentHashMap;
import net.ejemplo.sesiones.model.session.UsuariosSession;
import net.ejemplo.sesiones.model.usuario.Usuario;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *
 * @author terinventer
 */
public class SessionMonitor {

    protected final Log logger = LogFactory.getLog(getClass());
    // Map que mantiene los valores de los usuarios en sesión
    protected ConcurrentHashMap<String, UsuariosSession> usuariosSession = new ConcurrentHashMap<String, UsuariosSession>();

    /**
     * Actualizar el HashMap de usuarios con el nuevo valor
     *
     * @param id
     * @param object
     */
    public void attributeAdded(String id, Object object) {
        Usuario usuario = (Usuario) object;
        UsuariosSession usuarioSession = new UsuariosSession();
        usuarioSession.setIdSession(id);
        usuarioSession.setUsuario(usuario.getUsuario());
        // Se actualiza el idSession, utilizado por ese usuario.
        // Invalidando el idSession previo                    
        this.usuariosSession.put(usuarioSession.getUsuario(), usuarioSession);
    }

    /**
     * Cuando se cierra la sesión ya sea provocado o por timeout. Eliminar el
     * usuario de sesión
     *
     * @param sessionId, IdSession que ha sido finalizada
     */
    public void sessionDestroyed(String sessionId) {
        deleteUserBySessionId(sessionId);
    }

    /**
     * Eliminar el usuario del Map
     *
     * @param object
     */
    public void attributeRemoved(Object object) {
        Usuario usuario = (Usuario) object;
        this.usuariosSession.remove(usuario.getUsuario());
    }

    /**
     * Metodo privado para eliminar del Map, el usuario que estaba asociado a
     * sessionId
     *
     * @param sessionId
     */
    private void deleteUserBySessionId(String sessionId) {
        String usuarioToDelete = "";
        for (UsuariosSession usuario : this.getUsuariosSession().values()) {
            if (usuario.getIdSession().equals(sessionId)) {
                usuarioToDelete = usuario.getUsuario();
            }
        }
        if (!usuarioToDelete.equals("")) {
            this.usuariosSession.remove(usuarioToDelete);
        }

    }

    public ConcurrentHashMap<String, UsuariosSession> getUsuariosSession() {
        return usuariosSession;
    }

    public void setUsuariosSession(ConcurrentHashMap<String, UsuariosSession> usuariosSession) {
        this.usuariosSession = usuariosSession;
    }
}
