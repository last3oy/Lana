package kmutt.senior.pet.model;



/**
 * Created by last3oy on 11/04/2016.
 */
public class DogProfile  {

    public int dogId;
    public String dogName;
    public int idDogGender;
    public String breed;
    public int idDogSize;
    public int age;
    public byte[] picture;


    public DogProfile() {
    }


    //Getter ---------
    public int getDogId() {
        return dogId;
    }

    public String getDogName() {
        return dogName;
    }

    public int getIdDogGender() {
        return idDogGender;
    }

    public String getBreed() {
        return breed;
    }

    public int getIdSize() {
        return idDogSize;
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

    public void setIdDogGender(int idDogGender) {
        this.idDogGender = idDogGender;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public void setIdSize(int idDogSize) {
        this.idDogSize = idDogSize;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }


}
