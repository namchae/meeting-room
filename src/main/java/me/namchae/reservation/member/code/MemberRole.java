package me.namchae.reservation.member.code;

import lombok.Getter;

@Getter
public enum MemberRole {
    ADMIN("ROLE_ADMIN"), BASE("ROLE_USER");

    private String roleName;

    MemberRole(String roleName) {
        this.roleName = roleName;
    }
}
