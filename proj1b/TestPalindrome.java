import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();
    CharacterComparator comp = new OffByOne();
    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }
//    Uncomment this class once you've created your Palindrome class.
    @Test
    public void TestPalindrome(){
        String d1 = "AbbA";
        String d2 ="dwsd";
        String d3 = "d";
        String d4 = "wdDw";
        assertTrue(palindrome.isPalindrome(d1));
        assertTrue(palindrome.isPalindrome(d3));
        assertFalse(palindrome.isPalindrome(d2));
        assertFalse(palindrome.isPalindrome(d4));
        assertTrue(palindrome.isPalindrome(d1,comp));
        assertTrue(palindrome.isPalindrome(d3,comp));
        assertFalse(palindrome.isPalindrome(d2,comp));
        assertFalse(palindrome.isPalindrome(d4,comp));
    }
}
