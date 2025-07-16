package ar.edu.ps.tif.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ar.edu.ps.tif.model.UserSec;
import ar.edu.ps.tif.repository.IUserRepository;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Override
    public List<UserSec> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<UserSec> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public UserSec save(UserSec userSec) {
        return userRepository.save(userSec);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void update(UserSec userSec) {
        // Se usa save porque en JPA hace upsert (insert o update según corresponda)
        save(userSec);
    }

        @Override
    public String encriptPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    @Override
    public Optional<UserSec> findUserEntityByUsername(String username) {
    return userRepository.findUserEntityByUsername(username);
}

}


 
