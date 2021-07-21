package com.example.ra2pi_beta;

import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ra2pi_beta.funcionarios.PlanoQRCodeActivity;
import com.example.ra2pi_beta.funcionarios.activity_NavegacaoVoz;
import com.example.ra2pi_beta.funcionarios.activity_ListaPlanos;
import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity  implements AutoPermissionsListener {

    public void onRequestPermissionsResult(int requestCode,String permissions[],int[] grantResults){
         super.onRequestPermissionsResult(requestCode,permissions,grantResults);
         AutoPermissions.Companion.parsePermissions(this,requestCode,permissions,this);
    }
    @Override
    public void onDenied(int i, @NotNull String[] strings) {
        Toast.makeText(this, "permissions denied"+strings.length, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGranted(int i, @NotNull String[] strings) {
        Toast.makeText(this, "permissions granted"+ strings.length, Toast.LENGTH_SHORT).show();
    }

    public class CameraSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
        private SurfaceHolder mHolder;
        private Camera camera = null;

        public CameraSurfaceView (Context context){
            super(context);
            mHolder = getHolder();
            mHolder.addCallback(this);
        }
        public void surfaceCreated(SurfaceHolder holder){
            camera = Camera.open();
            try {
                camera.setPreviewDisplay(mHolder);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        public void surfaceChanged (SurfaceHolder holder, int format,int width, int height){
            camera.startPreview();
        }

        public void surfaceDestroyed (SurfaceHolder holder){
            camera.stopPreview();
            camera.release();
            camera = null;
        }

        public boolean capture(Camera.PictureCallback handler){
            if (camera != null){
                camera.takePicture(null,null,handler);
                return true;
            }else {
                return false;
            }
        }
    }

    CameraSurfaceView cameraView;


    ListView listViewInicial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lisviewinicial);
        FrameLayout previewFrame = findViewById(R.id.previewFrame);
        cameraView = new CameraSurfaceView(this);
        previewFrame.addView(cameraView);

        AutoPermissions.Companion.loadAllPermissions(this,101);
        listviewInicial();
    }


    public boolean listviewInicial() {
        


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

