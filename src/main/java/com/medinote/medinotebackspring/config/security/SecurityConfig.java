package com.medinote.medinotebackspring.config.security;

import com.medinote.medinotebackspring.api.repository.user.UserRefreshTokenRepository;
import com.medinote.medinotebackspring.config.properties.AppProperties;
import com.medinote.medinotebackspring.oauth.entity.RoleType;
import com.medinote.medinotebackspring.oauth.exception.RestAuthenticationEntryPoint;
import com.medinote.medinotebackspring.oauth.filter.TokenAuthenticationFilter;
import com.medinote.medinotebackspring.oauth.handler.OAuth2AuthenticationFailureHandler;
import com.medinote.medinotebackspring.oauth.handler.OAuth2AuthenticationSuccessHandler;
import com.medinote.medinotebackspring.oauth.handler.TokenAccessDeniedHandler;
import com.medinote.medinotebackspring.oauth.repository.HttpCookieOAuth2AuthorizationRequestRepository;
import com.medinote.medinotebackspring.oauth.service.CustomOAuth2UserService;
import com.medinote.medinotebackspring.oauth.token.AuthTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    //    private final CorsProperties corsProperties;
    private final AppProperties appProperties;
    private final AuthTokenProvider tokenProvider;
    //    private final CustomUserDetailsService userDetailsService;
    private final CustomOAuth2UserService oAuth2UserService;
    private final TokenAccessDeniedHandler tokenAccessDeniedHandler;
    private final UserRefreshTokenRepository userRefreshTokenRepository;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers("/error", "/favicon.ico");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                // 개발 환경에서 h2-console 사용하기 위한  임시 옵션
                .headers(header -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/hello-world","/api/v1/auth/refresh").permitAll()
//                                .requestMatchers("/h2-console/**","/api/v1/**","/hello-world").permitAll()
                                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                                .requestMatchers("/api/**").authenticated()
                                .requestMatchers("/api/*/admin/**").hasAnyAuthority(RoleType.ADMIN.getCode())
                                .anyRequest().authenticated()
                )
                .oauth2Login(oauth -> oauth
                        .authorizationEndpoint(endpoint -> endpoint
                                .baseUri("/oauth2/authorization")
                                .authorizationRequestRepository(oAuth2AuthorizationRequestBasedOnCookieRepository())
                        )
                        .redirectionEndpoint(endpoint -> endpoint
                                .baseUri("/*/oauth2/code/*")
                        )
                        .userInfoEndpoint(endpoint -> endpoint
                                .userService(oAuth2UserService)
                        )
                        .successHandler(oAuth2AuthenticationSuccessHandler())
                        .failureHandler(oAuth2AuthenticationFailureHandler())
                )
                .addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                // 인증이 일어나지 않았을 경우 처리
                .exceptionHandling(handling -> handling
                        .authenticationEntryPoint(new RestAuthenticationEntryPoint())
                        .accessDeniedHandler(tokenAccessDeniedHandler)
                )
                .build();

    }

//    /*
//     * security 설정 시, 사용할 인코더 설정
//     * */
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    /*
     * 토큰 필터 설정
     * */
    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(tokenProvider);
    }

    /*
     * 쿠키 기반 인가 Repository
     * 인가 응답을 연계 하고 검증할 때 사용.
     * */
    @Bean
    public HttpCookieOAuth2AuthorizationRequestRepository oAuth2AuthorizationRequestBasedOnCookieRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }

    /*
     * Oauth 인증 성공 핸들러
     * */
    @Bean
    public OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler() {
        return new OAuth2AuthenticationSuccessHandler(
                tokenProvider,
                appProperties,
                userRefreshTokenRepository,
                oAuth2AuthorizationRequestBasedOnCookieRepository()
        );
    }

    /*
     * Oauth 인증 실패 핸들러
     * */
    @Bean
    public OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler() {
        return new OAuth2AuthenticationFailureHandler(oAuth2AuthorizationRequestBasedOnCookieRepository());
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173", "https://medinote-five.vercel.app"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

