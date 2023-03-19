package com.nisum.api.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhoneRequest implements Serializable {

  @NotBlank(message = "El campo número de telefono no tiene que ser vacío")
  private String number;

  @NotBlank(message = "El código de ciudad no tiene que ser vacío")
  private String citycode;

  @NotBlank(message = "El código de país no tiene que ser vacío")
  private String contrycode;

}
