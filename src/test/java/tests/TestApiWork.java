package tests;


import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import pages.PageOperation;
import pages.PageSelection;
import utils.CardData;
import utils.DbOperation;

import java.sql.SQLException;



public class TestApiWork {

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

    @Test
    @DisplayName("Тест №01. Оплата картой статус APPROVED. Корректные данные")
    void payCardApproved() {
        pageOperation.openPage();
        pageSelection.cardPayment();
        pageOperation.fillData(CardData.approvedCard());
        pageOperation.paymentValidCard();
    }

    @Test
    @DisplayName("Тест №02. Оплата картой статус DECLINED. Корректные данные")
    void payCardDeclined() {
        pageOperation.openPage();
        pageSelection.cardPayment();
        pageOperation.fillData(CardData.declinedCard());
        pageOperation.paymentNotValidCard();
    }

    @Test
    @DisplayName("Тест №03. Оплата кредитом по данным карты статус APPROVED. Корректные данные")
    void payCreditApproved() {
        pageOperation.openPage();
        pageSelection.creditPayment();
        pageOperation.fillData(CardData.approvedCard());
        pageOperation.paymentValidCard();
    }

    @Test
    @DisplayName("Тест №04. Оплата кредитом по данным карты статус DECLINED. Корректные данные")
    void payCreditDeclined() {
        pageOperation.openPage();
        pageSelection.creditPayment();
        pageOperation.fillData(CardData.declinedCard());
        pageOperation.paymentNotValidCard();
    }

}
