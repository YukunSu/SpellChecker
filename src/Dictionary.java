import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

//TODO Future improvement: reading large file, will need to divide and conquer.
public class Dictionary {
    private HashSet<String> wordSet; 
    private BufferedReader br = null;
    private FileReader fr = null;

    public Dictionary(){
        wordSet = new HashSet<String>();
    }
    
    public HashSet<String> getWordSet(){
        return this.wordSet;
    }
    
    public void ReadFileAbsolutePath(String path){
        try {
            fr = new FileReader(path);
            br = new BufferedReader(fr);
            String sCurrentLine;
            br = new BufferedReader(new FileReader(path));
            while ((sCurrentLine = br.readLine()) != null) {
                wordSet.add(sCurrentLine);
                //System.out.println(sCurrentLine);
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
