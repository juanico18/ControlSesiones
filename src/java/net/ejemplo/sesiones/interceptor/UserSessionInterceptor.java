package net.ejemplo.sesiones.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.ejemplo.sesiones.EjemploConstants;
import net.ejemplo.sesiones.model.session.UsuariosSession;
import net.ejemplo.sesiones.model.usuario.Usuario;
import net.ejemplo.sesiones.service.SessionMonitor;
import net.ejemplo.sesiones.util.StringFunctions;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;



 /**
 * Interceptor que verifica si el usuario en sesión es el correcto
 * @author terinventer
 */
public class UserSessionInterceptor implements Interceptor {

    protected Log log = LogFactory.getLog(UserSessionInterceptor.class);
    SessionMonitor sessionMonitor;
    // Salida generica, cuando el usuario ha iniciado una sesión en otro lugar
    private String USER_ANOTHER_SESSION = "userAnotherSesion";

    public void destroy() {
    }

    public void init() {
    }

    public String intercept(ActionInvocation actionInvocation) throws Exception {

        Map session = actionInvocation.getInvocationContext().getSession();
        String userSession = ((Usuario) session.get(EjemploConstants.USER_SESSION)).getUsuario();
        HttpServletRequest request = ServletActionContext.getRequest();
        String idSession = request.getSession().getId();

        if (sessionMonitor.getUsuariosSession() != null) {
            // Si no contiene el usuario, asociado a ninguna Id sessión se permite el acceso
            if (sessionMonitor.getUsuariosSession().containsKey(userSession)) {
                UsuariosSession userMap = sessionMonitor.getUsuariosSession().get(userSession);
                // si el usuario esta asociado a algun Id, se comprueba
                if (!idSession.equals(userMap.getIdSession())) {
                    // Si las sesiones  son diferentes y el usuario ya esta asociado a otra sesión, no se permite el acceso.
                    return USER_ANOTHER_SESSION;
                }
            }

        }
        return actionInvocation.invoke();
    }

    public SessionMonitor getSessionMonitor() {
        return sessionMonitor;
    }

    public void setSessionMonitor(SessionMonitor sessionMonitor) {
        this.sessionMonitor = sessionMonitor;
    }

    public void logInfo(String usuario, String msg) {
        log.info("[" + StringFunctions.fillLeft(usuario, ' ', 15) + "] " + msg);
    }

    public void logDebug(String usuario, String msg) {
        log.debug("[" + StringFunctions.fillLeft(usuario, ' ', 15) + "] " + msg);
    }

    public void logException(String usuario, String msg, Exception ex) {
        String usr = StringFunctions.fillLeft(usuario, ' ', 15);
        log.error("[" + usr + "] " + msg + ex.getClass().getName() + ": " + ex.getMessage());
        for (StackTraceElement element : ex.getStackTrace()) {
            log.error("[" + usr + "] Excepcion: " + element.getClassName() + " : " + element.getLineNumber());
        }
    }

   
}
