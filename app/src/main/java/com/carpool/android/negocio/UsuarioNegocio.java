package com.carpool.android.negocio;

import android.net.Uri;

/**
 * Created by gustavopereira on 5/26/16.
 */
public class UsuarioNegocio {
    private static String nome;
    private static String facebookID;
    private static String token;
    private static Uri profileLink;
    private static Uri profilePicture;

    public static String getNome() {
        return nome;
    }

    public static void setNome(String nome) {
        UsuarioNegocio.nome = nome;
    }

    public static String getFacebookID() {
        return facebookID;
    }

    public static void setFacebookID(String facebookID) {
        UsuarioNegocio.facebookID = facebookID;
    }

    public static Uri getProfilePicture() {
        return profilePicture;
    }

    public static void setProfilePicture(Uri profilePicture) {
        UsuarioNegocio.profilePicture = profilePicture;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        UsuarioNegocio.token = token;
    }

    public static Uri getProfileLink() {
        return profileLink;
    }

    public static void setProfileLink(Uri profileLink) {
        UsuarioNegocio.profileLink = profileLink;
    }
}
