package by.ares.userservice.util;

import by.ares.userservice.dto.request.PaymentCardRequest;
import by.ares.userservice.dto.request.UserRequest;
import by.ares.userservice.dto.response.PaymentCardDto;
import by.ares.userservice.dto.response.UserDto;
import by.ares.userservice.model.PaymentCard;
import by.ares.userservice.model.User;

import static by.ares.userservice.util.TestConstants.*;

public class TestModelBuilder {
    public static UserRequest buildUserRequest(String email) {
        return UserRequest.builder()
                .email(email)
                .name(USER_NAME)
                .surname(USER_SURNAME)
                .birthDate(BIRTH_DATE)
                .build();
    }

    public static PaymentCardRequest buildPaymentCardRequest(String number, Long userId) {
        return PaymentCardRequest.builder()
                .number(number)
                .expirationDate(EXPIRATION_DATE)
                .userId(userId)
                .build();
    }

    public static User buildUser() {
        return new User()
                .setName(USER_NAME)
                .setSurname(USER_SURNAME)
                .setBirthDate(BIRTH_DATE);
    }

    public static UserDto buildUserDto() {
        return UserDto.builder()
                .id(USER_ID)
                .name(USER_NAME)
                .surname(USER_SURNAME)
                .email(USER_EMAIL)
                .birthDate(BIRTH_DATE)
                .build();
    }

    public static PaymentCard buildPaymentCard(User user) {
        return new PaymentCard()
                .setHolder(CARD_HOLDER)
                .setExpirationDate(EXPIRATION_DATE)
                .setUser(user);
    }

    public static PaymentCardDto buildPaymentCardDto() {
        return PaymentCardDto.builder()
                .id(CARD_ID)
                .number(CARD_NUMBER)
                .holder(CARD_HOLDER)
                .expirationDate(EXPIRATION_DATE)
                .build();
    }

}
