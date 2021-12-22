package kg.geektech.andrroid3less2.ui.fragments.posts;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import kg.geektech.andrroid3less2.data.models.Post;
import kg.geektech.andrroid3less2.databinding.ItemPostsBinding;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private List<Post> posts = new ArrayList<>();
    private static onItemClickListener onItemClickListener;




    public void setOnItemClickListener(onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
       notifyDataSetChanged();
    }

    public Post getItem(int position){
        return posts.get(position);
    }

    public void deleteItem(int pos) {
        posts.remove(pos);
        notifyDataSetChanged();
    }

    @NonNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPostsBinding binding = ItemPostsBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.onBind(posts.get(position));
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemPostsBinding  binding;

        public ViewHolder(@NonNull ItemPostsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(v ->
                    onItemClickListener.onClick(getAdapterPosition()));

            binding.getRoot().setOnLongClickListener(v -> {
                onItemClickListener.onLongClick(getAdapterPosition());
                return true;
            });
        }

        public void onBind(Post post) {
            binding.userID.setText(String.valueOf(post.getUserID()));
            binding.title.setText(post.getTitle());
            binding.description.setText(post.getContent());
        }
    }


}
