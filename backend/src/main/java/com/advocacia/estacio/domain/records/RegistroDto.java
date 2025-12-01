package com.advocacia.estacio.domain.records;

import com.advocacia.estacio.domain.enums.UserRole;

public record RegistroDto(String login, String password, UserRole role) {

}
