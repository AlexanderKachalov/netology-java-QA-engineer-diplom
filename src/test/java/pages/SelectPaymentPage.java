package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class SelectPaymentPage {

    private final SelenideElement paymentButton = $(byText("Купить")).parent().parent();;
    private final SelenideElement creditButton = $(byText("Купить в кредит")).parent().parent();
    private final SelenideElement paymentCardHead = $$(".heading").findBy(Condition.text("Оплата по карте"));
    private final SelenideElement paymentCreditHead = $$(".heading").findBy(Condition.text("Кредит по данным карты"));


    public void cardPayment() {
        paymentButton.click();
        paymentCardHead.waitUntil(Condition.visible, Page.timeout);
    }

    public void creditPayment() {
        creditButton.click();
        paymentCreditHead.waitUntil(Condition.visible, Page.timeout);
    }

    public void openPage() {
            open("http://localhost:8080");
    }

}

