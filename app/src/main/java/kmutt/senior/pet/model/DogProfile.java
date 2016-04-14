package kmutt.senior.pet.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by last3oy on 11/04/2016.
 */
public class DogProfile implements Parcelable {

    public int dogId;
    public String dogName;
    public String breed;
    public String size;
    public int age;
    byte[] picture;


    public DogProfile() {
    }

    protected DogProfile(Parcel in) {
        dogId = in.readInt();
        dogName = in.readString();
        breed = in.readString();
        size = in.readString();
        age = in.readInt();
        picture = in.createByteArray();
    }

    public static final Creator<DogProfile> CREATOR = new Creator<DogProfile>() {
        @Override
        public DogProfile createFromParcel(Parcel in) {
            return new DogProfile(in);
        }

        @Override
        public DogProfile[] newArray(int size) {
            return new DogProfile[size];
        }
    };



    //Getter ---------
    public int getDogId() {
        return dogId;
    }

    public String getDogName() {
        return dogName;
    }

    public String getBreed() {
        return breed;
    }

    public String getSize() {
        return size;
    }

    public int getAge() {
        return age;
    }

    public byte[] getPicture() {
        return picture;
    }


    //Setter ---------
    public void setDogId(int dogId) {
        this.dogId = dogId;
    }

    public void setDogName(String dogName) {
        this.dogName = dogName;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(dogId);
        dest.writeString(dogName);
        dest.writeString(breed);
        dest.writeString(size);
        dest.writeInt(age);
        dest.writeByteArray(picture);
    }
}
