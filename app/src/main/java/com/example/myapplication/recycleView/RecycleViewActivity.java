package com.example.myapplication.recycleView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityRecycleViewBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RecycleViewActivity extends AppCompatActivity {
    private List<BindingPeople> people;
    private MultiLayoutPeopleAdapter multiLayoutPeopleAdapter;
    private ActivityRecycleViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecycleViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        people = new ArrayList<>();
        people.add(new BindingPeople("国哥", 21));
        multiLayoutPeopleAdapter = new MultiLayoutPeopleAdapter(this, people);
//        binding.rvPeople.setLayoutManager(new LinearLayoutManager(this));
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        binding.rvPeople.setLayoutManager(layoutManager);
        binding.rvPeople.setAdapter(multiLayoutPeopleAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void onAddDataClick(View view) {
        people.add(new BindingPeople("哥哥", 27));
        people.add(new BindingPeople("姐姐", 30));
        people.add(new BindingPeople("小红", 16));
        people.add(new BindingPeople("小蓝", 15));
        people.add(new BindingPeople("小橙", 14));
        people.add(new BindingPeople("小绿", 13));
        people.add(new BindingPeople("小黄", 12));
        people.add(new BindingPeople("小花", 6));
        people.add(new BindingPeople("小德", 5));
        people.add(new BindingPeople("小梦", 4));
        multiLayoutPeopleAdapter.notifyDataSetChanged();
    }

    public void onChangeOneDataClick(View view){
        people.get(0).age.set(0);
    }
}