package com.example.touristapplicationbyhighhopes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder> implements Filterable {

    private Context context;
    private List<ActivityItem> activityItems;
    private List<ActivityItem> activityItemsFull; // To store the full list for filtering

    public ActivityAdapter(Context context, List<ActivityItem> activityItems) {
        this.context = context;
        this.activityItems = activityItems;
        this.activityItemsFull = new ArrayList<>(activityItems); // Copy of the original list
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_activity, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ActivityItem activityItem = activityItems.get(position);
        holder.bind(activityItem);
    }

    @Override
    public int getItemCount() {
        return activityItems.size();
    }

    @Override
    public Filter getFilter() {
        return activityFilter;
    }

    private Filter activityFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ActivityItem> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(activityItemsFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (ActivityItem item : activityItemsFull) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            activityItems.clear();
            activityItems.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvActivityName;
        private ImageView ivActivityImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvActivityName = itemView.findViewById(R.id.tvActivityName);
            ivActivityImage = itemView.findViewById(R.id.ivActivityImage);
            itemView.setOnClickListener(this);
        }

        public void bind(ActivityItem activityItem) {
            tvActivityName.setText(activityItem.getName());
            ivActivityImage.setImageResource(activityItem.getImageResource());
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                ActivityItem clickedItem = activityItems.get(position);
                Intent intent = new Intent(context, TouristSpotDetailActivity.class);
                intent.putExtra("spotName", clickedItem.getName());
                intent.putExtra("imageResourceId", clickedItem.getImageResource());
                context.startActivity(intent);
            }
        }
    }
}
