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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.navigation.NavigationFragment1Args;
import com.example.myapplication.navigation.NavigationFragment1Directions;
import com.example.myapplication.R;

public class NavigationFragment1 extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_navigation1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button button1 = requireActivity().findViewById(R.id.navigationFragment1Button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections navDirections = NavigationFragment1Directions.actionNavigationFragment1ToNestedGraph1();
                Navigation.findNavController(v).navigate(navDirections);
            }
        });

        Button button2 = requireActivity().findViewById(R.id.navigationFragment1Button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections navDirections = NavigationFragment1Directions.actionNavigationFragment1ToRegisterActivity();
                Navigation.findNavController(v).navigate(navDirections);
            }
        });

//        String str = requireArguments().getString("init");
        String str = NavigationFragment1Args.fromBundle(requireArguments()).getInit();
        TextView textView = requireActivity().findViewById(R.id.navigationFragment1TextView1);
        textView.setText(str);

    }

    @Override
    public void onResume() {
        super.onResume();
        OnBackPressedCallback callback = new OnBackPressedCallback(true ) {
            @Override
            public void handleOnBackPressed() {
                Log.i("navFragment1","back");
                boolean res= NavHostFragment.findNavController(NavigationFragment1.this).popBackStack();
                Log.i("navFragment1",String.valueOf(res));
                if (!res) {
                    requireActivity().finish();
                }

            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }


}