package net.hexabrain.hireo.config.security.oauth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hexabrain.hireo.config.security.oauth.userinfo.CustomOAuth2UserInfo;
import net.hexabrain.hireo.web.account.domain.Account;
import net.hexabrain.hireo.web.account.domain.AccountType;
import net.hexabrain.hireo.web.account.domain.Freelancer;
import net.hexabrain.hireo.web.account.repository.AccountRepository;
import net.hexabrain.hireo.web.account.domain.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        CustomOAuth2UserInfo userInfo = CustomOAuth2UserInfo.of(registrationId, user.getAttributes());

        Optional<Account> foundAccount = accountRepository.findByEmail(userInfo.getEmail());
        if (foundAccount.isEmpty()) {
            Account account = Freelancer.builder()
                    .email(userInfo.getEmail())
                    .password(createDummyPassword())
                    .type(AccountType.FREELANCER)
                    .profile(new Profile(userInfo.getName()))
                    .build();
            account.encodePassword(passwordEncoder);
            accountRepository.save(account);
            return new CustomOAuth2UserPrincipal(account, userInfo);
        }
        return new CustomOAuth2UserPrincipal(foundAccount.get(), userInfo);
    }

    private String createDummyPassword() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
