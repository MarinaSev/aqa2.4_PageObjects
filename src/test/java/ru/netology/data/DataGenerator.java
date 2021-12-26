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
    }

    public static CardInfo getCard1() {
        return new CardInfo("5559 0000 0000 0001");
    }
    public static CardInfo getCard2() {
        return new CardInfo("5559 0000 0000 0002");
    }

    @Value
    public static class ChooseCardForTransfer {
        private DataGenerator.CardInfo card;
    }
    public static ChooseCardForTransfer donorCard(int startBalance1, int startBalance2) {
        if(startBalance1 >= startBalance2) {
            return new ChooseCardForTransfer(DataGenerator.getCard1());
        } else {
            return new ChooseCardForTransfer(DataGenerator.getCard2());
        }
    }

    public static ChooseCardForTransfer recipientCard(int startBalance1, int startBalance2) {
        if(startBalance1 >= startBalance2) {
            return new ChooseCardForTransfer(DataGenerator.getCard2());
        } else {
            return new ChooseCardForTransfer(DataGenerator.getCard1());
        }
    }

    @Value
    public static class StartBalance {
        private int startBalance;
    }

    public static int donorStartBalance(int startBalance1, int startBalance2) {
        int donorStartBalance;
        if(startBalance1 >= startBalance2) {
            donorStartBalance = startBalance1;
        } else {
            donorStartBalance = startBalance2;
        }
        return donorStartBalance;
    }

    public static int recipientStartBalance(int startBalance1, int startBalance2) {
        int recipientStartBalance;
        if(startBalance1 >= startBalance2) {
            recipientStartBalance = startBalance2;
        } else {
            recipientStartBalance = startBalance1;
        }
        return recipientStartBalance;
    }

    @Value
    public static class CalcNewBalance {
        private int newBalance;
    }

    public static CalcNewBalance newDonorBalance(int startBalance, String sumTransfer) {
        return new CalcNewBalance(startBalance - Integer.parseInt(sumTransfer));
    }

    public static CalcNewBalance newRecipientBalance(int startBalance, String sumTransfer) {
        return new CalcNewBalance(startBalance + Integer.parseInt(sumTransfer));
    }
}
