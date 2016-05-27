package com.carpool.android.negocio;

import com.carpool.android.dominio.Usuario;
import com.carpool.android.service.UsuarioService;

public class UsuarioNegocio {
    private static Usuario usuarioLogado;

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(Usuario usuarioLogado) {
        UsuarioNegocio.usuarioLogado = usuarioLogado;
    }

    public void cadastrarUsuario(Usuario usuario) throws Exception {
        try {
            UsuarioService.cadastrarUsuario(usuario);
        } catch (Exception excecao){
            throw new Exception(excecao);
        }
    }

    public void editarUsuario(Usuario usuario) throws Exception {
        try {
            UsuarioService.editarUsuario(usuario);
        } catch (Exception excecao){
            throw new Exception(excecao);
        }
    }
}
