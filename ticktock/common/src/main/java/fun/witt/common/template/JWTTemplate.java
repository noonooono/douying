package fun.witt.common.template;

import fun.witt.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * JWT生成，验证
 */
public class JWTTemplate {
    //jwt有效时间
    public static final long JWT_TOKEN_VALIDITY = 24 * 60 * 60 * 1000;
    //签名
    private final String signed;
    //发行者
    private final String issuer;

    public JWTTemplate(String signed, String issuer) {
        this.signed = signed;
        this.issuer = issuer;
    }

    //从token获取用户id
    public Number getUserIDFromToken(String token) {
        return getClaimFromToken(token, claims -> claims.get("id", Number.class));
    }

    //从token获取用户名
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //从token获取过期时间
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    //从token获取所有参数
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //从token获取所有参数
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(signed).parseClaimsJws(token).getBody();
    }

    //判断token是否过期
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //封装信息，获取生成的jwt返回
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("name", user.getUserName());
        return doGenerateToken(claims, user.getUserName());
    }

    //生成token
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuer(issuer)//发行人
                .setSubject(subject)//主题
                .setIssuedAt(new Date(System.currentTimeMillis()))//发行时间
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))//过期时间
                .signWith(SignatureAlgorithm.HS256, signed).compact();
    }

    //验证token
    public Boolean validateToken(String token, User user) {
        final String username = getUsernameFromToken(token);
        return (username.equals(user.getUserName()) && !isTokenExpired(token));
    }
}