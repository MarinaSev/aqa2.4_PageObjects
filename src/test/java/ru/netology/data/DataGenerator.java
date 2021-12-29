package ru.netology.data;

import lombok.Value;

import java.util.Random;

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
    }

    public static CardInfo getCard1() {
        return new CardInfo("5559 0000 0000 0001");
    }
    public static CardInfo getCard2() {
        return new CardInfo("5559 0000 0000 0002");
    }

    @Value
    public static class SumTransfer {
        private int sumTransfer;
    }

    public static SumTransfer getSumTransfer (int StartBalance) {
        int upperBoarder = StartBalance + 1;
        int bottomBoarder = 1;
        Random random = new Random();
        int sumTransfer = random.nextInt(upperBoarder) + bottomBoarder;
        return new SumTransfer(sumTransfer);
    }

    @Value
    public static class OverBalanceSum {
        private int overSum;
    }

    public static OverBalanceSum calcOverSum(int donorBalance) {
        return new OverBalanceSum(donorBalance+200);
    }

    @Value
    public static class CalcNewBalance {
        private int newBalance;
    }

    public static CalcNewBalance newDonorBalance (int startBalance, int sumTransfer) {
        return new CalcNewBalance(startBalance - sumTransfer);
    }

    public static CalcNewBalance newRecipientBalance(int startBalance, int sumTransfer) {
        return new CalcNewBalance(startBalance + sumTransfer);
    }
}
