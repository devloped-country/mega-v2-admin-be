package com.app.mega.config.auth;

import com.app.mega.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    //로그인안해도 되게끔 전부 열어놓았음!!!!! 반드시 나중에 수정해야함!!!!!!!
    // 시큐리티 필터 설정
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/register/institution","/api/auth/login", "/api/auth/identify",
                                "/api/auth/identify/certificate","/api/auth/","/health").permitAll()
//                        .requestMatchers("/api/**", "/health").permitAll()
                        .anyRequest().authenticated())

                .csrf(AbstractHttpConfigurer::disable) .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 인증 공급자 추가
                .authenticationProvider(authenticationProvider)
                // 인증 전 처리 해야할 필터 추가
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class).build();

    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("http://localhost:3001", "https://admin.megamega-app.com"));
        config.addAllowedMethod("*"); // 모든 HTTP 메소드 허용
        config.addAllowedHeader("*"); // 모든 헤더 허용
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}


//@RequiredArgsConstructor
//@EnableWebSecurity
//@Configuration  //IoC
//public class SecurityConfig {
//
//    private final CustomOAuth2UserService customOAuth2UserService;
//
//    @Bean
//    public BCryptPasswordEncoder encode() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
//
//        http.csrf(csrf -> csrf.disable())  // csrf를 disable 한다.
//                .authorizeHttpRequests(request -> request
//                        .requestMatchers(
//                                //인증되지 않은 사용자가 접근했을 시 바로 로그인페이지로 이동시킬 경로
//                                //redirect-uri로 리디렉션 됨
//                                new AntPathRequestMatcher("/"),  // http://localhost:8080/
//                                new AntPathRequestMatcher("/api/**")
//                        ).authenticated()
//                        .anyRequest().permitAll());  //다른 경로들은 모두 허용한다.
//
//        // OAuth2 로그인을 활성화하고, 사용자 정보 엔드포인트를 설정하며, 사용자 서비스를 지정함
//        http.oauth2Login(oauth2 -> oauth2.userInfoEndpoint(
//                userInfoEndpointConfig -> userInfoEndpointConfig
//                        .userService(customOAuth2UserService))); // 사용자 정보를 가져오는 사용자 지정 서비스를 설정
//                    //.defaultSuccessUrl("/", true));
//                        //.oidcUserService(customOidcUserService)));  // OpenID Connect 사용 시 사용자 지정 서비스를 설정
//
//        // 로그아웃 구성: 로그아웃 시 "/logout" 경로로 이동하고, 로그아웃이 성공하면 "/"로 이동함
//        http.logout(form -> form.logoutSuccessUrl("/"));
//
//        return http.build();
//    }
//
//}
