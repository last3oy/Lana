package kmutt.senior.pet.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;


import com.rengwuxian.materialedittext.MaterialEditText;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.io.FileNotFoundException;
import java.io.IOException;


import de.hdodenhof.circleimageview.CircleImageView;
import kmutt.senior.pet.R;
import kmutt.senior.pet.bus.Contextor;
import kmutt.senior.pet.model.DogProfile;
import kmutt.senior.pet.service.DatabaseHelper;
import kmutt.senior.pet.service.DbBitmapUtility;


public class InputPetProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private final int SELECT_PHOTO = 1;
    DatabaseHelper db;
    MaterialBetterSpinner spinnerBreed;
    MaterialBetterSpinner spinnerSize;
    MaterialEditText edtName;
    MaterialEditText edtAge;
    CircleImageView imageViewPictureProfile;
    String[] BREED;
    String[] SIZE;
    Button btnSummit;
    ArrayAdapter<String> adapter_breed;
    ArrayAdapter<String> adapter_size;
    Toolbar toolbar;
    DogProfile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_petprofile);


        initInstances();


       /*if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer, InputPetProfileFragment.newInstance())
                    .commit();
        }*/

    }

    private void initInstances() {

        db = new DatabaseHelper(getApplicationContext());

        profile = new DogProfile();

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        BREED = getResources().getStringArray(R.array.breed);
        SIZE = getResources().getStringArray(R.array.size);

        edtName = (MaterialEditText) findViewById(R.id.edtName);
        edtAge = (MaterialEditText) findViewById(R.id.edtAge);
        spinnerSize = (MaterialBetterSpinner) findViewById(R.id.spinnerSize);
        spinnerBreed = (MaterialBetterSpinner) findViewById(R.id.spinnerBreed);
        imageViewPictureProfile = (CircleImageView) findViewById(R.id.pictureProfile);
        btnSummit = (Button) findViewById(R.id.btnSummit);


        adapter_breed = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_dropdown_item_1line, BREED);

        adapter_size = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_dropdown_item_1line, SIZE);

        spinnerBreed.setAdapter(adapter_breed);
        spinnerSize.setAdapter(adapter_size);

        imageViewPictureProfile.setOnClickListener(this);
        btnSummit.setOnClickListener(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    /*try {
                        final Uri imageUri = data.getData();
                        final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        imageViewPictureProfile.setImageBitmap(selectedImage);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }*/
                    Uri uri = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        imageViewPictureProfile.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    /*InputPetProfileActivity activity = (InputPetProfileActivity) getActivity();
                    Bitmap bitmap = getBitmapFromCameraData(data, activity);
                    imageViewPictureProfile.setImageBitmap(bitmap);
                    */

                }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public void onClick(View v) {
        if (v == imageViewPictureProfile) {
            Toast.makeText(this,
                    "The favorite list would appear on clicking this icon",
                    Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent
                    , "Select photo from"), SELECT_PHOTO);

        } else if (v == btnSummit) {
            setDogProfile();
            db.createProfile(profile);
            finish();

        }
    }

    private void setDogProfile() {
        Bitmap img = ((BitmapDrawable) imageViewPictureProfile.getDrawable()).getBitmap();
        // Bitmap img = ((BitmapDrawable)((LayerDrawable) imageViewPictureProfile.getDrawable()).getDrawable(0)).getBitmap(‌​);
        Log.i("TEST", "" + DbBitmapUtility.getBytes(img));
        Log.i("TEST", "" + spinnerBreed.getText().toString());
        Log.i("TEST", "" + edtName.getText().toString());
        Log.i("TEST", "" + spinnerSize.getText().toString());
        Log.i("TEST", "" + Integer.parseInt(edtAge.getText().toString()));


        profile.setPicture(DbBitmapUtility.getBytes(img));
        profile.setBreed(spinnerBreed.getText().toString());
        profile.setDogName(edtName.getText().toString());
        profile.setSize(spinnerSize.getText().toString());
        profile.setAge(Integer.parseInt(edtAge.getText().toString()));

    }

    @Override
    public void finish() {
        super.finish();
    }
}
