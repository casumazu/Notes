package com.example.notes.user.dto;

import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewUserDto {

    @Size(min = 2, max = 250)
    private String name;
    @Size(min = 2, max = 254)
    private String email;
}