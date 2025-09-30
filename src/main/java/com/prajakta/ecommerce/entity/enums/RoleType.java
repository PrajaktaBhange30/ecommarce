package com.prajakta.ecommerce.entity.enums;

import lombok.Getter;

import java.util.Set;

@Getter
public enum RoleType {
   ADMIN("Admin with full access..", Set.of(
           PermissionType.USER_READ, PermissionType.USER_WRITE,
           PermissionType.PRODUCT_READ,PermissionType.PRODUCT_WRITE,
           PermissionType.ROLE_READ,PermissionType.ROLE_WRITE,
           PermissionType.ORDER_READ,PermissionType.ORDER_WRITE

   )),

   CUSTOMER("Default customer role..", Set.of(
           PermissionType.USER_READ,
           PermissionType.PRODUCT_READ,
         PermissionType.ORDER_READ
   )),

    SELLER("Seller role with product management..", Set.of(
            PermissionType.USER_READ,
            PermissionType.PRODUCT_READ,PermissionType.PRODUCT_WRITE,
            PermissionType.ORDER_READ,PermissionType.ORDER_WRITE
    ));

   private final String description;
   private final Set<PermissionType> permissions;

   RoleType(String description, Set<PermissionType> permissions){
       this.description=description;
       this.permissions=permissions;
   }

}
