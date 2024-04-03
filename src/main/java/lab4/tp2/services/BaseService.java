package lab4.tp2.services;

import lab4.tp2.entities.BaseEntidad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public interface BaseService<E extends BaseEntidad, CODIGO extends Serializable> {
    public List<E> findALL() throws Exception; //GET
    public Page<E> findALL(Pageable pageable) throws Exception; //GET usando paginación
    public E findById(CODIGO codigo) throws Exception; //GET
    public E save(E entity) throws Exception; //POST
    public E update(E entity, CODIGO codigo) throws Exception; //PUT
    public boolean delete(CODIGO codigo) throws Exception; //DELETE
}
