package com.yakos.map.dto;

import com.yakos.map.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record CreateUserRequest(

        @NotBlank(message = "Ad boş olamaz")
        @Size(min = 2, max = 50, message = "Ad en az 2, en fazla 50 karakter olmalıdır")
        String firstName,

        @NotBlank(message = "Soyad boş olamaz")
        @Size(min = 2, max = 50, message = "Soyad en az 2, en fazla 50 karakter olmalıdır")
        String lastName,

        @NotBlank(message = "Kullanıcı adı boş olamaz")
        @Size(min = 5, max = 20, message = "Kullanıcı adı en az 5, en fazla 20 karakter olmalıdır")
        String username,

        @NotBlank(message = "E-posta boş olamaz")
        @Email(message = "Geçerli bir e-posta adresi giriniz")
        String mail,

        @NotBlank(message = "Şifre boş olamaz")
        @Size(min = 8, max = 20, message = "Şifre en az 8, en fazla 20 karakter olmalıdır")
        String password,

        Set<Role> authorities
) {
}
