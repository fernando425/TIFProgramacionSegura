package ar.edu.ps.tif.service;

import ar.edu.ps.tif.model.UserSec;
import java.util.List;
import java.util.Optional;


public interface IUserService {

    List<UserSec> findAll();
    Optional<UserSec> findById(Long id);
    UserSec save(UserSec userSec);
    void deleteById(Long id);
    void update(UserSec userSec);
    public String encriptPassword(String password);
    Optional<UserSec> findUserEntityByUsername(String username);

}
