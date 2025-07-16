package ar.edu.ps.tif.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "username", "password" })
public record AuthLoginRequestDTO(String username, String password) {

}
