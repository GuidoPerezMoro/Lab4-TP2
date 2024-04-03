package lab4.tp2.controllers;


import lab4.tp2.entities.BaseEntidad;
import lab4.tp2.services.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public abstract class BaseControllerImpl<E extends BaseEntidad, S extends BaseServiceImpl<E,Integer>> implements BaseController<E,Integer> {
    @Autowired
    protected S service;

    @GetMapping("") //URI
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.findALL());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error, por favor intente mas tarde.\"}");
        }
    }

    @GetMapping("/paged")
    public ResponseEntity<?> getAll(Pageable pageable) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.findALL(pageable));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error, por favor intente mas tarde.\"}");
        }
    }

    @GetMapping("/{codigo}") //URI
    public ResponseEntity<?> getOne(@PathVariable Integer codigo) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.findById(codigo));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error, no se encontró el valor solicitado.\"}");
        }
    }

    @PostMapping("") //URI
    public ResponseEntity<?> save(@RequestBody E entity) { //@RequestBody sirve para que la entidad esté dentro del body del request
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.save(entity));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error, por favor intente mas tarde.\"}");
        }
    }

    @PutMapping("/{codigo}") //URI
    public ResponseEntity<?> update(@RequestBody E entity, @PathVariable Integer codigo) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.update(entity, codigo));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. No se pudo actualizar. Por favor intente mas tarde.\"}");
        }
    }

    @DeleteMapping("/{codigo}") //URI
    public ResponseEntity<?> delete(@PathVariable Integer codigo) {
        try {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(service.delete(codigo));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error, por favor intente mas tarde.\"}");
        }
    }
}

