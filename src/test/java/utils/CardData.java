package utils;

import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardData {
    private String cardNumber;
    private String month;
    private String year;
    private String owner;
    private String cvc;
    private String status;


    // проверка карты APPROVED
    @Step("Генерация данных для карты со статусом APPROVED")
    public static CardData approvedCard() {
        String cardNumber = validApprovedCardNum();
        String month = validMonth();
        String year = validYear();
        String owner = validOwner();
        String cvc = validRandomCvc();
        String status = "APPROVED";
        return new CardData(cardNumber, month, year, owner, cvc, status );
    }

    // проверка карты DECLINED
    @Step("Генерация данных для карты карты со статусом DECLINED")
    public static CardData declinedCard() {
        String cardNumber = validDeclinedCardNum();
        String month = validMonth();
        String year = validYear();
        String owner = validOwner();
        String cvc = validRandomCvc();
        String status = "DECLINED";
        return new CardData(cardNumber, month, year, owner, cvc, status);
    }

    // Проверка поля Номер карты
    @Step("Генерация данных для карты со значением в поле 'Номер карты': произвольные 16-ть цифр")
    public static CardData randomCardNumber() {
        String cardNumber = notValidRandomCardNum();
        String month = validMonth();
        String year = validYear();
        String owner = validOwner();
        String cvc = validRandomCvc();
        String status = "";
        return new CardData(cardNumber, month, year, owner, cvc, status);
    }

    @Step("Генерация данных для карты с коротким значением в поле 'Номер карты': произвольные 12-ть цифр")
    public static CardData shortCardNumber() {
        String cardNumber = notValidShortCardNum();
        String month = validMonth();
        String year = validYear();
        String owner = validOwner();
        String cvc = validRandomCvc();
        String status = "";
        return new CardData(cardNumber, month, year, owner, cvc, status);
    }

    //проверка поля месяц
    @Step("Генерация данных для карты со значением в поле 'Месяц': 13")
    public static CardData impossibleMonth() {
        String cardNumber = validApprovedCardNum();
        String month = notValidNotPossibleMonth();
        String year = validYear();
        String owner = validOwner();
        String cvc = validRandomCvc();
        String status = "";
        return new CardData(cardNumber, month, year, owner, cvc, status);
    }

    @Step("Генерация данных для карты со значением в поле 'Месяц': 00")
    public static CardData zeroMonth() {
        String cardNumber = validApprovedCardNum();
        String month = notValidZeroMonth();
        String year = validYear();
        String owner = validOwner();
        String cvc = validRandomCvc();
        String status = "";
        return new CardData(cardNumber, month, year, owner, cvc, status);
    }

    @Step("Генерация данных для карты со значением в поле 'Год': -1 от текущего")
    public static CardData lastYear() {
        String cardNumber = validApprovedCardNum();
        String month = validMonth();
        String year = notValidPastYear();
        String owner = validOwner();
        String cvc = validRandomCvc();
        String status = "";
        return new CardData(cardNumber, month, year, owner, cvc, status);
    }

    @Step("Генерация данных для карты со значением в поле 'Год': +6 от текущего")
    public static CardData futureYear() {
        String cardNumber = validApprovedCardNum();
        String month = validMonth();
        String year = notValidFutureYear();
        String owner = validOwner();
        String cvc = validRandomCvc();
        String status = "";
        return new CardData(cardNumber, month, year, owner, cvc, status);
    }

    @Step("Генерация данных для карты со значением в поле 'Пользователь': набор на кириллице")
    public static CardData russianNameOwner() {
        String cardNumber = validApprovedCardNum();
        String month = validMonth();
        String year = validYear();
        String owner = notValidRussianNameOwner();
        String cvc = validRandomCvc();
        String status = "";
        return new CardData(cardNumber, month, year, owner, cvc, status);
    }

    @Step("Генерация данных для карты со значением в поле 'Пользователь': произвольные символы")
    public static CardData anyValuesOwner() {
        String cardNumber = validApprovedCardNum();
        String month = validMonth();
        String year = validYear();
        String owner = notValidAnyValuesOwner();
        String cvc = validRandomCvc();
        String status = "";
        return new CardData(cardNumber, month, year, owner, cvc, status);
    }

    @Step("Генерация данных для карты со значением в поле 'Пользователь': номера телефона")
    public static CardData phoneNumberOwner() {
        String cardNumber = validApprovedCardNum();
        String month = validMonth();
        String year = validYear();
        String owner = notValidPhoneNumberOwner();
        String cvc = validRandomCvc();
        String status = "";
        return new CardData(cardNumber, month, year, owner, cvc, status);
    }

    @Step("Генерация данных для карты со значением в поле 'Пользователь': пустое поле")
    public static CardData emptyFieldOwner() {
        String cardNumber = validApprovedCardNum();
        String month = validMonth();
        String year = validYear();
        String owner = emptyField();
        String cvc = validRandomCvc();
        String status = "";
        return new CardData(cardNumber, month, year, owner, cvc, status);
    }


    @Step("Генерация данных для карты со значением в поле 'CVC/CVV': 000")
    public static CardData zeroNumberCVC() {
        String cardNumber = validApprovedCardNum();
        String month = validMonth();
        String year = validYear();
        String owner = validOwner();
        String cvc = notValidZeroCvc();
        String status = "";
        return new CardData(cardNumber, month, year, owner, cvc, status);
    }

    @Step("Генерация данных для карты со значением в поле 'CVC/CVV': пустое поле")
    public static CardData emptyFieldCVC() {
        String cardNumber = validApprovedCardNum();
        String month = validMonth();
        String year = validYear();
        String owner = validOwner();
        String cvc = emptyField();
        String status = "";
        return new CardData(cardNumber, month, year, owner, cvc, status);
    }

    @Step("Генерация данных для карты: все поля остаются пустыми")
    public static CardData emptyAllFieldCard() {
        String cardNumber = emptyField();
        String month = emptyField();
        String year = emptyField();
        String owner = emptyField();
        String cvc = emptyField();
        String status = "";
        return new CardData(cardNumber, month, year, owner, cvc, status);
    }



    //номер карты
    private static String validApprovedCardNum() {
        return "4444444444444441";
    }

    private static String validDeclinedCardNum() {
        return "4444444444444442";
    }

    private static String notValidRandomCardNum() {
        Faker faker = new Faker();
        return Long.toString(faker.number().randomNumber(16, true));
    }

    private static String notValidShortCardNum() {
        Faker faker = new Faker();
        return Long.toString(faker.number().randomNumber(12, true));
    }

    //месяц
    private static String validMonth() {
        LocalDate today = LocalDate.now();
        return String.format("%tm", today.plusMonths(1));
    }

    private static String notValidNotPossibleMonth() {
        return "13";
    }

    private static String notValidZeroMonth() {
        return "00";
    }

    //год
    private static String validYear() {
        LocalDate today = LocalDate.now();
        return String.format("%ty", today.plusYears(1));
    }

    private static String notValidPastYear() {
        LocalDate today = LocalDate.now();
        return String.format("%ty", today.minusYears(1));

    }

    private static String notValidFutureYear() {
        LocalDate today = LocalDate.now();
        return String.format("%ty", today.plusYears(6));
    }

    //владелец
    private static String validOwner() {
        return "ALEXANDER KACHALOV";
    }

    private static String notValidRussianNameOwner() {
        return "АЛЕКСАНДР КАЧАЛОВ";
    }

    private static String notValidAnyValuesOwner() {
        return "!@#$%^&**()";
    }

    private static String notValidPhoneNumberOwner() {
        return "8(903)578-34-84";
    }

    //CVC/CVV
    private static String validRandomCvc() {
        int cvc = 100 + (int) (Math.random() * 899);
        return Integer.toString(cvc);
    }

    private static String notValidZeroCvc() {
        return "000";
    }

    private static String emptyField() {
        return "";
    }

}


