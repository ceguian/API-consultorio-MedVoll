package med.voll.api.domain.direccion;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Direccion {
    private String calle;
    private String distrito;
    private String ciudad;
    private String numero;
    private String complemento;

    public Direccion(DatosDireccion direccion) {
        this.calle = direccion.calle();
        this.ciudad = direccion.ciudad();
        this.complemento = direccion.complemento();
        this.numero = direccion.numero();
        this.distrito = direccion.distrito();
    }

    public Direccion actualizarDatos(DatosDireccion datosDireccion) {
        this.calle = datosDireccion.calle();
        this.ciudad = datosDireccion.ciudad();
        this.complemento = datosDireccion.complemento();
        this.numero = datosDireccion.numero();
        this.distrito = datosDireccion.distrito();
        return this;
    }
}
