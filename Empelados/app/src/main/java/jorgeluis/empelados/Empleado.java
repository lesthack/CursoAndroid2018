package jorgeluis.empelados;

public class Empleado {
    int idemp;
    String nombre;
    String apellido_paterno;
    String apellido_materno;
    String telefono;
    String email;
    String Departamento;
    int iddepto;

    public Empleado(
            int idemp,
            int iddepto,
            String nombre,
            String apellido_paterno,
            String apellido_materno,
            String telefono,
            String email,
            String Departamento
    ){
        this.idemp = idemp;
        this.iddepto = iddepto;
        this.nombre = nombre;
        this.apellido_paterno = apellido_paterno;
        this.apellido_materno = apellido_materno;
        this.telefono = telefono;
        this.email = email;
        this.Departamento = Departamento;
    }
}
