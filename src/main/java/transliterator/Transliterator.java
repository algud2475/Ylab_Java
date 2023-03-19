package transliterator;

public interface Transliterator {
    String transliterate(String input);

    String transliterate(char[] input);
}
