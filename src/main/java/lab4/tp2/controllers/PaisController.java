package lab4.tp2.controllers;

import lab4.tp2.entities.Pais;
import lab4.tp2.services.PaisServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/paises") //URI
public class PaisController extends BaseControllerImpl<Pais, PaisServiceImpl> {
}

