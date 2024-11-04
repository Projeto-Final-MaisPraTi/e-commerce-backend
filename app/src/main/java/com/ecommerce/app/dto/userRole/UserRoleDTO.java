package com.ecommerce.app.dto.userRole;

import com.ecommerce.app.model.user.User;
import lombok.Data;

import java.util.List;

@Data
public class UserRoleDTO {

    private User user;
    private List<String> role;

}
