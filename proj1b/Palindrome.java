public class Palindrome {
    public Deque<Character> wordToDeque(String word){
        int len = word.length();
        Deque<Character> ret = new ArrayDeque<>();
        for (int i = 0; i < len; i++){
            ret.addLast(word.charAt(i));
        }
        return ret;
    }
    public boolean isPalindrome(String word){
        Deque<Character> words = wordToDeque(word);
        if (words.size() == 0 || words.size() == 1){
            return true;
        }

        while (words.size()!=0 && words.size()!=1){
            char fist_word = words.removeFirst();
//            System.out.print(words.size());
            char last_word = words.removeLast();
//            System.out.print(words.size());
            if (fist_word==last_word){
                continue;
            }
            return false;
        }
        return true;

    }
    public boolean isPalindrome(String word,CharacterComparator comp){
        Deque<Character> words = wordToDeque(word);

        if (words.size() == 0 || words.size() == 1){
            return true;
        }

        while (words.size()!=0 && words.size()!=1){
            char fist_word = words.removeFirst();
//            System.out.print(words.size());
            char last_word = words.removeLast();
//            System.out.print(words.size());
            if (comp.equalChars(fist_word,last_word)){
                continue;
            }
            return false;
        }
        return true;
    }
}
