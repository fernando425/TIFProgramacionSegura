package ar.edu.ps.tif.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import ar.edu.ps.tif.model.Permission;
import ar.edu.ps.tif.model.Role;
import ar.edu.ps.tif.service.IPermissionService;
import ar.edu.ps.tif.service.IRoleService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin
@PreAuthorize("denyAll()")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IPermissionService permiService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO' ,'CLIENTE')")
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleService.findAll();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLEADO' ,'CLIENTE')")
    public ResponseEntity<Role> getRoleById(@PathVariable Long id) {
        Optional<Role> role = roleService.findById(id);
        return role.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('CREATE')")
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        Set<Permission> permiList = new HashSet<>();
        for (Permission per : role.getPermissionsList()) {
            Permission readPermission = permiService.findById(per.getId()).orElse(null);
            if (readPermission != null) {
                permiList.add(readPermission);
            }
        }

        role.setPermissionsList(permiList);
        Role newRole = roleService.save(role);
        return ResponseEntity.ok(newRole);
    }

    @PatchMapping("/{id}/permissions")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Role> updateRolePermissions(@PathVariable Long id, @RequestBody Set<Long> permissionIds) {
        Role role = roleService.findById(id).orElseThrow();
        Set<Permission> permissions = new HashSet<>();
        for (Long pid : permissionIds) {
            permiService.findById(pid).ifPresent(permissions::add);
        }
        role.setPermissionsList(permissions);
        roleService.save(role);
        return ResponseEntity.ok(role);
    }

}
