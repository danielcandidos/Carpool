package com.carpool.android.gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.carpool.android.R;

import java.util.ArrayList;
import java.util.List;

public class PessoaActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RadioGroup rgpGenero;
    private RadioButton rbtOutros;
    private Spinner spnGenero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pessoa);

        // Mapeando a toolbar da tela e setando evento de clique para retornar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        Util.buildToolbarHomeButton(PessoaActivity.this, toolbar);

        // Testando Spinner de generos
        List<String> valores = new ArrayList<String>();
        valores.add("Homem (trans)");
        valores.add("Homem transexual");
        valores.add("Mulher (trans)");
        valores.add("Mulher transexual");
        valores.add("Neutro");
        valores.add("Pessoa trans");
        valores.add("Pessoa transexual");
        valores.add("Sem gênero");
        valores.add("Trans homem");
        valores.add("Trans mulher");
        valores.add("Transgênero");
        valores.add("Travesti");
        spnGenero = (Spinner) findViewById(R.id.spnGenero);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, valores);
        spnGenero.setAdapter(adapter);

        rgpGenero = (RadioGroup) findViewById(R.id.rgpGenero);
        rbtOutros = (RadioButton) findViewById(R.id.rbtOutros);
        rgpGenero.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selecionado = group.getCheckedRadioButtonId();
                if (selecionado == rbtOutros.getId()) {
                    spnGenero.setVisibility(View.VISIBLE);
                } else {
                    spnGenero.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    public void cadastrar(View view){

    }
}
