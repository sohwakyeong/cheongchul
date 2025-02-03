package cheongchul.cheongchul_eolam.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
       String token =((HttpServletRequest)request).getHeader("Authorization");

       if(token != null && jwtTokenProvider.validateToken(token)){
           String email = jwtTokenProvider.getEmailFromToken(token);

           User user = new User(email, "", new ArrayList<>());

           UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                   user,
                   null,
                   user.getAuthorities());

           SecurityContextHolder.getContext().setAuthentication(authentication);
       }
        chain.doFilter(request, response);
    }
}