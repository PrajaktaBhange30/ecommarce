package com.prajakta.ecommerce.security;

import com.prajakta.ecommerce.entity.Permission;
import com.prajakta.ecommerce.entity.Role;
import com.prajakta.ecommerce.repository.PermissionRepository;
import com.prajakta.ecommerce.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class RoleInitializer implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Override
    @Transactional
    public void run(String... args)  {

        Permission userRead = createPermissionIfNotExists("USER_READ");
        Permission userWrite = createPermissionIfNotExists("USER_WRITE");
        Permission productRead = createPermissionIfNotExists("PRODUCT_READ");
        Permission productWrite = createPermissionIfNotExists("PRODUCT_WRITE");
        Permission roleRead = createPermissionIfNotExists("ROLE_READ");
        Permission roleWrite = createPermissionIfNotExists("ROLE_WRITE");
        Permission orderRead = createPermissionIfNotExists("ORDER_READ");
        Permission orderWrite = createPermissionIfNotExists("ORDER_WRITE");


        createRoleIfNotExists("CUSTOMER", "Default customer role",
                Set.of(userRead, productRead, orderRead));

        createRoleIfNotExists("SELLER", "Seller role with product management",
                Set.of(userRead, productRead, productWrite, orderRead, orderWrite));

        createRoleIfNotExists("ADMIN", "Administrator with full access",
                Set.of(userRead, userWrite, productRead, productWrite, roleRead, roleWrite, orderRead, orderWrite));
    }

    private Permission createPermissionIfNotExists(String name) {
        Permission permission = new Permission(name);
        return permissionRepository.findByName(name)
                .orElseGet(() -> permissionRepository.save(permission));
    }

    private void createRoleIfNotExists(String name, String description, Set<Permission> permissions) {
        roleRepository.findByName(name).orElseGet(() -> {
            Role role = new Role();
            role.setName(name);
            role.setDescription(description);
            role.setPermissions(permissions);
            return roleRepository.save(role);
        });
    }
}
