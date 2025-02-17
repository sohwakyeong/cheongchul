package cheongchul.cheongchul_eolam.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

public class authUtils {
    public static long getMemberId (Authentication authentication) {
        UsernamePasswordAuthenticationToken authToken = (UsernamePasswordAuthenticationToken) authentication;
        CustomUserDetails userDetails = (CustomUserDetails) authToken.getPrincipal();
        return userDetails.getMemberId();
    }
}
