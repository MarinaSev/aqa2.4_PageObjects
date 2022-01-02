package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataGenerator;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {

    DashboardPage dashboardPage;

    @BeforeEach
    void shouldLogin() {
        val loginPage = open("http://localhost:9999", LoginPage.class);
        val authInfo = DataGenerator.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataGenerator.getVerificationCodeFor(authInfo);
        dashboardPage = verificationPage.validVerify(verificationCode);
    }

    @Test
    public void shouldTransferMoneyFrom1To2() {
        val getStartBalance1 = dashboardPage.getCardBalance(DataGenerator.getCard1());
        val getStartBalance2 = dashboardPage.getCardBalance(DataGenerator.getCard2());
        val sumTransfer = DataGenerator.getSumTransfer(getStartBalance1).getSumTransfer();

        val moneyTransferPage = dashboardPage.goToForm(DataGenerator.getCard2());
        val newDashboardPage = moneyTransferPage.moneyTransfer(String.valueOf(sumTransfer), DataGenerator.getCard1());

        val actualBalance1 = newDashboardPage.getCardBalance(DataGenerator.getCard1());
        val actualBalance2 = newDashboardPage.getCardBalance(DataGenerator.getCard2());
        val expectedBalance1 = DataGenerator.newDonorBalance(getStartBalance1, sumTransfer).getNewBalance();
        val expectedBalance2 = DataGenerator.newRecipientBalance(getStartBalance2, sumTransfer).getNewBalance();

        assertEquals(expectedBalance1, actualBalance1);
        assertEquals(expectedBalance2, actualBalance2);
    }

    @Test
    public void shouldTransferMoneyFrom2To1() {
        val getStartBalance1 = dashboardPage.getCardBalance(DataGenerator.getCard1());
        val getStartBalance2 = dashboardPage.getCardBalance(DataGenerator.getCard2());
        val sumTransfer = DataGenerator.getSumTransfer(getStartBalance2).getSumTransfer();

        val moneyTransferPage = dashboardPage.goToForm(DataGenerator.getCard1());
        val newDashboardPage = moneyTransferPage.moneyTransfer(String.valueOf(sumTransfer), DataGenerator.getCard2());

        val actualBalance1 = newDashboardPage.getCardBalance(DataGenerator.getCard1());
        val actualBalance2 = newDashboardPage.getCardBalance(DataGenerator.getCard2());
        val expectedBalance2 = DataGenerator.newDonorBalance(getStartBalance2, sumTransfer).getNewBalance();
        val expectedBalance1 = DataGenerator.newRecipientBalance(getStartBalance1, sumTransfer).getNewBalance();

        assertEquals(expectedBalance2, actualBalance2);
        assertEquals(expectedBalance1, actualBalance1);
    }

    @Test
    public void shouldTransferOverSumFrom1To2() {
        val getStartBalance1 = dashboardPage.getCardBalance(DataGenerator.getCard1());
        val sumTransfer = DataGenerator.calcOverSum(getStartBalance1).getOverSum();

        val moneyTransferPage = dashboardPage.goToForm(DataGenerator.getCard2());
        moneyTransferPage.moneyTransfer(String.valueOf(sumTransfer), DataGenerator.getCard1());
        moneyTransferPage.failedMoneyTransferError();
    }

    @Test
    public void shouldTransferOverSumFrom2To1() {
        val getStartBalance2 = dashboardPage.getCardBalance(DataGenerator.getCard2());
        val sumTransfer = DataGenerator.calcOverSum(getStartBalance2).getOverSum();

        val moneyTransferPage = dashboardPage.goToForm(DataGenerator.getCard1());
        moneyTransferPage.moneyTransfer(String.valueOf(sumTransfer), DataGenerator.getCard2());
        moneyTransferPage.failedMoneyTransferError();
    }

    @Test
    public void shouldTransferNothingFrom1To2() {
        val moneyTransferPage = dashboardPage.goToForm(DataGenerator.getCard2());
        moneyTransferPage.emptyMoneyTransfer(DataGenerator.getCard1());
    }

    @Test
    public void shouldTransferNothingFrom2To1() {
        val moneyTransferPage = dashboardPage.goToForm(DataGenerator.getCard1());
        moneyTransferPage.emptyMoneyTransfer(DataGenerator.getCard2());
    }


    @Test
    public void shouldTransferNullFrom1To2() {
        val sumTransfer = 0;

        val moneyTransferPage = dashboardPage.goToForm(DataGenerator.getCard2());
        moneyTransferPage.moneyTransfer(String.valueOf(sumTransfer), DataGenerator.getCard1());
        moneyTransferPage.nullMoneyTransferError();
    }

    @Test
    public void shouldTransferNullFrom2To1() {
        val sumTransfer = 0;

        val moneyTransferPage = dashboardPage.goToForm(DataGenerator.getCard1());
        moneyTransferPage.moneyTransfer(String.valueOf(sumTransfer), DataGenerator.getCard2());
        moneyTransferPage.nullMoneyTransferError();
    }

}

