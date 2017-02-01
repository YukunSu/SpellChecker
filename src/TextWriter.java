import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TextWriter {
    public TextWriter(){
        
    }
    
    public void OverwriteExistingTextFile(String path, String content){
        File f = new File(path);
        FileWriter fWriter = null;
        try {
            fWriter = new FileWriter(f, false); // true to append, false to overwrite.
            fWriter.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
