package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataGenerator;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class MoneyTransferPage {
    private SelenideElement heading = $(".heading_size_xl");
    private static SelenideElement sumField = $("[data-test-id='amount'] .input__control");
    private static SelenideElement fromField = $("[data-test-id='from'] [placeholder='0000 0000 0000 0000']");
    private static SelenideElement toField = $("[data-test-id='to'] [type='text']");
    private static SelenideElement asseptButtom = $("[data-test-id='action-transfer']");

    public MoneyTransferPage() {
        heading.shouldBe(visible).shouldHave(exactText("Пополнение карты"));
    }

    public static DashboardPage moneyTransfer(String sumTransfer) {
        sumField.setValue(sumTransfer);
        fromField.setValue(DataGenerator.getCard1().getCard());
        toField.shouldBe(visible);
        asseptButtom.click();
        return new DashboardPage();
    }
}

