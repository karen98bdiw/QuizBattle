package com.example.karen.quizbattle;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class TestRecyclerViewAdapter extends RecyclerView.Adapter<TestRecyclerViewAdapter.ViewHolder>{

    TextView lastChechedTest;
    int testcheckCount = 0;

    interface CheckedTestListener{
         void oncheckedTestLister(Test test);
    }

    CheckedTestListener checkedTestListener;

    public void setCheckedTestListener(CheckedTestListener checkedTestListener) {
        this.checkedTestListener = checkedTestListener;
    }

    private List<Test> data;

    public TestRecyclerViewAdapter(List<Test> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public TestRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.test_recycler_item, viewGroup, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final TestRecyclerViewAdapter.ViewHolder viewHolder, int position) {

        viewHolder.testLookInRecycler.setTag(false);

        int adapterPosition = viewHolder.getAdapterPosition();

        final Test test = data.get(adapterPosition);

        viewHolder.testLookInRecycler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkedTestListener.oncheckedTestLister(test);
                viewHolder.testLookInRecycler.setBackgroundColor(Color.BLUE);
                viewHolder.testLookInRecycler.setTag(true);
                if(testcheckCount == 0){
                    lastChechedTest = viewHolder.testLookInRecycler;
                    Log.e("last","heyif");
                }else if(lastChechedTest.equals(viewHolder.testLookInRecycler)){
                    return;
                }else {
                    lastChechedTest.setBackgroundResource(R.drawable.text_view_custom_design);
                    lastChechedTest = viewHolder.testLookInRecycler;
                    Log.e("last","heyelse");
                }

                testcheckCount++;
            }
        });

        viewHolder.testLookInRecycler.setText(test.getTestName());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView testLookInRecycler;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            testLookInRecycler = itemView.findViewById(R.id.testLookInRecycler);
                }
}
}
