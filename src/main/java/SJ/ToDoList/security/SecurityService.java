package SJ.ToDoList.security;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class SecurityService {
	private static final String SECRET_KEY = "adgkamsdhgklasjgasdglkajklsdgklasdklgasklglaskdgkljaskldglalsdkg";
	
	
	// 로그인 서비스 던질때 - 
	public String createToken(String email, long expTime) {
		if(expTime<=0) {
			throw new RuntimeException("만료시간이 0보다 커야합니다.");
		}
		
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		
		byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
		Key signingKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());
		
		return Jwts.builder()
				.setSubject(email)
				.signWith(signingKey,signatureAlgorithm)
				.setExpiration(new Date(System.currentTimeMillis() + expTime))
				.compact();
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
