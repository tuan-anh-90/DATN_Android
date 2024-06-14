package com.example.calendar.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.calendar.Model.Form;
import com.example.calendar.R;
import java.util.List;

public class FormAdapter extends RecyclerView.Adapter<FormAdapter.FormViewHolder> {
    private List<Form> formList;

    public FormAdapter(List<Form> formList) {
        this.formList = formList;
    }

    @NonNull
    @Override
    public FormViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_form, parent, false);
        return new FormViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FormViewHolder holder, int position) {
        Form form = formList.get(position);
        holder.tvLyDo.setText(form.getLyDo());
        holder.tvTuNgay.setText(form.getTuNgay());
        holder.tvDenNgay.setText(form.getDenNgay());
        holder.tvTrangThai.setText(form.getTrangThai());

        // Set màu sắc cho trạng thái
        switch (form.getTrangThai()) {
            case "Đã duyệt":
                holder.tvTrangThai.setBackgroundColor(holder.itemView.getResources().getColor(R.color.trang_thai_da_duyet));
                break;
            case "Đã hủy":
                holder.tvTrangThai.setBackgroundColor(holder.itemView.getResources().getColor(R.color.trang_thai_da_huy));
                break;
            case "Đang xử lý":
                holder.tvTrangThai.setBackgroundColor(holder.itemView.getResources().getColor(R.color.trang_thai_dang_xu_ly));
                break;
            default:
                holder.tvTrangThai.setBackgroundColor(holder.itemView.getResources().getColor(R.color.trang_thai_default));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return formList.size();
    }

    public static class FormViewHolder extends RecyclerView.ViewHolder {
        TextView tvLyDo, tvTuNgay, tvDenNgay, tvTrangThai;

        public FormViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLyDo = itemView.findViewById(R.id.tvLyDo);
            tvTuNgay = itemView.findViewById(R.id.tvTuNgay);
            tvDenNgay = itemView.findViewById(R.id.tvDenNgay);
            tvTrangThai = itemView.findViewById(R.id.tvTrangThai);
        }
    }
}
