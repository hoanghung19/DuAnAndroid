package hung.com.bookmanager.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import hung.com.bookmanager.BillActivity;
import hung.com.bookmanager.HoaDonChiTietActivity;
import hung.com.bookmanager.R;
import hung.com.bookmanager.SQLiteDAO.BillDAO;
import hung.com.bookmanager.database.DatabaseHelper;
import hung.com.bookmanager.model.Bill;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.BillHolder> {
    private List<Bill> bills;
    private Context context;

    public BillAdapter(List<Bill> bills, Context context){
        this.bills = bills;
        this.context = context;
    }

    @NonNull
    @Override
    public BillHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bill, parent, false);
        BillHolder billHolder = new BillHolder(v);
        return billHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BillHolder holder, final int position) {
        final Bill bill = bills.get(position);
        String date = new Date(bill.getDate()).toString();
        holder.tvId.setText(bill.id);
        holder.tvDate.setText(date);

        holder.imgdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BillDAO billDAO = new BillDAO(new DatabaseHelper(context));
                billDAO.deleteBill(bill.id);
                bills.remove(position);

                notifyDataSetChanged();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return bills.size();
    }

    public class BillHolder extends RecyclerView.ViewHolder {
        private TextView tvId;
        private TextView tvDate;
        private ImageView imgdelete;

        public BillHolder(View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.tvBillID);
            tvDate = itemView.findViewById(R.id.tvDate);
            imgdelete = itemView.findViewById(R.id.imgdeleteBill);
        }
    }
}
