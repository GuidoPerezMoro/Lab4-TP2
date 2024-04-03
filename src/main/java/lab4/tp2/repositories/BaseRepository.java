package lab4.tp2.repositories;

import lab4.tp2.entities.BaseEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface BaseRepository<E extends BaseEntidad, CODIGO extends Serializable> extends JpaRepository<E,CODIGO> {
}
