package com.carpool.android.negocio;

import com.carpool.android.dominio.Usuario;
import com.carpool.android.service.UsuarioService;

public class UsuarioNegocio {
    private static Usuario usuarioLogado;

    public static Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public static void setUsuarioLogado(Usuario usuarioLogado) {
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
            String retornoValidacao = validarUsuario(usuario);
            if(retornoValidacao.equals("")){
                UsuarioService.editarUsuario(usuario);
            } else {
                throw new Exception(retornoValidacao);
            }
        } catch (Exception excecao){
            throw new Exception(excecao);
        }
    }

    /**
     * Verifica se todos os campos estão preenchidos
     *
     * @param usuario
     * @return
     */
    private String validarUsuario(Usuario usuario){
        StringBuilder builder = new StringBuilder();
        builder.append("");

        if(usuario.getNomeUsuario().equals("")){
            builder.append(" nome;");
        } else if(usuario.getEmail().equals("")){
            builder.append(" email;");
        } else if(usuario.getTelefone().equals("")){
            builder.append(" telefone;");
        }

        return builder.toString();
    }

}
