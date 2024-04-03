package lab4.tp2.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "pais")
public class Pais extends BaseEntidad {

    @Column(name = "nombrePais")
    private String nombrePais;

    @Column(name = "capitalPais")
    private String capitalPais;

    @Column(name = "region")
    private String region;

    @Column(name = "poblacion")
    private long poblacion;

    @Column(name = "latitud")
    private double latitud;

    @Column(name = "longitud")
    private double longitud;
}
