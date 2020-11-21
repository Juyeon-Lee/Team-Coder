package com.juyeon.team.teamcoder.config.auth;

import com.juyeon.team.teamcoder.config.auth.dto.OAuthAttributes;
import com.juyeon.team.teamcoder.config.auth.dto.SessionUser;
import com.juyeon.team.teamcoder.domain.user.User;
import com.juyeon.team.teamcoder.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.event.spi.SaveOrUpdateEvent;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();  // 현재 로그인 징행 중인 서비스 구별 위해. ex) 네이버, 구글
        String userNameAttributeName = userRequest.getClientRegistration()
                                        .getProviderDetails().getUserInfoEndpoint()
                                        .getUserNameAttributeName();            // 로그인 진행 시 키가 되는 필드값

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName,
                oAuth2User.getAttributes());        // OAuth2UserService 통해 가져온 OAuth2User의 속성 담을 클래스

        User user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(user));
        // SessionUser : dto 클래스. User class 사용하지 않고 만들어 쓴다. - 직렬화가 필요하기 때문

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                        attributes.getAttributes(),
                        attributes.getNameAttributeKey());
    }

    private User saveOrUpdate(OAuthAttributes attributes){
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(),
                        attributes.getPicture()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }


}
