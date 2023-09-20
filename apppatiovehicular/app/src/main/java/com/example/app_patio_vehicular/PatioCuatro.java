package com.example.app_patio_vehicular;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

public class PatioCuatro extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 101;
    private static final int FACE_RECT_COLOR = Color.RED; // Color del cuadro (en este caso, rojo)

    private ImageView imageView;
    private Button captureButton;
    private FaceDetector faceDetector;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return MenuUtils.onCreateOptionsMenu(menu, this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return MenuUtils.onOptionsItemSelected(item, this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patio_cuatro);

        imageView = findViewById(R.id.imageView);
        captureButton = findViewById(R.id.captureButton);

        faceDetector = new FaceDetector.Builder(this)
                .setTrackingEnabled(false)
                .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                .build();

        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
            detectFaces(imageBitmap);
        }
    }

    private void detectFaces(Bitmap imageBitmap) {
        if (!faceDetector.isOperational()) {
            // Manejar el caso en el que el detector no esté operativo
            return;
        }

        // Crear una copia mutable del bitmap para poder editarlo
        Bitmap mutableBitmap = imageBitmap.copy(Bitmap.Config.ARGB_8888, true);

        Frame frame = new Frame.Builder().setBitmap(mutableBitmap).build();
        SparseArray<Face> faces = faceDetector.detect(frame);

        // Crear un lienzo para dibujar el cuadro en la imagen
        Canvas canvas = new Canvas(mutableBitmap);


        Paint paint = new Paint();
        paint.setColor(FACE_RECT_COLOR);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);

        for (int i = 0; i < faces.size(); i++) {
            Face face = faces.valueAt(i);

            float x1 = face.getPosition().x;
            float y1 = face.getPosition().y;
            float x2 = x1 + face.getWidth();
            float y2 = y1 + face.getHeight();

            canvas.drawRect(x1, y1, x2, y2, paint);

            float smileProb = face.getIsSmilingProbability();
            Toast.makeText(this, "Probabilidad de sonrisa: " + smileProb, Toast.LENGTH_SHORT).show();
        }

        imageView.setImageBitmap(mutableBitmap);
    }

}