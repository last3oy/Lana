package kmutt.senior.pet.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.io.FileNotFoundException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import kmutt.senior.pet.R;
import kmutt.senior.pet.activity.InputPetProfileActivity;
import kmutt.senior.pet.bus.Contextor;

/**
 * Created by last3oy on 26/01/2016.
 */
public class InputPetProfileFragment extends Fragment {

    private final int SELECT_PHOTO = 1;
    MaterialBetterSpinner spinnerBreed;
    CircleImageView imageViewPictureProfile;
    String[] BREED;
    ArrayAdapter<String> adapter_blood;
    ArrayAdapter<String> adapter_breed;

    public InputPetProfileFragment() {
        super();
    }


    public static InputPetProfileFragment newInstance() {
        InputPetProfileFragment fragment = new InputPetProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_input_petprofile, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        // init instance with rootView.findViewById here
        //setRetainInstance(true);

        BREED = getResources().getStringArray(R.array.breed);


        spinnerBreed = (MaterialBetterSpinner) rootView.findViewById(R.id.spinner_breed);
        imageViewPictureProfile = (CircleImageView) rootView.findViewById(R.id.picture_profile);


        adapter_breed = new ArrayAdapter<String>(Contextor.getInstance().getContext(),
                android.R.layout.simple_dropdown_item_1line, BREED);

        spinnerBreed.setAdapter(adapter_breed);

        imageViewPictureProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),
                        "The favorite list would appear on clicking this icon",
                        Toast.LENGTH_LONG).show();
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    /*
     * Restore Instance State Here
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }
}
