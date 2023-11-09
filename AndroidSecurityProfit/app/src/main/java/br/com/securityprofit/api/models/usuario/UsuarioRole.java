package br.com.securityprofit.api.models.usuario;
public enum UsuarioRole {

    ADMIN("Administrador"),
    USER("Agente");

    private String role;

    UsuarioRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public static UsuarioRole fromString(String roleString) {
        for (UsuarioRole role : UsuarioRole.values()) {
            if (role.role.equals(roleString)) {

                return role;
            }

        }

        return null;
    }

    @Override
    public String toString() {
        return role;
    }
}
