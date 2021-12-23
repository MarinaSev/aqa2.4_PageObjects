package ru.netology.data;

import lombok.Value;

public class DataGenerator {
    private DataGenerator() {
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getOtherAuthInfo(AuthInfo original) {
        return new AuthInfo("petya", "123qwerty");
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
        return new VerificationCode("12345");
    }

    @Value
    public static class CardInfo {
        private String cardNumber;
        private String cardId;
    }

    public static CardInfo getCard1() {
        return new CardInfo("5559 0000 0000 0001", "92df3f1c-a033-48e6-8390-206f6b1f56c0");
    }
    public static CardInfo getCard2() {
        return new CardInfo("5559 0000 0000 0002", "0f3f5c2a-249e-4c3d-8287-09f7a039391d");
    }

    @Value
    public static class CalcNewBalance {
        private int newBalance;
    }

    public static CalcNewBalance newDonorBalance (int startBalance, String sumTransfer) {
        return new CalcNewBalance(startBalance - Integer.parseInt(sumTransfer));
    }

    public static CalcNewBalance newRecipientBalance (int startBalance, String sumTransfer) {
        return new CalcNewBalance(startBalance + Integer.parseInt(sumTransfer));
    }


}
