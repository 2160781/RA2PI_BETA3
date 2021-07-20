package com.example.ra2pi_beta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ra2pi_beta.funcoes.PlanoQRCodeActivity;
import com.example.ra2pi_beta.funcoes.activity_NavegacaoVoz;
import com.example.ra2pi_beta.funcoes.activity_ListaPlanos;

public class MainActivity extends AppCompatActivity {

    ListView listViewInicial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listviewInicial();
    }


    public boolean listviewInicial() {
        
        setContentView(R.layout.activity_lisviewinicial);

        listViewInicial = findViewById(R.id.listviewinicial);

        String[] values = new String[] {
                "Leitura de Qr Code",
                "Lista planos",
                "Microfone"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,values);

        listViewInicial.setAdapter(adapter);

        listViewInicial.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                position=position;
                if(position == 0){
                    Intent scan = new Intent(view.getContext(),
                            PlanoQRCodeActivity.class);
                    startActivity(scan);
                }
                if (position == 1) {
                    Intent planos = new Intent(view.getContext(),
                            activity_ListaPlanos.class);
                    startActivity(planos);
                }
                if (position == 2) {
                    Intent micro = new Intent(view.getContext(),
                            activity_NavegacaoVoz.class);
                    startActivity(micro);
                }
            }
        });
        return true;
    }
}

