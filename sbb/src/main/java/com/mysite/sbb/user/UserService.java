package com.mysite.sbb.user;

import com.mysite.sbb.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SiteUser create(String username, String email, String password){
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setEmail(email);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); 이렇게 직접적으로 new 객체를 선언하지 않음 Bean을 따로 만들어놓음
        user.setPassword(passwordEncoder.encode(password));
        this.userRepository.save(user);
        return user;
    }

    //SiteUser를 조회할 수 있는 getUser 메서드를 만든다.
    public SiteUser getUser(String username){
        Optional<SiteUser> siteUser = this.userRepository.findByUsername(username);

        if(siteUser.isPresent()){
            return siteUser.get();
        } else{
            throw new DataNotFoundException("siteuser not found");
        }
    }

}
