package ar.edu.ps.tif.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import ar.edu.ps.tif.model.Role;
import ar.edu.ps.tif.model.UserSec;
import ar.edu.ps.tif.service.IRoleService;
import ar.edu.ps.tif.service.IUserService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
// @PreAuthorize("denyAll()")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

 

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO' ,'CLIENTE')")
    public ResponseEntity<List<UserSec>> getAllUsers(){
        List<UserSec> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
   @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO' ,'CLIENTE')")
    public ResponseEntity<UserSec> getUserById(@PathVariable Long id) {
        Optional<UserSec> user = userService.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO' ,'CLIENTE') and hasAuthority('CREATE')")
    public ResponseEntity<UserSec> createUser(@RequestBody UserSec userSec) {
        Set<Role> roleList = new HashSet<>();
        for (Role role : userSec.getRolesList()) {
            Role readRole = roleService.findById(role.getId()).orElse(null);
            if (readRole != null) {
                roleList.add(readRole);
            }
        }

     if (!roleList.isEmpty()) {
        userSec.setRolesList(roleList);

        // 🔒 Encriptar password antes de guardar
        userSec.setPassword(userService.encriptPassword(userSec.getPassword()));

        UserSec newUser = userService.save(userSec);
        return ResponseEntity.ok(newUser);
    }

    return ResponseEntity.badRequest().build();
}

  


}

