package com.example.sizovsklad.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sizovsklad.R;
import com.example.sizovsklad.database.DatabaseHelper;
import com.example.sizovsklad.models.User;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private List<User> userList;
    private DatabaseHelper dbHelper;

    public UserAdapter(List<User> userList, DatabaseHelper dbHelper) {
        this.userList = userList;
        this.dbHelper = dbHelper;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = userList.get(position);
        holder.tvName.setText(user.getFullName());
        holder.tvEmail.setText(user.getEmail());
        holder.tvRole.setText("Роль: " + user.getRole());

        holder.btnPromote.setOnClickListener(v -> {
            // Например, повышаем роль: junior -> senior -> manager -> director
            String newRole = user.getRole();
            switch (user.getRole()) {
                case "junior": newRole = "senior"; break;
                case "senior": newRole = "manager"; break;
                case "manager": newRole = "director"; break;
                default: newRole = "junior";
            }
            dbHelper.updateUserRole(user.getId(), newRole);
            user.setRole(newRole);
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvEmail, tvRole;
        Button btnPromote;

        ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvUserName);
            tvEmail = itemView.findViewById(R.id.tvUserEmail);
            tvRole = itemView.findViewById(R.id.tvUserRole);
            btnPromote = itemView.findViewById(R.id.btnPromote);
        }
    }
}