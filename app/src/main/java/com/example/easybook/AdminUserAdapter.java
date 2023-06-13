package com.example.easybook;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class AdminUserAdapter extends RecyclerView.Adapter<AdminUserAdapter.AdminViewHolder>
{
    private Context context;
    private List<AdminClass> AdminList;
    private AdminUserAdapter.OnItemClickListener onItemClickListener; // Define the listener interface

    public interface OnItemClickListener {
        void onItemClick(AdminClass trainer);
    }

    public AdminUserAdapter(Context context, List<AdminClass> AdminList, AdminUserAdapter.OnItemClickListener onItemClickListener) {
        this.context = context;
        this.AdminList = AdminList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public AdminUserAdapter.AdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.admin_item_user, parent, false);
        return new AdminUserAdapter.AdminViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminUserAdapter.AdminViewHolder holder, int position) {
        AdminClass admin = AdminList.get(position);
        holder.adminName.setText(admin.getName());
        holder.trainerCategory.setText(admin.getCategory());
        holder.trainerDisplayReason.setText(admin.getReason());
        holder.trainerDisplayPrice.setText(admin.getPrice());


        // Load the profile picture using Glide
        Glide.with(context)
                .load(admin.getProfilePictureUrl())  // Use the appropriate method to get the profile picture URL
                .placeholder(R.drawable.personvector)  // Placeholder image while loading
                .error(R.drawable.personvector)  // Error image if loading fails
                .into(holder.adminImage);


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(admin);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return AdminList.size();
    }

    public static class AdminViewHolder extends RecyclerView.ViewHolder {
        TextView adminName;
        TextView trainerCategory;
        TextView trainerDisplayReason;
        TextView trainerDisplayPrice;
        CardView cardView;

        ImageView adminImage;  // Add this line

        public AdminViewHolder(View itemView) {
            super(itemView);
            adminName = itemView.findViewById(R.id.trainerDisplayName);
            trainerCategory = itemView.findViewById(R.id.trainerDisplayCategory);
            trainerDisplayReason = itemView.findViewById(R.id.trainerDisplayReason);
            trainerDisplayPrice = itemView.findViewById(R.id.trainerDisplayPrice);
            cardView = itemView.findViewById(R.id.itemLayoutAdmin);
            adminImage = itemView.findViewById(R.id.trainerDisplayImage);
        }
    }



}
