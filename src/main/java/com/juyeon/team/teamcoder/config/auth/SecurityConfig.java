package com.juyeon.team.teamcoder.config.auth;

import com.juyeon.team.teamcoder.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .headers().frameOptions().disable()  //h2-console 사용 위해 disable
                .and()
                    .authorizeRequests()  // URL별 권한 관리 설정하는 옵션의 시작점
                    .antMatchers("/", "/css/**", "/assets/img/**", "/js/**", "/h2-console/**",
                            "/logoption/**").permitAll()  // 전체 열람 권한
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name()) // USER 권한 가진 사람만 가능 
                    .anyRequest().authenticated()   // 나머지 URL 들은 모두 인증된(로그인한) 사용자들만 가능
                .and()
                    .logout().logoutSuccessUrl("/")
                .and()
                    .oauth2Login()
                        .defaultSuccessUrl("/")
                        .userInfoEndpoint()// oauth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정들 담당
                            .userService(customOAuth2UserService);  // 인터페이스의 구현체 등록

    }

}
