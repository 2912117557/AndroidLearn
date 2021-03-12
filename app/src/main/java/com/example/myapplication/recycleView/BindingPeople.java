package com.example.myapplication.recycleView;

import android.view.View;

import androidx.databinding.Bindable;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableArrayMap;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.databinding.ObservableMap;

import java.util.Observable;

public class BindingPeople {
    public ObservableField<String> name = new ObservableField<>();
    public ObservableInt age = new ObservableInt();
    public BindingPeople(String name, int age) {
        this.name.set(name);
        this.age.set(age);
    }


}
