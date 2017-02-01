import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;

public class Start {

    public static void main(String[] args) {
        /*** Input section ***/
        TextReader tr = new TextReader();

        // Read dictionary words
        Path path = Paths.get(Constants.currentDirectory, Constants.TEST_FILES_FOLDER, Constants.DICTIONARY);
        tr.ReadFileAbsolutePath(path.toString(), true);

        // Create dictionary
        Dictionary d = new Dictionary(tr.getDictionaryWordSet());

        // Read text file that need to perform spell check
        path = Paths.get(Constants.currentDirectory, Constants.TEST_FILES_FOLDER, Constants.TEXT1);
        tr.ReadFileAbsolutePath(path.toString(), false);

        // Get the words as a list from the text file
        List<Word> test = tr.getWordSet();

        /*** Spell checking section ***/
        SpellChecker sc = new SpellChecker(test, d);

        if (test.size() >= Constants.THREAD_SIZE) {
            int increment = test.size() / Constants.THREAD_SIZE;
            for (int i = 0; i < Constants.THREAD_SIZE; i++) {
                if (i == Constants.THREAD_SIZE - 1) {
                    sc.startChecking(i * increment, test.size());
                } else {
                    sc.startChecking(i * increment, (i + 1) * increment);
                }
            }
        } else {
            sc.startChecking(0, test.size() / 2);
            sc.startChecking(test.size() / 2, test.size());
        }

        /*** Output Section ***/
        List<String> copy = tr.getWordSetWithPunction();
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<copy.size(); i++){
            sb.append(copy.get(i) + " ");
        }
        String content = sb.toString();

        HashSet<String> set = new HashSet<String>();
        
        for (int i = 0; i < sc.getWordList().size(); i++) {
            Word wTemp = sc.getWordList().get(i);
            String tempWord = wTemp.getWord();
            System.out.print((i + 1) + " " + tempWord + " ");
            
            if (!wTemp.isWord()) {
                System.out.print("Possible wrong spelling. ");
                if (wTemp.getNewProposedWords() != null) {
                    System.out.print("Proposed new words: ");
                    
                    //Avoid repetition
                    if(!set.contains(tempWord)){
                        content = content.replaceAll(tempWord, tempWord + "(" + wTemp.getNewProposedWords().get(0).getWord() + ")");
                        set.add(tempWord);
                    }
                    
                    for (int j = 0; j < wTemp.getNewProposedWords().size(); j++) {
                        System.out.print((j + 1) + " " + wTemp.getNewProposedWords().get(j).getWord() + " ");
                    }
                } else {
                    System.out.print("No suggestion");
                    //Avoid repetition
                    if(!set.contains(tempWord)){
                        content = content.replaceAll(tempWord, tempWord + "(No suggestion)");
                        set.add(tempWord);
                    }
                }
            }
            System.out.println();
        }
        
        System.out.println(content);
        TextWriter writer = new TextWriter();
        writer.OverwriteExistingTextFile(Paths.get(Constants.currentDirectory, Constants.TEST_FILES_FOLDER, Constants.TEXT2).toString(), content);
    }
    
    public static void print(List<String> l){
         for(int i=0; i<l.size(); i++){
             System.out.println(l.get(i));
         }
    }

}
