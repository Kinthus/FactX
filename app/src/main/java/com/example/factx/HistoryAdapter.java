package com.example.factx;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HistoryAdapter
        extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private List<HistoryItem> historyList;

    // Delete mode
    private boolean deleteMode = false;


    public HistoryAdapter(List<HistoryItem> historyList) {
        this.historyList = historyList;
    }


    // ==========================================
    // ENABLE / DISABLE DELETE MODE
    // ==========================================

    public void setDeleteMode(boolean deleteMode) {

        this.deleteMode = deleteMode;

        // When leaving delete mode,
        // remove all selections
        if (!deleteMode) {

            for (HistoryItem item : historyList) {

                item.setSelected(false);
            }
        }

        notifyDataSetChanged();
    }


    // ==========================================
    // CHECK DELETE MODE
    // ==========================================

    public boolean isDeleteMode() {

        return deleteMode;
    }


    // ==========================================
    // CREATE VIEW HOLDER
    // ==========================================

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(
                                R.layout.item_history,
                                parent,
                                false
                        );

        return new HistoryViewHolder(view);
    }


    // ==========================================
    // BIND DATA
    // ==========================================

    @Override
    public void onBindViewHolder(
            @NonNull HistoryViewHolder holder,
            int position) {

        HistoryItem item =
                historyList.get(position);


        // ==========================================
        // CHECKBOX
        // ==========================================

        // Remove old listener before changing state
        holder.checkHistory.setOnCheckedChangeListener(
                null
        );


        // Show checkbox only in Delete Mode

        if (deleteMode) {

            holder.checkHistory.setVisibility(
                    View.VISIBLE
            );

            holder.checkHistory.setChecked(
                    item.isSelected()
            );

        } else {

            holder.checkHistory.setVisibility(
                    View.GONE
            );

            holder.checkHistory.setChecked(
                    false
            );
        }


        // ==========================================
        // NEWS TYPE
        // ==========================================

        holder.txtHistoryType.setText(
                item.getType()
        );


        // ==========================================
        // ACTUAL NEWS
        // ==========================================

        holder.txtHistoryNews.setText(
                "News: " + item.getNews()
        );


        // ==========================================
        // EXPLANATION
        // ==========================================

        holder.txtHistoryExplanation.setText(
                "Explanation: " + item.getExplanation()
        );


        // ==========================================
        // PREDICTION
        // ==========================================

        holder.txtHistoryResult.setText(
                "Prediction: " + item.getResult()
        );


        // ==========================================
        // CONFIDENCE
        // ==========================================

        holder.txtHistoryConfidence.setText(
                "Confidence: " + item.getConfidence()
        );


        // ==========================================
        // DATE AND TIME
        // ==========================================

        holder.txtHistoryDate.setText(
                "Date & Time: " + item.getDate()
        );


        // ==========================================
        // CHECKBOX CLICK
        // ==========================================

        holder.checkHistory.setOnCheckedChangeListener(
                (buttonView, isChecked) -> {

                    if (deleteMode) {

                        item.setSelected(isChecked);
                    }
                }
        );
    }


    // ==========================================
    // ITEM COUNT
    // ==========================================

    @Override
    public int getItemCount() {

        return historyList.size();
    }


    // ==========================================
    // VIEW HOLDER
    // ==========================================

    public static class HistoryViewHolder
            extends RecyclerView.ViewHolder {

        CheckBox checkHistory;

        TextView txtHistoryType;
        TextView txtHistoryNews;
        TextView txtHistoryExplanation;
        TextView txtHistoryResult;
        TextView txtHistoryConfidence;
        TextView txtHistoryDate;


        public HistoryViewHolder(
                @NonNull View itemView) {

            super(itemView);


            checkHistory =
                    itemView.findViewById(
                            R.id.checkHistory
                    );


            txtHistoryType =
                    itemView.findViewById(
                            R.id.txtHistoryType
                    );


            txtHistoryNews =
                    itemView.findViewById(
                            R.id.txtHistoryNews
                    );


            txtHistoryExplanation =
                    itemView.findViewById(
                            R.id.txtHistoryExplanation
                    );


            txtHistoryResult =
                    itemView.findViewById(
                            R.id.txtHistoryResult
                    );


            txtHistoryConfidence =
                    itemView.findViewById(
                            R.id.txtHistoryConfidence
                    );


            txtHistoryDate =
                    itemView.findViewById(
                            R.id.txtHistoryDate
                    );
        }
    }


    // ==========================================
    // HISTORY ITEM
    // ==========================================

    public static class HistoryItem {

        private String type;
        private String news;
        private String explanation;
        private String result;
        private String confidence;
        private String date;

        private boolean selected;


        public HistoryItem(
                String type,
                String news,
                String explanation,
                String result,
                String confidence,
                String date) {

            this.type = type;
            this.news = news;
            this.explanation = explanation;
            this.result = result;
            this.confidence = confidence;
            this.date = date;

            this.selected = false;
        }


        public String getType() {

            return type;
        }


        public String getNews() {

            return news;
        }


        public String getExplanation() {

            return explanation;
        }


        public String getResult() {

            return result;
        }


        public String getConfidence() {

            return confidence;
        }


        public String getDate() {

            return date;
        }


        // ==========================================
        // SELECTION
        // ==========================================

        public boolean isSelected() {

            return selected;
        }


        public void setSelected(
                boolean selected) {

            this.selected = selected;
        }
    }
}