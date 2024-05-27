package com.sspu.intelligentlifeassistant.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sspu.intelligentlifeassistant.R;
import com.sspu.intelligentlifeassistant.models.FinanceRecord;

import java.util.List;

public class FinanceRecordAdapter extends RecyclerView.Adapter<FinanceRecordAdapter.ViewHolder> {
    private List<FinanceRecord> data;

    public FinanceRecordAdapter(List<FinanceRecord> data) {
        this.data = data;
    }

    public void setData(List<FinanceRecord> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 创建记录项View
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.finance_record_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // 绑定记录项数据
        FinanceRecord record = data.get(position);
        holder.bind(record);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // 记录项视图的控件
        private ImageView ivIcon;
        private TextView tvType, tvAmount, tvNote;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.iv_icon);
            tvType = itemView.findViewById(R.id.tv_type);
            tvAmount = itemView.findViewById(R.id.tv_amount);
            tvNote = itemView.findViewById(R.id.tv_note);
        }

        public void bind(FinanceRecord record) {
            // 将记录项数据绑定到视图控件
            Glide.with(itemView).load("file:///android_asset/images/finance/" + record.getImage()).into(ivIcon);
            tvType.setText(record.getType());
            if(!record.isExpense()){
                tvAmount.setText("+"+String.format("%.2f", record.getAmount()));
            }else{
                tvAmount.setText("-"+String.format("%.2f", record.getAmount()));
            }
            tvNote.setText(record.getNote());
        }
    }
}
