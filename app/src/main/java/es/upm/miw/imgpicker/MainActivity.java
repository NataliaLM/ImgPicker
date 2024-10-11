package es.upm.miw.imgpicker;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    static final String TAG = "MiW";
    static final int RC_IMAGE_PICK = 2025;

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        imageView = findViewById(R.id.imageView);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear intent implícito para elegir una imagen
                Intent intentImgPicker = new Intent(Intent.ACTION_GET_CONTENT);
                intentImgPicker.setType("image/*");
                intentImgPicker.putExtra(Intent.EXTRA_LOCAL_ONLY, true);

                // lanzar actividad
                startActivityForResult(
                        Intent.createChooser(
                                intentImgPicker,
                                getString(R.string.strActionPicker)
                        ),
                        RC_IMAGE_PICK
                );
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RC_IMAGE_PICK: {
                if (resultCode == RESULT_OK && data != null) { // Mostrar imagen
                    Uri selectedImageUri = data.getData();
                    if (selectedImageUri != null) {
                        Log.i(TAG, "URI: " + selectedImageUri.toString());
                        imageView.setImageURI(selectedImageUri);
                    }
                }
            }
            case RESULT_CANCELED: {
                Log.i(TAG, "Operación cancelada");
            }
        }
    }
}
