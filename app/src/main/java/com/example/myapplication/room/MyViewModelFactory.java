package com.example.myapplication.room;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.lang.reflect.InvocationTargetException;

public class MyViewModelFactory implements ViewModelProvider.Factory {
    AppDatabase db;
    public MyViewModelFactory(AppDatabase db) {
        this.db=db;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass){
//        return (T)new MyViewModel(db);
        T viewmodel = null;
        try {
            viewmodel = modelClass.getConstructor(AppDatabase.class).newInstance(db);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        return viewmodel;

    }
}