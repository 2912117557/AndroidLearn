package com.example.myapplication.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.myapplication.R;
import com.example.myapplication.databinding.Fragment1Binding;
import com.example.myapplication.other.DragOnLongClick;
import com.example.myapplication.other.MyOnDragListener;

public class MainActivityTabFragment1 extends Fragment {
    private Fragment1Binding binding;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = Fragment1Binding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }



    @Override
    public void onResume() {
        super.onResume();
        BindingFunction bindingFun = new BindingFunction(requireActivity());
        binding.setBindingFun(bindingFun);
        MyViewModel myViewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);
        Button button7 = binding.fragment1Button7;
        button7.setOnTouchListener(myViewModel.getOnTouchListener());

        Button button1 = binding.fragment1Button1;
        button1.setOnLongClickListener(new DragOnLongClick(requireActivity()));
        Button button2 = binding.fragment1Button2;
        button2.setOnDragListener(new MyOnDragListener());
        Button button3 = binding.fragment1Button3;
        button3.setOnDragListener(new MyOnDragListener());

        binding.fragment1Button13.setOnClickListener(new View.OnClickListener() {
            boolean vis = true;

            @Override
            public void onClick(View v) {
                if (vis) {
                    binding.fragment1Button11.setVisibility(View.GONE);
                    vis = false;
                } else {
                    binding.fragment1Button11.setVisibility(View.VISIBLE);
                    vis = true;
                }
            }
        });
        SwipeRefreshLayout mSwipeRefreshLayout = binding.fragment1Refresh;
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                beginRefresh();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    void beginRefresh() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i("main", "refresh");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        Log.i("f1", "create");
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragment1, menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuF1_1:
                Log.i("menu", "F1_1");
                return true;
            case R.id.menuF1_refresh:
                SwipeRefreshLayout mSwipeRefreshLayout = binding.fragment1Refresh;
                mSwipeRefreshLayout.setRefreshing(true);
                beginRefresh();
                mSwipeRefreshLayout.setRefreshing(false);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}