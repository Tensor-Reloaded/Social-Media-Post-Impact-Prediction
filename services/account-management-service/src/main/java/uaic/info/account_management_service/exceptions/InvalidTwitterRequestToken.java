package uaic.info.account_management_service.exceptions;

public class InvalidTwitterRequestToken extends Exception{
    public InvalidTwitterRequestToken(){
        super("Request tokens do not match!");
    }
}
