package ru.netology.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;
import ru.netology.data.DataGenerator;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private ElementsCollection cards = $$(".list__item");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";
    private SelenideElement addMoneу = $("[data-test-id= '0f3f5c2a-249e-4c3d-8287-09f7a039391d'] .button");

    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public int getBalanceCard1 (String id1) {
        val text = cards.first().text();
        return extractBalance(text);
    }

    public int getBalanceCard2(String id2) {
        val text = cards.last().text();
        return extractBalance(text);
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public DashboardPage goToForm() {
        addMoneу.click();
        return new DashboardPage();
    }

    public int calcNewBalanceCard1 (int startBalance, String sumTransfer) {
        return startBalance - Integer.parseInt(sumTransfer);
    }

    public int calcNewBalanceCard2 (int startBalance, String sumTransfer) {
        return startBalance + Integer.parseInt(sumTransfer);
    }

    public boolean compareCardBalance(int startBalance, int newBalance) {
        if(startBalance == newBalance) {
            return true;
        } else {
            return false;
        }
    }
}

