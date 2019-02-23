package com.example.karen.quizbattle;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class QuestionsRecyclerViewAdapter extends RecyclerView.Adapter<QuestionsRecyclerViewAdapter.ViewHolder> {

    private List<Question> data;

    public QuestionsRecyclerViewAdapter(List<Question> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public QuestionsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.questions_recycler_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {

        int adapterPosition = viewHolder.getAdapterPosition();
        Question question = data.get(adapterPosition);
        viewHolder.questionLookInRecycler.setText(question.getQuestionText());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView questionLookInRecycler;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            questionLookInRecycler = itemView.findViewById(R.id.questionLookInRecycler);

        }
    }
}