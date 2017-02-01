import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

//TODO Future improvement: able to read large file
public class TextReader {
    private List<Word> wordSet;
    private List<String> wordsWithPunction; 
    private HashSet<String> dictionaryWordSet;
    private BufferedReader br = null;
    private FileReader fr = null;
    private StringBuilder sb = null;

    public TextReader(){
        wordSet = new ArrayList<Word>();
        wordsWithPunction = new ArrayList<String>();
        sb = new StringBuilder();
        dictionaryWordSet = new HashSet<String>();
    }
    
    public List<Word> getWordSet(){
        return this.wordSet;
    }
    
    public List<String> getWordSetWithPunction(){
        return this.wordsWithPunction;
    }
    
    public HashSet<String> getDictionaryWordSet(){
        return this.dictionaryWordSet;
    }
    
    public void ReadFileAbsolutePath(String path, boolean isDictionary){
        try {
            fr = new FileReader(path);
            br = new BufferedReader(fr);
            String sCurrentLine;
            br = new BufferedReader(new FileReader(path));

            while ((sCurrentLine = br.readLine()) != null) {
                if(isDictionary){
                    dictionaryWordSet.add(sCurrentLine);
                }else{
                    sb.append(sCurrentLine);
                }
            }
            
            if(!isDictionary){
                String[] words = sb.toString().split("\\s+");
                
                for(int j=0; j<words.length; j++){
                    wordsWithPunction.add(words[j]);
                }
                
                for(int i=0; i<words.length; i++){
                    String temp = Word.removeStartAndEndPunctuationOfAWord(words[i]);
                    if(temp!=null){
                        wordSet.add(new Word(temp));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();

                if (fr != null)
                    fr.close();
                
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
