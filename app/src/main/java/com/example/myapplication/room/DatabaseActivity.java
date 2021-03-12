package com.example.myapplication.room;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.Update;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowInsets;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityDatabaseBinding;

import java.util.List;


public class DatabaseActivity extends AppCompatActivity {

    ActivityDatabaseBinding binding;
    AppDatabase db;
    List<User> users2;
    LiveData<List<User>> users3;
    Handler handler =new Handler();


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDatabaseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.setLifecycleOwner(this);
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "myDatabase")
                .fallbackToDestructiveMigration()
                .build();

        MyViewModel model=new ViewModelProvider(this,new MyViewModelFactory(db)).get(MyViewModel.class);


        User user1 = new User("a",true);
        User user2 = new User("b",true);
        User user3 = new User("c",true);

        User[] users1={user1,user2,user3};

        new Thread(new Runnable() {
            @Override
            public void run() {
                db.userDao().insertAll(users1);
                users2=db.userDao().getAll();
                for(int i=0;i<users2.size();i++){
                    Log.i("databaseAll",users2.get(i).getId()+users2.get(i).getUsername()+users2.get(i).isIs_login());
                }
//                users3=db.userDao().getSpecificOneUser("b");
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        binding.setUsers(model.getUsers());
                        users3 = model.getUsers();
                        LiveData<List<User>> testUsers=users3;
                        testUsers.observe(DatabaseActivity.this, new Observer<List<User>>() {
                            @Override
                            public void onChanged(List<User> users) {
                                if(users!=null){
                                    Log.i("databaseSize",String.valueOf(users.size()));
                                    User user6 = users.get(0);
                                    Log.i("database",String.valueOf(user6.getUsername())+user6.getId());
                                    Log.i("database",String.valueOf(user6.isIs_login()));
                                }
                            }
                        });
                    }
                });
            }
        }).start();
        Log.i("databaseTest","mainThread");
//        model.ToastTest();
    }

    public void onChangeDatabase(View view){

//        finish();
        new Thread(new Runnable() {
            @Override
            public void run() {
                User user7=new User("b",false);
                user7.setId(2);
//                user7.username="b";
//                user7.is_login=false;
//                user7.id=2;
                int num = db.userDao().UpadteSpecificOneUser(user7);
                Log.i("database",String.valueOf(num));
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}
