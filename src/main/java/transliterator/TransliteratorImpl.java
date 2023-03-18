package transliterator;

public class TransliteratorImpl implements Transliterator {
    private String changeString(String letter) {
        switch(letter) {
            case "А":
                return "A";
            case "Б":
                return "B";
            case "В":
                return "V";
            case "Г":
                return "G";
            case "Д":
                return "D";
            case "Е":
                return "E";
            case "Ё":
                return "E";
            case "Ж":
                return "ZH";
            case "З":
                return "Z";
            case "И":
                return "I";
            case "Й":
                return "I";
            case "К":
                return "K";
            case "Л":
                return "L";
            case "М":
                return "M";
            case "Н":
                return "N";
            case "О":
                return "O";
            case "П":
                return "P";
            case "Р":
                return "R";
            case "С":
                return "S";
            case "Т":
                return "T";
            case "У":
                return "U";
            case "Ф":
                return "F";
            case "Х":
                return "KH";
            case "Ц":
                return "TS";
            case "Ч":
                return "CH";
            case "Ш":
                return "SH";
            case "Щ":
                return "SHCH";
            case "Ы":
                return "Y";
            case "Ь":
                return "";
            case "Ъ":
                return "IE";
            case "Э":
                return "E";
            case "Ю":
                return "IU";
            case "Я":
                return "IA";
            default:
                return letter;
        }
    }

    private String changeChar(char letter) {
        if (letter == 1040 || letter == 1040 + 32) {
            return "A";
        }
        if (letter == 1041 || letter == 1041 + 32) {
            return "B";
        }
        if (letter == 1042 || letter == 1042 + 32) {
            return "V";
        }
        if (letter == 1043 || letter == 1043 + 32) {
            return "G";
        }
        if (letter == 1044 || letter == 1044 + 32) {
            return "D";
        }
        if (letter == 1045 || letter == 1045 + 32) {
            return "E";
        }
        if (letter == 1025 || letter == 1105) {
            return "E";
        }
        if (letter == 1046 || letter == 1046 + 32) {
            return "ZH";
        }
        if (letter == 1047 || letter == 1047 + 32) {
            return "Z";
        }
        if (letter == 1048 || letter == 1048 + 32) {
            return "I";
        }
        if (letter == 1049 || letter == 1049 + 32) {
            return "I";
        }
        if (letter == 1050 || letter == 1050 + 32) {
            return "K";
        }
        if (letter == 1051 || letter == 1051 + 32) {
            return "L";
        }
        if (letter == 1052 || letter == 1052 + 32) {
            return "M";
        }
        if (letter == 1053 || letter == 1053 + 32) {
            return "N";
        }
        if (letter == 1054 || letter == 1054 + 32) {
            return "O";
        }
        if (letter == 1055 || letter == 1055 + 32) {
            return "P";
        }
        if (letter == 1056 || letter == 1056 + 32) {
            return "R";
        }
        if (letter == 1057 || letter == 1057 + 32) {
            return "S";
        }
        if (letter == 1058 || letter == 1058 + 32) {
            return "T";
        }
        if (letter == 1059 || letter == 1059 + 32) {
            return "U";
        }
        if (letter == 1060 || letter == 1060 + 32) {
            return "F";
        }
        if (letter == 1061 || letter == 1061 + 32) {
            return "KH";
        }
        if (letter == 1062 || letter == 1062 + 32) {
            return "TS";
        }
        if (letter == 1063 || letter == 1063 + 32) {
            return "CH";
        }
        if (letter == 1064 || letter == 1064 + 32) {
            return "SH";
        }
        if (letter == 1065 || letter == 1065 + 32) {
            return "SHCH";
        }
        if (letter == 1066 || letter == 1066 + 32) {
            return "IE";
        }
        if (letter == 1067 || letter == 1067 + 32) {
            return "Y";
        }
        if (letter == 1068 || letter == 1068 + 32) {
            return "";
        }
        if (letter == 1069 || letter == 1069 + 32) {
            return "E";
        }
        if (letter == 1070 || letter == 1070 + 32) {
            return "IU";
        }
        if (letter == 1071 || letter == 1071 + 32) {
            return "IA";
        }
        return String.valueOf(letter);
    }


    public String transliterate(String input) {
        StringBuilder output = new StringBuilder();
        for (char ch : input.toCharArray()) {
            output.append(changeString(String.valueOf(ch)));
        }
        return output.toString();
    }

    public String transliterate(char[] input) {
        StringBuilder output = new StringBuilder();
        for (char ch : input) {
            output.append(changeChar(ch));
        }
        return output.toString();
    }

}
