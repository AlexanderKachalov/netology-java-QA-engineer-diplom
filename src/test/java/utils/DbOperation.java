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

public class DbOperation {

    public DbOperation() {
    }

    public DbData getPaymentEntityDataBaseValues() throws SQLException {
        return paymentEntityDataBaseValues();
    }

    public DbData getCreditRequestEntityDataBaseValues() throws SQLException {
        return creditRequestEntityDataBaseValues();
    }

    public DbData getOrderEntityDataBaseValues() throws SQLException {
        return orderEntityDataBaseValues();
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

    @Step("Выборка полей status, amount, transaction_id из таблицы payment_entity при оплате картой")
    private DbData paymentEntityDataBaseValues() throws SQLException {
        val selectCardPayValues = "SELECT status, amount, transaction_id FROM payment_entity;";
        val runner = new QueryRunner();
        return runner.query(conn(), selectCardPayValues, new BeanHandler<>(DbData.class));
    }

    @Step("Выборка полей status, bank_id из таблицы credit_request_entity при оплате кредитом")
    private DbData creditRequestEntityDataBaseValues() throws SQLException {
        val selectCreditPayValues = "SELECT status, bank_id FROM credit_request_entity;";
        val runner = new QueryRunner();
        return runner.query(conn(), selectCreditPayValues, new BeanHandler<>(DbData.class));
    }

    @Step("Выборка полей payment_id и credit_id из таблицы order_entity")
    private DbData orderEntityDataBaseValues() throws SQLException {
        val selectCreditPayValues = "SELECT payment_id, credit_id FROM order_entity;";
        val runner = new QueryRunner();
        return runner.query(conn(), selectCreditPayValues, new BeanHandler<>(DbData.class));
    }

    @Step("Проверка числа записей в таблице payment_entity при оплате картой")
    private int countPaymentEntity() throws SQLException {
        val runner = new QueryRunner();
        val totalRecordsPaymentEntity = "SELECT COUNT(*) FROM payment_entity;";
        val countRecord =  runner.query(conn(), totalRecordsPaymentEntity, new ScalarHandler<>());
        return Integer.parseInt(countRecord.toString());
    }

    @Step("Проверка числа записей в таблице credit_request_entity при оплате кредитом")
    private int countCreditRequestEntity() throws SQLException {
        val runner = new QueryRunner();
        val totalRecordsCreditRequestEntity = "SELECT COUNT(*) FROM credit_request_entity;";
        val countRecord = runner.query(conn(), totalRecordsCreditRequestEntity, new ScalarHandler<>());
        return Integer.parseInt(countRecord.toString());
    }

    @Step("Проверка числа записей в таблице order_entity")
    private int countOrderEntity() throws SQLException {
        val runner = new QueryRunner();
        val totalRecordsOrderEntity = "SELECT COUNT(*) FROM order_entity;";
        val countRecord = runner.query(conn(), totalRecordsOrderEntity, new ScalarHandler<>());
        return Integer.parseInt(countRecord.toString());
    }



    private static String url() {
        return System.getProperty("db.url");
    }
    private static String user() {
        return "app";
    }
    private static String password() {
        return "pass";
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