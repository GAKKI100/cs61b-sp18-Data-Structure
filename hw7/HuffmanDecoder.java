import java.util.ArrayList;
import java.util.List;

public class HuffmanDecoder {
    public static void main(String[] args){
        ObjectReader or = new ObjectReader(args[0]);
        BinaryTrie trie = (BinaryTrie) or.readObject();
        int num = (Integer) or.readObject();
        BitSequence bs = (BitSequence) or.readObject();
        char[] chars = new char[num];


        for(int i = 0; i < num; i++){
            Match match = trie.longestPrefixMatch(bs);
            chars[i] = match.getSymbol();
            bs = bs.allButFirstNBits(match.getSequence().length());
        }

        FileUtils.writeCharArray(args[1], chars);
    }
}