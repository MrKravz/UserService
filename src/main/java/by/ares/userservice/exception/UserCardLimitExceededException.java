package by.ares.userservice.exception;

public class UserCardLimitExceededException extends RuntimeException {
    public UserCardLimitExceededException(String message) {
        super(message);
    }
}
