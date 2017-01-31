
public class Word {
    private String word = "";
    public Word(String word) {
        this.word = word;
    }
    
    public String getWord(){
        return this.word;
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
