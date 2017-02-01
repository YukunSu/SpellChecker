import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

//TODO Future improvement: able to read large file
public class TextReader {
    private List<Word> _wordSet;
    private List<String> _wordsWithPunction; 
    private HashSet<String> _dictionaryWordSet;
    private BufferedReader _br = null;
    private FileReader _fr = null;
    private StringBuilder _sb = null;

    public TextReader(){
        _wordSet = new ArrayList<Word>();
        _wordsWithPunction = new ArrayList<String>();
        _sb = new StringBuilder();
        _dictionaryWordSet = new HashSet<String>();
    }
    
    public List<Word> getWordSet(){
        return this._wordSet;
    }
    
    public List<String> getWordSetWithPunction(){
        return this._wordsWithPunction;
    }
    
    public HashSet<String> getDictionaryWordSet(){
        return this._dictionaryWordSet;
    }
    
    public void ReadFileAbsolutePath(String path, boolean isDictionary){
        try {
            _fr = new FileReader(path);
            _br = new BufferedReader(_fr);
            String sCurrentLine;
            _br = new BufferedReader(new FileReader(path));

            while ((sCurrentLine = _br.readLine()) != null) {
                if(isDictionary){
                    _dictionaryWordSet.add(sCurrentLine);
                }else{
                    _sb.append(sCurrentLine);
                }
            }
            
            if(!isDictionary){
                String[] words = _sb.toString().split("\\s+");
                
                for(int j=0; j<words.length; j++){
                    _wordsWithPunction.add(words[j]);
                }
                
                for(int i=0; i<words.length; i++){
                    String temp = Word.removeStartAndEndPunctuationOfAWord(words[i]);
                    if(temp!=null){
                        _wordSet.add(new Word(temp));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (_br != null)
                    _br.close();

                if (_fr != null)
                    _fr.close();
                
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
