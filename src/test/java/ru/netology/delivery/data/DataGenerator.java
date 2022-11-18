package ru.netology.delivery.data;

import com.github.javafaker.Faker;
import lombok.Data;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {
    private static Random random = new Random();

    DataGenerator() {
    }

    public static String generateDate(int shift) {
        return LocalDate.now().plusDays(shift).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static String generateCity(String locale) {
        var cities = new String[]{"Москва", "Сочи", "Пермь", "Кострома", "Казань", "Ярославль", "Иваново",
                "Тюмень", "Брянск", "Псков", "Вологда", "Киров", "Владимир"};
        var faker = new Faker(new Locale(locale));
        return cities[faker.number().numberBetween(0, cities.length - 1)];
    }

    public static String generateName(String locale) {
        var faker = new Faker(new Locale(locale));
        return faker.name().lastName().replace("ё", "е") + " " + faker.name().firstName().replace("ё", "е");
    }

    public static String generatePhone(String locale) {
        var faker = new Faker(new Locale(locale));
        return faker.phoneNumber().phoneNumber();
    }


    public static class Registration {
        private Registration() {
        }

        public static RequestInfo generateUser(String locale) {
            return new RequestInfo(generateCity(locale), generateName(locale), generatePhone(locale));
        }
    }

    public static String generateInvalidPhone() {
        return "+7926856325";
    }

    public static String generateOnlyName() {
        return "Ирина";
    }
    @Value
    public static class RequestInfo {
        String city;
        String name;
        String phone;

    }
}
