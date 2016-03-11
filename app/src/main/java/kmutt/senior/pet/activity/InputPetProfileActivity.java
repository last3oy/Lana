package kmutt.senior.pet.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.io.FileNotFoundException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import kmutt.senior.pet.R;

public class InputPetProfileActivity extends AppCompatActivity {

    private final int SELECT_PHOTO = 1;
    MaterialBetterSpinner spinnerBloodType;
    MaterialBetterSpinner spinnerBreed;
    CircleImageView imageViewPictureProfile;
    String[] BLOOD;
    String[] BREED;
    ArrayAdapter<String> adapter_blood;
    ArrayAdapter<String> adapter_breed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_pet_profile);

        initInstances();

    }

    private void initInstances() {
        BLOOD = getResources().getStringArray(R.array.blood);
        BREED = getResources().getStringArray(R.array.breed);

        spinnerBloodType = (MaterialBetterSpinner) findViewById(R.id.spinner_blood_type);
        spinnerBreed = (MaterialBetterSpinner) findViewById(R.id.spinner_breed);
        imageViewPictureProfile = (CircleImageView) findViewById(R.id.picture_profile);


        adapter_blood = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, BLOOD);
        adapter_breed = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, BREED);

        spinnerBloodType.setAdapter(adapter_blood);
        spinnerBreed.setAdapter(adapter_breed);

        imageViewPictureProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(InputPetProfileActivity.this,
                        "The favorite list would appear on clicking this icon",
                        Toast.LENGTH_LONG).show();
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
            }
        });






    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch(requestCode) {
            case SELECT_PHOTO:
                if(resultCode == RESULT_OK){
                    try {
                        final Uri imageUri = imageReturnedIntent.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        imageViewPictureProfile.setImageBitmap(selectedImage);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
        }
    }
}
