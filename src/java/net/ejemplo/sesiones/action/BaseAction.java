package net.ejemplo.sesiones.action;

import com.opensymphony.xwork2.ActionSupport;
import java.util.Map;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.TokenHelper;

/**
 *
 * @author terinventer
 */
public class BaseAction extends ActionSupport implements SessionAware{

    static final long serialVersionUID = 1l;
    private String tokenRedirect;
    private Map session;
    
    public BaseAction() {
    }

    @Override
    public String execute() throws Exception {
        return SUCCESS;
    }



    public String getTokenRedirect() {
        
       /**
        * Actualizar el token para el action redirect.
        */
        String tokenAux = TokenHelper.generateGUID();
        try {
            session.put("struts.token", tokenAux);
        } catch (IllegalStateException e) {
            String msg = "Error creating HttpSession due response is commited to client. You can use the CreateSessionInterceptor or create the HttpSession from your action before the result is rendered to the client: " + e.getMessage();
            LOG.error(msg, e);
            throw new IllegalArgumentException(msg);
        }
        
        tokenRedirect = tokenAux;
        return tokenRedirect;
    }

    public void setTokenRedirect(String tokenRedirect) {
        this.tokenRedirect = tokenRedirect;
    }

    public Map getSession() {
        return session;
    }

    public void setSession(Map session) {
        this.session = session;
    }
    
}