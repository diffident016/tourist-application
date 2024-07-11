package com.example.touristapplicationbyhighhopes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private List<CommentItem> commentItems;

    public CommentAdapter(List<CommentItem> commentItems) {
        this.commentItems = commentItems;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        CommentItem commentItem = commentItems.get(position);
        holder.tvUserName.setText(commentItem.getUserName());
        holder.tvCommentText.setText(commentItem.getCommentText());
    }

    @Override
    public int getItemCount() {
        return commentItems.size();
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {

        private TextView tvUserName;
        private TextView tvCommentText;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvCommentText = itemView.findViewById(R.id.tvCommentText);
        }
    }
}
