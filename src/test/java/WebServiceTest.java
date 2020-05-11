import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
import pages.Page;
import pages.SelectPaymentPage;
import utils.CardData;
import utils.DbUtils;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class WebServiceTest {

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
        DbUtils.cleanTables();
    }

    Page page = new Page();

    // оплата картой
    @Test
    @DisplayName("Тест №01. Оплата картой статус APPROVED. Корректные данные")
    void payCardApproved() {
        SelectPaymentPage selectPaymentPage = new SelectPaymentPage();
        selectPaymentPage.openPage();
        selectPaymentPage.cardPayment();
        page.fillData(CardData.approvedCard());
        page.paymentValidCard();
    }

    @Test
    @DisplayName("Тест №02. Оплата картой статус DECLINED. Корректные данные")
    void payCardDeclined() {
        SelectPaymentPage selectPaymentPage = new SelectPaymentPage();
        selectPaymentPage.openPage();
        selectPaymentPage.cardPayment();
        page.fillData(CardData.declinedCard());
        page.paymentNotValidCard();
    }

    @Test
    @DisplayName("Тест №03. Оплата картой c произвольным номером карты")
    void payCardNotValidNumber() {
        SelectPaymentPage selectPaymentPage = new SelectPaymentPage();
        selectPaymentPage.openPage();
        selectPaymentPage.cardPayment();
        page.fillData(CardData.randomCardNumber());
        page.paymentNotValidCard();
    }

    @Test
    @DisplayName("Тест №04. Оплата картой с неполным номером карты: произвольные двенадцать цифр")
    void payCardShortNumber() {
        SelectPaymentPage selectPaymentPage = new SelectPaymentPage();
        selectPaymentPage.openPage();
        selectPaymentPage.cardPayment();
        page.fillData(CardData.shortCardNumber());
        page.paymentNotValidCardData("Неверный формат");
    }

    @Test
    @DisplayName("Тест №05. Оплата картой c некорректным номером месяца: 13")
    void payCardNotValidMonth() {
        SelectPaymentPage selectPaymentPage = new SelectPaymentPage();
        selectPaymentPage.openPage();
        selectPaymentPage.cardPayment();
        page.fillData(CardData.impossibleMonth());
        page.paymentNotValidCardData("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Тест №06. Оплата картой c некорректным номером месяца: 00")
    void payCardNotValidZeroMonth() {
        SelectPaymentPage selectPaymentPage = new SelectPaymentPage();
        selectPaymentPage.openPage();
        selectPaymentPage.cardPayment();
        page.fillData(CardData.zeroMonth());
        page.paymentNotValidCardData("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Тест №07. Оплата картой c истекшим годом: -1 от текущего")
    void payCardNotValidLastYear() {
        SelectPaymentPage selectPaymentPage = new SelectPaymentPage();
        selectPaymentPage.openPage();
        selectPaymentPage.cardPayment();
        page.fillData(CardData.lastYear());
        page.paymentNotValidCardData("Истёк срок действия карты");
    }

    @Test
    @DisplayName("Тест №08. Оплата картой c годом более пяти лет: +6 от текущего")
    void payCardNotValidFutureYear() {
        SelectPaymentPage selectPaymentPage = new SelectPaymentPage();
        selectPaymentPage.openPage();
        selectPaymentPage.cardPayment();
        page.fillData(CardData.futureYear());
        page.paymentNotValidCardData("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Тест №09. Оплата картой. Поле владелец: введено кириллицей")
    void payCardNotValidRusOwner() {
        SelectPaymentPage selectPaymentPage = new SelectPaymentPage();
        selectPaymentPage.openPage();
        selectPaymentPage.cardPayment();
        page.fillData(CardData.russianNameOwner());
        page.paymentNotValidCardData("Неверный формат");
    }

    @Test
    @DisplayName("Тест №10. Оплата картой. Поле владелец: произвольные символы")
    void payCardNotValidAnyValuesOwner() {
        SelectPaymentPage selectPaymentPage = new SelectPaymentPage();
        selectPaymentPage.openPage();
        selectPaymentPage.cardPayment();
        page.fillData(CardData.anyValuesOwner());
        page.paymentNotValidCardData("Неверный формат");
    }

    @Test
    @DisplayName("Тест №11. Оплата картой. Поле владелец: номер телефона")
    void payCardNotValidPhoneNumberOwner() {
        SelectPaymentPage selectPaymentPage = new SelectPaymentPage();
        selectPaymentPage.openPage();
        selectPaymentPage.cardPayment();
        page.fillData(CardData.phoneNumberOwner());
        page.paymentNotValidCardData("Неверный формат");
    }

    @Test
    @DisplayName("Тест №12. Оплата картой. Поле владелец: пустое")
    void payCardNotValidEmptyOwner() {
        SelectPaymentPage selectPaymentPage = new SelectPaymentPage();
        selectPaymentPage.openPage();
        selectPaymentPage.cardPayment();
        page.fillData(CardData.emptyFieldOwner());
        page.paymentNotValidCardData("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Тест №13. Оплата картой. Поле CVC/CVV: 000")
    void payCardNotValidZeroCVC() {
        SelectPaymentPage selectPaymentPage = new SelectPaymentPage();
        selectPaymentPage.openPage();
        selectPaymentPage.cardPayment();
        page.fillData(CardData.zeroNumberCVC());
        page.paymentNotValidCardData("Неверный формат");
    }

    @Test
    @DisplayName("Тест №14. Оплата картой. Поле CVC/CVV: пустое")
    void payCardNotValidEmptyCVC() {
        SelectPaymentPage selectPaymentPage = new SelectPaymentPage();
        selectPaymentPage.openPage();
        selectPaymentPage.cardPayment();
        page.fillData(CardData.emptyFieldCVC());
        page.paymentNotValidCardData("Неверный формат");
    }

    @Test
    @DisplayName("Тест №15. Оплата картой. Все поля формы пустые")
    void payCardNotValidEmptyAllField() {
        SelectPaymentPage selectPaymentPage = new SelectPaymentPage();
        selectPaymentPage.openPage();
        selectPaymentPage.cardPayment();
        page.fillData(CardData.emptyAllFieldCard());
        page.paymentNotValidCardData("Неверный формат");
    }

    @Test
    @DisplayName("Тест №16. Оплата картой. Поле CVC/CVV пустое, затем заполняется, должно пропасть сообщение о неверно заполненном поле")
    void payCardNotValidEmptyCVCThenValidCvc() {
        SelectPaymentPage selectPaymentPage = new SelectPaymentPage();
        selectPaymentPage.openPage();
        selectPaymentPage.cardPayment();
        page.fillData(CardData.emptyFieldCVC());
        page.paymentNotValidCardData("Неверный формат");
        page.putValidCvcCheckTextError("Неверный формат");
    }

    // оплата кредитом
    @Test
    @DisplayName("Тест №17. Оплата кредитом по данным карты статус APPROVED. Корректные данные")
    void payCreditApproved() {
        SelectPaymentPage selectPaymentPage = new SelectPaymentPage();
        selectPaymentPage.openPage();
        selectPaymentPage.creditPayment();
        page.fillData(CardData.approvedCard());
        page.paymentValidCard();
    }

    @Test
    @DisplayName("Тест №18. Оплата кредитом по данным карты статус DECLINED. Корректные данные")
    void payCreditDeclined() {
        SelectPaymentPage selectPaymentPage = new SelectPaymentPage();
        selectPaymentPage.openPage();
        selectPaymentPage.creditPayment();
        page.fillData(CardData.declinedCard());
        page.paymentNotValidCard();
    }

    @Test
    @DisplayName("Тест №19. Оплата кредитом по данным карты c произвольным номером карты")
    void payCreditNotValidNumber() {
        SelectPaymentPage selectPaymentPage = new SelectPaymentPage();
        selectPaymentPage.openPage();
        selectPaymentPage.creditPayment();
        page.fillData(CardData.randomCardNumber());
        page.paymentNotValidCard();
    }

    @Test
    @DisplayName("Тест №20. Оплата кредитом по данным карты с неполным номером карты: произвольные двенадцать цифр")
    void payCreditShortNumber() {
        SelectPaymentPage selectPaymentPage = new SelectPaymentPage();
        selectPaymentPage.openPage();
        selectPaymentPage.creditPayment();
        page.fillData(CardData.shortCardNumber());
        page.paymentNotValidCardData("Неверный формат");
    }

    @Test
    @DisplayName("Тест №21. Оплата кредитом по данным карты c некорректным номером месяца: 13")
    void payCreditNotValidMonth() {
        SelectPaymentPage selectPaymentPage = new SelectPaymentPage();
        selectPaymentPage.openPage();
        selectPaymentPage.creditPayment();
        page.fillData(CardData.impossibleMonth());
        page.paymentNotValidCardData("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Тест №22. Оплата кредитом по данным карты c некорректным номером месяца: 00")
    void payCreditNotValidZeroMonth() {
        SelectPaymentPage selectPaymentPage = new SelectPaymentPage();
        selectPaymentPage.openPage();
        selectPaymentPage.creditPayment();
        page.fillData(CardData.zeroMonth());
        page.paymentNotValidCardData("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Тест №23. Оплата кредитом по данным карты c истекшим годом: -1 от текущего")
    void payCreditNotValidLastYear() {
        SelectPaymentPage selectPaymentPage = new SelectPaymentPage();
        selectPaymentPage.openPage();
        selectPaymentPage.creditPayment();
        page.fillData(CardData.lastYear());
        page.paymentNotValidCardData("Истёк срок действия карты");
    }

    @Test
    @DisplayName("Тест №24. Оплата кредитом по данным карты c годом более пяти лет: +6 от текущего")
    void payCreditNotValidFutureYear() {
        SelectPaymentPage selectPaymentPage = new SelectPaymentPage();
        selectPaymentPage.openPage();
        selectPaymentPage.creditPayment();
        page.fillData(CardData.futureYear());
        page.paymentNotValidCardData("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Тест №25. Оплата кредитом по данным карты. Поле владелец: введено на кириллице")
    void payCreditNotValidRusOwner() {
        SelectPaymentPage selectPaymentPage = new SelectPaymentPage();
        selectPaymentPage.openPage();
        selectPaymentPage.creditPayment();
        page.fillData(CardData.russianNameOwner());
        page.paymentNotValidCardData("Неверный формат");
    }

    @Test
    @DisplayName("Тест №26. Оплата кредитом по данным карты. Поле владелец: произвольные символы")
    void payCreditNotValidAnyValuesOwner() {
        SelectPaymentPage selectPaymentPage = new SelectPaymentPage();
        selectPaymentPage.openPage();
        selectPaymentPage.creditPayment();
        page.fillData(CardData.anyValuesOwner());
        page.paymentNotValidCardData("Неверный формат");
    }

    @Test
    @DisplayName("Тест №27. Оплата кредитом по данным карты. Поле владелец: номер телефона")
    void payCreditNotValidPhoneNumberOwner() {
        SelectPaymentPage selectPaymentPage = new SelectPaymentPage();
        selectPaymentPage.openPage();
        selectPaymentPage.creditPayment();
        page.fillData(CardData.phoneNumberOwner());
        page.paymentNotValidCardData("Неверный формат");
    }

    @Test
    @DisplayName("Тест №28. Оплата кредитом по данным карты. Поле владелец: пустое")
    void payCreditNotValidEmptyOwner() {
        SelectPaymentPage selectPaymentPage = new SelectPaymentPage();
        selectPaymentPage.openPage();
        selectPaymentPage.creditPayment();
        page.fillData(CardData.emptyFieldOwner());
        page.paymentNotValidCardData("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("Тест №29. Оплата кредитом по данным карты. Поле CVC/CVV: 000")
    void payCreditNotValidZeroCVC() {
        SelectPaymentPage selectPaymentPage = new SelectPaymentPage();
        selectPaymentPage.openPage();
        selectPaymentPage.creditPayment();
        page.fillData(CardData.zeroNumberCVC());
        page.paymentNotValidCardData("Неверный формат");
    }

    @Test
    @DisplayName("Тест №30. Оплата кредитом по данным карты. Поле CVC/CVV: пустое")
    void payCreditNotValidEmptyCVC() {
        SelectPaymentPage selectPaymentPage = new SelectPaymentPage();
        selectPaymentPage.openPage();
        selectPaymentPage.creditPayment();
        page.fillData(CardData.emptyFieldCVC());
        page.paymentNotValidCardData("Неверный формат");
    }

    @Test
    @DisplayName("Тест №31. Оплата кредитом по данным карты. Все поля формы пустые")
    void payCreditNotValidEmptyAllField() {
        SelectPaymentPage selectPaymentPage = new SelectPaymentPage();
        selectPaymentPage.openPage();
        selectPaymentPage.creditPayment();
        page.fillData(CardData.emptyAllFieldCard());
        page.paymentNotValidCardData("Неверный формат");
    }

    @Test
    @DisplayName("Тест №32. Оплата кредитом по данным карты. Поле CVC/CVV пустое, затем заполняется, должно пропасть сообщение о неверно заполненном поле")
    void payCreditNotValidEmptyCVCThenValidCvc() {
        SelectPaymentPage selectPaymentPage = new SelectPaymentPage();
        selectPaymentPage.openPage();
        selectPaymentPage.creditPayment();
        page.fillData(CardData.emptyFieldCVC());
        page.paymentNotValidCardData("Неверный формат");
        page.putValidCvcCheckTextError("Неверный формат");
    }

    //проверка базы данных
    @Test
    @DisplayName("Тест №33. Оплата картой со статусом APPROVED. Проверка поля status в таблице payment_entity")
    void checkResponseStatusFieldFromDbIfCardPayApproved() throws SQLException {
        DbUtils dbUtils = new DbUtils();
        SelectPaymentPage selectPaymentPage = new SelectPaymentPage();
        selectPaymentPage.openPage();
        selectPaymentPage.cardPayment();
        page.fillData(CardData.approvedCard());
        page.paymentValidCard();
        val statusPaymentEntityExpected = CardData.approvedCard().getStatus();
        val statusPaymentEntityActual = dbUtils.getCardPayDataBaseValues().getStatus();
        assertEquals(statusPaymentEntityExpected, statusPaymentEntityActual);
    }

    @Test
    @DisplayName("Тест №34. Оплата картой со статусом DECLINED. Проверка поля status в таблице payment_entity")
    void checkResponseStatusFieldFromDbIfCardPayDeclined() throws SQLException {
        DbUtils dbUtils = new DbUtils();
        SelectPaymentPage selectPaymentPage = new SelectPaymentPage();
        selectPaymentPage.openPage();
        selectPaymentPage.cardPayment();
        page.fillData(CardData.declinedCard());
        page.paymentValidCard();
        val statusPaymentEntityExpected = CardData.declinedCard().getStatus();
        val statusPaymentEntityActual = dbUtils.getCardPayDataBaseValues().getStatus();
        assertEquals(statusPaymentEntityExpected, statusPaymentEntityActual);
    }

    @Test
    @DisplayName("Тест №35. Оплата кредитом по данным карты с статусом APPROVED. Проверка поля status в таблице credit_request_entity")
    void checkResponseStatusFieldFromDbIfCreditPayApproved() throws SQLException {
        DbUtils dbUtils = new DbUtils();
        SelectPaymentPage selectPaymentPage = new SelectPaymentPage();
        selectPaymentPage.openPage();
        selectPaymentPage.creditPayment();
        page.fillData(CardData.approvedCard());
        page.paymentValidCard();
        val statusCreditRequestEntityExpected = CardData.approvedCard().getStatus();
        val statusCreditRequestEntityActual = dbUtils.getCreditPayDataBaseValues().getStatus();
        assertEquals(statusCreditRequestEntityExpected, statusCreditRequestEntityActual);
    }

    @Test
    @DisplayName("Тест №36. Оплата кредитом по данным карты со статусом DECLINED. Проверка поля status в таблице credit_request_entity")
    void checkResponseStatusFieldFromDbIfCreditPayDeclined() throws SQLException {
        DbUtils dbUtils = new DbUtils();
        SelectPaymentPage selectPaymentPage = new SelectPaymentPage();
        selectPaymentPage.openPage();
        selectPaymentPage.creditPayment();
        page.fillData(CardData.declinedCard());
        page.paymentValidCard();
        val statusCreditRequestEntityExpected = CardData.declinedCard().getStatus();
        val statusCreditRequestEntityActual = dbUtils.getCreditPayDataBaseValues().getStatus();
        assertEquals(statusCreditRequestEntityExpected, statusCreditRequestEntityActual);
    }

    // bug
    @Test
    @DisplayName("Тест №37. Оплата картой со статусом APPROVED. Проверка поля amount в таблице payment_entity. Значение поля amount должно быть эквивалентным стоимости тура: amount = 45000")
    void checkAmountFieldFromDbIfCardPayApproved() throws SQLException {
        DbUtils dbUtils = new DbUtils();
        SelectPaymentPage selectPaymentPage = new SelectPaymentPage();
        selectPaymentPage.openPage();
        selectPaymentPage.cardPayment();
        page.fillData(CardData.approvedCard());
        page.paymentValidCard();
        val amountActual = dbUtils.getCardPayDataBaseValues().getAmount();
        assertEquals(45000, amountActual);
    }

    // bug
    @Test
    @DisplayName("Тест №38. Оплата картой со статусом DECLINED. Проверка поля amount в таблице payment_entity. Значение поля amount должно быть эквивалентным стоимости тура: amount = 45000")
    void checkAmountFieldFromDbIfCardPayDeclined() throws SQLException {
        DbUtils dbUtils = new DbUtils();
        SelectPaymentPage selectPaymentPage = new SelectPaymentPage();
        selectPaymentPage.openPage();
        selectPaymentPage.cardPayment();
        page.fillData(CardData.declinedCard());
        page.paymentValidCard();
        val amountActual = dbUtils.getCardPayDataBaseValues().getAmount();
        assertEquals(45000, amountActual);
    }

    @Test
    @DisplayName("Тест №39. Оплата картой со статусом APPROVED. Сравнение полей transaction_id таблицы payment_entity и payment_id таблицы order_entity")
    void comparePaymentOrderIfCardPayApproved() throws SQLException {
        DbUtils dbUtils = new DbUtils();
        SelectPaymentPage selectPaymentPage = new SelectPaymentPage();
        selectPaymentPage.openPage();
        selectPaymentPage.cardPayment();
        page.fillData(CardData.approvedCard());
        page.paymentValidCard();
        val transactionIdActual = dbUtils.getCardPayDataBaseValues().getTransaction_id();
        val orderPaymentIdExpected = dbUtils.getCompareOrderPaymentId();
        assertEquals(orderPaymentIdExpected, transactionIdActual);
    }

    @Test
    @DisplayName("Тест №40. Оплата картой со статусом DECLINED. Сравнение полей transaction_id таблицы payment_entity и payment_id таблицы order_entity")
    void comparePaymentOrderIfCardPayDeclined() throws SQLException {
        DbUtils dbUtils = new DbUtils();
        SelectPaymentPage selectPaymentPage = new SelectPaymentPage();
        selectPaymentPage.openPage();
        selectPaymentPage.cardPayment();
        page.fillData(CardData.declinedCard());
        page.paymentValidCard();
        val transactionIdActual = dbUtils.getCardPayDataBaseValues().getTransaction_id();
        val orderPaymentIdExpected = dbUtils.getCompareOrderPaymentId();
        assertEquals(orderPaymentIdExpected, transactionIdActual);
    }

    @Test
    @DisplayName("Тест №41. Оплата кредитом по данным карты APPROVED. Сравнение полей credit_id таблицы order_entity и bank_id таблицы credit_request_entity")
    void compareCreditOrderIfCreditPayApproved() throws SQLException {
        DbUtils dbUtils = new DbUtils();
        SelectPaymentPage selectPaymentPage = new SelectPaymentPage();
        selectPaymentPage.openPage();
        selectPaymentPage.creditPayment();
        page.fillData(CardData.approvedCard());
        page.paymentValidCard();
        val bankIdActual = dbUtils.getCreditPayDataBaseValues().getBank_id();
        val creditIdExpected = dbUtils.getCompareOrderCreditId();
        assertEquals(creditIdExpected, bankIdActual);
    }

    @Test
    @DisplayName("Тест №42. Оплата кредитом по данным карты DECLINED. Сравнение полей credit_id таблицы order_entity и bank_id таблицы credit_request_entity")
    void compareCreditOrderIfCreditPayDeclined() throws SQLException {
        DbUtils dbUtils = new DbUtils();
        SelectPaymentPage selectPaymentPage = new SelectPaymentPage();
        selectPaymentPage.openPage();
        selectPaymentPage.creditPayment();
        page.fillData(CardData.declinedCard());
        page.paymentValidCard();
        val bankIdActual = dbUtils.getCreditPayDataBaseValues().getBank_id();
        val creditIdExpected = dbUtils.getCompareOrderCreditId();
        assertEquals(creditIdExpected, bankIdActual);
    }

    @Test
    @DisplayName("Тест №43. Оплата невалидной картой. В таблицы payment_entity и order_entity не должны добавляться записи")
    void checkNullRecordsDbIfNotValidCardPay() throws SQLException {
        DbUtils dbUtils = new DbUtils();
        SelectPaymentPage selectPaymentPage = new SelectPaymentPage();
        selectPaymentPage.openPage();
        selectPaymentPage.cardPayment();
        page.fillData(CardData.randomCardNumber());
        page.paymentNotValidCard();
        int countPaymentEntityActual = dbUtils.getCountPaymentEntity();
        int countOrderEntityActual = dbUtils.getCountOrderEntity();
        int sumActual = countPaymentEntityActual + countOrderEntityActual;
        assertEquals(0, sumActual);
    }

    @Test
    @DisplayName("Тест №44. Оплата кредитом невалидной картой. В таблицы credit_request_entity и order_entity не должны добавляться записи")
    void checkNullRecordsDbIfNotValidCreditPay() throws SQLException {
        DbUtils dbUtils = new DbUtils();
        SelectPaymentPage selectPaymentPage = new SelectPaymentPage();
        selectPaymentPage.openPage();
        selectPaymentPage.creditPayment();
        page.fillData(CardData.randomCardNumber());
        page.paymentNotValidCard();
        int countCreditRequestEntityActual = dbUtils.getCountCreditRequestEntity();
        int countOrderEntityActual = dbUtils.getCountOrderEntity();
        int sumActual = countCreditRequestEntityActual + countOrderEntityActual;
        assertEquals(0, sumActual);
    }

}
