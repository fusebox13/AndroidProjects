import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class NormalJavaWeb extends JFrame {
    JTextArea ta= new JTextArea(30,70);
    
    NormalJavaWeb(String name)
    {
        super(name);
        setLayout(new FlowLayout());
        
        final JTextField tf = new JTextField(60);
        add(tf);
        tf.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                System.out.println("Enter key received");
                Thread t = new Thread(new Runnable()
                {

                    @Override
                    public void run() {
                        String url = tf.getText();
                        System.out.println("url="+url);
                        String str = loadFile(url);
                        System.out.println(str);
                        ta.setText(str);                    
                    }

                });
                t.start();

            }

        });
        
        JScrollPane scrollPane = new JScrollPane(ta);
        add(scrollPane);
        //String str = loadFile("http://russet.wccnet.edu/~chasselb/girls_names.txt");
        //ta.append(str);
        
        
        
        
      
    }

    private void error(String str)
    {
       System.out.println(str); 
    }
    private String loadFile(String urlStr)
    {
        StringBuffer bs= new StringBuffer();
           
        URL url;
        URLConnection connection;
        HttpURLConnection httpConnection=null;
        InputStream in=null;
        Scanner scan=null;

        try {
            url = new URL(urlStr);
            connection = url.openConnection();

            httpConnection = (HttpURLConnection)connection; 
            int responseCode = httpConnection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) { 
                in = httpConnection.getInputStream(); 
                scan = new Scanner(in);
                while(scan.hasNextLine())
                {
                    String str = scan.nextLine();
                    bs.append(str +"\n");                   
                }

            } 
        }
        catch (MalformedURLException e) {
            error( "MalformedURLException "+e);
        } catch (IOException e) {
            error( "IOException "+e);
        }
        finally {
            try{
                if (in != null)in.close();
            }catch (IOException e){}
            if (httpConnection != null)httpConnection.disconnect();
        }
        return bs.toString();
    }

    
    public static void main(String[] args) { 
        NormalJavaWeb aFrame = new NormalJavaWeb("Normal Java");    
        aFrame.setSize(800,600);     
        aFrame.setVisible(true); 
      } // end of main

}
