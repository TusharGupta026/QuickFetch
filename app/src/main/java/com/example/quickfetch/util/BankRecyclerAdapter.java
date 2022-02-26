package com.example.quickfetch.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickfetch.R;
import com.example.quickfetch.model.Bank;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BankRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE = 1;
    private final Context context;
    private int selectedPosition=-1;
    private final List<Object> listRecyclerItem;

    public BankRecyclerAdapter(Context context, List<Object> listRecyclerItem) {
        this.context = context;
        this.listRecyclerItem = listRecyclerItem;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private ImageView logo;
        private CheckBox checkBox;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            logo = (ImageView) itemView.findViewById(R.id.logo);
            checkBox=(CheckBox) itemView.findViewById(R.id.checkbox);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i) {
            case TYPE:

            default:

                View layoutView = LayoutInflater.from(viewGroup.getContext()).inflate(
                        R.layout.bank_list_item, viewGroup, false);

                return new ItemViewHolder((layoutView));
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, @SuppressLint("RecyclerView") final int i) {

        int viewType = getItemViewType(i);


        switch (viewType) {
            case TYPE:
            default:

                ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
                Bank bank = (Bank) listRecyclerItem.get(i);


                itemViewHolder.name.setText(bank.getName());
                Picasso.get().load(bank.getLogo()).placeholder(R.drawable.round_shape).fit().into(itemViewHolder.logo);

                itemViewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selectedPosition = itemViewHolder.getBindingAdapterPosition();
                        notifyDataSetChanged();
                    }
                });

                if (selectedPosition == i) {
                    itemViewHolder.checkBox.setChecked(true);
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt("selectedPosition", selectedPosition);
                    editor.apply();

                } else {
                    itemViewHolder.checkBox.setChecked(false);
                }

        }

    }

    @Override
    public int getItemCount() {
        return listRecyclerItem.size();
    }
}
