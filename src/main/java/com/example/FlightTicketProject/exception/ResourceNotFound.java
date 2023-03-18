package com.example.FlightTicketProject.exception;

public class ResourceNotFound extends RuntimeException {

  public ResourceNotFound() {}

  public ResourceNotFound(String message) {
    super(message);
  }
}
