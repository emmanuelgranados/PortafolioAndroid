package com.example.app_patio_vehicular.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_patio_vehicular.R;
import com.example.app_patio_vehicular.VerActivity;
import com.example.app_patio_vehicular.entidades.Contactos;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class ListaContactosAdapter extends RecyclerView.Adapter<ListaContactosAdapter.ContactoViewHolder> {

    ArrayList<Contactos> listaContactos;
    ArrayList<Contactos> listaOriginal;

    public ListaContactosAdapter(ArrayList<Contactos> listaContactos){
        this.listaContactos =listaContactos;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listaContactos);
    }

    @NonNull
    @Override
    public ContactoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_contacto,null,false);
        return new ContactoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactoViewHolder holder, int position) {
        holder.viewNombre.setText(listaContactos.get(position).getNombre());
        holder.viewTelefono.setText(listaContactos.get(position).getTelefono());
        holder.viewCorreo.setText(listaContactos.get(position).getCorreo_electronico());
    }

    public void filtrado(String txtBuscar){
        int longitud = txtBuscar.length();
        listaContactos.clear(); // Limpia la lista antes de aplicar el filtro

        if(longitud == 0){
            listaContactos.addAll(listaOriginal); // Restaura la lista original si no hay texto de búsqueda
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                listaContactos.addAll(listaOriginal.stream()
                        .filter(i -> i.getNombre().toLowerCase().contains(txtBuscar.toLowerCase()))
                        .collect(Collectors.toList()));
            } else {
                for(Contactos c : listaOriginal) {
                    if (c.getNombre().toLowerCase().contains(txtBuscar.toLowerCase())) {
                        listaContactos.add(c);
                    }
                }
            }
        }

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listaContactos.size();
    }

    public class ContactoViewHolder extends RecyclerView.ViewHolder {

        TextView  viewNombre, viewTelefono,viewCorreo;

        public ContactoViewHolder(@NonNull View itemView) {
            super(itemView);
            viewNombre = itemView.findViewById(R.id.ViewNombre);
            viewTelefono = itemView.findViewById(R.id.ViewTelefono);
            viewCorreo = itemView.findViewById(R.id.ViewCorreo);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                   Context context = view.getContext();
                    Intent intent = new Intent(context, VerActivity.class);
                    intent.putExtra("ID",listaContactos.get(getAdapterPosition()).getId());
                    context.startActivity(intent);
                }
            });
        }
    }
}
