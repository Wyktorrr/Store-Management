package com.store.api.management.auth;

import com.store.api.management.exception.JwtTokenInvalidException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.store.api.management.auth.util.JwtUtil.SECRET_KEY;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class); // Logger instance
    private final UserDetailsService userDetailsService;

    @Autowired
    public JwtAuthenticationFilter(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        logger.debug("Authorization header: {}", header);

        if (header != null && header.startsWith("Bearer ")) {
            String jwt = header.substring(7); // Extracting JWT token from the Authorization header
            logger.info("Extracted JWT token from the Authorization header."); // Info log for successful token extraction

            try {
                // Parse the JWT to extract claims
                Claims claims = Jwts.parser()
                        .setSigningKey(SECRET_KEY)
                        .build()
                        .parseClaimsJws(jwt)
                        .getBody();

                String username = claims.getSubject(); // Extracting the username from claims
                logger.debug("Extracted username from claims: {}", username);

                if (username != null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    var authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    logger.info("User '{}' authenticated successfully.", username);
                }
            } catch (Exception e) {
                logger.error("JWT token validation failed: {}", e.getMessage());
                throw new JwtTokenInvalidException("JWT token validation failed");
            }
        } else {
            logger.debug("No Authorization header found or it does not start with Bearer.");
        }

        filterChain.doFilter(request, response); // Continue the filter chain
    }
}
