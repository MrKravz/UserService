package by.ares.userservice.util;

import by.ares.userservice.model.ActivationStatus;

import java.time.LocalDate;

public class TestConstants {

    public static final Long USER_ID = 1L;
    public static final Long CARD_ID = 12L;
    public static final String USER_NAME = "Nick";
    public static final String USER_SURNAME = "Fury";
    public static final String UPDATED_USER_SURNAME = "Wild";
    public static final String USER_EMAIL = "Nick.FURY@mail.com";
    public static final String USER_EMAIL_2 = "Nick.FURY2@mail.com";
    public static final String USER_EMAIL_3 = "Nick.FURY3@mail.com";
    public static final String USER_EMAIL_4 = "Nick.FURY4@mail.com";
    public static final String USER_EMAIL_5 = "Nick.FURY5@mail.com";
    public static final String CARD_NUMBER = "1111222233334444";
    public static final String CARD_NUMBER_2 = "1234567890123456";
    public static final String CARD_NUMBER_3 = "9999000011112222";
    public static final String CARD_HOLDER = "Nick Fury";
    public static final ActivationStatus INACTIVE = ActivationStatus.INACTIVE;
    public static final LocalDate BIRTH_DATE = LocalDate.of(1990, 1, 1);
    public static final LocalDate EXPIRATION_DATE = LocalDate.of(2030, 1, 1);
    public static final LocalDate UPDATED_EXPIRATION_DATE = LocalDate.of(2035, 1, 1);

}
