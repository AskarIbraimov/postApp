package com.example.postapp.ui.posts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.postapp.data.models.Post;
import com.example.postapp.data.students.Students;
import com.example.postapp.databinding.ItemPostBinding;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<Post> posts = new ArrayList<>();
    private OnClick onClick;

    public void setOnClick(OnClick onClick) {
        this.onClick = onClick;
    }

    public void removePost(int position) {
        posts.remove(position);
        notifyItemRemoved(position);
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPostBinding binding = ItemPostBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new PostViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        holder.onBind(posts.get(position));
        holder.itemView.setOnLongClickListener(view -> {
            onClick.onLongClick(posts.get(holder.getAdapterPosition()).getId(), holder.getAdapterPosition());
            return true;
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.onClick(posts.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    protected class PostViewHolder extends RecyclerView.ViewHolder {
        private ItemPostBinding binding;

        public PostViewHolder(@NonNull ItemPostBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void onBind(Post post) {
            if (Students.getStudents().containsKey(post.getUserId())) {
                binding.tvUserId.setText(Students.getStudents().get(post.getUserId()));
            } else {
                binding.tvUserId.setText(String.valueOf(post.getUserId()));
            }
            binding.tvTitle.setText(post.getTitle());
            binding.tvContent.setText(post.getContent());
        }

    }

    interface OnClick {
        void onLongClick(int id, int pos);

        void onClick(Post post);
    }
}
