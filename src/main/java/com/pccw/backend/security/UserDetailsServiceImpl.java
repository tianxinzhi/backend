package com.pccw.backend.security;

import com.pccw.backend.entity.DbResAccount;
import com.pccw.backend.repository.ResAccountRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

//import github.javaguide.springsecurityjwtguide.system.entity.User;
//import github.javaguide.springsecurityjwtguide.system.service.UserService;


/**
 * @author shuang.kou
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final ResAccountRepository accountRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserDetailsServiceImpl(ResAccountRepository accountRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.accountRepository = accountRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        DbResAccount user = accountRepository.findDbResAccountByAccountName(name);
        user.setAccountPassword(bCryptPasswordEncoder.encode(user.getAccountPassword()));
        return new JwtUser(user);
    }


//    public void saveUser(Map<String, String> registerUser) {
//        Optional<User> optionalUser = userRepository.findByUsername(registerUser.get("username"));
//        if (optionalUser.isPresent()) {
//            throw new UserNameAlreadyExistException("User name already exist!Please choose another user name.");
//        }
//        User user = new User();
//        user.setUsername(registerUser.get("username"));
//        user.setPassword(bCryptPasswordEncoder.encode(registerUser.get("password")));
//        user.setRoles("DEV,PM");
//        user.setStatus(UserStatus.CAN_USE);
//        userRepository.save(user);
//    }
//
//    public DbResAccount findUserByUserName(String name) {
//        return accountRepository.findDbResAccountByAccountName(name)
//                .orElseThrow(() -> new UsernameNotFoundException("No user found with username " + name));
//    }
//
//    public void deleteUserByUserName(String name) {
//        userService.deleteByAccountName(name);
//    }


//    public Page<User> getAllUser(int pageNum, int pageSize) {
//        return userService.findAll(PageRequest.of(pageNum, pageSize));
//    }

}
