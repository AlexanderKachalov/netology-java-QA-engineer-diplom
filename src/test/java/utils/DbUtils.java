package utils;


import io.qameta.allure.Step;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DbUtils {

    private static String url() {
        return System.getProperty("db.url");
    }
    private static String user() {
        return "app";
    }
    private static String password() {
        return "pass";
    }


    public DbUtils() {
    }

    public DbData getCardPayDataBaseValues() throws SQLException {
        return cardPayDataBaseValues();
    }

    public DbData getCreditPayDataBaseValues() throws SQLException {
        return creditPayDataBaseValues();
    }

    public String getCompareOrderPaymentId() throws SQLException {
        return compareOrderPaymentId();
    }

    public String getCompareOrderCreditId() throws SQLException {
        return compareOrderCreditId();
    }

    public int getCountPaymentEntity() throws SQLException {
        return countPaymentEntity();
    }

    public int getCountCreditRequestEntity() throws SQLException {
        return countCreditRequestEntity();
    }

    public int getCountOrderEntity() throws SQLException {
        return countOrderEntity();
    }

    @Step("выборка полей status, amount, transaction_id из таблицы payment_entity при оплате картой")
    private DbData cardPayDataBaseValues() throws SQLException {
        DbData values;
        val selectCardPayValues = "SELECT status, amount, transaction_id FROM payment_entity;";
        val runner = new QueryRunner();
        values = runner.query(conn(), selectCardPayValues, new BeanHandler<>(DbData.class));
        return values;
    }

    @Step("выборка полей status, bank_id из таблицы credit_request_entity при оплате кредитом")
    private DbData creditPayDataBaseValues() throws SQLException {
        DbData values;
        val selectCreditPayValues = "SELECT status, bank_id FROM credit_request_entity;";
        val runner = new QueryRunner();
        values = runner.query(conn(), selectCreditPayValues, new BeanHandler<>(DbData.class));
        return values;
    }

    @Step("сравнение значения payment_id из order_entity и transaction_id из payment_entity при оплате картой")
    private String compareOrderPaymentId() throws SQLException {
        val runner = new QueryRunner();
        val selectPaymentIdValues = "SELECT payment_id FROM order_entity ORDER BY created;";
        return runner.query(conn(), selectPaymentIdValues, new ScalarHandler<>());
    }

    @Step("Сравнение bank_id таблицы credit_request_entity с credit_id таблицы order_entity")
    private String compareOrderCreditId() throws SQLException {
        val runner = new QueryRunner();
        val selectCreditIdValues = "SELECT credit_id FROM order_entity ORDER BY created;";
        return runner.query(conn(), selectCreditIdValues, new ScalarHandler<>());
    }

    @Step("Проверка числа записей в таблице payment_entity при оплате картой")
    private int countPaymentEntity() throws SQLException {
        int count;
        val runner = new QueryRunner();
        val totalRecordsPaymentEntity = "SELECT COUNT(*) FROM payment_entity;";
        val countRecord =  runner.query(conn(), totalRecordsPaymentEntity, new ScalarHandler<>());
        count = Integer.parseInt(countRecord.toString());
        return count;
    }

    @Step("Проверка числа записей в таблице credit_request_entity при оплате кредитом")
    private int countCreditRequestEntity() throws SQLException {
        int count;
        val runner = new QueryRunner();
        val totalRecordsCreditRequestEntity = "SELECT COUNT(*) FROM credit_request_entity;";
        val countRecord = runner.query(conn(), totalRecordsCreditRequestEntity, new ScalarHandler<>());
        count = Integer.parseInt(countRecord.toString());
        return count;
    }

    @Step("Проверка числа записей в таблице order_entity")
    private int countOrderEntity() throws SQLException {
        int count;
        val runner = new QueryRunner();
        val totalRecordsOrderEntity = "SELECT COUNT(*) FROM order_entity;";
        val countRecord = runner.query(conn(), totalRecordsOrderEntity, new ScalarHandler<>());
        count = Integer.parseInt(countRecord.toString());
        return count;
    }




    public static Connection conn() {
        Connection conn;
        try {
            conn = DriverManager.getConnection(url(), user(), password());
            {
                System.out.println("Connection to DB is good!");
                return conn;
            }
        } catch (Exception ex) {
            System.out.println("Connection to DB is failed...");
            System.out.println(ex);
        }
        return null;
    }

    public static void cleanTables() throws SQLException {
        val deleteOrderEntity = "DELETE FROM order_entity;";
        val deletePaymentEntity = "DELETE FROM payment_entity;";
        val deleteCreditEntity = "DELETE FROM credit_request_entity;";
        try (
                Connection connectionMysql = DriverManager.getConnection(url(), user(), password());

                PreparedStatement statementOrderEntity = connectionMysql.prepareStatement(deleteOrderEntity);
                PreparedStatement statementPaymentEntity = connectionMysql.prepareStatement(deletePaymentEntity);
                PreparedStatement statementCreditEntity = connectionMysql.prepareStatement(deleteCreditEntity);
        ) {
            statementOrderEntity.executeUpdate();
            statementPaymentEntity.executeUpdate();
            statementCreditEntity.executeUpdate();
        }
    }

}