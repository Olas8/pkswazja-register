package com.example.dziennikazja.db;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.dziennikazja.BR;

@Entity(foreignKeys = @ForeignKey(entity = Group.class,
        parentColumns = "nazwa",
        childColumns = "nazwa_grupy",
        onDelete = ForeignKey.SET_NULL,
        onUpdate = ForeignKey.CASCADE))
public class Member extends BaseObservable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "imie")
    public String firstName;

    @ColumnInfo(name = "nazwisko")
    public String lastName;

    @ColumnInfo(name = "pseudonim")
    public String pseudonym;

    @ColumnInfo(name = "id_grupy", index = true)
    public int groupId;

    @ColumnInfo(name = "nazwa_grupy")
    public String groupName;

    @ColumnInfo(name = "data_urodzenia")
    public String birthDate;

    @ColumnInfo(name = "adres")
    public String address;

    @ColumnInfo(name = "nr_tel")
    public String phoneNr;

    @ColumnInfo(name = "imie_ojca")
    public String fathersName;

    @ColumnInfo(name = "imie_matki")
    public String mothersName;

    @ColumnInfo(name = "data_dolaczenia")
    public String joinDate;

    @ColumnInfo(name = "stopien")
    public String tkdGrade;



    public Member(String firstName, String lastName, String pseudonym, String birthDate, String address, String phoneNr, String fathersName, String mothersName, String joinDate, String tkdGrade, String groupName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.pseudonym = pseudonym;
        this.birthDate = birthDate;
        this.address = address;
        this.phoneNr = phoneNr;
        this.fathersName = fathersName;
        this.mothersName = mothersName;
        this.joinDate = joinDate;
        this.tkdGrade = tkdGrade;
        this.groupName = groupName;
    }

    @Ignore
    public Member(String firstName) {
        this.firstName = firstName;
    }

    @Ignore
    public Member() {
    }

    @Bindable
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        notifyPropertyChanged(BR.firstName);
    }

    @Bindable
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        notifyPropertyChanged(BR.lastName);
    }

    @Bindable
    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
        notifyPropertyChanged(BR.lastName);
    }

    @Bindable
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        notifyPropertyChanged(BR.address);
    }

    @Bindable
    public String getPhoneNr() {
        return phoneNr;
    }

    public void setPhoneNr(String phoneNr) {
        this.phoneNr = phoneNr;
        notifyPropertyChanged(BR.phoneNr);
    }

    @Bindable
    public String getFathersName() {
        return fathersName;
    }

    public void setFathersName(String fathersName) {
        this.fathersName = fathersName;
        notifyPropertyChanged(BR.fathersName);
    }

    @Bindable
    public String getMothersName() {
        return mothersName;
    }

    public void setMothersName(String mothersName) {
        this.mothersName = mothersName;
        notifyPropertyChanged(BR.mothersName);
    }

    @Bindable
    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
        notifyPropertyChanged(BR.joinDate);
    }

    @Bindable
    public String getTkdGrade() {
        return tkdGrade;
    }

    public void setTkdGrade(String tkdGrade) {
        this.tkdGrade = tkdGrade;
        notifyPropertyChanged(BR.tkdGrade);
    }

    @Bindable
    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
        notifyPropertyChanged(BR.groupId);
    }

    @Bindable
    public String getPseudonym() {
        return pseudonym;
    }

    public void setPseudonym(String pseudonym) {
        this.pseudonym = pseudonym;
        notifyPropertyChanged(BR.pseudonym);
    }

    @Bindable
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
        notifyPropertyChanged(BR.groupName);
    }
}
