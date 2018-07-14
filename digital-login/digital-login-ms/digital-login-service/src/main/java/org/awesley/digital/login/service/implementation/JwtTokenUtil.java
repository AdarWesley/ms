package org.awesley.digital.login.service.implementation;

import java.io.Serializable;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.awesley.digital.login.service.model.Authority;
import org.awesley.digital.login.service.model.AuthorityName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.lang.Strings;

@Component
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = -3301605591108950415L;

    static final String CLAIM_KEY_USERNAME = "sub";
    static final String CLAIM_KEY_AUDIENCE = "aud";
    static final String CLAIM_KEY_CREATED = "iat";
    static final String CLAIM_KEY_ROLES = "rol";

    //static final String AUDIENCE_UNKNOWN = "unknown";
    static final String AUDIENCE_WEB = "web";
    //static final String AUDIENCE_MOBILE = "mobile";
    //static final String AUDIENCE_TABLET = "tablet";

    @Autowired
    private ApplicationContext ctx;
    
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getIssuedAtDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public String getAudienceFromToken(String token) {
        return getClaimFromToken(token, Claims::getAudience);
    }
    
    public List<Authority> getAuthoritiesFromToken(String token){
    	Claims claims = getAllClaimsFromToken(token);
    	List<Authority> authoritiesList = 
    			Arrays.stream(Strings.split(claims.get(CLAIM_KEY_ROLES, String.class), ";"))
    			.map(r -> {
    				Authority a = ctx.getBean(Authority.class);
    				a.setName(AuthorityName.valueOf(r));
    				return a;
    			}).collect(Collectors.toList());
    	return authoritiesList;
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(Date.from(Instant.now()));
    }

    public String generateToken(String username, List<? extends Authority> authorities) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, username, AUDIENCE_WEB, authorities);
    }

    private String doGenerateToken(Map<String, Object> claims, String username, String audience, List<? extends Authority> authorities) {
        final Date createdDate = Date.from(Instant.now());
        final Date expirationDate = calculateExpirationDate(createdDate);
        String allAuthorities = String.join(":", authorities.stream().map(a -> a.getName().name()).collect(Collectors.toList()));

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setAudience(audience)
                .claim(CLAIM_KEY_ROLES, allAuthorities)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)	
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String refreshToken(String token) {
        final Date createdDate = Date.from(Instant.now());
        final Date expirationDate = calculateExpirationDate(createdDate);

        final Claims claims = getAllClaimsFromToken(token);
        claims.setIssuedAt(createdDate);
        claims.setExpiration(expirationDate);

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    private Date calculateExpirationDate(Date createdDate) {
        return new Date(createdDate.getTime() + expiration * 1000);
    }
}
