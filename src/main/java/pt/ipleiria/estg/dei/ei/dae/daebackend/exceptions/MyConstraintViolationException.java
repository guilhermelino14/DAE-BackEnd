package pt.ipleiria.estg.dei.ei.dae.daebackend.exceptions;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import java.util.stream.Collectors;
public class MyConstraintViolationException extends Exception {
    public MyConstraintViolationException(String message) {
        super(message);
    }
}