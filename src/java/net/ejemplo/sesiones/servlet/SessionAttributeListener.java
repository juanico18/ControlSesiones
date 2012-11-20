package net.ejemplo.sesiones.servlet;

import javax.servlet.http.*;
import net.ejemplo.sesiones.EjemploConstants;
import net.ejemplo.sesiones.model.usuario.Usuario;
import net.ejemplo.sesiones.service.SessionMonitor;
import org.springframework.context.ApplicationContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;



/**
 *
 * @author terinventer
 */
public class SessionAttributeListener implements HttpSessionAttributeListener {

    protected Log log = LogFactory.getLog(SessionAttributeListener.class);

    /**
     * Comprueba que el nuevo atributo para la sesión corresponde con el
     * identificador del usuario y actualizamos el HashMap
     *
     * @param sessionEvent
     */
    public void attributeAdded(HttpSessionBindingEvent sessionEvent) {
        HttpSession session = sessionEvent.getSession();
        if (sessionEvent.getName().equals(EjemploConstants.USER_SESSION)) {
            ApplicationContext ctx =
                    WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
            SessionMonitor sessionService =
                    (SessionMonitor) ctx.getBean("sessionMonitor");
            sessionService.attributeAdded(session.getId(), sessionEvent.getValue());
        }
    }

    /**
     * Comprueba que el atributo eliminado para la sesión corresponde con el
     * identificador del usuario y actualizamos el HashMap
     *
     * @param sessionEvent
     */
    public void attributeRemoved(HttpSessionBindingEvent sessionEvent) {
        HttpSession session = sessionEvent.getSession();
        if (sessionEvent.getName().equals(EjemploConstants.USER_SESSION)) {
            ApplicationContext ctx =
                    WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());

            SessionMonitor sessionService =
                    (SessionMonitor) ctx.getBean("sessionMonitor");

            sessionService.attributeRemoved(sessionEvent.getValue());
        }
    }

    /**
     * Comprueba que el atributo actualizado en sesión corresponde con el
     * identificador del usuario y actualizamos el HashMap
     *
     * @param sessionEvent
     */
    public void attributeReplaced(HttpSessionBindingEvent sessionEvent) {
        HttpSession session = sessionEvent.getSession();
        if (sessionEvent.getName().equals(EjemploConstants.USER_SESSION)) {
            // Si se obtiene el valor del evento, se obtiene el valor anterior, No nos vale.
            // Hay que obtener el valor en sesión, porque ya es el nuevo valor.
            Usuario userSession = ((Usuario) session.getAttribute(EjemploConstants.USER_SESSION));
            ApplicationContext ctx =
                    WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
            SessionMonitor sessionService =
                    (SessionMonitor) ctx.getBean("sessionMonitor");
            sessionService.attributeAdded(session.getId(), userSession);
        }
    }
}
