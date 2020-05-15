package tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import pages.PageOperation;
import pages.PageSelection;
import utils.CardData;
import utils.DbOperation;

import java.sql.SQLException;



public class TestCardPay {

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

    // оплата картой
    @Test
    @DisplayName("Тест №05. Оплата картой c произвольным номером карты")
    void payCardNotValidNumber() {
        pageOperation.openPage();
        pageSelection.cardPayment();
        pageOperation.fillData(CardData.randomCardNumber());
        pageOperation.paymentNotValidCard();
    }

    @Test
    @DisplayName("Тест №06. Оплата картой с неполным номером карты: произвольные двенадцать цифр")
    void payCardShortNumber() {
        pageOperation.openPage();
        pageSelection.cardPayment();
        pageOperation.fillData(CardData.shortCardNumber());
        pageOperation.paymentNotValidCardData("Неверный формат");
    }

    @Test
    @DisplayName("Тест №07. Оплата картой c некорректным номером месяца: 13")
    void payCardNotValidMonth() {
        pageOperation.openPage();
        pageSelection.cardPayment();
        pageOperation.fillData(CardData.impossibleMonth());
        pageOperation.paymentNotValidCardData("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Тест №08. Оплата картой c некорректным номером месяца: 00")
    void payCardNotValidZeroMonth() {
        pageOperation.openPage();
        pageSelection.cardPayment();
        pageOperation.fillData(CardData.zeroMonth());
        pageOperation.paymentNotValidCardData("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Тест №09. Оплата картой c истекшим годом: -1 от текущего")
    void payCardNotValidLastYear() {
        pageOperation.openPage();
        pageSelection.cardPayment();
        pageOperation.fillData(CardData.lastYear());
        pageOperation.paymentNotValidCardData("Истёк срок действия карты");
    }

    @Test
    @DisplayName("Тест №10. Оплата картой c годом более пяти лет: +6 от текущего")
    void payCardNotValidFutureYear() {
        pageOperation.openPage();
        pageSelection.cardPayment();
        pageOperation.fillData(CardData.futureYear());
        pageOperation.paymentNotValidCardData("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Тест №11. Оплата картой. Поле владелец: введено кириллицей")
    void payCardNotValidRusOwner() {
        pageOperation.openPage();
        pageSelection.cardPayment();
        pageOperation.fillData(CardData.russianNameOwner());
        pageOperation.paymentNotValidCardData("Неверный формат");
    }

    @Test
    @DisplayName("Тест №12. Оплата картой. Поле владелец: произвольные символы")
    void payCardNotValidAnyValuesOwner() {
        pageOperation.openPage();
        pageSelection.cardPayment();
        pageOperation.fillData(CardData.anyValuesOwner());
        pageOperation.paymentNotValidCardData("Неверный формат");
    }

    @Test
    @DisplayName("Тест №13. Оплата картой. Поле владелец: номер телефона")
    void payCardNotValidPhoneNumberOwner() {
        pageOperation.openPage();
        pageSelection.cardPayment();
        pageOperation.fillData(CardData.phoneNumberOwner());
        pageOperation.paymentNotValidCardData("Неверный формат");
    }

    @Test
    @DisplayName("Тест №14. Оплата картой. Поле владелец: пустое")
    void payCardNotValidEmptyOwner() {
        pageOperation.openPage();
        pageSelection.cardPayment();
        pageOperation.fillData(CardData.emptyFieldOwner());
        pageOperation.paymentNotValidCardData("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Тест №15. Оплата картой. Поле CVC/CVV: 000")
    void payCardNotValidZeroCVC() {
        pageOperation.openPage();
        pageSelection.cardPayment();
        pageOperation.fillData(CardData.zeroNumberCVC());
        pageOperation.paymentNotValidCardData("Неверный формат");
    }

    @Test
    @DisplayName("Тест №16. Оплата картой. Поле CVC/CVV: пустое")
    void payCardNotValidEmptyCVC() {
        pageOperation.openPage();
        pageSelection.cardPayment();
        pageOperation.fillData(CardData.emptyFieldCVC());
        pageOperation.paymentNotValidCardData("Неверный формат");
    }

    @Test
    @DisplayName("Тест №17. Оплата картой. Все поля формы пустые")
    void payCardNotValidEmptyAllField() {
        pageOperation.openPage();
        pageSelection.cardPayment();
        pageOperation.fillData(CardData.emptyAllFieldCard());
        pageOperation.paymentNotValidCardData("Неверный формат");
    }

    @Test
    @DisplayName("Тест №18. Оплата картой. Поле CVC/CVV пустое, затем заполняется, должно пропасть сообщение о неверно заполненном поле")
    void payCardNotValidEmptyCVCThenValidCvc() {
        pageOperation.openPage();
        pageSelection.cardPayment();
        pageOperation.fillData(CardData.emptyFieldCVC());
        pageOperation.paymentNotValidCardData("Неверный формат");
        pageOperation.putValidCvcCheckTextError("Неверный формат");
    }

}
