package in.codersage.securitydemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    BCryptPasswordEncoder bCrypt;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;


    @Override
    public void save(User user) {
        user.setPassword(bCrypt.encode(user.getPassword()));
        user.setRoles(new HashSet<>(roleRepository.findAll()));
        userRepository.save(user);
    }

    @Override
    public User findUserByName(String username) {
        return userRepository.findUserByUsername(username);
    }

    public void processOAuthPostLogin(String username) {
        User existUser = userRepository.findUserByUsername(username);

        if (existUser == null) {
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setProvider(Provider.GOOGLE);
            //newUser.setEnabled(true);

            userRepository.save(newUser);
        }

    }


}
