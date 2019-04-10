package com.showme.service.impl;

import com.showme.model.Role;
import com.showme.model.User;
import com.showme.repository.RoleRepository;
import com.showme.repository.UserRepository;
import com.showme.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findAllUsers() {
        return (List<User>)userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if(user == null) {
            throw new UsernameNotFoundException(String.format("E-mail %s não encontrado", email));
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        });

        UserDetails userDetails = new org.springframework.security.core.userdetails.
                User(user.getEmail(), user.getPassword(), authorities);

        return userDetails;
    }

    @Override
    public void cadastrar(User user) {

        Set roles = new HashSet<Role>();

        User dbUser = userRepository.findByEmail(user.getEmail());
        if (dbUser != null) {
            throw new RuntimeException("User already exist.");
        }
        String pass = user.getPassword();
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        roles.add(roleRepository.findById(1L).get());
        user.setRoles(roles);
        userRepository.save(user);
        OAuth2AccessToken token = getToken(user.getEmail(), pass);
        System.out.println(token);

    }


    protected OAuth2AccessToken getToken(String username, String password) {

        ResourceOwnerPasswordResourceDetails resource = new ResourceOwnerPasswordResourceDetails();

        resource.setAccessTokenUri("http://localhost:8080/oauth/token");
        resource.setClientId("showme-client");
        resource.setClientSecret("showme-secret");
        resource.setClientAuthenticationScheme(AuthenticationScheme.header);
        resource.setGrantType("password");
        resource.setUsername(username);
        resource.setPassword(password);

        OAuth2RestTemplate rest= new OAuth2RestTemplate(resource, new DefaultOAuth2ClientContext(new DefaultAccessTokenRequest()));
        rest.setAccessTokenProvider(new ResourceOwnerPasswordAccessTokenProvider());
        OAuth2AccessToken token= rest.getAccessToken();

        return token;
    }

}
