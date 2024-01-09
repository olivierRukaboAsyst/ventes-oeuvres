package lab.anubis.lesavis.service;

import lab.anubis.lesavis.Repository.AvisRepository;
import lab.anubis.lesavis.entity.Avis;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AvisService {

    private AvisRepository avisRepository;

    public void creer(Avis avis){
        this.avisRepository.save(avis);
    }
}
