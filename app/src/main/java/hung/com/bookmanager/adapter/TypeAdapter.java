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
import hung.com.bookmanager.SQLiteDAO.TypeBookDAO;
import hung.com.bookmanager.model.TypeBook;

public class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.TypeHolder> {
    private final List<TypeBook> types;
    private final Context context;
    private final TypeBookDAO typeBookDAO;

    public TypeAdapter(Context context, List<TypeBook> types, TypeBookDAO typeBookDAO) {
        this.context = context;
        this.types = types;
        this.typeBookDAO = typeBookDAO;
    }

    @NonNull
    @Override
    public TypeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_type, parent, false);
        return new TypeHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final TypeHolder holder, int position) {
        final TypeBook typeBook = types.get(holder.getAdapterPosition());
        holder.imgType.setImageResource(R.drawable.cateicon);
        holder.tvMaLoai.setText(types.get(holder.getAdapterPosition()).getMaLoai());
        holder.tvTenLoai.setText(types.get(holder.getAdapterPosition()).getTenLoai());

        holder.imgDeleteType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long result = typeBookDAO.deleteTypeBook(typeBook.getMaLoai());

                if (result < 0) {
                    Toast.makeText(context, context.getString(R.string.delete_not_successful), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, context.getString(R.string.delete_successful), Toast.LENGTH_SHORT).show();

                    types.remove(holder.getAdapterPosition());

                    notifyDataSetChanged();
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setTitle(types.get(holder.getAdapterPosition()).getTenLoai());
                dialog.setContentView(R.layout.dialog_edit_type_book);

                final EditText edtNameType;
                final EditText edtDes;
                final EditText edtPos;
                Button btnAddType;
                Button btnCancel;

                edtNameType = dialog.findViewById(R.id.edtNameType);
                edtDes = dialog.findViewById(R.id.edtDes);
                edtPos = dialog.findViewById(R.id.edtPos);
                btnAddType = dialog.findViewById(R.id.btnAddType);
                btnCancel = dialog.findViewById(R.id.btnCancel);

                edtNameType.setText(typeBook.getTenLoai());
                edtDes.setText(typeBook.getMoTa());
                edtPos.setText(Integer.toString(typeBook.getViTri()));

                btnAddType.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            String id = typeBook.getMaLoai();
                            String name = edtNameType.getText().toString().trim();
                            String des = edtDes.getText().toString().trim();
                            int pos = Integer.parseInt(edtPos.getText().toString().trim());
                            TypeBook typeBook1 = new TypeBook(id, name, des, pos);

                            if(name.equals("")){
                                edtNameType.setError(context.getString(R.string.data_tenLoai_null));
                                return;
                            }
                            long result = typeBookDAO.updateTypeBook(typeBook1);
                            if (result < 0) {
                                Toast.makeText(context, context.getString(R.string.update_not_successful), Toast.LENGTH_SHORT).show();
                            } else {
                                TypeBook typesList = new TypeBook();
                                typesList.setMaLoai(types.get(holder.getAdapterPosition()).getMaLoai());
                                typesList.setTenLoai(edtNameType.getText().toString().trim());
                                typesList.setMoTa(edtDes.getText().toString().trim());
                                typesList.setViTri(Integer.parseInt(edtPos.getText().toString().trim()));

                                types.get(holder.getAdapterPosition()).setTenLoai(edtNameType.getText().toString().trim());
                                types.get(holder.getAdapterPosition()).setMoTa(edtDes.getText().toString().trim());
                                types.get(holder.getAdapterPosition()).setViTri(Integer.parseInt(edtPos.getText().toString().trim()));

                                notifyDataSetChanged();

                                Toast.makeText(context, context.getString(R.string.notify_save_successful), Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                            }
                        }catch (Exception e){
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
        return types.size();
    }

    public class TypeHolder extends RecyclerView.ViewHolder {
        private final CardView cvType;
        private final TextView tvMaLoai;
        private final TextView tvTenLoai;
        private final ImageView imgType;
        private final ImageView imgDeleteType;

        TypeHolder(View itemView) {
            super(itemView);
            cvType = itemView.findViewById(R.id.cvType);
            tvMaLoai = itemView.findViewById(R.id.tvMaLoai);
            tvTenLoai = itemView.findViewById(R.id.tvTenLoai);
            imgType = itemView.findViewById(R.id.imgType);
            imgDeleteType = itemView.findViewById(R.id.imgdeleteType);

        }
    }
}
