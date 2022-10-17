package SJ.ToDoList.security;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.stereotype.Service;

import SJ.ToDoList.entity.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SecurityService {
	
	private static final String SECRET_KEY = "adgkamsdhgklasjgasdglkajklsdgklasdklgasklglaskdgkljaskldglalsdkg";
	
	
	// 로그인 서비스 요청 
	 public String generateToken(String email) {
        Claims claims = Jwts.claims();
        claims.put("email", email);
        return createToken(claims, email); // email을 subject로 해서 token 생성
	 }	
	 
	// token 생성
	public String createToken(Claims claims, String email) {
		
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		
		byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
		Key signingKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());
		
		return Jwts.builder()				
				.setClaims(claims)
				.signWith(signingKey,signatureAlgorithm)
				.setExpiration(new Date(System.currentTimeMillis() + (60*2000*60)))
				.compact();
	}
	
    /**
     * 토큰 유효여부 확인
     */
    public Boolean isValidToken(String token, String email) {
        log.info("isValidToken token = {}", token);
        String userEmail = getEmailFromToken(token);
        return (userEmail.equals(email)) && !isTokenExpired(token);
    }

    /**
     * 토큰의 Claim 디코딩
     */
    private Claims getAllClaims(String token) {
        log.info("getAllClaims token = {}", token);
        return Jwts.parserBuilder()
        		.setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .build()
				.parseClaimsJws(token)
                .getBody();
    }

    /**
     * Claim 에서 email 가져오기
     */
    public String getEmailFromToken(String token) {
        String userEmail = String.valueOf(getAllClaims(token).get("email"));
        log.info("getEmailFromToken Email = {}", userEmail);
        return userEmail;
    }

    /**
     * 토큰 만료기한 가져오기
     */
    public Date getExpirationDate(String token) {
        Claims claims = getAllClaims(token);
        return claims.getExpiration();
    }

    /**
     * 토큰이 만료되었는지
     */
    private boolean isTokenExpired(String token) {
        return getExpirationDate(token).before(new Date());
    }	
	
	// 토큰 검증 메서드를 boolean
	public String getSubject(String token) {
		Claims claims = Jwts.parserBuilder()
							.setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
							.build()
							.parseClaimsJws(token)
							.getBody();
		return claims.getSubject();
	}	
}
