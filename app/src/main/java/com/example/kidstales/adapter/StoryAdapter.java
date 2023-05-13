package com.example.kidstales.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kidstales.R;
import com.example.kidstales.model.Story;
import com.example.kidstales.utils.FavoritesManager;

import java.util.List;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.StoryViewHolder> {

    private List<Story> stories;
    private Context context;
    private int layoutResId;

    public StoryAdapter(Context context, List<Story> stories, @LayoutRes int layoutResId) {
        this.context = context;
        this.stories = stories;
        this.layoutResId = layoutResId;
    }

    @Override
    public StoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(layoutResId, parent, false);
        return new StoryViewHolder(itemView, stories);
    }

    @Override
    public void onBindViewHolder(StoryViewHolder holder, int position) {
        Story story = stories.get(position);
        holder.imageView.setImageResource(story.getCoverImage());
        holder.titleTextView.setText(story.getTitle());

        // Update the favorite ImageButton's icon based on the isFavorite field
        if (story.isFavorite()) {
            holder.favoriteButton.setImageResource(R.drawable.ic_heart_filled);
        } else {
            holder.favoriteButton.setImageResource(R.drawable.ic_heart_outline);
        }
    }

    @Override
    public int getItemCount() {
        return stories.size();
    }

    public class StoryViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleTextView;
        ImageButton favoriteButton;
        List<Story> stories;

        public StoryViewHolder(View itemView, List<Story> stories) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_storyCover);
            titleTextView = itemView.findViewById(R.id.tv_storyTitle);
            favoriteButton = itemView.findViewById(R.id.ib_favorite);
            this.stories = stories;

            // Implement a click listener for the favorite ImageButton
            favoriteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        // Get the corresponding story
                        Story story = stories.get(position);

                        // Toggle the isFavorite field
                        boolean isFavorite = story.isFavorite();
                        story.setFavorite(!isFavorite);

                        // Update the ImageButton's icon based on the isFavorite field
                        if (story.isFavorite()) {
                            favoriteButton.setImageResource(R.drawable.ic_heart_filled);
                            FavoritesManager.getInstance().addFavoriteStory(story); // Add the story to favorites
                        } else {
                            favoriteButton.setImageResource(R.drawable.ic_heart_outline);
                            FavoritesManager.getInstance().removeFavoriteStory(story); // Remove the story from favorites
                        }
                    }
                }
            });
        }
    }
}