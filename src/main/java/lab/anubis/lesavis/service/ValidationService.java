package lab.anubis.lesavis.service;

import lab.anubis.lesavis.Repository.ValidationRepository;
import lab.anubis.lesavis.entity.User;
import lab.anubis.lesavis.entity.Validation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Random;

import static java.time.temporal.ChronoUnit.MINUTES;

@Service
@AllArgsConstructor
public class ValidationService {

    private ValidationRepository validationRepository;
    private NotificationService notificationService;

    public void enregistrer(User user){
        Validation validation = new Validation();
        validation.setUser(user);
        Instant creation = Instant.now();
        validation.setCreation(creation);
        Instant expiration = creation.plus(10, MINUTES);
        validation.setExpiration(expiration);

        Random random = new Random();
        int randomInteger = random.nextInt(999999);
        String code = String.format("%06d", randomInteger);

        validation.setCode(code);
        this.validationRepository.save(validation);
        this.notificationService.envoyer(validation);
    }

    public Validation lireEnfonctionDuCode(String code){
        return this.validationRepository.findByCode(code).orElseThrow(()
        -> new RuntimeException("Votre code est invalide"));
    }
}
