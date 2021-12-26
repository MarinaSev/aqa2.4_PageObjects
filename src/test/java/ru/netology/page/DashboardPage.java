package ru.netology.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;
import ru.netology.data.DataGenerator;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private ElementsCollection cards = $$(".list__item");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public int getCardBalance (DataGenerator.CardInfo card) {
//        val text = cards.findBy(attribute("data-test-id", card.getCardId())).getText();
        val text = cards.findBy(text(card.getCardNumber().substring(16,19))).getText();
        return extractBalance(text);
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public MoneyTransferPage goToForm(DataGenerator.CardInfo recipientCard) {
        cards.findBy(text(recipientCard.getCardNumber().substring(16,19))).$(".button").click();
        return new MoneyTransferPage();
    }

//    public DataGenerator.CardInfo donorCard(int startBalance1, int startBalance2) {
//        if(startBalance1 >= startBalance2) {
//            return DataGenerator.getCard1();
//        } else {
//            return DataGenerator.getCard2();
//        }
//    }
//
//    public DataGenerator.CardInfo recipientCard(int startBalance1, int startBalance2) {
//        if(startBalance1 >= startBalance2) {
//            return DataGenerator.getCard2();
//        } else {
//            return DataGenerator.getCard1();
//        }
//    }


}

