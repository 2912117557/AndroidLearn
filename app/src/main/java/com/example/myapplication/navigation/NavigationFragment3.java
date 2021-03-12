package com.example.myapplication.navigation;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.NavGraphDirections;
import com.example.myapplication.navigation.NavigationFragment3Args;
import com.example.myapplication.R;


public class NavigationFragment3 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_navigation3, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String str = NavigationFragment3Args.fromBundle(requireArguments()).getArg1();
        TextView textView = requireActivity().findViewById(R.id.navigationFragment3TextView1);
        textView.setText(str);
        Intent i = requireActivity().getIntent();
        Uri uri = i.getData();
        Log.i("nav",String.valueOf(uri));

    }
}