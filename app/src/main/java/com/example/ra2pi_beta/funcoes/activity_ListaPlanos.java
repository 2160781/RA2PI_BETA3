package com.example.ra2pi_beta.funcoes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.ra2pi_beta.MainActivity;
import com.example.ra2pi_beta.PlayActivity;
import com.example.ra2pi_beta.R;

public class activity_ListaPlanos extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_planos);

        listviewPlanos();
    }

    public boolean listviewPlanos(){

        listView = findViewById(R.id.listview);

        int numP = PlayActivity.Main.dadosApp_.getNumeroPlanos();

        String[] values = new String[numP+1];

        for(int i = 0; i < PlayActivity.Main.dadosApp_.getNumeroPlanos(); i++) {
            values[i] = "" + PlayActivity.Main.dadosApp_.getTextPlano(i) + " - "
                    + PlayActivity.Main.dadosApp_.getNumeroPassosFeitos(i)
                    + " de " + PlayActivity.Main.dadosApp_.getSizeListPassos(i);
        }

        values[values.length-1] = "Voltar atrás";

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,values);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                position=position;

                for(int p = 0; p < PlayActivity.Main.dadosApp_.getNumeroPlanos(); p++) {
                    if(p == position){
                        Intent Tarefa = new Intent(view.getContext(),
                                activity_tarefas.class);
                        Tarefa.putExtra("NumeroPlano",position);
                        startActivity(Tarefa);
                    }else{
                            Intent main = new Intent(view.getContext(),
                                    MainActivity.class);
                            startActivity(main);
                    }
                }

            }
        });
        return true;
    }
}