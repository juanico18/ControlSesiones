package net.ejemplo.sesiones.servlet;

import javax.servlet.http.*;
import net.ejemplo.sesiones.service.SessionMonitor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author terinventer
 */
public class SessionListener implements HttpSessionListener {

    protected Log log = LogFactory.getLog(SessionListener.class);
    /**
     * No se implementa porque no lo necesitamos.
     * @param se 
     */
    public void sessionCreated(HttpSessionEvent se) {
        // Not implenented
    }
    /**
     * Captura el evento, cuando una sesión es finalizada.
     * @param sessionEvent 
     */
    public void sessionDestroyed(HttpSessionEvent sessionEvent) {
        HttpSession session = sessionEvent.getSession();
        ApplicationContext ctx =
                WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
        SessionMonitor sessionService =
                (SessionMonitor) ctx.getBean("sessionMonitor");
        sessionService.sessionDestroyed(session.getId());

    }
}
