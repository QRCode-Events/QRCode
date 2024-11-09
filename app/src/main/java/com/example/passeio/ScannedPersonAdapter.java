package com.example.passeio;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.edu.unicid.qrcodeteste.R;

public class ScannedPersonAdapter extends RecyclerView.Adapter<ScannedPersonAdapter.ViewHolder> {

    private List<ScannedPerson> scannedPeople;

    public ScannedPersonAdapter(List<ScannedPerson> scannedPeople) {
        this.scannedPeople = scannedPeople;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_scanned_person, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ScannedPerson scannedPerson = scannedPeople.get(position);
        holder.txtNome.setText(scannedPerson.nome);
        holder.txtDataNascimento.setText(scannedPerson.dataNascimento);
        holder.txtId.setText(String.valueOf(scannedPerson.id));
    }

    @Override
    public int getItemCount() {
        return scannedPeople.size();
    }

    public void updateList(List<ScannedPerson> newScannedPeople) {
        this.scannedPeople = newScannedPeople;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtNome;
        public TextView txtDataNascimento;
        public TextView txtId; // TextView for ID

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNome = itemView.findViewById(R.id.txtNome);
            txtDataNascimento = itemView.findViewById(R.id.txtDataNascimento);
            txtId = itemView.findViewById(R.id.txtId); // Find the ID TextView
        }
    }

}
