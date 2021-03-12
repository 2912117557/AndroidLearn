package com.example.myapplication.pagedList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.room.Room;

import com.example.myapplication.room.User;

public class PagedListViewModel extends ViewModel {

    public LiveData<PagedList<User>> usersList;

    PagedList.Config mPagingConfig = new PagedList.Config.Builder()
            .setPageSize(20)
            .setPrefetchDistance(60)
            .setEnablePlaceholders(false)
            .build();

    public PagedListViewModel(PagedListUserDao pagedListUserDao) {
        usersList = new LivePagedListBuilder<>(
                pagedListUserDao.usersByDate(), mPagingConfig).build();

    }


}
