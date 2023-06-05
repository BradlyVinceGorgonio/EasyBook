package com.example.easybook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.easybook.TrainerClass;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.TrainerViewHolder> {

    private Context context;
    private List<TrainerClass> trainerList;

    public UserAdapter(Context context, List<TrainerClass> trainerList) {
        this.context = context;
        this.trainerList = trainerList;
    }

    @NonNull
    @Override
    public TrainerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
        return new TrainerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrainerViewHolder holder, int position) {
        TrainerClass trainer = trainerList.get(position);
        holder.trainerName.setText(trainer.getName());
        holder.trainerDescription.setText(trainer.getDescription());
    }

    @Override
    public int getItemCount() {
        return trainerList.size();
    }

    public static class TrainerViewHolder extends RecyclerView.ViewHolder {
        TextView trainerName;
        TextView trainerDescription;

        public TrainerViewHolder(View itemView) {
            super(itemView);
            trainerName = itemView.findViewById(R.id.trainerName);
            trainerDescription = itemView.findViewById(R.id.trainerDescription);
        }
    }
}
