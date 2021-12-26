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
        int getStartBalance1 = dashboardPage.getCardBalance(DataGenerator.getCard1());
        int getStartBalance2 = dashboardPage.getCardBalance(DataGenerator.getCard2());

        val donorStartBalance = DataGenerator.donorStartBalance(getStartBalance1, getStartBalance2);
        val recipientStartBalance = DataGenerator.recipientStartBalance(getStartBalance1, getStartBalance2);

        val donorCard = DataGenerator.donorCard(getStartBalance1, getStartBalance2).getCard();
        val recipientCard= DataGenerator.recipientCard(getStartBalance1, getStartBalance2).getCard();

        dashboardPage.goToForm(recipientCard);
        val newDashboardPage = MoneyTransferPage.moneyTransfer(sumTransfer, donorCard);
        val getDonorBalance = newDashboardPage.getCardBalance(donorCard);
        val getRecipientBalance = newDashboardPage.getCardBalance(recipientCard);
        val calculateDonorBalance = DataGenerator.newDonorBalance(donorStartBalance, sumTransfer).getNewBalance();
        val calculateRecipientBalance = DataGenerator.newRecipientBalance(recipientStartBalance, sumTransfer).getNewBalance();

        assertEquals(calculateDonorBalance, getDonorBalance);
        assertEquals(calculateRecipientBalance, getRecipientBalance);
    }

    @Test
    public void shouldTransferOverSumFrom1To2() {
        int getStartBalance1 = dashboardPage.getCardBalance(DataGenerator.getCard1());
        int getStartBalance2 = dashboardPage.getCardBalance(DataGenerator.getCard2());
        val sumTransfer = DataGenerator.calcOverSum(getStartBalance1).getSumTransfer();

        dashboardPage.goToForm(DataGenerator.getCard2());
        val newDashboardPage = MoneyTransferPage.moneyTransfer(sumTransfer, DataGenerator.getCard1());
        //ToDo: вставить проверку сообщения об ошибке, когда баг будет исправлен
        val getDonorBalance = newDashboardPage.getCardBalance(DataGenerator.getCard1());
        val getRecipientBalance = newDashboardPage.getCardBalance(DataGenerator.getCard2());
        val calculateDonorBalance = DataGenerator.newDonorBalance(getStartBalance1, sumTransfer).getNewBalance();
        val calculateRecipientBalance = DataGenerator.newRecipientBalance(getStartBalance2, sumTransfer).getNewBalance();

        assertEquals(calculateDonorBalance, getDonorBalance);
        assertEquals(calculateRecipientBalance, getRecipientBalance);
    }
}

