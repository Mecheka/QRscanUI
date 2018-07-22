package com.devman.QRscanUI.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.devman.QRscanUI.R;
import com.devman.QRscanUI.model.menu.MenuModel;

import java.util.ArrayList;

public class ButtomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<MenuModel> menuList;
    private Context mContext;
    private IOnClick onClick;

    public void setOnItemClick(IOnClick onItemClick){
        this.onClick = onItemClick;
    }

    public ButtomAdapter(ArrayList<MenuModel> menuList, Context mContext) {
        this.menuList = menuList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.custom_buttom_item, parent, false);
        return new ButtomViewHolder(view, onClick);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ButtomViewHolder buttomViewHolder = (ButtomViewHolder) holder;
        MenuModel menuModel = menuList.get(position);
        setUpButton(buttomViewHolder, menuModel);
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    private void setUpButton(ButtomViewHolder buttomViewHolder, MenuModel menuModel){

        buttomViewHolder.button.setText(menuModel.getName());

    }

    public class ButtomViewHolder extends RecyclerView.ViewHolder{

        public Button button;

        public ButtomViewHolder(View itemView, final IOnClick onClick) {
            super(itemView);

            button = itemView.findViewById(R.id.button);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION){
                        onClick.onClick(position);
                    }

                }
            });

        }
    }

    public interface IOnClick{
        void onClick(int position);
    }

}
