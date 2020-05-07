package pages;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import utils.CardData;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class Page {
    public static int timeout = 40000;

    private final SelenideElement continueButton = $(byText("Продолжить")).parent().parent();;
    private final SelenideElement cardNumberField = $(byText("Номер карты")).parent();
    private final SelenideElement monthField = $(byText("Месяц")).parent();;
    private final SelenideElement yearField = $(byText("Год")).parent();
    private final SelenideElement ownerField = $(byText("Владелец")).parent();
    private final SelenideElement cvcField = $(byText("CVC/CVV")).parent();
    private final SelenideElement successOperation = $(withText("Операция одобрена Банком."));
    private final SelenideElement errorOperation = $(withText("Ошибка! Банк отказал в проведении операции."));
    private final ElementsCollection errorText = $$(".input__sub");


    @Step("Проверка оплаты c валидными данными")
    public void paymentValidCard() {
        continueButton.click();
        successOperation.waitUntil(Condition.visible, timeout);
    }

    @Step("Проверка оплаты с невалидными данными")
    public void paymentNotValidCardData(String textError) {
        continueButton.click();
        SelenideElement errorField = errorText.findBy(Condition.text(textError));
        errorField.waitUntil(Condition.visible, timeout);
    }

    @Step("Проверка появления всплывающего окна с ошибкой - Банк отклонил операцию")
    public void paymentNotValidCard() {
        continueButton.click();
        errorOperation.waitUntil(Condition.visible, timeout);
    }

    @Step("Заполнение полей данными карты")
    public void fillData(CardData cardData) {
        cardNumberField.$(".input__control").setValue(cardData.getCardNumber());
        monthField.$(".input__control").setValue(cardData.getMonth());
        yearField.$(".input__control").setValue(cardData.getYear());
        ownerField.$(".input__control").setValue(cardData.getOwner());
        cvcField.$(".input__control").setValue(cardData.getCvc());
    }

    @Step("Повторное заполнение поля CVC/CVV")
    public void putValidCvcCheckTextError(String textError) {
        cvcField.$(".input__control").setValue(CardData.approvedCard().getCvc());
        continueButton.click();
        SelenideElement errorField = errorText.findBy(Condition.text(textError));
        errorField.shouldNotBe(Condition.text(textError));
    }

}
