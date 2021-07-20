package com.example.ra2pi_beta.administradores;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.fonts.SystemFonts;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ra2pi_beta.PlayActivity;
import com.example.ra2pi_beta.R;
import com.example.ra2pi_beta.funcoes.PlanoQRCodeActivity;
import com.example.ra2pi_beta.funcoes.activity_ListaPlanos;
import com.example.ra2pi_beta.funcoes.activity_NavegacaoVoz;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MainAdministradoresActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    ListView listViewTrabalhador;
    String[] usernames;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_administradores);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        listViewTrabalhador = findViewById(R.id.listviewinicial);
        getUsers();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
          //      listviewUtilizadorTrabalhador();
            }
        }, 3000);
    }


    public void getUsers(){

        DatabaseReference reference1 = mDatabase;
        List<String> userList = new ArrayList<>();

        reference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for(DataSnapshot singleSnapshot : snapshot.getChildren()){
                    String username = singleSnapshot.getKey();
                    Boolean tipo = isTrabalhador(username);

                    System.out.println(tipo);
                    if(tipo){
                        userList.add(username);
                        Toast toast = Toast.makeText(getApplicationContext(), "" + username
                                , Toast.LENGTH_SHORT);
                        toast.show();
                        System.out.println(username);

                    }
                }
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if(userList != null){
                    usernames = new String[userList.size()];

                    int i = 0;
                    for (String user:userList) {
                        usernames[i] = user;
                        i++;
                    }
                }
            }
        }, 1500);

    }

    public boolean isAdministrador(String username){
        DatabaseReference reference1 = mDatabase.child(username);

        try {
            reference1.child("tipo").getKey();
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public boolean isTrabalhador(String username){
        DatabaseReference reference1 = mDatabase.child(username);
        String key = null;

        try {
            key = reference1.child("0").child("name").getKey();
        }catch (Exception e){

        }

        if(key.equals(null)){
            return false;
        }else{
            return true;
        }

    }

    public boolean listviewUtilizadorTrabalhador() {

        String[] values = new String[usernames.length+1];

        for(int i = 0; i < values.length; i++) {
            values[i] = "Trabalhador: " + usernames[i];
        }

        values[values.length-1] = "Criar utilizador";

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,values);

        listViewTrabalhador.setAdapter(adapter);

        listViewTrabalhador.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                position=position;

                for(int p = 0; p < values.length; p++) {
                    if(p == position){

                    }
                }

            }
        });
        return true;
    }
}