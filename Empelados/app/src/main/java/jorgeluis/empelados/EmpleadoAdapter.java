package jorgeluis.empelados;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class EmpleadoAdapter extends RecyclerView.Adapter<EmpleadoAdapter.EmpleadoViewHolder>{

    Context context;
    List<Empleado> lista_empleados;

    public class EmpleadoViewHolder extends RecyclerView.ViewHolder {
        public TextView mNombreEmpleado;
        public TextView mDepartamentoEmpleado;
        public EmpleadoViewHolder(@NonNull View itemView) {
            super(itemView);

            mNombreEmpleado = itemView.findViewById(R.id.card_text_nombre);
            mDepartamentoEmpleado = itemView.findViewById(R.id.card_text_departamento);
        }
    }

    public EmpleadoAdapter(Context c, List<Empleado> le){
        context = c;
        lista_empleados = le;
    }

    @NonNull
    @Override
    public EmpleadoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.empleado_card, viewGroup, false);
        EmpleadoViewHolder vh = new EmpleadoViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull EmpleadoViewHolder holder, int i) {
        holder.mNombreEmpleado.setText(
                lista_empleados.get(i).nombre + " " +
                lista_empleados.get(i).apellido_paterno + " " +
                lista_empleados.get(i).apellido_materno);
        holder.mDepartamentoEmpleado.setText(lista_empleados.get(i).Departamento);
    }

    @Override
    public int getItemCount() {
        return lista_empleados.size();
    }
}
