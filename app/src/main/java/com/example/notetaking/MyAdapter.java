package com.example.notetaking;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    Context context;
    List<item> items;
    List<Long> noteIds;
    NoteDatabaseHelper dbHelper;

    public void deleteItem(int position) {
        long noteId = noteIds.get(position);
        items.remove(position);
        noteIds.remove(position);
        notifyItemRemoved(position);
        dbHelper.deleteNoteById(noteId);
    }
    public MyAdapter(Context context, List<item> items, List<Long> noteIds, NoteDatabaseHelper dbHelper) {
        this.context = context;
        this.items = items;
        this.noteIds = noteIds;
        this.dbHelper = dbHelper;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(items.get(position).getTitle());
        holder.description.setText(items.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
