import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {
    static Palindrome palindrome = new Palindrome();
    CharacterComparator comp = new OffByN(5);
    CharacterComparator n2 = new OffByN(2);
    @Test
    public void testOffByN(){
        String str1 = "fwa";
        String str2 = "fhg";
        String str3 = "gce";
        assertTrue(palindrome.isPalindrome(str1,comp));
        assertFalse(palindrome.isPalindrome(str2,comp));
        assertTrue(palindrome.isPalindrome(str3,n2));
    }
}
