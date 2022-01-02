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
        val getStartBalance1 = dashboardPage.getCardBalance(DataGenerator.getCard1());
        val getStartBalance2 = dashboardPage.getCardBalance(DataGenerator.getCard2());
        val sumTransfer = getStartBalance1;

        val moneyTransferPage = dashboardPage.goToForm(DataGenerator.getCard2());
        val newDashboardPage = moneyTransferPage.moneyTransfer(String.valueOf(sumTransfer), DataGenerator.getCard1());

        val actualBalance1 = newDashboardPage.getCardBalance(DataGenerator.getCard1());
        val actualBalance2 = newDashboardPage.getCardBalance(DataGenerator.getCard2());
        val expectedBalance2 = DataGenerator.newRecipientBalance(getStartBalance2, sumTransfer).getNewBalance();

        assertEquals(0, actualBalance1);
        assertEquals(expectedBalance2, actualBalance2);
    }

    @Test
    public void shouldTransferAllSumFrom2To1() {
        val getStartBalance1 = dashboardPage.getCardBalance(DataGenerator.getCard1());
        val getStartBalance2 = dashboardPage.getCardBalance(DataGenerator.getCard2());
        val sumTransfer = getStartBalance2;

        val moneyTransferPage = dashboardPage.goToForm(DataGenerator.getCard1());
        val newDashboardPage = moneyTransferPage.moneyTransfer(String.valueOf(sumTransfer), DataGenerator.getCard2());

        val actualBalance1 = newDashboardPage.getCardBalance(DataGenerator.getCard1());
        val actualBalance2 = newDashboardPage.getCardBalance(DataGenerator.getCard2());
        val expectedBalance1 = DataGenerator.newRecipientBalance(getStartBalance1, sumTransfer).getNewBalance();

        assertEquals(0, actualBalance2);
        assertEquals(expectedBalance1, actualBalance1);
    }

}
