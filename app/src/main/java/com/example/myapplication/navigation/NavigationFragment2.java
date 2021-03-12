package com.example.myapplication.navigation;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myapplication.navigation.NavigationFragment2Directions;
import com.example.myapplication.R;


public class NavigationFragment2 extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_navigation2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button button1 = requireActivity().findViewById(R.id.navigationFragment2Button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationFragment2Directions.ActionNavigationFragment2ToNavigationFragment3 navDirections = NavigationFragment2Directions.actionNavigationFragment2ToNavigationFragment3();
                navDirections.setArg1("myArg1");
                Navigation.findNavController(v).navigate(navDirections);

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        OnBackPressedCallback callback = new OnBackPressedCallback(true ) {
            @Override
            public void handleOnBackPressed() {
                Log.i("navFragment2","back");
                NavHostFragment.findNavController(NavigationFragment2.this).popBackStack();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }
}