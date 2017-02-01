import java.util.List;
import java.lang.InterruptedException;

//TODO Future improvement, more sophisticated spell check, use data structure like trie.
public class SpellChecker implements Runnable {
    private final static char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private List<Word> words;
    private Dictionary dictionary;
    private int minIndex;
    private int maxIndex;
    private Thread thread;

    public SpellChecker(List<Word> words, Dictionary dictionary) {
        this.words = words;
        this.dictionary = dictionary;
    }
    
    public List<Word> getWordList(){
        return this.words;
    }
    
    public void startChecking(int start, int end){
        this.minIndex = start;
        this.maxIndex = end;
        thread = new Thread(this);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
            for (int i = minIndex; i < maxIndex; i++) {
                Word w = words.get(i);

                if (!dictionary.getWordSet().contains(w.getWord()) && !dictionary.getWordSet().contains(w.getWord().toLowerCase()))
                {
                    w.setIsWord(false);
                    getProposedWordList(w);
                }
            }
    }

    private void getProposedWordList(Word w) {
        oneCharMissingAtStartOrEnd(w);
        oneCharExcessInTheWord(w);
    }

    private void oneCharMissingAtStartOrEnd(Word w) {
        for (char c : alphabet) {
            String atFront = c + w.getWord();
            String atBack = w.getWord() + c;
            if (dictionary.getWordSet().contains(atFront)) {
                w.addNewProposedWord(atFront);
            }
            if (dictionary.getWordSet().contains(atBack)) {
                w.addNewProposedWord(atBack);
            }
        }
    }

    private void oneCharExcessInTheWord(Word w) {
        int len = w.getWord().length() - 1;
        // try removing char from the front
        String withoutFirstChar = w.getWord().substring(1);
        if (dictionary.getWordSet().contains(withoutFirstChar)) {
            w.addNewProposedWord(withoutFirstChar);
        }
        
        for (int i = 1; i < len; i++) {
            // try removing each char between (not including) the first and last
            String working = w.getWord().substring(0, i);
            working = working.concat(w.getWord().substring((i + 1), w.getWord().length()));
            if (dictionary.getWordSet().contains(working)) {
                w.addNewProposedWord(working);
            }
        }

        String withoutLastChar = w.getWord().substring(0, len);
        if (dictionary.getWordSet().contains(withoutLastChar)) {
            w.addNewProposedWord(withoutLastChar);
        }
    }
}
