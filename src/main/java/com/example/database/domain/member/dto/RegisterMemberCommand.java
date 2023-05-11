package com.example.database.domain.member.dto;

import java.time.LocalDate;

public record RegisterMemberCommand (
    String email,
    String nick,
    LocalDate birthday
) {
}
