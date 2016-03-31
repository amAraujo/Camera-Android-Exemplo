package br.com.arthur.usingcameraandroid;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static br.com.arthur.usingcameraandroid.R.drawable.ic_pessoa;

public class MainActivity extends AppCompatActivity {

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

    @Bind(R.id.image_photo)
    ImageView mIvImagePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.image_photo)
    public void selectImage(View view){
        tirarFoto(view);
    }

    /** Metodo abre a camera e tira a foto */
    public void tirarFoto(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    /** Matodo que recebe o resultado da camera*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
                Bitmap imagem = (Bitmap) data.getExtras().get("data");
                mIvImagePhoto.setScaleType(ImageView.ScaleType.FIT_XY);
                mIvImagePhoto.setImageBitmap(imagem);

                //qualidade da camera
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                imagem.compress(Bitmap.CompressFormat.JPEG, 100, stream);

                String timeStand = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                byte[] bytes = stream.toByteArray();

                String nomeArquivo = Environment.getExternalStorageDirectory().getAbsolutePath() +
                        "/image" + timeStand + "jpeg";

                /** Salva a imagem no local especificado por nomeArquivo*/
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(nomeArquivo);
                    fileOutputStream.write(bytes);
                    fileOutputStream.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Toast.makeText(this, "Imagem salva com sucesso!", Toast.LENGTH_LONG).show();
            }
        }
    }
}
