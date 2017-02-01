import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class GUI extends JFrame{

    private JButton _bLoadDictionary;
    private JButton _bLoadFile;
    private JButton _bSpellCheck;
    private JButton _bOverwrite;
    private Panel _pPanel1;
    private Panel _pPanel2;
    private JFileChooser _fcFileChooser;
    private JLabel _lResult;
    
    private TextReader _reader;
    private Dictionary _dict;
    private List<Word> _test;
    private SpellChecker _checker;
    private File _selectedInputText;
    
    public GUI() {
        this.setTitle("Spell Checker");
        this.setSize(600,200); // default size is 0,0
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

        init();

        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      }

    private void init() {
        _bLoadDictionary = new JButton("Load Dictionary");
        _bLoadFile = new JButton("Load File");
        _bSpellCheck = new JButton("Spell Check");
        _bOverwrite = new JButton("Overwrite");
        _lResult = new JLabel("Welcome!");
        _pPanel1 = new Panel();
        _pPanel2 = new Panel();
        _pPanel1.add(_lResult);
        
        this.add(_pPanel1, BorderLayout.SOUTH);
        _pPanel2.setLayout(new GridLayout(2,2,15,15));
        _pPanel2.add(_bLoadDictionary);
        _pPanel2.add(_bLoadFile);
        _pPanel2.add(_bSpellCheck);
        _pPanel2.add(_bOverwrite);

        this.add(_pPanel2);
        _bLoadDictionary.addActionListener(new ButtonListener());
        _bLoadFile.addActionListener(new ButtonListener());
        _bSpellCheck.addActionListener(new ButtonListener());
        _bOverwrite.addActionListener(new ButtonListener());
    }
    
    public class ButtonListener implements ActionListener {
        ButtonListener() {
        }

        public void actionPerformed(ActionEvent e) {
          if (e.getActionCommand().equals("Load Dictionary")) {
            //System.out.println("Button1 has been clicked");
            _fcFileChooser = new JFileChooser();
            Path path = Paths.get(Constants.currentDirectory, Constants.TEST_FILES_FOLDER);
            _fcFileChooser.setCurrentDirectory(new File(path.toString()));
            int result = _fcFileChooser.showOpenDialog(new JFrame());
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = _fcFileChooser.getSelectedFile();
                _reader = new TextReader();
                _reader.ReadFileAbsolutePath(selectedFile.getAbsolutePath(), true);
                _dict = new Dictionary(_reader.getDictionaryWordSet());
                _lResult.setText("Selected file: " + selectedFile.getAbsolutePath());
                //System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                }
          }
          
          if (e.getActionCommand().equals("Load File")) {
              //System.out.println("Button1 has been clicked");
              _fcFileChooser = new JFileChooser();
              Path path = Paths.get(Constants.currentDirectory, Constants.TEST_FILES_FOLDER);
              _fcFileChooser.setCurrentDirectory(new File(path.toString()));
              int result = _fcFileChooser.showOpenDialog(new JFrame());
              if (result == JFileChooser.APPROVE_OPTION) {
                  _selectedInputText = _fcFileChooser.getSelectedFile();
                  _reader = new TextReader();
                  _reader.ReadFileAbsolutePath(_selectedInputText.getAbsolutePath(), false);
                  _test = _reader.getWordSet();
                  _lResult.setText("Selected file: " + _selectedInputText.getAbsolutePath());
                  //System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                  }
            }
          
          if (e.getActionCommand().equals("Spell Check")) {
              _checker = new SpellChecker(_test, _dict);

              if (_test.size() >= Constants.THREAD_SIZE) {
                  int increment = _test.size() / Constants.THREAD_SIZE;
                  for (int i = 0; i < Constants.THREAD_SIZE; i++) {
                      if (i == Constants.THREAD_SIZE - 1) {
                          _checker.startChecking(i * increment, _test.size());
                      } else {
                          _checker.startChecking(i * increment, (i + 1) * increment);
                      }
                  }
              } else {
                  _checker.startChecking(0, _test.size() / 2);
                  _checker.startChecking(_test.size() / 2, _test.size());
              }
              _lResult.setText("Spell checking finished.");
            }
          
          if (e.getActionCommand().equals("Overwrite")) {
              List<String> copy = _reader.getWordSetWithPunction();
              StringBuilder sb = new StringBuilder();
              for(int i=0; i<copy.size(); i++){
                  sb.append(copy.get(i) + " ");
              }
              String content = sb.toString();

              HashSet<String> set = new HashSet<String>();
              
              for (int i = 0; i < _checker.getWordList().size(); i++) {
                  Word wTemp = _checker.getWordList().get(i);
                  String tempWord = wTemp.getWord();
                  //System.out.print((i + 1) + " " + tempWord + " ");
                  
                  if (!wTemp.isWord()) {
                      //System.out.print("Possible wrong spelling. ");
                      if (wTemp.getNewProposedWords() != null) {
                          //System.out.print("Proposed new words: ");
                          
                          //Avoid repetition
                          if(!set.contains(tempWord)){
                              content = content.replaceAll(tempWord, tempWord + "(" + wTemp.getNewProposedWords().get(0).getWord() + ")");
                              set.add(tempWord);
                          }
                          
//                          for (int j = 0; j < wTemp.getNewProposedWords().size(); j++) {
//                              System.out.print((j + 1) + " " + wTemp.getNewProposedWords().get(j).getWord() + " ");
//                          }
                      } else {
                          //System.out.print("No suggestion");
                          //Avoid repetition
                          if(!set.contains(tempWord)){
                              content = content.replaceAll(tempWord, tempWord + "(No suggestion)");
                              set.add(tempWord);
                          }
                      }
                  }
                  //System.out.println();
              }
              
              //System.out.println(content);
              TextWriter writer = new TextWriter();
              writer.OverwriteExistingTextFile(_selectedInputText.getAbsolutePath(), content);
              _lResult.setText("Overwrite file finished.");
            }
        }
      }
}
