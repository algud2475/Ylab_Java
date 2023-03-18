package transliterator;

public class TransliteratorTest {
    public static void main(String[] args) {
        Transliterator transliterator = new TransliteratorImpl();
        String resA = transliterator.transliterate("HELLO! ПРИВЕТ! Go, boy!");
        String resB = transliterator.transliterate("HELLO! ПРИВЕТ! Go, boy!".toCharArray());
        System.out.println(resA);
        System.out.println(resB);
    }
}
