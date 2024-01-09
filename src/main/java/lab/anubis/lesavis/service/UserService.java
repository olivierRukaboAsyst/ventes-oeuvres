package lab.anubis.lesavis.service;

import lab.anubis.lesavis.Repository.UserReposiroty;
import lab.anubis.lesavis.entity.Role;
import lab.anubis.lesavis.entity.TypeDeRole;
import lab.anubis.lesavis.entity.User;
import lab.anubis.lesavis.entity.Validation;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private UserReposiroty userReposiroty;
    private BCryptPasswordEncoder passwordEncoder;
    private ValidationService validationService;

    public void inscription(User user){
        if (!user.getEmail().contains("@")) {
            throw new RuntimeException("Votre mail est invalide");
        }
        if (!user.getEmail().contains(".")){
            throw new RuntimeException("Votre mail est invalide");
        }
        Optional<User> userOptional = this.userReposiroty.findByEmail(user.getEmail());
        if (userOptional.isPresent()){
            throw new RuntimeException("Un utilisateur existe deja avec cet email");
        }
        String passCrypte = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(passCrypte);

        Role userRole = new Role();
        userRole.setLibelle(TypeDeRole.USER);
        user.setRole(userRole);
        user = this.userReposiroty.save(user);
        this.validationService.enregistrer(user);
    }

    public void activation(Map<String, String> activation) {
        Validation validation = this.validationService.lireEnfonctionDuCode(activation.get("code"));
        if (Instant.now().isAfter(validation.getExpiration())){
            throw new RuntimeException("Votre code a expiré");
        }
        User user = this.userReposiroty.findById(validation.getUser().getId()).orElseThrow(()
                            -> new RuntimeException("Utilisateur Inconnu"));
        user.setActive(true);
        this.userReposiroty.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userReposiroty
                    .findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Cet email n'as aucun utilisateur dans le systeme"));
    }
}





















