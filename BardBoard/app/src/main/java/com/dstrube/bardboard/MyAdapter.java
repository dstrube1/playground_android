package com.dstrube.bardboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    private final List<ButtonRow> buttons;

    // Constructor
    public MyAdapter(List<ButtonRow> buttons) {
        this.buttons = buttons;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.button_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ButtonRow buttonRow = buttons.get(position);

        holder.buttonA.setText(buttonRow.getButtonAText());
        holder.buttonB.setText(buttonRow.getButtonBText());
        holder.buttonC.setText(buttonRow.getButtonCText());
    }

    @Override
    public int getItemCount() {
        return buttons.size();
    }

    // ViewHolder class
    static class MyViewHolder extends RecyclerView.ViewHolder {
        Button buttonA, buttonB, buttonC;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            buttonA = itemView.findViewById(R.id.buttonA);
            buttonB = itemView.findViewById(R.id.buttonB);
            buttonC = itemView.findViewById(R.id.buttonC);
        }
    }
}
