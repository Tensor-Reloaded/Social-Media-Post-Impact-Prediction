package uaic.info.account_management_service.exceptions;

public class InvalidBearerTokenException extends Exception{
    public InvalidBearerTokenException() { super("Bearer token twitterID does not match subject!");
    }
}
