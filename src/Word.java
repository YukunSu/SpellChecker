import java.util.ArrayList;
import java.util.List;

public class Word {
    private String word = "";
    private boolean isWord = true;
    private List<Word> newProposedWords;
    
    public Word(String word) {
        this.word = word;
    }
    
    public String getWord(){
        return this.word;
    }
    
    public boolean isWord() {
        return isWord;
    }

    public void setWord(boolean isWord) {
        this.isWord = isWord;
    }

    public List<Word> getNewProposedWords() {
        return newProposedWords;
    }

    public void addNewProposedWord(String newProposedWords) {
        if(this.newProposedWords==null) this.newProposedWords = new ArrayList<Word>();
        synchronized(this){
        for(int i=0; i<this.newProposedWords.size(); i++){
            if(this.newProposedWords.get(i).getWord().equals(newProposedWords)){
                return;
            }
        }
        this.newProposedWords.add(new Word(newProposedWords));
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
