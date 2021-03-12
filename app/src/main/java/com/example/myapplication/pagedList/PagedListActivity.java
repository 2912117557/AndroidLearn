package com.example.myapplication.pagedList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.DataSource;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.os.Bundle;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.room.User;


@Dao
interface PagedListUserDao  {

    @Insert
    long insertOne(User user);

    @Query("SELECT rowid,* FROM user")
    DataSource.Factory<Integer, User> usersByDate();

}

@Database(entities = {User.class}, version = 1,exportSchema = false)
abstract class PagedListAppDatabase extends RoomDatabase {
    public abstract PagedListUserDao pagedListUserDao();
}


public class PagedListActivity extends AppCompatActivity {

    PagedListAppDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paged_list);
        db = Room.databaseBuilder(getApplicationContext(),
                PagedListAppDatabase.class, "myDatabase2.db")
                .fallbackToDestructiveMigration()
                .build();

        PagedListViewModel pagedListViewModel=new ViewModelProvider(this,new PagedListViewModelFactory(db.pagedListUserDao()))
                .get(PagedListViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.pagedListRecycleView);
        UserAdapter useradapter = new UserAdapter();
        pagedListViewModel.usersList.observe(this, useradapter::submitList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(useradapter);

    }

    public void onAddDataClick(View v){
        User user=new User("wst",true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<300;i++){
                    db.pagedListUserDao().insertOne(user);
                }
            }
        }).start();
    }
}