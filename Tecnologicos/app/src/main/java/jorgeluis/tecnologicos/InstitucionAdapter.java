package jorgeluis.tecnologicos;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class InstitucionAdapter extends RecyclerView.Adapter<InstitucionAdapter.InstitucionViewHolder> {

    private List<Institucion> list_institucion;
    private Context context;

    public static class InstitucionViewHolder extends RecyclerView.ViewHolder{
        public ImageView image_logo;
        public TextView text_nombre;
        public TextView text_nombre_corto;
        public InstitucionViewHolder(@NonNull View itemView) {
            super(itemView);

            image_logo = itemView.findViewById(R.id.imagen_logo);
            text_nombre = itemView.findViewById(R.id.nombre);
            text_nombre_corto = itemView.findViewById(R.id.nombre_corto);
        }

    }

    public InstitucionAdapter(Context c, List<Institucion> l){
        list_institucion = l;
        context = c;
    }

    @NonNull
    @Override
    public InstitucionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tarjeta, parent, false);
        InstitucionViewHolder vh = new InstitucionViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull InstitucionViewHolder holder, int position) {
        holder.text_nombre.setText(list_institucion.get(position).getNombre());
        holder.text_nombre_corto.setText(list_institucion.get(position).getNombre_corto());
        try {
            Bitmap bitmap = Ion.with(context).load(list_institucion.get(position).getLogo()).withBitmap().asBitmap().get();
            holder.image_logo.setImageBitmap(bitmap);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list_institucion.size();
    }
}
