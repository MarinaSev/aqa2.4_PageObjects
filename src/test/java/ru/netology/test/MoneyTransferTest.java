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

public class MoneyTransferTest {

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
    public void shouldTransferMoneyBetweenCards() {
        String sumTransfer = "200";

        val getStartBalance1 = dashboardPage.getCardBalance(DataGenerator.getCard1());
        val getStartBalance2 = dashboardPage.getCardBalance(DataGenerator.getCard2());

        if(getStartBalance1 >= getStartBalance2) {
            dashboardPage.goToForm(DataGenerator.getCard2());
            val donorCard = DataGenerator.getCard1();
            val recipientCard = DataGenerator.getCard2();

            val newDashboardPage = MoneyTransferPage.moneyTransfer(sumTransfer, donorCard);
            val getDonorBalance = newDashboardPage.getCardBalance(donorCard);
            val getRecipientBalance = newDashboardPage.getCardBalance(recipientCard);
            val calculateDonorBalance = DataGenerator.newDonorBalance(getStartBalance1, sumTransfer);
            val calculateRecipientBalance = DataGenerator.newRecipientBalance(getStartBalance2, sumTransfer);

            assertEquals(calculateDonorBalance, getDonorBalance);
            assertEquals(calculateRecipientBalance, getRecipientBalance);

        } else {
            dashboardPage.goToForm(DataGenerator.getCard1());
            val donorCard = DataGenerator.getCard2();
            val recipientCard = DataGenerator.getCard1();
            val newDashboardPage = MoneyTransferPage.moneyTransfer(sumTransfer, donorCard);
            val getDonorBalance = newDashboardPage.getCardBalance(donorCard);
            val getRecipientBalance = newDashboardPage.getCardBalance(recipientCard);
            val calculateDonorBalance = DataGenerator.newDonorBalance(getStartBalance2, sumTransfer);
            val calculateRecipientBalance = DataGenerator.newRecipientBalance(getStartBalance1, sumTransfer);

            assertEquals(calculateDonorBalance, getDonorBalance);
            assertEquals(calculateRecipientBalance, getRecipientBalance);
        }
    }
}

