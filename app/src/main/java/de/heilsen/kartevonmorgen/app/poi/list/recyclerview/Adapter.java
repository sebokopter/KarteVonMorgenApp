package de.heilsen.kartevonmorgen.app.poi.list.recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.heilsen.kartevonmorgen.app.R;

public class Adapter extends RecyclerView.Adapter<ViewHolder> {
    private Context context;

    public Adapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.viewholder_poi_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        TextView textView = viewHolder.itemView.findViewById(R.id.textview_poi_list_viewholder);
        textView.setText(String.valueOf(i));
    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
