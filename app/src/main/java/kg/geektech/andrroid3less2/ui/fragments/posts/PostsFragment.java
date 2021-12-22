package kg.geektech.andrroid3less2.ui.fragments.posts;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import kg.geektech.andrroid3less2.App;
import kg.geektech.andrroid3less2.R;
import kg.geektech.andrroid3less2.data.models.Post;
import kg.geektech.andrroid3less2.databinding.FragmentPostsBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PostsFragment extends Fragment implements Callback<List<Post>> {

    FragmentPostsBinding binding;
    private PostAdapter adapter;
    private NavController navController;

    public PostsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new PostAdapter();
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity()
                .getSupportFragmentManager()
                .findFragmentById(R.id.frag_container);

        assert navHostFragment != null;
        navController = navHostFragment.getNavController();


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPostsBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       binding.postsRecycler.setAdapter(adapter);
        App.api.getPosts().enqueue(this);

        adapter.setOnItemClickListener(new onItemClickListener() {
            @Override
            public void onClick(int position) {
                Bundle bundle = new Bundle();
                Post post = adapter.getItem(position);
                bundle.putSerializable("post",post);
                navController.navigate(R.id.formFragment,bundle);

            }

            @Override
            public void onLongClick(int position) {


                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle("Delete the task ");
                builder.setMessage("Are you sure you wsnt to delete this task?");
                builder.setPositiveButton("Confirm", (dialogInterface, i) -> App.api.deletePost(adapter.getItem(position).getId()).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(@NonNull Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()){
                            adapter.deleteItem(position);
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                }));

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_postsFragment_to_formFragment);
            }
        });
    }

    @Override
    public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
        if (response.isSuccessful()&&response.body()!= null){
            adapter.setPosts(response.body());
        }
    }

    @Override
    public void onFailure(Call<List<Post>> call, Throwable t) {

    }

}