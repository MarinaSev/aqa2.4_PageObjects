package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

public class MoneyTransferPage {
    private SelenideElement heading = $(".heading_size_xl");
    private static SelenideElement sumField = $("[data-test-id='amount'] .input__control");
    private static SelenideElement fromField = $("[data-test-id='from'] [placeholder='0000 0000 0000 0000']");
    private static SelenideElement toField = $("[data-test-id='to'] [type='text']");
    private static SelenideElement asseptButtom = $("[data-test-id='action-transfer']");
    private DashboardPage dashboardPage;


    public MoneyTransferPage() {
        heading.shouldBe(visible).shouldHave(exactText("Пополнение карты"));
    }

    public DashboardPage moneyTransfer(String sumTransfer, DataGenerator.CardInfo card) {
        sumField.setValue(sumTransfer);
        fromField.setValue(card.getCardNumber());
        toField.shouldBe(visible);
        asseptButtom.click();
        return new DashboardPage();
    }

    public void failedMoneyTransferError() {
        $(withText("Ошибка! Суммы на вашем балансе недостаточно для перевода")).shouldBe(appear, Duration.ofSeconds(10));
        //ToDo: проверить текст сообщения об ошибке, когда будет исправлен баг
    }

    public void nullMoneyTransferError() {
        $(withText("Ошибка! Перевод нулевой суммы невозможен")).shouldBe(appear, Duration.ofSeconds(10));
        //ToDo: проверить текст сообщения об ошибке, когда будет исправлен баг
    }

    public void emptyMoneyTransfer(DataGenerator.CardInfo card) {
        fromField.setValue(card.getCardNumber());
        toField.shouldBe(visible);
        asseptButtom.click();
        $(withText("Ошибка! Необходимо указать сумму перевода")).shouldBe(appear, Duration.ofSeconds(10));
        //ToDo: проверить текст сообщения об ошибке, когда будет исправлен баг
    }
}

