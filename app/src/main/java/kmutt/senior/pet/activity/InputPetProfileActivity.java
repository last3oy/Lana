package kmutt.senior.pet.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;


import com.rengwuxian.materialedittext.MaterialEditText;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;


import de.hdodenhof.circleimageview.CircleImageView;
import kmutt.senior.pet.R;
import kmutt.senior.pet.model.DogProfileInput;
import kmutt.senior.pet.util.DatabaseHelper;
import kmutt.senior.pet.util.DbBitmapUtility;


public class InputPetProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private final int SELECT_PHOTO = 1;

    private String[] BREED;
    private String[] SIZE;
    private String[] GENDER;
    private DatabaseHelper db;
    private MaterialBetterSpinner spinnerBreed;
    private MaterialBetterSpinner spinnerSize;
    private MaterialBetterSpinner spinnerGender;
    private MaterialEditText edtName;
    private MaterialEditText edtAge;
    private CircleImageView imageViewPictureProfile;


    private Button btnSummit;
    private ArrayAdapter<String> adapter_breed;
    private ArrayAdapter<String> adapter_size;
    private ArrayAdapter<String> adapter_gender;
    private DogProfileInput profile;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_petprofile);
        initInstances();
    }

    private void initInstances() {

        db = new DatabaseHelper(getApplicationContext());

        profile = new DogProfileInput();


        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add my dog");

        BREED = getResources().getStringArray(R.array.breed);
        SIZE = getResources().getStringArray(R.array.size);
        GENDER = getResources().getStringArray(R.array.gender);


        edtName = (MaterialEditText) findViewById(R.id.edtName);
        edtAge = (MaterialEditText) findViewById(R.id.edtAge);
        spinnerSize = (MaterialBetterSpinner) findViewById(R.id.spinnerSize);
        spinnerBreed = (MaterialBetterSpinner) findViewById(R.id.spinnerBreed);
        spinnerGender = (MaterialBetterSpinner) findViewById(R.id.spinnerGender);
        imageViewPictureProfile = (CircleImageView) findViewById(R.id.pictureProfile);
        btnSummit = (Button) findViewById(R.id.btnSummit);


        adapter_breed = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_dropdown_item_1line, BREED);

        adapter_size = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_dropdown_item_1line, SIZE);

        adapter_gender = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_dropdown_item_1line, GENDER);


        spinnerBreed.setAdapter(adapter_breed);
        spinnerSize.setAdapter(adapter_size);
        spinnerGender.setAdapter(adapter_gender);


        initAlertDialog();

        imageViewPictureProfile.setOnClickListener(this);
        btnSummit.setOnClickListener(this);

    }

    private void initAlertDialog() {
        alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("Need to add all fields");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        Uri imageUri = data.getData();
                        InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        selectedImage.compress(Bitmap.CompressFormat.JPEG, 50, stream);
                        imageViewPictureProfile.setImageBitmap(selectedImage);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

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
            if (checkInput()) {
                setDogProfile();
                db.createProfile(profile);
                finish();
            } else {
                alertDialog.show();
            }


        }
    }

    private boolean checkInput() {
        if (edtName.getText().toString().isEmpty())
            return false;
        if (spinnerGender.getText().toString().isEmpty())
            return false;
        if (spinnerBreed.getText().toString().isEmpty())
            return false;
        if (spinnerSize.getText().toString().isEmpty())
            return false;
        if (edtAge.getText().toString().isEmpty())
            return false;
        return true;
    }

    private void setDogProfile() {
        Bitmap img = ((BitmapDrawable) imageViewPictureProfile.getDrawable()).getBitmap();

        profile.setPicture(DbBitmapUtility.getBytes(img));
        profile.setDogName(edtName.getText().toString());
        profile.setIdDogGender(setIdGender(spinnerGender.getText().toString()));
        profile.setBreed(spinnerBreed.getText().toString());
        profile.setIdSize(setSize(spinnerSize.getText().toString()));
        profile.setAge(Integer.parseInt(edtAge.getText().toString()));


    }

    private int setIdGender(String gender) {
        if (gender.matches(getString(R.string.gender_male))) {
            return 1;
        } else
            return 2;
    }

    private int setSize(String size) {

        switch (size) {
            case "Small (<13 Kg)":
                return 1;
            case "Medium-Large (13-40 Kg)":
                return 2;
            case "X-Large (>40 Kg)":
                return 3;
        }
        return 0;
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.enter_from_right,R.anim.exit_out_left);

    }
}