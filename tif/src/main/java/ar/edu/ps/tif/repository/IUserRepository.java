package ar.edu.ps.tif.repository;


import ar.edu.ps.tif.model.UserSec;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<UserSec, Long> {

    //Crea la sentencia en base al nombre en inglés del método
    //Tmb se puede hacer mediante Query pero en este caso no es necesario
    @EntityGraph(attributePaths = {"rolesList", "rolesList.permissionsList"})
    
    Optional<UserSec> findUserEntityByUsername(String username);
}
