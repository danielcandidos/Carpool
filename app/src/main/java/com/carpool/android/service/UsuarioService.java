package com.carpool.android.service;

import com.carpool.android.dominio.Usuario;
import com.carpool.android.negocio.UsuarioNegocio;

public class UsuarioService {

    public static void cadastrarUsuario(Usuario usuario){
        //conecta com webservice
        return;
    }

    public static void editarUsuario(Usuario usuario){
        UsuarioNegocio.setUsuarioLogado(usuario);
    }

}
