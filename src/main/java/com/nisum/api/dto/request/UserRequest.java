package com.nisum.api.dto.request;

import com.nisum.api.util.Util;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest implements Serializable {

  @NotBlank(message = "El campo nombre no tiene que ser vacío")
  private String name;

  @Email(regexp = Util.EMAIL_REGEX, message = "El campo correo tiene formato incorrecto")
  @NotBlank(message = "El correo no puede estar vacío")
  private String email;

  @NotBlank(message = "La campo contraseña no tiene que ser vacío")
  @Pattern(regexp = Util.PASSWORD_REGEX, message = "La contraseña tiene formato incorrecto")
  private String password;

  @NotEmpty(message = "Lista de teléfonos no tiene que ser vacío")
  @Valid
  @Size(min = 1)
  private List<PhoneRequest> phones;

}
