package hung.com.bookmanager.adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import hung.com.bookmanager.R;
import hung.com.bookmanager.SQLiteDAO.UserDAO;
import hung.com.bookmanager.model.User;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder> {
    private final List<User> users;
    private final Context context;
    private final UserDAO userDAO;

    public UserAdapter(Context context, List<User> users, UserDAO userDAO) {
        this.context = context;
        this.users = users;
        this.userDAO = userDAO;
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final UserHolder holder, int position) {
        holder.imgUser.setImageResource(R.drawable.emone);
        holder.tvHoTen.setText(users.get(holder.getAdapterPosition()).getHoTen());
        holder.tvPhone.setText(users.get(holder.getAdapterPosition()).getPhone());

        holder.imgdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(users.size() == 1){
                Toast.makeText(context, context.getString(R.string.delete_not_successful), Toast.LENGTH_SHORT).show();
            }else{
                userDAO.deleteUser(users.get(holder.getAdapterPosition()).getUserName());

                users.remove(holder.getAdapterPosition());

                notifyDataSetChanged();
                Toast.makeText(context, context.getString(R.string.delete_successful), Toast.LENGTH_SHORT).show();
            }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);

                User user = users.get(holder.getAdapterPosition());

                dialog.setTitle(users.get(holder.getAdapterPosition()).getHoTen());
                dialog.setContentView(R.layout.dialog_edit_user);
                final EditText edtPassword;
                final EditText edtPassAgain;
                final EditText edtphonenumber;
                final EditText edtHoten;
                Button btnSave;
                Button btnCancel;

                btnSave = dialog.findViewById(R.id.btnSave);
                btnCancel = dialog.findViewById(R.id.btnCancel);

                edtPassword = dialog.findViewById(R.id.edtPassword);
                edtPassAgain = dialog.findViewById(R.id.edtPassAgain);
                edtphonenumber = dialog.findViewById(R.id.edtphonenumber);
                edtHoten = dialog.findViewById(R.id.edtHoten);

                edtphonenumber.setText(user.getPhone());
                edtHoten.setText(user.getHoTen());

                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            String pass = edtPassword.getText().toString().trim();
                            String passagain = edtPassAgain.getText().toString().trim();
                            String phone = edtphonenumber.getText().toString().trim();
                            String hoTen = edtHoten.getText().toString().trim();

                            if(pass.equals("") || phone.equals("") || hoTen.equals("")){
                                edtPassword.setError(context.getString(R.string.error_PassWord));
                                edtphonenumber.setError("Số điện thoại không được để trống!!!");
                                edtHoten.setError("Họ tên không được để trống!!!");
                                return;
                            }else if(pass.length() < 6){
                                edtPassword.setError(context.getString(R.string.error_password_ngan));
                                return;
                            }else if(!passagain.equals(pass)){
                                edtPassAgain.setError(context.getString(R.string.passagain));
                                return;
                            }else{
                                User user = new User();
                                user.setUserName(users.get(holder.getAdapterPosition()).getUserName());
                                user.setPassword(edtPassword.getText().toString().trim());
                                user.setPhone(edtphonenumber.getText().toString().trim());
                                user.setHoTen(edtHoten.getText().toString().trim());

                                userDAO.updateUser(user);

                                users.get(holder.getAdapterPosition()).setHoTen(edtHoten.getText().toString().trim());
                                users.get(holder.getAdapterPosition()).setPhone(edtphonenumber.getText().toString().trim());
                                notifyDataSetChanged();

                                Toast.makeText(context, context.getString(R.string.notify_save_successful), Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                            }

                        } catch (Exception e){
                            Log.e("Error", "" + e);
                        }
                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                dialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class UserHolder extends RecyclerView.ViewHolder {
        private final CardView cvUser;
        private final TextView tvHoTen;
        private final TextView tvPhone;
        private final ImageView imgUser;

        final ImageView imgdelete;

        UserHolder(View itemView) {
            super(itemView);
            cvUser = itemView.findViewById(R.id.cvUser);
            tvHoTen = itemView.findViewById(R.id.tvHoTen);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            imgUser = itemView.findViewById(R.id.imgUser);

            imgdelete = itemView.findViewById(R.id.imgdeleteUser);
        }
    }
}
