package com.juyeon.team.teamcoder.config.auth;

import com.juyeon.team.teamcoder.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    public void configure(WebSecurity web) throws Exception {
        // static 디렉터리의 하위 파일 목록은 인증 무시 ( = 항상통과 )
        web.ignoring().antMatchers("/css/**", "/js/**", "/assets/img/**", "/lib/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .headers().frameOptions().disable()  //h2-console 사용 위해 disable
                .and()
                    .authorizeRequests()  // URL별 권한 관리 설정하는 옵션의 시작점
                    .antMatchers("/**", "/h2-console/**").permitAll()  // 전체 열람 권한
                    .antMatchers("/api/v1/user/**", "/api/v1/**").hasRole("GUEST")
                    .antMatchers("/api/v1/group/**").hasRole("USER") // USER 권한 가진 사람만 가능
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

}
