package org.example;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DiveIntoTheLines {

    public static int[] splitNumberTestCases = {0, 1, 10, 12, 123, 110, 100, 101, 831, 2023, 10502, 45768};

    public static String[] splitNumberResults = {
            "0",
            "1",
            "10",
            "10 + 2",
            "100 + 20 + 3",
            "100 + 10",
            "100",
            "100 + 1",
            "800 + 30 + 1",
            "2000 + 20 + 3",
            "10000 + 500 + 2",
            "40000 + 5000 + 700 + 60 + 8"
    };

    public static List<String> emails = Arrays.asList(
            "Polyana@mail.ru",                                     // true
            "Poka-vokzal@test.zoloto.beru",                        // true
            "@Varit@kotel.ru",                                     // false
            "Тест@почта.рф",                                       // false
            "!!!IM.in.POCHTA@sizhu.zhru",                          // true
            "Gde Moi@bulki.dorogoi",                               // false
            "vot.tak.vot@",                                        // false
            "poloska.polosk.polosk.polosk.polosk.polosk@mail.ru",  // true
            "poloska.polosk.polosk.polosk.poloska.polosk@mail.ru", // false
            "qwerty@mail@mail.ru",                                 // false
            "@mail.ru",                                            // false
            "valid.email@ru",                                      // false
            "domashka@zadolbala.ochen.ru",                         // true
            "NORMALNO@0TDIHAEM.COM",                               // true
            "test@mail-poshta.ru",                                 // true
            "test@mail-poshta.ru.",                                // false
            "test@mail-poshta.ru-",                                // false
            "pоchemu.tо@ne.rаbotaet.ru",                           // false
            "nаkоnets.to@posledni.email",                          // false
            "valid$email@.ru",                                     // false
            "valid$email@mail.ru",                                 // true
            "valid.email@ma$il.ru"                                 // false
    );

    public static List<Boolean> checkEmailResults = Arrays.asList(
            true, true, false, false, true, false, false, true, false, false, false, false, true,
            true, true, false, false, false, false, false, true, false
    );

    public static void main(String[] args) {
        System.out.println("\nTests for splitNumber");
        AntiCheat.run();
        for (int i = 0; i < splitNumberTestCases.length; i++)
            printTestCase(i, splitNumberResults[i], splitNumber(splitNumberTestCases[i]), 30);
        System.out.println("\nTests for isValidEmail");
        AntiCheat.run();
        for (int i = 0; i < emails.size(); i++)
            printTestCase(i, checkEmailResults.get(i).toString(), String.valueOf(isValidEmail(emails.get(i))), 10);
    }

    /**
     * ДЗ-6
     * Написать функцию, которая разбивает число на слагаемые
     * Напрмер: 831 превращается в строку "800 + 30 + 1"
     */
    public static String splitNumber(int number){
        Integer x = number;
        String numberInString = x.toString();
        ArrayList<Integer> numbers = new ArrayList<>();

        for (int i = 0; i < numberInString.length(); i++) {
            char symbol = numberInString.charAt(i);
            int numeric = Character.getNumericValue(symbol);
            numbers.add(numeric);
        }

        int y = numbers.size();
        int numberCheck = 0;
        StringBuilder sb = new StringBuilder();

        for (Integer i : numbers) {
            y--;
            numberCheck = i * (int) Math.pow(10, y);
            if (numberCheck != 0 || sb.length() == 0)
                sb.append(numberCheck + " + ");
        }

        return sb.toString().substring(0, sb.length() - 3);
    }

    /**
     * ДЗ-6
     * ЭТО НЕ РЕАЛЬНЫЕ ПРАВИЛА ВАЛИДАЦИИ ПОЧТЫ
     * В ЖИЗНИ ОНИ СЛОЖНЕЕ И ДРУГИЕ
     * Написать функцию которая проверяет является ли строка email-ом (Упрощенно)
     * РЕГУЛЯРНЫЕ ВЫРАЖЕНИЯ ИСПОЛЬЗОВАТЬ НЕЛЬЗЯ
     * Строка является имейлом, тольео если:
     *  - Адрес должен содержать ровно один символ @.
     *  - Адрес не должен превышать 50 символов
     *  - Адрес не должен содержать Русских букв
     *  - Адрес должен включать локальное имя (текст, который идет перед символом @),
     *        затем символ @ и имя домена (текст, который идет после символа @).
     *  - Имя домена должно содержать как минимум одну точку (такую как ups.com).
     *  - Локальное имя может содержать буквы от A до Z (верхний и нижний регистры), а так же - . ! ? $ #.
     *      Нельзя использовать следующие символы: < > ( ) [ ] @ , ; : \ / " * или пробел
     *  - Имя домена должно содержать две текстовые строки, разделенные точкой, например, ups.com.
     *      Имя может состоять из букв от A до Z (верхний или нижний регистры), цифры от 0 до 9 и знак минус (-);
     *      тем не менее последний символ не может быть знаком минуса, дефисом или точкой.
     */
    public static boolean isValidEmail(String email){
        boolean check = true;
        int countSymbol = 0;
        int countDot = 0;

        for (int i = 0; i < email.length(); i++) if (Objects.equals('@', email.charAt(i))) countSymbol++;
        for (int i = 0; i < email.length(); i++) if (Objects.equals('.', email.charAt(i))) countDot++;

        String emailLocalName = email.substring(0, email.indexOf('@'));
        String emailDomen = email.substring(email.indexOf('@')).toLowerCase();

        int x = email.length() - 1;
        char lastSymbolDomen = email.charAt(x);

        int y = emailDomen.indexOf('.');

        int countDotDomen = 0;
        for (int i = 0; i < emailDomen.length(); i++) if (Objects.equals('.', emailDomen.charAt(i))) countDotDomen++;

        for (int i = 0; i < emailLocalName.length(); i++) { // Нельзя использовать следующие символы: < > ( ) [ ] @ , ; : \ / " * или пробел
            switch (emailLocalName.charAt(i)) {
                case '<':
                case '>':
                case '(':
                case ')':
                case '[':
                case ']':
                case '@':
                case ',':
                case ';':
                case ':':
                case '\\':
                case '/':
                case '"':
                case '*':
                case ' ':
                    check = false;
                    break;
            }
        }

        // Адрес должен содержать ровно один символ @
        if (countSymbol != 1) {
            check = false;
        } else if (email.length() > 50) { // Адрес не должен превышать 50 символов
            check = false;
        } else if (isRussianWords(email)) { // Адрес не должен содержать Русских букв
            check = false;
        } else if (Objects.equals(emailLocalName, "")) { // Адрес должен включать локальное имя
            check = false;
        } else if (Objects.equals(emailDomen, "")) { // Адрес должен включать домен
            check = false;
            System.out.println(email);
        } else if (countDotDomen == 0) { // Домен должен содержать хотя бы одну точку
            check = false;
        } else if (Objects.equals(lastSymbolDomen, '-') || Objects.equals(lastSymbolDomen, '.')) {
            check = false; // Последний символ не может быть знаком минуса, дефисом или точкой.
        } else if (y <= 1) {
            check = false;
        }

        if (check) {
            for (int i = 0; i < emailDomen.length(); i++) {
                if (!isSymbol(emailDomen.charAt(i))) {
                    check = false;
                    break;
                }
            }
        }

        return check;
    }

    public static boolean isRussianWords(String str) {
        boolean returned = false;

        List<Character> russian = new ArrayList<>(33);
        russian.add('ё');

        char startRussian = 'а';
        int i = 1;
        while (i < 33) {
            russian.add(startRussian);
            i++;
            startRussian++;
        }

        for (int x = 0; x < str.length(); x++) {
            for (Character letter : russian)
                if (Objects.equals(letter, str.charAt(x))) {
                    returned = true;
                    break;
                }
        }

        return returned;
    }

    public static boolean isSymbol(char symbol) {
        boolean returned = false;

        int[] numbers = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        List<Character> english = new ArrayList<>(26);

        char startEnglish = 'a';
        int i = 1;
        while (i <= 26) {
            english.add(startEnglish);
            i++;
            startEnglish++;
        }

        String sym = String.valueOf(symbol);

        for (Integer y : numbers) {
            if (Objects.equals(Integer.toString(y), sym)) {
                returned = true;
                break;
            }
        }
        if (!returned) {
            for (Character letter : english) {
                if (Objects.equals(letter, symbol)) {
                    returned = true;
                    break;
                }
            }
        }
        if (Objects.equals(symbol, '-') || Objects.equals(symbol, '.') || Objects.equals(symbol, '@')) {
            returned = true;
        }

        return returned;
    }

    public static class AntiCheat {

        public static void run() {
            StringBuilder sb = new StringBuilder("");
            for (int num : splitNumberTestCases) sb.append(num);
            for (String str: splitNumberResults) sb.append(str);
            List<String> antiCheatList = new ArrayList<>();
            antiCheatList.addAll(emails);
            antiCheatList.addAll(checkEmailResults.stream().map(Object::toString).collect(Collectors.toList()));
            antiCheatList.add(sb.toString());
            calcHash(antiCheatList);
        };

        public static String bytesToHex(byte[] bytes) {
            char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
            char[] hexChars = new char[bytes.length * 2];
            for (int j = 0; j < bytes.length; j++) {
                int v = bytes[j] & 0xFF;
                hexChars[j * 2] = HEX_ARRAY[v >>> 4];
                hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
            }
            return new String(hexChars);
        }

        public static void calcHash(List<String> list) {
            String total = String.join("", list);
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(total.getBytes());
                byte[] digest = md.digest();
                System.out.println("AntiCheatCheck: " + bytesToHex(digest));
            } catch (NoSuchAlgorithmException ignored) {}
        }
    }

    public static String constLen(String str, int len) {
        StringBuilder sb = new StringBuilder(str);
        while (len-- - str.length() > 0) sb.append(" ");
        return sb.toString();
    }

    public static void printTestCase(int n, String exp, String act, int minLen) {
        Function<String, String> green = str -> "\u001B[34m" + str + "\u001B[0m";
        Function<String, String> yellow = str -> "\u001B[33m" + str + "\u001B[0m";
        System.out.print( "TEST CASE " + constLen(String.valueOf(n), 4));
        System.out.print( "Ожидание: " + yellow.apply(constLen(exp, minLen)) + " Реальность: " + green.apply(constLen(act, minLen) + " "));
        if (Objects.equals(exp, act)) System.out.print("✅"); else System.out.print("❌");
        System.out.println();
    }

}

