package com.example.myapplication.main;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.myapplication.R;
import com.example.myapplication.databinding.Fragment2Binding;
import com.example.myapplication.other.CopyCallBack;

public class MainActivityTabFragment2 extends Fragment {


    private Fragment2Binding binding;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding=Fragment2Binding.inflate(inflater,container,false);

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
        TextView string_tv = requireActivity().findViewById(R.id.fragment2TextView3);
        string_tv.setMovementMethod(LinkMovementMethod.getInstance());


        TextView spannable_tv = requireActivity().findViewById(R.id.mm_tv4);
        SpannableString ss = new SpannableString("其他协议");
        ss.setSpan(new URLSpan("app://baidu.com"), 0, 4,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable_tv.setText(ss);
        spannable_tv.setMovementMethod(LinkMovementMethod.getInstance());

        TextView textView6 = requireActivity().findViewById(R.id.fragment2TextView6);
        TextView textView7 = requireActivity().findViewById(R.id.fragment2TextView7);
        textView7.setSelectAllOnFocus(true);
        EditText editText1 = requireActivity().findViewById(R.id.fragment2EditText1);

        textView6.setCustomSelectionActionModeCallback(new CopyCallBack(requireActivity().getApplicationContext(),textView6));
        editText1.setCustomSelectionActionModeCallback(new CopyCallBack(requireActivity().getApplicationContext(),editText1));

        requireActivity().findViewById(R.id.fragment2Button1).setOnClickListener(new View.OnClickListener() {
            String url = "https://dss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2229864841,4232235061&fm=26&gp=0.jpg";
            String url2 = "https://dss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2583035764,1571388243&fm=26&gp=0.jpg";
            ImageView imageView = requireActivity().findViewById(R.id.fragment2ImageView1);
            @Override
            public void onClick(View v) {
                try{
                    DrawableRequestBuilder<Integer> thumbnailRequest = Glide.with(requireActivity()).load(R.drawable.ic_3);
                    Glide.with(requireActivity())
                            .load(url)
                            .crossFade(1000)
                            .placeholder(R.drawable.ic_launcher)
                            .error(R.drawable.ic_1)
                            .thumbnail(thumbnailRequest)
                            .skipMemoryCache(true)
//                            .override(300,300)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(imageView);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        Log.i("f2","create");
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragment2, menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuF2_1:
                Log.i("menu","F2_1");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}