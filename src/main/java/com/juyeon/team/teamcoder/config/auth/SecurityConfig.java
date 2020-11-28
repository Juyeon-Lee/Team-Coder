package com.juyeon.team.teamcoder.config.auth;

import com.juyeon.team.teamcoder.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

//    @Autowired
//    @Qualifier("sessionRegistry")
//    private SessionRegistry sessionRegistry;

    @Override
    public void configure(WebSecurity web) throws Exception {
        // static 디렉터리의 하위 파일 목록은 인증 무시 ( = 항상통과 )
        web.ignoring().antMatchers("/css/**", "/js/**", "/assets/img/**", "/lib/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.sessionManagement()
//                .maximumSessions(1)
//                .maxSessionsPreventsLogin(true)
//                .expiredUrl("/duplicated-login")
//                .sessionRegistry(sessionRegistry);  // 중복 로그인 방지
        // Principal, UserDetails interface를 구현하는 객체는 equals와 hashcode를 반드시 override하셔야 중복 로그인 방지 처리가 가능해집니다.

        http.csrf().disable()
                .headers().frameOptions().disable()  //h2-console 사용 위해 disable
                .and()
                    .authorizeRequests()  // URL별 권한 관리 설정하는 옵션의 시작점
                    .antMatchers("/", "/logoption","/user/denied", "/search/**", "/h2-console/**", "/privacy/rule","/profile").permitAll()  // 전체 열람 권한
                    .antMatchers("/api/v1/user/**", "/api/v1/**").hasRole("GUEST")
                    .antMatchers("/api/v1/group/**", "/group/**", "/api/v1/participate/**").hasRole("USER") // USER 권한 가진 사람만 가능
                    .anyRequest().authenticated()   // 나머지 URL 들은 모두 인증된(로그인한) 사용자들만 가능
                .and()
                    .logout().logoutSuccessUrl("/")
                    .deleteCookies("JSESSIONID")
                    .invalidateHttpSession(true)
                .and()
                    .exceptionHandling()
                    .accessDeniedPage("/user/denied")
                .and()
                    .oauth2Login()
                        .loginPage("/logoption")
                        .defaultSuccessUrl("/")
                        .userInfoEndpoint()// oauth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정들 담당
                            .userService(customOAuth2UserService);  // 인터페이스의 구현체 등록

    }

//    @Bean
//    public SessionRegistry sessionRegistry() {
//        return new SessionRegistryImpl();
//        // equals, hashcode override 해야 동시 로그인 방지 기능 완료됨.
//    }

    /*
     session의 추가 혹은 삭제라는 변경사항이 발생하면 모든 WAS로 전파는 되지만 Spring Security까지 전달이 되지 않는다.
     Spring Security가 전달받기 위해서
     */
    @Bean
    public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
    }
}
