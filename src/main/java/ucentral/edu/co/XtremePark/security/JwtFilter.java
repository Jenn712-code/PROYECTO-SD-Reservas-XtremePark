package ucentral.edu.co.XtremePark.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String auth = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (auth != null && auth.startsWith("Bearer ")) {
            String token = auth.substring(7);
            try {
                String username = jwtUtil.getUsername(token);
                String role = jwtUtil.getRole(token);
                var authToken = new UsernamePasswordAuthenticationToken(username, null, List.of(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase())));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } catch (Exception ex) {
                // invalid token, ignore and continue without auth
            }
        }
        filterChain.doFilter(request, response);
    }
}
