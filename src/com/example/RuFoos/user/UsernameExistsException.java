package com.example.RuFoos.user;


public class UsernameExistsException extends RuntimeException
{
  public UsernameExistsException()
  {
  }

  public UsernameExistsException(String message)
  {
    super(message);
  }

  public UsernameExistsException(String message, Throwable cause)
  {
    super(message, cause);
  }

  public UsernameExistsException(Throwable cause)
  {
    super(cause);
  }
}
