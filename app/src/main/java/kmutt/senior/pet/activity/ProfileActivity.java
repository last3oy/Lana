package kmutt.senior.pet.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
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
import kmutt.senior.pet.model.DogProfile;
import kmutt.senior.pet.model.DogProfileDTO;
import kmutt.senior.pet.service.DatabaseHelper;
import kmutt.senior.pet.service.DbBitmapUtility;

/**
 * Created by last3oy on 14/04/2016.
 */
public class ProfileActivity extends AppCompatActivity {

    private final int SELECT_PHOTO = 1;
    private int idProfile;
    private CircleImageView pictureProfile;
    private MaterialEditText edtName;
    private MaterialBetterSpinner spinnerGender;
    private MaterialBetterSpinner spinnerBreed;
    private MaterialBetterSpinner spinnerSize;
    private MaterialEditText edtAge;
    private Button btnSummit;
    private AlertDialog alertDialog;
    private String[] BREED;
    private String[] SIZE;
    private String[] GENDER;
    private ArrayAdapter<String> adapter_breed;
    private ArrayAdapter<String> adapter_size;
    private ArrayAdapter<String> adapter_gender;

    private DogProfileDTO profile;
    private DatabaseHelper db;
    private boolean bool = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initInstances();

    }


    private void initInstances() {
        Intent intent = getIntent();
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My Dog");
        Bundle bundle = intent.getBundleExtra("idBundle");

        BREED = getResources().getStringArray(R.array.breed);
        SIZE = getResources().getStringArray(R.array.size);
        GENDER = getResources().getStringArray(R.array.gender);

        pictureProfile = (CircleImageView) findViewById(R.id.pictureProfile);
        edtName = (MaterialEditText) findViewById(R.id.edtName);
        spinnerGender = (MaterialBetterSpinner) findViewById(R.id.spinnerGender);
        spinnerBreed = (MaterialBetterSpinner) findViewById(R.id.spinnerBreed);
        spinnerSize = (MaterialBetterSpinner) findViewById(R.id.spinnerSize);
        edtAge = (MaterialEditText) findViewById(R.id.edtAge);
        btnSummit = (Button) findViewById(R.id.btnSummit);
        idProfile = bundle.getInt("idProfile");

        adapter_breed = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_dropdown_item_1line, BREED);

        adapter_size = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_dropdown_item_1line, SIZE);

        adapter_gender = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_dropdown_item_1line, GENDER);





        db = new DatabaseHelper(this);
        profile = db.getDogProfile(idProfile);
        if (profile != null) {
            Toast.makeText(this, "haha", Toast.LENGTH_LONG).show();
        }

        pictureProfile.setImageBitmap(DbBitmapUtility.getImage(profile.getPicture()));
        edtName.setText(profile.getName());
        spinnerGender.setText(profile.getGender());
        spinnerBreed.setText(profile.getBreed());
        spinnerSize.setText(profile.getSize());
        edtAge.setText(String.valueOf(profile.getAge()));

        pictureProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent
                        , "Select photo from"), SELECT_PHOTO);
            }
        });

        alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("Need to add all fields");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        btnSummit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInput()) {
                    setDogProfile();
                    db.updateProfile(setDogProfile());
                    finish();
                } else {
                    alertDialog.show();
                }
            }
        });

        setViewEnabled(false);


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
                        pictureProfile.setImageBitmap(selectedImage);


                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }


                }
        }
    }

    private void setViewEnabled(boolean enabled) {
        pictureProfile.setEnabled(enabled);
        edtName.setEnabled(enabled);
        spinnerGender.setEnabled(enabled);
        spinnerBreed.setEnabled(enabled);
        spinnerSize.setEnabled(enabled);
        edtAge.setEnabled(enabled);
        spinnerBreed.setAdapter(adapter_breed);
        spinnerSize.setAdapter(adapter_size);
        spinnerGender.setAdapter(adapter_gender);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_edit:
                if (bool) {
                    setViewEnabled(bool);
                    btnSummit.setVisibility(View.VISIBLE);
                    item.setTitle("Undo");
                    bool = false;
                } else {
                    setViewEnabled(bool);
                    btnSummit.setVisibility(View.GONE);
                    item.setTitle("Edit");
                    bool = true;
                }

                return true;
            case R.id.action_delete:
                db.deleteProfile(profile.getId());
                finish();
                return true;
        }


        return super.onOptionsItemSelected(item);
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

    private DogProfile setDogProfile() {
        DogProfile mProfile = new DogProfile();
        Bitmap img = ((BitmapDrawable) pictureProfile.getDrawable()).getBitmap();

        Log.i("TEST", "" + DbBitmapUtility.getBytes(img));
        Log.i("TEST", "" + spinnerBreed.getText().toString());
        Log.i("TEST", "" + edtName.getText().toString());
        Log.i("TEST", "" + spinnerSize.getText().toString());
        Log.i("TEST", "" + Integer.parseInt(edtAge.getText().toString()));

        mProfile.setPicture(DbBitmapUtility.getBytes(img));
        mProfile.setDogName(edtName.getText().toString());
        mProfile.setIdDogGender(setIdGender(spinnerGender.getText().toString()));
        mProfile.setBreed(spinnerBreed.getText().toString());
        mProfile.setIdSize(setSize(spinnerSize.getText().toString()));
        mProfile.setAge(Integer.parseInt(edtAge.getText().toString()));
        mProfile.setDogId(idProfile);

        return mProfile;

    }

    private int setIdGender(String gender) {
        if (gender.matches(getString(R.string.gender_male))) {
            //  Log.i("return", getString(R.string.gender_male));
            return 1;
        } else
            // Log.i("return", getString(R.string.gender_female));
            return 2;
    }

    private int setSize(String size) {
        Log.i("setidsize", size);
        String s = getString(R.string.size_small);
        String m = getString(R.string.size_medium);

        switch (size) {
            case "Small (<13 Kg)":
                //  Log.i("return", getString(R.string.size_small));
                return 1;
            case "Medium-Large (13-40 Kg)":
                // Log.i("return", getString(R.string.size_medium));
                return 2;
            case "X-Large (>40 Kg)":
                // Log.i("return", getString(R.string.size_large));
                return 3;
        }
        // Log.i("return", "0");
        return 0;
    }

}
