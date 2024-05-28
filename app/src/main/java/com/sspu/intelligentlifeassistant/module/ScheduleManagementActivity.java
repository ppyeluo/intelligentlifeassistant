package com.sspu.intelligentlifeassistant.module;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.sspu.intelligentlifeassistant.R;

public class ScheduleManagementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_management);

        // 返回按钮
        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> finish());

        // "新建日程"按钮
        Button newScheduleButton = findViewById(R.id.new_schedule_button);
        newScheduleButton.setOnClickListener(v -> showNewScheduleDialog());

        // 分段按钮
        Button byDateButton = findViewById(R.id.by_date_button);
        Button byCategoryButton = findViewById(R.id.by_category_button);
        Button byPriorityButton = findViewById(R.id.by_priority_button);

        byDateButton.setOnClickListener(v -> loadScheduleList(0));
        byCategoryButton.setOnClickListener(v -> loadScheduleList(1));
        byPriorityButton.setOnClickListener(v -> loadScheduleList(2));

        // 初始化选中"按日期"按钮
        setSelectedButton(byDateButton);

    }

    private void showNewScheduleDialog() {
        // 创建新建日程的对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_new_schedule, null);

        // 设置对话框的视图
        builder.setView(dialogView);

        // 获取对话框中的控件
        EditText scheduleNameEditText = dialogView.findViewById(R.id.schedule_name_edit_text);
        TimePicker scheduleTimePicker = dialogView.findViewById(R.id.schedule_time_picker);
        RadioGroup scheduleTypeRadioGroup = dialogView.findViewById(R.id.schedule_type_radio_group);
        RadioGroup schedulePriorityRadioGroup = dialogView.findViewById(R.id.schedule_priority_radio_group);
        Button cancelButton = dialogView.findViewById(R.id.cancel_button);
        Button saveButton = dialogView.findViewById(R.id.save_button);
        ImageButton closeButton = dialogView.findViewById(R.id.close_button);

        // 创建对话框
        AlertDialog dialog = builder.create();

        // 设置关闭按钮的点击事件
        closeButton.setOnClickListener(v -> dialog.dismiss());

        // 设置取消按钮的点击事件
        cancelButton.setOnClickListener(v -> dialog.dismiss());

        // 设置保存按钮的点击事件
        saveButton.setOnClickListener(v -> {
            // 获取用户输入的日程信息
            String scheduleName = scheduleNameEditText.getText().toString();
            int scheduleHour = scheduleTimePicker.getCurrentHour();
            int scheduleMinute = scheduleTimePicker.getCurrentMinute();
            int scheduleType = scheduleTypeRadioGroup.getCheckedRadioButtonId();
            int schedulePriority = schedulePriorityRadioGroup.getCheckedRadioButtonId();

            // 保存日程信息并关闭对话框
            saveSchedule(scheduleName, scheduleHour, scheduleMinute, scheduleType, schedulePriority);
            dialog.dismiss();
        });

        // 显示对话框
        dialog.show();
    }

    private void saveSchedule(String scheduleName, int scheduleHour, int scheduleMinute, int scheduleType, int schedulePriority) {
        // 将日程信息保存到数据库或其他存储中
        // ...
    }

    private void loadScheduleList(int index) {
        // 根据选择的菜单项(index)加载并显示相应的日程列表
        // 0: 按日期, 1: 按类别, 2: 按优先级
        // ...

        // 设置选中的按钮
        setSelectedButton(index == 0 ? findViewById(R.id.by_date_button) :
                index == 1 ? findViewById(R.id.by_category_button) : findViewById(R.id.by_priority_button));
    }

    private void setSelectedButton(Button selectedButton) {
        findViewById(R.id.by_date_button).setBackgroundColor(ContextCompat.getColor(this, R.color.gray));
        findViewById(R.id.by_category_button).setBackgroundColor(ContextCompat.getColor(this, R.color.gray));
        findViewById(R.id.by_priority_button).setBackgroundColor(ContextCompat.getColor(this, R.color.gray));

        selectedButton.setBackgroundColor(ContextCompat.getColor(this, R.color.primary));
    }
}