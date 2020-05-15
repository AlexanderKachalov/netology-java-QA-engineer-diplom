package tests;


import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import pages.PageOperation;
import pages.PageSelection;
import utils.CardData;
import utils.DbOperation;

import java.sql.SQLException;



public class TestCreditPay {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    public void cleanDataBase() throws SQLException {
        DbOperation.cleanTables();
    }

    PageSelection pageSelection = new PageSelection();
    PageOperation pageOperation = new PageOperation();

    // оплата кредитом
    @Test
    @DisplayName("Тест №19. Оплата кредитом по данным карты c произвольным номером карты")
    void payCreditNotValidNumber() {
        pageOperation.openPage();
        pageSelection.creditPayment();
        pageOperation.fillData(CardData.randomCardNumber());
        pageOperation.paymentNotValidCard();
    }

    @Test
    @DisplayName("Тест №20. Оплата кредитом по данным карты с неполным номером карты: произвольные двенадцать цифр")
    void payCreditShortNumber() {
        pageOperation.openPage();
        pageSelection.creditPayment();
        pageOperation.fillData(CardData.shortCardNumber());
        pageOperation.paymentNotValidCardData("Неверный формат");
    }

    @Test
    @DisplayName("Тест №21. Оплата кредитом по данным карты c некорректным номером месяца: 13")
    void payCreditNotValidMonth() {
        pageOperation.openPage();
        pageSelection.creditPayment();
        pageOperation.fillData(CardData.impossibleMonth());
        pageOperation.paymentNotValidCardData("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Тест №22. Оплата кредитом по данным карты c некорректным номером месяца: 00")
    void payCreditNotValidZeroMonth() {
        pageOperation.openPage();
        pageSelection.creditPayment();
        pageOperation.fillData(CardData.zeroMonth());
        pageOperation.paymentNotValidCardData("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Тест №23. Оплата кредитом по данным карты c истекшим годом: -1 от текущего")
    void payCreditNotValidLastYear() {
        pageOperation.openPage();
        pageSelection.creditPayment();
        pageOperation.fillData(CardData.lastYear());
        pageOperation.paymentNotValidCardData("Истёк срок действия карты");
    }

    @Test
    @DisplayName("Тест №24. Оплата кредитом по данным карты c годом более пяти лет: +6 от текущего")
    void payCreditNotValidFutureYear() {
        pageOperation.openPage();
        pageSelection.creditPayment();
        pageOperation.fillData(CardData.futureYear());
        pageOperation.paymentNotValidCardData("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Тест №25. Оплата кредитом по данным карты. Поле владелец: введено на кириллице")
    void payCreditNotValidRusOwner() {
        pageOperation.openPage();
        pageSelection.creditPayment();
        pageOperation.fillData(CardData.russianNameOwner());
        pageOperation.paymentNotValidCardData("Неверный формат");
    }

    @Test
    @DisplayName("Тест №26. Оплата кредитом по данным карты. Поле владелец: произвольные символы")
    void payCreditNotValidAnyValuesOwner() {
        pageOperation.openPage();
        pageSelection.creditPayment();
        pageOperation.fillData(CardData.anyValuesOwner());
        pageOperation.paymentNotValidCardData("Неверный формат");
    }

    @Test
    @DisplayName("Тест №27. Оплата кредитом по данным карты. Поле владелец: номер телефона")
    void payCreditNotValidPhoneNumberOwner() {
        pageOperation.openPage();
        pageSelection.creditPayment();
        pageOperation.fillData(CardData.phoneNumberOwner());
        pageOperation.paymentNotValidCardData("Неверный формат");
    }

    @Test
    @DisplayName("Тест №28. Оплата кредитом по данным карты. Поле владелец: пустое")
    void payCreditNotValidEmptyOwner() {
        pageOperation.openPage();
        pageSelection.creditPayment();
        pageOperation.fillData(CardData.emptyFieldOwner());
        pageOperation.paymentNotValidCardData("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Тест №29. Оплата кредитом по данным карты. Поле CVC/CVV: 000")
    void payCreditNotValidZeroCVC() {
        pageOperation.openPage();
        pageSelection.creditPayment();
        pageOperation.fillData(CardData.zeroNumberCVC());
        pageOperation.paymentNotValidCardData("Неверный формат");
    }

    @Test
    @DisplayName("Тест №30. Оплата кредитом по данным карты. Поле CVC/CVV: пустое")
    void payCreditNotValidEmptyCVC() {
        pageOperation.openPage();
        pageSelection.creditPayment();
        pageOperation.fillData(CardData.emptyFieldCVC());
        pageOperation.paymentNotValidCardData("Неверный формат");
    }

    @Test
    @DisplayName("Тест №31. Оплата кредитом по данным карты. Все поля формы пустые")
    void payCreditNotValidEmptyAllField() {
        pageOperation.openPage();
        pageSelection.creditPayment();
        pageOperation.fillData(CardData.emptyAllFieldCard());
        pageOperation.paymentNotValidCardData("Неверный формат");
    }

    @Test
    @DisplayName("Тест №32. Оплата кредитом по данным карты. Поле CVC/CVV пустое, затем заполняется, должно пропасть сообщение о неверно заполненном поле")
    void payCreditNotValidEmptyCVCThenValidCvc() {
        pageOperation.openPage();
        pageSelection.creditPayment();
        pageOperation.fillData(CardData.emptyFieldCVC());
        pageOperation.paymentNotValidCardData("Неверный формат");
        pageOperation.putValidCvcCheckTextError("Неверный формат");
    }
}
