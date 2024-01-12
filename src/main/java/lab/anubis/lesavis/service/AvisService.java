package lab.anubis.lesavis.service;

import lab.anubis.lesavis.Repository.AvisRepository;
import lab.anubis.lesavis.entity.Avis;
import lab.anubis.lesavis.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AvisService {

    private AvisRepository avisRepository;

    public void creer(Avis avis){
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        avis.setUser(user);
        this.avisRepository.save(avis);
    }
}
