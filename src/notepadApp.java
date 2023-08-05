import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

//main class
public class notepadApp 
{
    public static void main(String[] args)
    {
        System.out.print("\033[H\033[2J");

        new Notepad("Scribbie");
        Notepad.frame.setVisible(true);

        System.out.print("\nProgram running...");
    }
}

//drafting Notepad components
interface NotepadComponents
{
    void trial();
    void menubar();
}

//notepad class
class Notepad implements NotepadComponents
{
    public static JFrame frame;
    public static boolean isLight=true;
    public static JTextArea textArea;
    public static JMenuBar menuBar;
    public static JScrollPane textJScrollPane;
    private int fontSize = 16;
    
    Notepad(String title)
    {
        System.out.print("Instance Created");
        frame = new JFrame(title);
        frame.pack();
        
        //setting Notepad properties
        frame.setLayout(new BorderLayout());
        frame.setSize(500, 500);
        frame.getContentPane().setBackground(Color.WHITE);

        //creating Notepad components
        this.menubar();
        this.trial();
    }

    /********** TRIAL METHOD **********/
    public void trial()
    {
        textArea = new JTextArea();
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setFont(new Font("Arial", Font.PLAIN, fontSize));
        textArea.setBorder(new EmptyBorder(5, 5, 5, 5));

        //making textArea scrollable
        textJScrollPane = new JScrollPane(textArea);
        textJScrollPane.setBorder(null);
        frame.add(textJScrollPane);
    }

    /********** CREATING MENU BAR **********/
    public void menubar()
    {
        //setting properties of menuBar
        menuBar = new JMenuBar();
        menuBar.setSize(500, 20);
        menuBar.setVisible(true);
        menuBar.setBackground(Color.WHITE);

        /**** adding menu "FILE" ****/
        JMenu fileMenu = new JMenu("File");
        JMenuItem saveItem = new JMenuItem("Save");     //menuItem "SAVE"
        saveItem.addActionListener(new ActionListener() {    //adding save function 
            @Override
            public void actionPerformed(ActionEvent e) {
                menuMethods.save();
            }
        });
        JMenuItem exitItem = new JMenuItem("Exit");    //menuItem "EXIT"
        exitItem.addActionListener(new ActionListener() {   //adding exit function
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        JMenuItem newWindowItem = new JMenuItem("New Window");  //menuItem "NEW WINDOW"
        newWindowItem.addActionListener(new ActionListener() {       //adding new window function
            @Override
            public void actionPerformed(ActionEvent e) {
                notepadApp.main(null);
            }
        });
        fileMenu.add(saveItem);
        fileMenu.add(newWindowItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);

        /**** adding menu "VIEW" ****/
        JMenu viewMenu = new JMenu("View");                 
        JMenuItem theme = new JMenuItem("Dark/Light Theme");    //menuItem "THEME"
        theme.addActionListener(new ActionListener() {               //adding theme alter funtcion
            @Override
            public void actionPerformed(ActionEvent e) {
                menuMethods.setTheme(fileMenu, viewMenu);
            }
        });
        JMenuItem zoomInMenuItem = new JMenuItem("Zoom In");    //menuItem "ZOOM IN"
        zoomInMenuItem.addActionListener(new ActionListener() {      //adding zoom in function
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setFont(new Font("Arial", Font.PLAIN, fontSize+=2));
            }
        });
        JMenuItem zoomOutMenuItem = new JMenuItem("Zoom Out");    //menuItem "ZOOM OUT"
        zoomOutMenuItem.addActionListener(new ActionListener() {      //adding zoom in function
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setFont(new Font("Arial", Font.PLAIN, fontSize-=2));
            }
        });
        viewMenu.add(zoomOutMenuItem);
        viewMenu.add(zoomInMenuItem);
        viewMenu.addSeparator();
        viewMenu.add(theme);
        menuBar.add(viewMenu);

        frame.setJMenuBar(menuBar);
    }   
}

//methods required in menuBar
class menuMethods
{
    //saving text file
    public static void save()
    {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showSaveDialog(Notepad.frame);
        if (returnValue == JFileChooser.APPROVE_OPTION) 
        {
            File selectedFile = fileChooser.getSelectedFile();
            try (FileWriter fileWriter = new FileWriter(selectedFile)) 
            {
                fileWriter.write(Notepad.textArea.getText());
                JOptionPane.showMessageDialog(Notepad.frame, "File saved successfully.");
            } 
            catch (IOException ex) 
            {
                JOptionPane.showMessageDialog(Notepad.frame, "Error while saving the file.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    //setting notepad theme
    public static void setTheme(JMenu file, JMenu view)
    {
        if(Notepad.isLight)
        {
            Notepad.frame.getContentPane().setBackground(Color.BLACK);
            Notepad.textArea.setForeground(Color.WHITE);
            Notepad.textArea.setBackground(Color.BLACK);
            Notepad.menuBar.setBackground(Color.BLACK);
            Notepad.textJScrollPane.getVerticalScrollBar().setBackground(Color.BLACK);
            file.setForeground(Color.WHITE);
            view.setForeground(Color.WHITE);
            Notepad.isLight = false;
        }
        else
        {
            Notepad.frame.getContentPane().setBackground(Color.WHITE);
            Notepad.textArea.setForeground(Color.BLACK);
            Notepad.textArea.setBackground(Color.WHITE);
            Notepad.menuBar.setBackground(Color.WHITE);
            Notepad.textJScrollPane.getVerticalScrollBar().setBackground(Color.WHITE);
            file.setForeground(Color.BLACK);
            view.setForeground(Color.BLACK);
            Notepad.isLight = true;
        }
    }
}