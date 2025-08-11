package com.example.react_gametime.infrastructure.persistence;

import com.example.react_gametime.model.Role;
import lombok.Builder;
import lombok.Getter;

import static com.example.react_gametime.model.Role.CHILD;

@Getter
@Builder
public class UserEntityFixture {

    @Builder.Default
    private Long id = null;

    @Builder.Default
    private String username = "defaultUser";

    @Builder.Default
    private String email = "default@example.com";

    @Builder.Default
    private String password = "password123";

    @Builder.Default
    private Role role = CHILD;

    @Builder.Default
    private long version = 1L;

    public UserEntity toEntity() {
        return new UserEntity(id, username, password, role, email, version);
    }
}
