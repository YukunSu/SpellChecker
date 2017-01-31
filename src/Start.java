import java.util.List;


public class Start {

    public static void main(String[] args) {
        //Dictionary d = new Dictionary();
        //d.ReadFileAbsolutePath("C:\\我的文件夹\\workspace\\SpellChecker\\Test Files\\Dictionary.txt");
        //System.out.println(d.getWordSet().size());
        
        
        
        TextReader tr = new TextReader();
        tr.ReadFileAbsolutePath("C:\\我的文件夹\\workspace\\SpellChecker\\Test Files\\test1.txt");
        List<Word> test = tr.getWordSet();
        for(int i=0; i<test.size(); i++){
            System.out.println(test.get(i).getWord());
        }
        System.out.println(tr.getWordSet().size());
    }

}
