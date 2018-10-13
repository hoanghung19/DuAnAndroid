package hung.com.bookmanager.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import hung.com.bookmanager.HoaDonChiTietActivity;
import hung.com.bookmanager.R;
import hung.com.bookmanager.model.Book;
import hung.com.bookmanager.model.HoaDonChiTiet;

public class BillDetailAdapter extends RecyclerView.Adapter<BillDetailAdapter.BillDetailHolder> {
    private final ArrayList<HoaDonChiTiet> billdetails;
    private final ArrayList<Book> bookArrayList;
    private final HoaDonChiTietActivity context;
    private final int i;

    public BillDetailAdapter(ArrayList<HoaDonChiTiet> billdetails, ArrayList<Book> bookArrayList, HoaDonChiTietActivity context, int i) {
        this.billdetails = billdetails;
        this.bookArrayList = bookArrayList;
        this.context = context;
        this.i = i;
    }

    @NonNull
    @Override
    public BillDetailHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_bill_detail, parent, false);
        return new BillDetailHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final BillDetailHolder holder, int position) {
        final HoaDonChiTiet bill = billdetails.get(holder.getAdapterPosition());
        int pos = -1;
        for (int i = 0; i < billdetails.size(); i++) {
            Book book = bookArrayList.get(i);
            if (book.getMaSach().equalsIgnoreCase(bill.getMaSach())) {
                pos = i;
                break;
            }
        }

        Book book = bookArrayList.get(pos);
        holder.tvSoLuong.setText("Số lượng: " + bill.getSoLuongMua());
        holder.tvMaSach.setText("Mã sách: " + bill.getMaSach());
        holder.tvGiaBia.setText("Giá bìa: " + book.getGiaBia() + "\tVND");
        holder.tvThanhTien.setText("Thành tiền: " + bill.getSoLuongMua() * book.getGiaBia() + "\tVND");

        holder.imgdeleteBillDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.delete(bill.getMaHDCT(), holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return billdetails.size();
    }

    public class BillDetailHolder extends RecyclerView.ViewHolder {
        private final CardView cvBillDetail;
        private final TextView tvMaSach;
        private final TextView tvSoLuong;
        private final TextView tvGiaBia;
        private final TextView tvThanhTien;
        private final ImageView imgdeleteBillDetail;


        BillDetailHolder(View itemView) {
            super(itemView);

            cvBillDetail = itemView.findViewById(R.id.cvBillDetail);
            tvMaSach = itemView.findViewById(R.id.tvMaSach);
            tvSoLuong = itemView.findViewById(R.id.tvSoLuong);
            tvGiaBia = itemView.findViewById(R.id.tvGiaBia);
            tvThanhTien = itemView.findViewById(R.id.tvThanhTien);
            imgdeleteBillDetail = itemView.findViewById(R.id.imgdeleteBillDetail);

        }
    }
}
