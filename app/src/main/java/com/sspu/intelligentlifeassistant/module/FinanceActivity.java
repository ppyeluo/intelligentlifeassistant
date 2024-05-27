package com.sspu.intelligentlifeassistant.module;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.sspu.intelligentlifeassistant.R;
import com.sspu.intelligentlifeassistant.module.finance.FinanceRecordActivity;

public class FinanceActivity extends AppCompatActivity {

    private TextView tvIncome, tvExpense;
    private LinearLayout incomeLayout, expenseLayout;
    private TextView tvFinanceRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance);

        // 初始化界面元素
        initViews();

        // 设置收入/支出切换文字的点击事件
        setTextClickListeners();

        // 默认显示收入页面
        showIncomeLayout();
        setSelectedTextColor(tvIncome);
    }

    private void initViews() {
        tvIncome = findViewById(R.id.btn_income);
        tvExpense = findViewById(R.id.btn_expense);
        incomeLayout = findViewById(R.id.income_layout);
        expenseLayout = findViewById(R.id.expense_layout);
        tvFinanceRecord = findViewById(R.id.tv_finance_record);
        tvFinanceRecord.setOnClickListener(v -> {
            // 跳转到FinanceRecordActivity
            // 跳转到FinanceActivity并删除当前页面
            Intent intent = new Intent(this, FinanceRecordActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
    }

    private void setTextClickListeners() {
        tvIncome.setOnClickListener(v -> {
            showIncomeLayout();
            setSelectedTextColor(tvIncome);
            setUnselectedTextColor(tvExpense);
        });

        tvExpense.setOnClickListener(v -> {
            showExpenseLayout();
            setSelectedTextColor(tvExpense);
            setUnselectedTextColor(tvIncome);
        });
    }

    private void showIncomeLayout() {
        incomeLayout.setVisibility(View.VISIBLE);
        expenseLayout.setVisibility(View.GONE);
    }

    private void showExpenseLayout() {
        incomeLayout.setVisibility(View.GONE);
        expenseLayout.setVisibility(View.VISIBLE);
    }

    private void setSelectedTextColor(TextView textView) {
        textView.setTextColor(getResources().getColor(R.color.primary));
    }

    private void setUnselectedTextColor(TextView textView) {
        textView.setTextColor(getResources().getColor(R.color.black));
    }
}