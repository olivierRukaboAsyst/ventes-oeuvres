package lab.anubis.lesavis.controller;

import lab.anubis.lesavis.entity.Avis;
import lab.anubis.lesavis.service.AvisService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("avis")
@RestController
@AllArgsConstructor
public class AvisController {

    private AvisService avisService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void creerAvis(@RequestBody Avis avis){
        avisService.creer(avis);
    }
}
