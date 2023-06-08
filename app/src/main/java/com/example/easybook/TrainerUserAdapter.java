package com.example.easybook;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TrainerUserAdapter extends RecyclerView.Adapter<TrainerUserAdapter.ClientViewHolder> {
    private Context context;
    private List<BookRequestClass> bookList;
    private OnItemClickListener onItemClickListener; // Define the listener interface

    public interface OnItemClickListener {
        void onItemClick(BookRequestClass trainer);
    }

        public TrainerUserAdapter(Context context, List<BookRequestClass> bookList, OnItemClickListener onItemClickListener) {
            this.context = context;
            this.bookList = bookList;
            this.onItemClickListener = onItemClickListener;
        }

        @NonNull
        @Override
        public TrainerUserAdapter.ClientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.trainer_item_user, parent, false);
            return new TrainerUserAdapter.ClientViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TrainerUserAdapter.ClientViewHolder holder, int position) {
            Log.d("Checker", "onBindViewHolder called for position: " + position);
            BookRequestClass client = bookList.get(position);
            Log.d("Checker", "Name: " + client.getName() + ", Level: " + client.getLevel());
            holder.clientName.setText(client.getName());
            holder.clientLevel.setText(client.getLevel());
            holder.clientLocation.setText(client.getLocation());
            holder.clientSchedule.setText(client.getSchedule());
            Log.d("Checker", "Client name: " + client.getName()); // Log the value of client's name
            Log.d("Checker", "Client level: " + client.getLevel()); // Log the value of client's level
            Log.d("Checker", "Client location: " + client.getLocation()); // Log the value of client's location
            Log.d("Checker", "Client schedule: " + client.getSchedule()); // Log the value of client's schedule



            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(client);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return bookList.size();
        }

    public static class ClientViewHolder extends RecyclerView.ViewHolder {
        TextView clientName;
        TextView clientLevel;
        TextView clientSchedule;
        TextView clientLocation;
        CardView cardView;

        public ClientViewHolder(View itemView) {
            super(itemView);
            clientName = itemView.findViewById(R.id.clientName);
            clientLevel = itemView.findViewById(R.id.clientLevel);
            clientSchedule = itemView.findViewById(R.id.clientSchedule);
            clientLocation = itemView.findViewById(R.id.clientLocation);
            cardView = itemView.findViewById(R.id.itemLayoutTrainer);
        }
    }
}

