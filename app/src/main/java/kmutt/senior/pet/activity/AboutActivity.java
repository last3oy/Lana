package kmutt.senior.pet.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import kmutt.senior.pet.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Animation myRotation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotator);
        CircleImageView myImage =  (CircleImageView)findViewById(R.id.book);
        myImage.startAnimation(myRotation);

        final Animation myRotation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
        CircleImageView myImage1 =  (CircleImageView)findViewById(R.id.pai);
        myImage1.startAnimation(myRotation1);

        final Animation myRotation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.myanimation);
        CircleImageView myImage2 =  (CircleImageView)findViewById(R.id.yai);
        myImage2.startAnimation(myRotation2);

        final Animation myRotation3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
        TextView text1 =  (TextView)findViewById(R.id.textView2);
        text1.startAnimation(myRotation3);

        final Animation myRotation4 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
        TextView text2 =  (TextView)findViewById(R.id.textView);
        text2.startAnimation(myRotation4);

        final Animation myRotation5 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
        TextView text3 =  (TextView)findViewById(R.id.textView1);
        text3.startAnimation(myRotation5);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
