package com.advocacia.estacio.domain.records;

import com.advocacia.estacio.domain.enums.UserRole;

public record LoginResponseDto(String token, String login, UserRole role) {

}
