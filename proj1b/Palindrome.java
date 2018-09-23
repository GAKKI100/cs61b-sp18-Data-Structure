import javax.print.DocFlavor;

public class Palindrome {
    public Deque<Character> wordToDeque(String word){
        Deque<Character> a = new LinkedListDeque<>();
        Character c;
        for (int i = 0; i < word.length();i++)
        {
            c = word.charAt(i);
            a.addLast(c);
        }
        return a;
    }
    public static int count = 0;

   public boolean isPalindrome(String word) {
        if (word.length() == 0 || word.length() == 1)
        {
            return true;
        }
        else{
            Deque<Character> que = new LinkedListDeque<>();
            que = wordToDeque(word);
            palindromeHelper(que);
            if (count == 0)
            {
                return true;
            }
            else{
                count = 0;
                return false;
            }
        }
    }
    private Deque<Character> palindromeHelper(Deque<Character> a){
        if (a.size() == 1 || a.size() == 0){
            return a;
        }
        char temp1 = a.removeFirst();
        char temp2 = a.removeLast();
        if (temp1 != temp2){
            count++;
        }
        return palindromeHelper(a);
    }



    private static int count1;
    public boolean isPalindrome(String word, CharacterComparator cc)
    {
        if (word.length() == 0 || word.length() == 1)
        {
            return true;
        }
        else{
            Deque<Character> que = new LinkedListDeque<>();
            que = wordToDeque(word);
            palindromeHelper1(que,cc);
            if (count1 == 0)
            {
                return true;
            }
            else{
                count1 = 0;
                return false;
            }
        }
    }
    private Deque<Character> palindromeHelper1(Deque<Character> b, CharacterComparator cc){
        if (b.size() == 1 || b.size() == 0){
            return b;
        }
        char temp1 = b.removeFirst();
        char temp2 = b.removeLast();
        if (!cc.equalChars(temp1,temp2)){
            count1++;
        }
        return palindromeHelper1(b,cc);
    }

}
