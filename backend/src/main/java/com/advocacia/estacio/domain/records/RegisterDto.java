package com.advocacia.estacio.domain.records;

import com.advocacia.estacio.domain.enums.UserRole;

public record RegisterDto(String login, String password, UserRole role) {

}
