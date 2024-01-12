package com.example.vehiclerestapi.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ErrorResponse {
    private HttpStatus status;
    private List<String> errors;
}
