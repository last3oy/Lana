package kmutt.senior.pet.model;

/**
 * Created by last3oy on 11/04/2016.
 */
public class DogProfile {

    int dogId;
    String dogName;
    String breed;
    String size;
    int age;
    byte[] picture;


    public DogProfile() {
    }

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
}
