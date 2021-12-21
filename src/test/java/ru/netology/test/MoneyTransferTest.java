package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataGenerator;
import ru.netology.page.LoginPage;
import ru.netology.page.MoneyTransferPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MoneyTransferTest {
    @Test
    public void shouldTransferMoneyBetweenOwnCards() {
        String sumTransfer = "200";
        final String id1 = "92df3f1c-a033-48e6-8390-206f6b1f56c0";
        final String id2 = "0f3f5c2a-249e-4c3d-8287-09f7a039391d";

        open("http://localhost:9999");
        val loginPage = new LoginPage();
        val authInfo = DataGenerator.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataGenerator.getVerificationCodeFor(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        val getStartBalance1 = dashboardPage.getBalanceCard1(id1);
        val getStartBalance2 = dashboardPage.getBalanceCard2(id2);

        dashboardPage.goToForm();
        val newDashbordPage = MoneyTransferPage.moneyTransfer(sumTransfer);

        val getNewBalance1 = newDashbordPage.getBalanceCard1(id1);
        val getNewBalance2 = newDashbordPage.getBalanceCard2(id2);
        val calculateNewBalance1 = newDashbordPage.calcNewBalanceCard1(getStartBalance1,sumTransfer);
        val calculateNewBalance2 = newDashbordPage.calcNewBalanceCard2(getStartBalance2,sumTransfer);

        assertTrue(newDashbordPage.compareCardBalance(getNewBalance1, calculateNewBalance1));
        assertTrue(newDashbordPage.compareCardBalance(getNewBalance2, calculateNewBalance2));
    }
}

