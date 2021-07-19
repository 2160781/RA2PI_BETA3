package com.example.ra2pi_beta.funcoes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ra2pi_beta.MainActivity;
import com.example.ra2pi_beta.PlayActivity;
import com.example.ra2pi_beta.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class PlanoQRCodeActivity extends AppCompatActivity {

    String plano;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plano_q_r_code);
        new IntentIntegrator(this).initiateScan();

        Toast toast = Toast.makeText(getApplicationContext(), "Voltar atrás: DPAD LEFT",
                Toast.LENGTH_SHORT);
        toast.show();
    }


    //Não funciona ver
  /*  @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent main = new Intent(getApplicationContext(),
                MainActivity.class);
        startActivity(main);
    }

    @Override
    public boolean dispatchKeyEvent( KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();

        switch (keyCode) {
            //Anterior
            case KeyEvent.KEYCODE_DPAD_LEFT:
                if (action == KeyEvent.ACTION_DOWN) {

                    Toast toast = Toast.makeText(getApplicationContext(), "MAU",
                            Toast.LENGTH_SHORT);
                    onBackPressed();
                }
                return true;
            default:
                return super.dispatchKeyEvent(event);
        }
    }
*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        String dados = result.getContents();
        Intent intent = null;


        for (int i = 0; i < PlayActivity.Main.dadosApp_.getNumeroPlanos(); i++) {

            plano = PlayActivity.Main.dadosApp_.getTextPlano(i);

            if (plano.equals(dados)) {
                Intent Plano = new Intent(this,
                        activity_tarefas.class);
                Plano.putExtra("NumeroPlano", i);
                startActivity(Plano);

            }

        }


        if (intent != null) {
            startActivity(intent);
        } else {
            Context context = getApplicationContext();
            CharSequence text = "QRCode invalido!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            new IntentIntegrator(this).initiateScan();
        }

    }


}