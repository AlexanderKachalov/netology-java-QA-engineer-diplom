package tests;


import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
import pages.PageOperation;
import pages.PageSelection;
import utils.CardData;
import utils.DbOperation;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestDataBaseWork {

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
    DbOperation dbOperation = new DbOperation();

    //проверка базы данных
    @Test
    @DisplayName("Тест №33. Оплата картой со статусом APPROVED. Проверка поля status в таблице payment_entity")
    void checkResponseStatusFieldFromDbIfCardPayApproved() throws SQLException {
        pageOperation.openPage();
        pageSelection.cardPayment();
        pageOperation.fillData(CardData.approvedCard());
        pageOperation.paymentValidCard();
        val statusPaymentEntityExpected = CardData.approvedCard().getStatus();
        val statusPaymentEntityActual = dbOperation.getPaymentEntityDataBaseValues().getStatus();
        assertEquals(statusPaymentEntityExpected, statusPaymentEntityActual);
    }

    @Test
    @DisplayName("Тест №34. Оплата картой со статусом DECLINED. Проверка поля status в таблице payment_entity")
    void checkResponseStatusFieldFromDbIfCardPayDeclined() throws SQLException {
        pageOperation.openPage();
        pageSelection.cardPayment();
        pageOperation.fillData(CardData.declinedCard());
        pageOperation.paymentValidCard();
        val statusPaymentEntityExpected = CardData.declinedCard().getStatus();
        val statusPaymentEntityActual = dbOperation.getPaymentEntityDataBaseValues().getStatus();
        assertEquals(statusPaymentEntityExpected, statusPaymentEntityActual);
    }

    @Test
    @DisplayName("Тест №35. Оплата кредитом по данным карты с статусом APPROVED. Проверка поля status в таблице credit_request_entity")
    void checkResponseStatusFieldFromDbIfCreditPayApproved() throws SQLException {
        pageOperation.openPage();
        pageSelection.creditPayment();
        pageOperation.fillData(CardData.approvedCard());
        pageOperation.paymentValidCard();
        val statusCreditRequestEntityExpected = CardData.approvedCard().getStatus();
        val statusCreditRequestEntityActual = dbOperation.getCreditRequestEntityDataBaseValues().getStatus();
        assertEquals(statusCreditRequestEntityExpected, statusCreditRequestEntityActual);
    }

    @Test
    @DisplayName("Тест №36. Оплата кредитом по данным карты со статусом DECLINED. Проверка поля status в таблице credit_request_entity")
    void checkResponseStatusFieldFromDbIfCreditPayDeclined() throws SQLException {
        pageOperation.openPage();
        pageSelection.creditPayment();
        pageOperation.fillData(CardData.declinedCard());
        pageOperation.paymentValidCard();
        val statusCreditRequestEntityExpected = CardData.declinedCard().getStatus();
        val statusCreditRequestEntityActual = dbOperation.getCreditRequestEntityDataBaseValues().getStatus();
        assertEquals(statusCreditRequestEntityExpected, statusCreditRequestEntityActual);
    }

    // bug
    @Test
    @DisplayName("Тест №37. Оплата картой со статусом APPROVED. Проверка поля amount в таблице payment_entity. Значение поля amount должно быть эквивалентным стоимости тура: amount = 45000")
    void checkAmountFieldFromDbIfCardPayApproved() throws SQLException {
        pageOperation.openPage();
        pageSelection.cardPayment();
        pageOperation.fillData(CardData.approvedCard());
        pageOperation.paymentValidCard();
        val amountActual = dbOperation.getPaymentEntityDataBaseValues().getAmount();
        assertEquals(45000, amountActual);
    }

    // bug
    @Test
    @DisplayName("Тест №38. Оплата картой со статусом DECLINED. Проверка поля amount в таблице payment_entity. Значение поля amount должно быть эквивалентным стоимости тура: amount = 45000")
    void checkAmountFieldFromDbIfCardPayDeclined() throws SQLException {
        pageOperation.openPage();
        pageSelection.cardPayment();
        pageOperation.fillData(CardData.declinedCard());
        pageOperation.paymentValidCard();
        val amountActual = dbOperation.getPaymentEntityDataBaseValues().getAmount();
        assertEquals(45000, amountActual);
    }

    @Test
    @DisplayName("Тест №39. Оплата картой со статусом APPROVED. Сравнение полей transaction_id таблицы payment_entity и payment_id таблицы order_entity")
    void comparePaymentOrderIfCardPayApproved() throws SQLException {
        pageOperation.openPage();
        pageSelection.cardPayment();
        pageOperation.fillData(CardData.approvedCard());
        pageOperation.paymentValidCard();
        val transactionIdActual = dbOperation.getPaymentEntityDataBaseValues().getTransaction_id();
        val orderPaymentIdExpected = dbOperation.getOrderEntityDataBaseValues().getPayment_id();
        assertEquals(orderPaymentIdExpected, transactionIdActual);
    }

    @Test
    @DisplayName("Тест №40. Оплата картой со статусом DECLINED. Сравнение полей transaction_id таблицы payment_entity и payment_id таблицы order_entity")
    void comparePaymentOrderIfCardPayDeclined() throws SQLException {
        pageOperation.openPage();
        pageSelection.cardPayment();
        pageOperation.fillData(CardData.declinedCard());
        pageOperation.paymentValidCard();
        val transactionIdActual = dbOperation.getPaymentEntityDataBaseValues().getTransaction_id();
        val orderPaymentIdExpected = dbOperation.getOrderEntityDataBaseValues().getPayment_id();
        assertEquals(orderPaymentIdExpected, transactionIdActual);
    }

    @Test
    @DisplayName("Тест №41. Оплата кредитом по данным карты APPROVED. Сравнение полей credit_id таблицы order_entity и bank_id таблицы credit_request_entity")
    void compareCreditOrderIfCreditPayApproved() throws SQLException {
        pageOperation.openPage();
        pageSelection.creditPayment();
        pageOperation.fillData(CardData.approvedCard());
        pageOperation.paymentValidCard();
        val bankIdActual = dbOperation.getCreditRequestEntityDataBaseValues().getBank_id();
        val creditIdExpected = dbOperation.getOrderEntityDataBaseValues().getCredit_id();
        assertEquals(creditIdExpected, bankIdActual);
    }

    @Test
    @DisplayName("Тест №42. Оплата кредитом по данным карты DECLINED. Сравнение полей credit_id таблицы order_entity и bank_id таблицы credit_request_entity")
    void compareCreditOrderIfCreditPayDeclined() throws SQLException {
        pageOperation.openPage();
        pageSelection.creditPayment();
        pageOperation.fillData(CardData.declinedCard());
        pageOperation.paymentValidCard();
        val bankIdActual = dbOperation.getCreditRequestEntityDataBaseValues().getBank_id();
        val creditIdExpected = dbOperation.getOrderEntityDataBaseValues().getCredit_id();
        assertEquals(creditIdExpected, bankIdActual);
    }

    @Test
    @DisplayName("Тест №43. Оплата картой с некорректными данными. В таблицы payment_entity и order_entity не должны добавляться записи")
    void checkNullRecordsDbIfNotValidCardPay() throws SQLException {
        pageOperation.openPage();
        pageSelection.cardPayment();
        pageOperation.fillData(CardData.randomCardNumber());
        pageOperation.paymentNotValidCard();
        int countPaymentEntityActual = dbOperation.getCountPaymentEntity();
        int countOrderEntityActual = dbOperation.getCountOrderEntity();
        int sumActual = countPaymentEntityActual + countOrderEntityActual;
        assertEquals(0, sumActual);
    }

    @Test
    @DisplayName("Тест №44. Оплата кредитом по карте с некорректными данными. В таблицы credit_request_entity и order_entity не должны добавляться записи")
    void checkNullRecordsDbIfNotValidCreditPay() throws SQLException {
        pageOperation.openPage();
        pageSelection.creditPayment();
        pageOperation.fillData(CardData.randomCardNumber());
        pageOperation.paymentNotValidCard();
        int countCreditRequestEntityActual = dbOperation.getCountCreditRequestEntity();
        int countOrderEntityActual = dbOperation.getCountOrderEntity();
        int sumActual = countCreditRequestEntityActual + countOrderEntityActual;
        assertEquals(0, sumActual);
    }

}
