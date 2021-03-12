package com.example.myapplication.recycleView;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ItemRvPeople01Binding;

import java.util.List;

public class MultiLayoutPeopleAdapter extends RecyclerView.Adapter<MultiLayoutPeopleAdapter.PeopleViewHolder> {
    private List<BindingPeople> people;
    private static Activity mActivity;
    public MultiLayoutPeopleAdapter(Activity activity, List<BindingPeople> people) {
        mActivity = activity;
        this.people = people;
    }
    @Override
    public PeopleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return PeopleViewHolder.create(LayoutInflater.from(parent.getContext()), parent, viewType);
    }

    @Override
    public void onBindViewHolder(PeopleViewHolder holder, final int position) {
        holder.bindTo(people.get(position));
        if (position==1){
//            holder.mBinding.tvAge.setHeight(60);
            TextView tvAge=holder.mBinding.tvAge;
            LinearLayout.LayoutParams layoutParams= (LinearLayout.LayoutParams)tvAge.getLayoutParams();
            layoutParams.height = layoutParams.height/3;
            tvAge.setLayoutParams(layoutParams);
        }

        if (holder.mBinding instanceof ItemRvPeople01Binding) {
            ItemRvPeople01Binding item01 = (ItemRvPeople01Binding) holder.mBinding;
            item01.itemRvPeople.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mActivity, "item01的" + position + "被点了！", Toast.LENGTH_SHORT).show();
                }
            });
        }
//        else {
//            ItemRvPeople02Binding item02 = (ItemRvPeople02Binding) holder.mBinding;
//            item02.itemRvPeople.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(mActivity, "item02的" + position + "被点了！", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
    }

    @Override
    public int getItemCount() {
        return people.size();
    }

//    @Override
//    public int getItemViewType(int position) {
//        if (position % 2 == 0) {
//            return R.layout.item_rv_people_01;
//        } else {
//            return R.layout.item_rv_people_02;
//        }
//    }

    static class PeopleViewHolder extends RecyclerView.ViewHolder {

        ItemRvPeople01Binding mBinding;

        static PeopleViewHolder create(LayoutInflater inflater, ViewGroup parent, int type) {
            ItemRvPeople01Binding binding = ItemRvPeople01Binding.inflate(inflater, parent, false);
            return new PeopleViewHolder(binding);
        }

        private PeopleViewHolder(ItemRvPeople01Binding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        void bindTo(BindingPeople person) {
//            mBinding.setVariable(BR.person, person);
            mBinding.setPerson(person);
//            mBinding.executePendingBindings();
        }




    }
}
