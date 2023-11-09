package br.com.securityprofit.role;

import android.content.Context;
import android.widget.ArrayAdapter;
import br.com.securityprofit.api.models.usuario.UsuarioRole;

public class UsuarioRoleAdapter extends ArrayAdapter<UsuarioRole> {

    public UsuarioRoleAdapter(Context context, int resource, UsuarioRole[] roles) {
        super(context, resource, roles);
    }
}
