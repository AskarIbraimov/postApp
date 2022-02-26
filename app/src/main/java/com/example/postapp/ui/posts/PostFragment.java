package com.example.postapp.ui.posts;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.postapp.App;
import com.example.postapp.R;
import com.example.postapp.base.BaseFragment;
import com.example.postapp.data.models.Post;
import com.example.postapp.databinding.FragmentPostBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostFragment extends BaseFragment<FragmentPostBinding> {

    private FragmentPostBinding binding;
    private PostAdapter adapter;


    @Override
    public FragmentPostBinding bind() {
        return null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPostBinding.inflate(inflater, container, false);
        adapter = new PostAdapter();
        controller = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.recycler.setAdapter(adapter);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.navigate(R.id.action_postFragment_to_formFragment);
            }
        });

        App.api.getPosts().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter.setPosts(response.body());
                    adapter.setOnClick(new PostAdapter.OnClick() {
                        @Override
                        public void onLongClick(int id, int pos) {
                            new AlertDialog.Builder(requireActivity())
                                    .setMessage("Delete?")
                                    .setNegativeButton("No", null)
                                    .setPositiveButton("Yes", (dialogInterface, i)
                                            -> App.api.deletePost(String.valueOf(id)).enqueue(new Callback<Post>() {
                                        @Override
                                        public void onResponse(Call<Post> call1, Response<Post> response1) {
                                            adapter.removePost(pos);
                                        }

                                        @Override
                                        public void onFailure(Call<Post> call1, Throwable t) {

                                        }
                                    }))
                                    .show();

                        }

                        @Override
                        public void onClick(Post post) {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("post", post);
                            controller.navigate(R.id.action_postFragment_to_formFragment, bundle);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

            }
        });
    }
}