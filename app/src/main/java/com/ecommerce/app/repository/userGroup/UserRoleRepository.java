package com.ecommerce.app.repository.userGroup;

import com.ecommerce.app.model.user.User;
import com.ecommerce.app.model.userRole.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    @Query(" select distinct g.rolename from UserRole ug join ug.role g join ug.user u where u = ?1 ")
    List<String> findPermissionsByUser(User user);

}
