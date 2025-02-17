package cheongchul.cheongchul_eolam.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private final Key key;
    private final long validateInMs;

    public JwtTokenProvider(@Value("${jwt.secret}")String secretKey, @Value("${jwt.expiration}") long validateInMs){
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
        this.validateInMs = validateInMs;
    }

    //토큰 생성
    public String createToken(long memebrId){
        Date now = new Date();
        Date validity = new Date(now.getTime() + validateInMs);

        return Jwts.builder()
                .setSubject(String.valueOf(memebrId))
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
    //클라이언트에서 토큰 받아서 아이디 추출
    public long getMemberIdFromToken(String token){
       String memberIdStr =  Jwts.parserBuilder()
               .setSigningKey(key)
               .build()
               .parseClaimsJws(token)
               .getBody()
               .getSubject();
       return Long.parseLong(memberIdStr);
    }

    //토큰 검증
   public boolean validateToken(String token){
        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }catch (JwtException | IllegalArgumentException e ){
            return false;
        }
   }
}
