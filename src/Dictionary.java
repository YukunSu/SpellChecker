import java.util.HashSet;

//TODO Future improvement: reading large file, will need to divide and conquer.
public class Dictionary {
    private HashSet<String> wordSet; 

    public Dictionary(HashSet<String> set){
        this.wordSet = set;
    }
    
    public HashSet<String> getWordSet(){
        return this.wordSet;
    }
    
    public void setWordSet(HashSet<String> set){
        this.wordSet = set;
    }
}
