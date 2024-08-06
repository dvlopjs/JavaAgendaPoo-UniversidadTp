import java.util.ArrayList;
import java.util.List;

public class Agenda {
    private List<Contacto> contactos = new ArrayList<>();

    public void agregarContacto(Contacto contacto) {
        contactos.add(contacto);
    }

    public void eliminarContacto(String nombre) {
        contactos.removeIf(contacto -> contacto.getNombre().equalsIgnoreCase(nombre));
    }

    public Contacto buscarContacto(String nombre) {
        return contactos.stream()
                .filter(contacto -> contacto.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElse(null);
    }

    public List<Contacto> listarContactos() {
        return contactos;
    }
}