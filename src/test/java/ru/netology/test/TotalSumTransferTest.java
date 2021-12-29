package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataGenerator;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;
import ru.netology.page.MoneyTransferPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TotalSumTransferTest {
    DashboardPage dashboardPage;

    @BeforeEach
    void shouldLogin() {
        open("http://localhost:9999");
        val loginPage = new LoginPage();
        val authInfo = DataGenerator.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataGenerator.getVerificationCodeFor(authInfo);
        dashboardPage = verificationPage.validVerify(verificationCode);
    }
    @Test
    public void shouldTransferAllSumFrom1To2() {
        int getStartBalance1 = dashboardPage.getCardBalance(DataGenerator.getCard1());
        int getStartBalance2 = dashboardPage.getCardBalance(DataGenerator.getCard2());
        val sumTransfer = getStartBalance1;

        dashboardPage.goToForm(DataGenerator.getCard2());
        val newDashboardPage = MoneyTransferPage.moneyTransfer(String.valueOf(sumTransfer), DataGenerator.getCard1());

        val getNewBalance1 = newDashboardPage.getCardBalance(DataGenerator.getCard1());
        val getNewBalance2 = newDashboardPage.getCardBalance(DataGenerator.getCard2());
        val calculateNewBalance2 = DataGenerator.newRecipientBalance(getStartBalance2, sumTransfer).getNewBalance();

        assertEquals(0, getNewBalance1);
        assertEquals(calculateNewBalance2, getNewBalance2);
    }

    @Test
    public void shouldTransferAllSumFrom2To1() {
        int getStartBalance1 = dashboardPage.getCardBalance(DataGenerator.getCard1());
        int getStartBalance2 = dashboardPage.getCardBalance(DataGenerator.getCard2());
        val sumTransfer = getStartBalance2;

        dashboardPage.goToForm(DataGenerator.getCard1());
        val newDashboardPage = MoneyTransferPage.moneyTransfer(String.valueOf(sumTransfer), DataGenerator.getCard2());

        val getNewBalance1 = newDashboardPage.getCardBalance(DataGenerator.getCard1());
        val getNewBalance2 = newDashboardPage.getCardBalance(DataGenerator.getCard2());
        val calculateNewBalance1 = DataGenerator.newRecipientBalance(getStartBalance1, sumTransfer).getNewBalance();

        assertEquals(0, getNewBalance2);
        assertEquals(calculateNewBalance1, getNewBalance1);
    }

}
