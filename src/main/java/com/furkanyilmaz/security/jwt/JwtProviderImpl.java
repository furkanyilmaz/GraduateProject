package com.furkanyilmaz.security.jwt;

import com.furkanyilmaz.security.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

//spring tarafından tanınması için; component
@Component
public class JwtProviderImpl implements IJwtProvider {

    //Token header
    private static final String JWT_TOKEN_PREFIX = "Bearer";
    private static final String JWT_HEADER_BEARER = "Authorization";

    //application.properties
    @Value("${authentication.jwt.expiration-ms}") //yaşam süresi
    private Long JWT_EXPRIRATION_MS;

    //Genel Anahtar(Public)
    private PublicKey jwtPublicKey;

    //Özel Anahtar(Private)
    private PrivateKey jwtPrivateKey;

    private void rsaMethodConfig(String publicData, String privateData) {
        //
    }

    //parametreli constructor
    public JwtProviderImpl(@Value("${authentication.jwt.public-key}") String jwtPublicKeyData, @Value("${authentication.jwt.private-key}") String jwtPrivateKeyData) {
        try {
            //Spring Security RSA algorithm
            KeyFactory keyFactory = getKeyFactory();

            Base64.Decoder decoder = Base64.getDecoder();
            //Spring Security için public private anahtarlar için

            //public key
            X509EncodedKeySpec publicKeyEncoder = new X509EncodedKeySpec(decoder.decode(jwtPublicKeyData.getBytes()));
            jwtPublicKey = keyFactory.generatePublic(publicKeyEncoder);

            //private key
            PKCS8EncodedKeySpec privateKeyEncoder = new PKCS8EncodedKeySpec(decoder.decode(jwtPrivateKeyData.getBytes()));
            jwtPrivateKey = keyFactory.generatePrivate(privateKeyEncoder);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("key invalid", e);
        }
    }

    //1.YÖNTEM (RSA)
    //userPrincipal: username,password,roles
    @Override
    public KeyFactory getKeyFactory() {
        try {
            return KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException keyFactory) {
            throw new RuntimeException("bilinmeyenn key algoritması ", keyFactory);
        }
    }

    //1.YÖNTEM (Token create)
    @Override
    public String generateToken(UserPrincipal userPrincipal) {
        //JWT create
        String rolles = userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining());
        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .claim("roles", rolles)
                .claim("userId", userPrincipal.getId())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPRIRATION_MS))//yaşama süresi
                .signWith(jwtPrivateKey, SignatureAlgorithm.RS512) //imza
                .compact();
    }

    //2.YÖNTEM (jwt ayrıştırılması, Bearer)
    //HEADER: bearer => 7
    @Override
    public String resolveToken(HttpServletRequest httpServletRequest) {
        String bearerToken = httpServletRequest.getHeader(JWT_HEADER_BEARER);
        if (bearerToken.startsWith(JWT_TOKEN_PREFIX) && bearerToken != null) {
            return bearerToken.substring(7);
        }
        return null;
    }

    @Override
    public Authentication getAuthentication(HttpServletRequest httpServletRequest) {
        String tokenData = resolveToken(httpServletRequest);
        //eğer token varsa ayrıştırmaya başlansın null ise bir şey yapmasın.
        if (tokenData == null)
            return null;
        //JWT'de tüm bilgileri almak için
        //JWT parse builder
        Claims claims = Jwts.parserBuilder().setSigningKey(jwtPublicKey).build().parseClaimsJws(tokenData).getBody();
        //Claims'den bilgileri al -> username, rolles, userId
        Long userId = claims.get("userId", Long.class);
        String username = claims.getSubject();
        //rolles
        List<GrantedAuthority> grantedAuthorities =
                Arrays.stream(claims.get("rolles").toString().split(","))
                        .map(rol -> rol.startsWith("ROLE_") ? rol : "ROLE_" + rol)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
        //UserDetails kimlik doğrulama
        UserDetails userDetails = new UserPrincipal(userId, username, null);
        Authentication kimlikDogrulama = username != null ? new UsernamePasswordAuthenticationToken(userDetails, null, grantedAuthorities) : null;
        return kimlikDogrulama;
    }

    //3.YÖNTEM (token süresini kontrol etmek )
    @Override
    public boolean isValidateToken(HttpServletRequest httpServletRequest) {
        String tokenData = resolveToken(httpServletRequest);
        //eğer token varsa ayrıştırmaya başlansın null ise bir şey yapmasın.
        if (tokenData == null)
            return false;
        //JWT'de tüm bilgileri almak için
        //JWT parse builder
        Claims claims = Jwts.parserBuilder().setSigningKey(jwtPublicKey).build().parseClaimsJws(tokenData).getBody();
        //eğer token süresi dolmuşsa false döndür
        if (claims.getExpiration().before(new Date()))
            return false;
        return true;
    }
}
