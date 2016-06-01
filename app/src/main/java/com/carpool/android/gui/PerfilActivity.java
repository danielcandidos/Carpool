package com.carpool.android.gui;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.carpool.android.R;
import com.carpool.android.dominio.Usuario;
import com.carpool.android.negocio.CaronaProcurarNegocio;
import com.carpool.android.negocio.UsuarioNegocio;

public class PerfilActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView txtPerfilNome;
    private Usuario motorista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        // Mapeando e reconhecendo a toolbar da tela
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        Util.buildToolbarHomeButton(PerfilActivity.this, toolbar);

        motorista = CaronaProcurarNegocio.getCaronaPedida().getItinerario().getMotorista();

        txtPerfilNome = (TextView) findViewById(R.id.txtPerfilNome);
        txtPerfilNome.setText(motorista.getNomeUsuario());
    }

    public void sendEmail(View view) {
        if (!(motorista.getEmail() == null)){
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto: " + motorista.getEmail()));
            intent.putExtra(Intent.EXTRA_SUBJECT,
                    "CARPOOL - Nova Solicitação de Carona");
            intent.putExtra(Intent.EXTRA_TEXT,
                    UsuarioNegocio.getUsuarioLogado().getNomeUsuario() + " está solicitando uma carona a você! UHUUUL");
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        } else {
            Util.showMsgToastLong(PerfilActivity.this, getString(R.string.msg_sem_email));
        }
    }

    public void sendWhatsapp(View view) {
        Util.showMsgAlertOk(PerfilActivity.this, "Whatsapp:", motorista.getTelefone());
        /*PackageManager pm = getPackageManager();
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setData(Uri.parse("smsto: +5581999984782 "));
            intent.setType("text/plain");
            PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            intent.setPackage("com.whatsapp");
            intent.putExtra(Intent.EXTRA_TEXT, "Eaeee");
            startActivity(intent);

        } catch (PackageManager.NameNotFoundException n) {
            Util.showMsgToastLong(PerfilActivity.this, "Whatsapp não está instalado =/");
        }*/
    }

    public void sendFacebook(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://facebook.com/"+motorista.getIdPerfilFacebook()));
        startActivity(intent);
    }

}
