package com.example.myapplication.search;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.ItemSearchBinding;
import com.example.myapplication.room.User;

import java.util.List;

public class SearchUserAdapter extends RecyclerView.Adapter<SearchUserAdapter.SearchViewHolder> {
    List<User> userList;

    public SearchUserAdapter(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public SearchUserAdapter.SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return SearchUserAdapter.SearchViewHolder.create(LayoutInflater.from(parent.getContext()), parent, viewType);
    }

    @Override
    public void onBindViewHolder(SearchUserAdapter.SearchViewHolder holder, final int position) {
        holder.bindTo(userList.get(position));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    static class SearchViewHolder extends RecyclerView.ViewHolder {

        ItemSearchBinding mBinding;

        static SearchUserAdapter.SearchViewHolder create(LayoutInflater inflater, ViewGroup parent, int type) {
            ItemSearchBinding binding = ItemSearchBinding.inflate(inflater, parent, false);
            return new SearchUserAdapter.SearchViewHolder(binding);
        }

        private SearchViewHolder(ItemSearchBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        void bindTo(User user) {
            mBinding.itemSearchTextView1.setText(String.valueOf(user.getId()));
            mBinding.itemSearchTextView2.setText(user.getUsername());
            mBinding.itemSearchTextView3.setText(String.valueOf(user.isIs_login()));
        }


    }
}
