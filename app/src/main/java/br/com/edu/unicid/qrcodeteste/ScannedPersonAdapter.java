package br.com.edu.unicid.qrcodeteste;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import br.com.edu.unicid.qrcodeteste.ScannedPerson;

public class ScannedPersonAdapter extends RecyclerView.Adapter<ScannedPersonAdapter.ViewHolder> {

    private List<ScannedPerson> scannedPeople;

    public ScannedPersonAdapter(List<ScannedPerson> scannedPeople) {
        this.scannedPeople = scannedPeople;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_person, parent, false); // Inflate item_person.xml
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ScannedPerson scannedPerson = scannedPeople.get(position);

        // Set the complete text including prefixes
        holder.txtNome.setText("Nome: " + scannedPerson.getNome());
        holder.txtDataNascimento.setText("Data de nascimento: " + scannedPerson.getDataNascimento());
        holder.txtId.setText("ID: " + scannedPerson.getId());
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
