package com.example.juliuls.s03;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private List<Cameras> Cameras;
    private OnCameraListener cameraListener;

    public Adapter(List<Cameras> camerasList, MainActivity onCameraListener){
        this.Cameras = camerasList;
        this.cameraListener= (OnCameraListener) onCameraListener;
    }


    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list,null,false);
        return new ViewHolder(view,cameraListener);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder viewHolder, int i) {
        viewHolder.name.setText(Cameras.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return Cameras.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        OnCameraListener cameraListener;
        public ViewHolder(@NonNull View itemView, OnCameraListener cameraListener) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.cameraName);
            this.cameraListener = cameraListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            cameraListener.OnCameraListener(getAdapterPosition());
        }
    }

    public interface OnCameraListener{
        void OnCameraListener(int position);
    }
}
