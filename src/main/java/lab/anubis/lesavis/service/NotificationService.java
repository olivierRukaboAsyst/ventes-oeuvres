package lab.anubis.lesavis.service;

import lab.anubis.lesavis.entity.Validation;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotificationService {

    JavaMailSender javaMailSender;

    public void envoyer(Validation validation){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("no-reply@anubis.lab");
        message.setTo(validation.getUser().getEmail());
        message.setSubject("Votre code d'activation");
        String texte = String.format("Bonjour %s, <br/> Votre code d'activation est %s; votre code expire dans %s minutes <br/> A bientot !",
                validation.getUser().getNom(), validation.getCode(), validation.getExpiration());
        message.setText(texte);

        javaMailSender.send(message);
    }
}
