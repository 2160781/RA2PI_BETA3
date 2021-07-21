package com.example.ra2pi_beta.funcionarios;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.KeyEvent;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ra2pi_beta.MainActivity;
import com.example.ra2pi_beta.R;

import java.text.Normalizer;
import java.util.ArrayList;

public class activity_NavegacaoVoz extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private static final int RECOGNIZE_SPEECH_ACTIVITY = 2;
    private static final int RECONHECEDOR_VOZ = 7;
    private TextView ouve;
    private TextView resposta;
    private ArrayList <Resposta> respostas;
    private TextToSpeech ler;
    private Object TextView;

    private String fala;
    private String resp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navegacaovoz);
        inicializar();
        ativacaoMicro();

        //Text view
      /*  Toast toast = Toast.makeText(getApplicationContext(), "Voltar atrás: DPAD LEFT"
                + "Ligar microfone: DPAD RIGTH",
                Toast.LENGTH_SHORT);*/
    }


    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode, resultCode, data );

        if (resultCode == RESULT_OK && requestCode == RECONHECEDOR_VOZ) {
            ArrayList<String> reconhecido =
                    data.getStringArrayListExtra( RecognizerIntent.EXTRA_RESULTS );
            String ouvir = reconhecido.get(0);
            fala = ouvir;
            ouve.setText(ouvir);
            prepararRespuesta(ouvir);
        }
    }

    private void prepararRespuesta (String ouvir) {
        String normalizar = Normalizer.normalize(ouvir, Normalizer.Form.NFD);
        String sint = normalizar.replaceAll("[^\\p{ASCII}]", "");

        for (int i = 0; i < respostas.size(); i++) {
            int resultado = sint.toLowerCase().indexOf(respostas.get(i).getFala());
            if (resultado != -1) {
                responder(respostas.get(i));
                return;
            }
        }
    }

    private void responder (Resposta resposta) {
        startActivity( resposta.getIntent() );
    }


    private void inicializar () {

        ouve = (TextView) findViewById(R.id.textView);
        resposta = (TextView) findViewById(R.id.tvRespuesta);
        respostas = provarDados();
        ler = new TextToSpeech(this, this);
    }

    private ArrayList <Resposta> provarDados () {
        ArrayList <Resposta> respostas = new ArrayList <>();

        //Qr code Planos
        respostas.add(new Resposta("scan", "  ",
                new Intent(this,
                PlanoQRCodeActivity.class)));
        respostas.add(new Resposta("qr code", "  ",
                new Intent(this,
                PlanoQRCodeActivity.class)));
        respostas.add(new Resposta("code", "  ",
                new Intent(this,
                        PlanoQRCodeActivity.class)));
        respostas.add(new Resposta("qr", "  ",
                new Intent(this,
                        PlanoQRCodeActivity.class)));
        respostas.add(new Resposta("scaner", "  ",
                new Intent(this,
                        PlanoQRCodeActivity.class)));

        //Lista dos Planos

        respostas.add(new Resposta("planos", "  ",
                new Intent(this,
                        activity_ListaPlanos.class)));
        respostas.add(new Resposta("lista planos", "  ",
                new Intent(this,
                        activity_ListaPlanos.class)));
        respostas.add(new Resposta("tarefas" , "  ",
                new Intent(this,
                        activity_ListaPlanos.class)));

        //Inicio
        respostas.add( new Resposta( "inicio", " ",
                new Intent(this,
                MainActivity.class)));

        return respostas;
    }

    @Override
    public boolean dispatchKeyEvent( KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        switch (keyCode) {
            //Ligar micro
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if (action == KeyEvent.ACTION_DOWN) {
                    ativacaoMicro();
                }
                return true;
            //Anterior
            case KeyEvent.KEYCODE_DPAD_LEFT:
                if (action == KeyEvent.ACTION_DOWN) {
                    Intent main = new Intent(getApplicationContext(),
                            MainActivity.class);
                    startActivity(main);
                }
                return true;
            default:
                return super.dispatchKeyEvent(event);
        }
    }

    public void ativacaoMicro(){
        Intent falar = new Intent( RecognizerIntent.ACTION_RECOGNIZE_SPEECH );
        falar.putExtra( RecognizerIntent.EXTRA_LANGUAGE_MODEL, "es-MX" );
        startActivityForResult( falar, RECONHECEDOR_VOZ);
    }

    @Override
    public void onInit(int status) {

    }
}
