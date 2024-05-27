package com.sspu.intelligentlifeassistant.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sspu.intelligentlifeassistant.R;
import com.sspu.intelligentlifeassistant.models.NotepadItem;

import java.util.List;

public class NotepadPendingAdapter extends RecyclerView.Adapter<NotepadPendingAdapter.ViewHolder> {
    private List<NotepadItem> data;

    public NotepadPendingAdapter(List<NotepadItem> data) {
        this.data = data;
    }

    public void setData(List<NotepadItem> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NotepadPendingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 创建记录项View
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notepad_pending_item, parent, false);
        return new NotepadPendingAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotepadPendingAdapter.ViewHolder holder, int position) {
        // 绑定记录项数据
        NotepadItem record = data.get(position);
        holder.bind(record);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // 记录项视图的控件
        private TextView tv_title, tv_content;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_content = itemView.findViewById(R.id.tv_content);
        }

        public void bind(NotepadItem record) {
            // 将记录项数据绑定到视图控件
            tv_title.setText(record.getTitle());
            tv_content.setText(record.getContent());
        }
    }
}
