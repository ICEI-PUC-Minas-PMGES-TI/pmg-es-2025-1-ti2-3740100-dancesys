package com.dancesys.dancesys.dto;

import com.dancesys.dancesys.entity.Usuario;
import com.dancesys.dancesys.infra.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class LoginDto extends BaseDto {
    private Long id;
    private Integer enumTipo;
    private Integer status;
    private String urlFoto;
}

