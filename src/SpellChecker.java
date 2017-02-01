import java.util.List;
import java.lang.InterruptedException;

//TODO Future improvement, more sophisticated spell check, use data structure like trie.
public class SpellChecker implements Runnable {
    private final static char[] ALPHABET = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private List<Word> _words;
    private Dictionary _dictionary;
    private int _minIndex;
    private int _maxIndex;
    private Thread _thread;

    public SpellChecker(List<Word> words, Dictionary dictionary) {
        this._words = words;
        this._dictionary = dictionary;
    }
    
    public List<Word> getWordList(){
        return this._words;
    }
    
    public void startChecking(int start, int end){
        this._minIndex = start;
        this._maxIndex = end;
        _thread = new Thread(this);
        _thread.start();
        try {
            _thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        for (int i = _minIndex; i < _maxIndex; i++) {
            Word w = _words.get(i);

            if (!_dictionary.getWordSet().contains(w.getWord()) && !_dictionary.getWordSet().contains(w.getWord().toLowerCase()))
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
        for (char c : ALPHABET) {
            String atFront = c + w.getWord();
            String atBack = w.getWord() + c;
            if (_dictionary.getWordSet().contains(atFront)) {
                w.addNewProposedWord(atFront);
            }
            if (_dictionary.getWordSet().contains(atBack)) {
                w.addNewProposedWord(atBack);
            }
        }
    }

    private void oneCharExcessInTheWord(Word w) {
        int len = w.getWord().length() - 1;
        // try removing char from the front
        String withoutFirstChar = w.getWord().substring(1);
        if (_dictionary.getWordSet().contains(withoutFirstChar)) {
            w.addNewProposedWord(withoutFirstChar);
        }
        
        for (int i = 1; i < len; i++) {
            // try removing each char between (not including) the first and last
            String working = w.getWord().substring(0, i);
            working = working.concat(w.getWord().substring((i + 1), w.getWord().length()));
            if (_dictionary.getWordSet().contains(working)) {
                w.addNewProposedWord(working);
            }
        }

        String withoutLastChar = w.getWord().substring(0, len);
        if (_dictionary.getWordSet().contains(withoutLastChar)) {
            w.addNewProposedWord(withoutLastChar);
        }
    }
}
