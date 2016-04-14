package kmutt.senior.pet.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import kmutt.senior.pet.R;
import kmutt.senior.pet.bus.Contextor;
import kmutt.senior.pet.model.DogProfile;

/**
 * Created by last3oy on 31/03/2016.
 */
public class DogProfileAdapter extends BaseAdapter {
    private ArrayList<DogProfile> mProfile;
    LayoutInflater mInflator;

    public DogProfileAdapter(Context context,ArrayList<DogProfile> Profile) {
        mInflator = LayoutInflater.from(context);
        this.mProfile = Profile;
        for (DogProfile todo : this.mProfile) {
            Log.d("ToDo", todo.getDogName());
        }
    }

    public DogProfile getProfile(int position){
        return mProfile.get(position);
    }
    @Override
    public int getCount() {
        return mProfile.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        // General ListView optimization code
        if (convertView == null) {
            convertView = mInflator.inflate(R.layout.listitem_profile,null);
            viewHolder = new ViewHolder();
            viewHolder.profileName = (TextView) convertView.findViewById(R.id.profileName);
            viewHolder.profileBreed = (TextView) convertView.findViewById(R.id.profileBreed);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        DogProfile Profile = mProfile.get(position);
        viewHolder.profileName.setText("Name: "+Profile.getDogName());
        viewHolder.profileBreed.setText("Breed: "+Profile.getBreed());

        return convertView;
    }

    static class ViewHolder {
        TextView profileName;
        TextView profileBreed;
    }
}
