package net.ejemplo.sesiones.model.session;

import java.io.Serializable;

/**
 *
 * @author terinventer
 */
public class UsuariosSession implements Serializable {

    private static final long serialVersionUID = 1L;
    private String idSession;
    private String usuario;

    public UsuariosSession() {
    }

    public UsuariosSession(String idSession, String usuario) {
        this.idSession = idSession;
        this.usuario = usuario;
    }


    public String getIdSession() {
        return idSession;
    }

    public void setIdSession(String idSession) {
        this.idSession = idSession;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "UsuariosSession{" + "idSession=" + idSession + ", usuario=" + usuario + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UsuariosSession other = (UsuariosSession) obj;
        if ((this.idSession == null) ? (other.idSession != null) : !this.idSession.equals(other.idSession)) {
            return false;
        }
        if ((this.usuario == null) ? (other.usuario != null) : !this.usuario.equals(other.usuario)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.idSession != null ? this.idSession.hashCode() : 0);
        hash = 89 * hash + (this.usuario != null ? this.usuario.hashCode() : 0);
        return hash;
    }

}
