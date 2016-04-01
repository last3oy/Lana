package kmutt.senior.pet.activity;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import kmutt.senior.pet.R;
import kmutt.senior.pet.fragment.WelcomeFragment;

public class WelcomeActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer,WelcomeFragment.newInstance())
                    .commit();
        }
    }




}
