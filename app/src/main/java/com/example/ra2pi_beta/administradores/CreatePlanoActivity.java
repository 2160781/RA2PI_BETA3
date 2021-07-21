package com.example.ra2pi_beta.administradores;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ra2pi_beta.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreatePlanoActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;

    Button btnCreatePlano;
    Button btnCancel;
    EditText editTextPlanoname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_plano);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        editTextPlanoname = findViewById(R.id.editTextPlano);
        btnCreatePlano = findViewById(R.id.btn_createPlano);
        btnCancel = findViewById(R.id.btn_cancel);

        btnCreatePlano.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                createPlano();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                cancelAndBack();
            }
        });

    }

    public void createPlano(){
        String userplano = editTextPlanoname.getText().toString();
        DatabaseReference reference;
        if (userplano!=null){
            if(!planoExist(userplano)){
                reference = mDatabase;
                reference.child(String.valueOf(Integer.parseInt(userplano)));

                Toast.makeText(this, "Plano com o nome "+userplano+ " criado!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(),ViewListPlanosUtilizadorActivity.class);
                startActivity(intent);
            }else {
                Toast.makeText(this, "Plano já existe", Toast.LENGTH_SHORT).show();
            }

        }

    }

    public void cancelAndBack(){
        Intent intent = new Intent(getApplicationContext(),ViewListPlanosUtilizadorActivity.class);
        startActivity(intent);

    }

    public boolean planoExist(String planos){
        if(MainAdministradoresActivity.MainAdministradores.usernames.length>0){
            String[] listUsernames = MainAdministradoresActivity.MainAdministradores.usernames;
            String[] listPlanos = MainAdministradoresActivity.MainAdministradores.planos;

            for (int i = 0; i<listUsernames.length;i++){
                for(int p = 0; p<listPlanos.length;p++){
                    if(listPlanos[i].equals(planos)){
                        return true;
                    }
                }
            }
            return false;
        }else {
            return false;
        }
    }
}