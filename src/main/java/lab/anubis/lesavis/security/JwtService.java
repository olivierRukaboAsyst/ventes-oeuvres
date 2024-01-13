package lab.anubis.lesavis.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lab.anubis.lesavis.Repository.JwtReposiroty;
import lab.anubis.lesavis.entity.Jwt;
import lab.anubis.lesavis.entity.User;
import lab.anubis.lesavis.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.security.KeyPair;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class JwtService {
    public static final String BEARER = "bearer";
    private final String ENCRYPTION_KEY = "c10cfe892e14fd5d744b20455b333b10ec6d7fd260d6ab25c095ae3a103da120";
    private UserService userService;
    private JwtReposiroty jwtReposiroty;

    public Map<String, String> generate(String username){
        User user = this.userService.loadUserByUsername(username);

        Map<String, String> jwtMap = this.generateJwt(user);
        Jwt jwt = Jwt.builder().valeur(jwtMap.get(BEARER)).desactive(false).expire(false).user(user).build();
        this.jwtReposiroty.save(jwt);
        return jwtMap;
    }
    public String extractUsername(String token) {
        return this.getClaim(token, Claims::getSubject);
    }

    public boolean isTokenExpired(String token) {
        Date expirationDate = getExpirationDateFromToken(token);
        return expirationDate.before(new Date());
    }

    private Date getExpirationDateFromToken(String token) {
        return this.getClaim(token, Claims::getExpiration);
    }

    private <T> T getClaim(String token, Function<Claims, T> function){
        Claims claims = getAllClaims(token);
        return function.apply(claims);
    }

    private Claims getAllClaims(String token) {
        Claims body = Jwts.parserBuilder()
                .setSigningKey(this.getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return body;
    }

    private Map<String, String> generateJwt(User user) {
        final long currentTime = System.currentTimeMillis();
        final long expirationTime = currentTime + 30 * 60 * 1000;

        Map<String, Object> userClaims = Map.of(
                "username", user.getNom(),
                Claims.EXPIRATION, new Date(expirationTime),
                Claims.SUBJECT, user.getEmail()
        );


        final String bearer = Jwts.builder()
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(expirationTime))
                .setSubject(user.getEmail())
                .setClaims(userClaims)
                .signWith(getKey())
                .compact();
        return Map.of(BEARER, bearer);
    }

    private Key getKey(){
        final byte[] decoder = Decoders.BASE64.decode(ENCRYPTION_KEY);
        return Keys.hmacShaKeyFor(decoder);
    }

}