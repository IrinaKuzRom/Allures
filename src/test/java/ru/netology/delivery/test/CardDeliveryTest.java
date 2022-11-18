package ru.netology.delivery.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

class CardDeliveryTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }
    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }


    @Test
    void shouldRegister() {
        val requestInfo = DataGenerator.Registration.generateUser("ru");
        $("[data-test-id=city] input").setValue(requestInfo.getCity());
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[placeholder='Дата встречи']").setValue(firstMeetingDate);
        $("[name='name']").setValue(requestInfo.getName());
        $("[name='phone']").setValue(requestInfo.getPhone());
        $("[data-test-id=agreement]").click();
        $(withText("Запланировать")).click();
        $("[data-test-id=success-notification] .notification__content")
                .shouldHave(exactText("Встреча успешно запланирована на  " + firstMeetingDate));
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[placeholder='Дата встречи']").setValue(secondMeetingDate);
        $(withText("Запланировать")).click();
        $(withText("Перепланировать")).click();
        $("[data-test-id=success-notification] .notification__content")
                .shouldHave(exactText("Встреча успешно запланирована на  " + secondMeetingDate));
    }

    @Test
    void shouldRegisterWithoutChangingDate() {
        val requestInfo = DataGenerator.Registration.generateUser("ru");
        $("[data-test-id=city] input").setValue(requestInfo.getCity());
        val MeetingDate = 10;
        var oneMeetingDate = DataGenerator.generateDate(MeetingDate);
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[placeholder='Дата встречи']").setValue(oneMeetingDate);
        $("[name='name']").setValue(requestInfo.getName());
        $("[name='phone']").setValue(requestInfo.getPhone());
        $("[data-test-id=agreement]").click();
        $(withText("Запланировать")).click();
        $("[data-test-id=success-notification] .notification__content")
                .shouldHave(exactText("Встреча успешно запланирована на  " + MeetingDate));

    }
}
