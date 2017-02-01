import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Start {
    
    public static void main(String[] args) {
        String currentDir = Constants.currentDirectory;
        //System.out.println(currentDir);
        Path path = Paths.get(currentDir, Constants.TEST_FILES_FOLDER, Constants.DICTIONARY);
        //System.out.println(path.toString());
        Dictionary d = new Dictionary();
        d.ReadFileAbsolutePath(path.toString());
        //System.out.println(d.getWordSet().size());
        
        TextReader tr = new TextReader();
        path = Paths.get(currentDir, Constants.TEST_FILES_FOLDER, Constants.TEXT1);
        tr.ReadFileAbsolutePath(path.toString());
        List<Word> test = tr.getWordSet();
//        for(int i=0; i<test.size(); i++){
//            System.out.println(test.get(i).getWord());
//        }
        System.out.println(tr.getWordSet().size());
        
        SpellChecker sc = new SpellChecker(test, d);
        

        if(test.size() >= Constants.THREAD_SIZE){
            int increment = test.size() / Constants.THREAD_SIZE;
            for(int i=0; i<Constants.THREAD_SIZE; i++){
                if(i == Constants.THREAD_SIZE-1){
                    sc.startChecking(i*increment, test.size());
                }else{
                    sc.startChecking(i*increment, (i+1)*increment);
                }
            }
        }else{
            sc.startChecking(0, test.size()/2);
            sc.startChecking(test.size()/2, test.size());
        }
        
      for(int i=0; i<sc.getWordList().size(); i++){
          System.out.print((i+1) + " " + sc.getWordList().get(i).getWord() + " ");
          //System.out.print(sc.getWordList().get(i).isWord() + " ");
          if(!sc.getWordList().get(i).isWord()) {
              System.out.print("Possible wrong spelling. ");
          if(sc.getWordList().get(i).getNewProposedWords()!=null){
              System.out.print("Proposed new words: ");
              for(int j=0; j<sc.getWordList().get(i).getNewProposedWords().size(); j++){
                  System.out.print((j+1) + " " + sc.getWordList().get(i).getNewProposedWords().get(j).getWord() + " ");
              }
          } else{
              System.out.print("No suggestion");
          }
          }
          System.out.println();
      }
    }

}
