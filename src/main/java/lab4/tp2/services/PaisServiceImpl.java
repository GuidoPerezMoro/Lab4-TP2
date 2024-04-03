package lab4.tp2.services;

import lab4.tp2.entities.Pais;
import lab4.tp2.repositories.BaseRepository;
import lab4.tp2.repositories.PaisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaisServiceImpl extends BaseServiceImpl<Pais, Integer> implements PaisService {
    @Autowired
    private PaisRepository paisRepository;

    public PaisServiceImpl(BaseRepository<Pais, Integer> baseRepository, PaisRepository paisRepository) {
        super(baseRepository);
        this.paisRepository = paisRepository;
    }
}