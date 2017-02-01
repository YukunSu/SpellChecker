import java.util.ArrayList;
import java.util.List;

public class Word {
    private String _word = "";
    private boolean _isWord = true;
    private List<Word> _newProposedWords;
    
    public Word(String word) {
        this._word = word;
    }
    
    public String getWord(){
        return this._word;
    }
    
    public void setWord(String word){
        this._word = word;
    }
    
    public boolean isWord() {
        return _isWord;
    }

    public void setIsWord(boolean isWord) {
        this._isWord = isWord;
    }

    public List<Word> getNewProposedWords() {
        return _newProposedWords;
    }

    public void addNewProposedWord(String newProposedWords) {
        if(this._newProposedWords==null) this._newProposedWords = new ArrayList<Word>();
        synchronized(this){
        for(int i=0; i<this._newProposedWords.size(); i++){
            if(this._newProposedWords.get(i).getWord().equals(newProposedWords)){
                return;
            }
        }
        this._newProposedWords.add(new Word(newProposedWords));
        }
    }

    //TODO need improvement
    public static String removeStartAndEndPunctuationOfAWord(String t){
        String s = t;
        if(s.startsWith("0") || s.endsWith("0") ||
                s.startsWith("1") || s.endsWith("1") ||
                s.startsWith("2") || s.endsWith("2") ||
                s.startsWith("3") || s.endsWith("3") ||
                s.startsWith("4") || s.endsWith("4") ||
                s.startsWith("5") || s.endsWith("5") ||
                s.startsWith("6") || s.endsWith("6") ||
                s.startsWith("7") || s.endsWith("7") ||
                s.startsWith("8") || s.endsWith("8") ||
                s.startsWith("9") || s.endsWith("9")
                )
            return null;
        
        if(s.startsWith("\"")) s = s.replace("\"", "");
        if(s.startsWith("\'")) s = s.replace("\'", "");
        if(s.endsWith("\"")) s = s.replace("\"", "");
        if(s.endsWith("\'")) s = s.replace("\'", "");
        
        if(s.endsWith(".")) s = s.replace(".", "");
        if(s.endsWith("?")) s = s.replace("?", "");
        if(s.endsWith("!")) s = s.replace("!", "");
        if(s.endsWith(":")) s = s.replace(":", "");
        if(s.endsWith(";")) s = s.replace(";", "");
        if(s.endsWith(",")) s = s.replace(",", "");
        
        if(s.startsWith("(")) s = s.replace("(", "");
        if(s.startsWith("[")) s = s.replace("[", "");
        if(s.startsWith("{")) s = s.replace("{", "");
        if(s.endsWith(")")) s = s.replace(")", "");
        if(s.endsWith("]")) s = s.replace("]", "");
        if(s.endsWith("}")) s = s.replace("}", "");
        
        return s;
    }

}
