import java.awt.FlowLayout;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class NormalJavaClock extends JFrame implements Runnable{

    JTextArea ta= new JTextArea(30,70);
    boolean running = true;
    NormalJavaClock(String name)
    {
        super(name);
        setLayout(new FlowLayout());
        JScrollPane scrollPane = new JScrollPane(ta);
        add(scrollPane);
        
        Thread t = new Thread(this);
        t.start();
    }
    public void run()
    {
        while (running)
        {
            try
            {
                Thread.sleep(1000);
                Date d = new Date();
                ta.append(d.toString() + "\n");
            }
            catch (InterruptedException e)
            {
                
            }
        }
    }
      
    public static void main(String[] args) { 
        NormalJavaClock aFrame = new NormalJavaClock("Normal Java");    
        aFrame.setSize(800,600);     
        aFrame.setVisible(true); 
      } // end of main

}
