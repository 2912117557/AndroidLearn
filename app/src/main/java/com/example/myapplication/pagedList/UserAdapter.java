package com.example.myapplication.pagedList;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.ItemUserBinding;
import com.example.myapplication.room.User;

public class UserAdapter extends PagedListAdapter<User, UserAdapter.UserViewHolder> {

    protected UserAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return UserViewHolder.create(LayoutInflater.from(parent.getContext()), parent);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder,
                                 int position) {
        User user = getItem(position);
        if (user != null) {
            holder.bindTo(user);
        } else {
            holder.clear();
        }
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {

        ItemUserBinding mBinding;

        static UserAdapter.UserViewHolder create(LayoutInflater inflater, ViewGroup parent) {
            ItemUserBinding binding = ItemUserBinding.inflate(inflater, parent, false);
            return new UserAdapter.UserViewHolder(binding);
        }

        private UserViewHolder(ItemUserBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        void bindTo(User user) {
            mBinding.setUser(user);
        }

        void clear(){
        }
    }



    private static DiffUtil.ItemCallback<User> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<User>() {
                @Override
                public boolean areItemsTheSame(User oldUser, User newUser) {
                    return oldUser.getId() == newUser.getId();
                }

                @Override
                public boolean areContentsTheSame(User oldUser,
                                                  User newUser) {
                    return (oldUser.getUsername()).equals(newUser.getUsername()) && oldUser.isIs_login() == newUser.isIs_login();
                }
            };
}

