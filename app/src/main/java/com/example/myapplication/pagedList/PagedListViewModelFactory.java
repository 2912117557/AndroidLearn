package com.example.myapplication.pagedList;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.lang.reflect.InvocationTargetException;

public class PagedListViewModelFactory implements ViewModelProvider.Factory {
    PagedListUserDao pagedListUserDao;
    public PagedListViewModelFactory(PagedListUserDao pagedListUserDao){
        this.pagedListUserDao=pagedListUserDao;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass){
//        return (T)new MyViewModel(db);
        T viewmodel = null;
        try {
            viewmodel = modelClass.getConstructor(PagedListUserDao.class).newInstance(pagedListUserDao);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        return viewmodel;

    }
}