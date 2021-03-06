package com.carpool.android.gui;

import com.carpool.android.dominio.Usuario;
import com.carpool.android.negocio.UsuarioNegocio;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.carpool.android.R;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences preferences;

    private CallbackManager callbackManager;
    private LoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_main);
        printKeyHash();
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken accessToken = loginResult.getAccessToken();
                Profile profile = Profile.getCurrentProfile();
                getFacebookData(accessToken, profile);
                entrar();
                Util.showMsgToastLong(MainActivity.this, getString(R.string.msg_diga_ola) + " " + profile.getName());
            }

            @Override
            public void onCancel() {
                Toast.makeText(MainActivity.this.getApplicationContext(), getString(R.string.msg_login_cancelado), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException e) {
                Toast.makeText(MainActivity.this.getApplicationContext(), getString(R.string.msg_login_sem_sucesso), Toast.LENGTH_LONG).show();
            }
        });

        //PEGANDO INFORMAÇÕES DO PREFERENCES PARA VERIFICAR LOGIN ANTERIOR
        preferences = getSharedPreferences("pref", Context.MODE_PRIVATE);
        String id = preferences.getString("id", null);
        String token = preferences.getString("token", null);
        if (id != null && token != null) {
            entrar();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void printKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.carpool.facebook",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    public void getFacebookData(AccessToken accessToken, Profile profile) {
        // Salva informações do facebook do usuario em Usuario Logado
        Usuario usuario = new Usuario();
        usuario.setNomeUsuario(profile.getName());
        usuario.setEmail("");
        usuario.setIdPerfilFacebook(accessToken.getUserId());
        usuario.setTokenFacebook(accessToken.getToken());
        usuario.setUrlPerfil(profile.getLinkUri().toString());
        usuario.setUrlFoto(profile.getProfilePictureUri(500, 500).toString());

        UsuarioNegocio.setUsuarioLogado(usuario);

        // Salva informações de login no SharedPreferences do usuario
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("id", accessToken.getUserId());
        editor.putString("token", accessToken.getToken());
        editor.commit();
    }

    public void entrar() {
        if (!(UsuarioNegocio.getUsuarioLogado() == null)) {
            String id = UsuarioNegocio.getUsuarioLogado().getIdPerfilFacebook();
            String token = UsuarioNegocio.getUsuarioLogado().getTokenFacebook();
            if (!(id == null) && !(token == null)) {
                Util.trocarTela(MainActivity.this, MapsActivity.class);
                finish();
            } else {
                Util.showMsgToastLong(MainActivity.this, getString(R.string.msg_precisa_logar));
            }
        } else {
            Util.showMsgToastLong(MainActivity.this, getString(R.string.msg_precisa_logar));
        }
    }
}
