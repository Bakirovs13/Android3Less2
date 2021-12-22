package kg.geektech.andrroid3less2.ui.fragments.form;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kg.geektech.andrroid3less2.App;
import kg.geektech.andrroid3less2.R;
import kg.geektech.andrroid3less2.data.models.Post;
import kg.geektech.andrroid3less2.databinding.FragmentFormBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FormFragment extends Fragment {

    FragmentFormBinding binding;
    private static final int USER_ID = 0;
    private static final int GROUP_ID = 36;
    private NavController navController;
    private boolean isUpdate = false;
    private Post post;


    public FormFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFormBinding.inflate(inflater, container, false);
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity()
                .getSupportFragmentManager()
                .findFragmentById(R.id.frag_container);

        navController = navHostFragment.getNavController();
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            post = (Post) getArguments().getSerializable("post");
            isUpdate = true;
            setPost();
        }

        binding.sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isUpdate){
                   update();
                }else{
                   create();
                }
            }
        });

    }

    private void update() {
        String title = binding.titleEt.getText().toString();
        String content = binding.contentEt.getText().toString();
        Post post2 = new Post(title , content , USER_ID , GROUP_ID);
        App.api.putPost(post.getId(), post2).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful() && response.body() != null) {
                    navController.navigateUp();
                }
            }
            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });
    }

    private void create() {
        App.api.createPost(getPost()).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful()) {
                    navController.popBackStack();
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });
    }


    private void setPost() {
        binding.titleEt.setText(post.getTitle());
        binding.contentEt.setText(post.getContent());
        binding.userID.setText(String.valueOf(post.getUserID()));
    }

    private Post getPost() {
        String title = binding.titleEt.getText().toString();
        String content = binding.contentEt.getText().toString();
        Post post = new Post(
                title,
                content,
                USER_ID,
                GROUP_ID
        );
        return post;

    }
}