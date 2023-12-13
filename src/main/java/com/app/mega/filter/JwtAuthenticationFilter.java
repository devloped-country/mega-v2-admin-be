package com.app.mega.filter;

import com.app.mega.service.jpa.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println("JwtAuthenticationFilter doFilterInternal 발동!!");
        // 헤더에서 Authroization 값을 찾음
        final String JWT_PREFIX = "Bearer ";
        final String JWT_HEADER_KEY = "Authorization";
        final String authHeader = request.getHeader(JWT_HEADER_KEY);
        final String jwt;
        final String userEmail;

        // jwt token 형식이 아니면 요청을 차단함
        if (authHeader == null || !authHeader.startsWith(JWT_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(JWT_PREFIX.length());
        userEmail = jwtService.extractUsername(jwt); // JWT 토큰으로 부터 유저 이메일 추출
        // jwt 토큰에 유저 이메일이 확인되는데 아직 인증되지 않은 유저라면
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // db에서 유저 정보를 가져옴
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            // token이 유효하다면
            if (jwtService.isTokenValid(jwt, userDetails)) {
                //UsernamePasswordAuthenticationToken = Authentication 객체
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // SecurityContext를 갱신하고 controller로 요청을 전달한다.
                SecurityContextHolder.getContext().setAuthentication(authToken);  //얘가 세션정보!!
            }
        }
        filterChain.doFilter(request, response);
    }
}