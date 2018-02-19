package fileutils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import java.util.HashSet;

import java.util.Scanner;
import java.util.StringTokenizer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;



public class WordListDownloader extends AsyncTask<String, Integer, ArrayList<String>>{

    ProgressDialog pleaseWaitDialog=null;
    String downloadMsg="";
    Context context;
    public ArrayList<String> downloadedWords;
    ArrayAdapter<String> wordListAdapter;
    
    public WordListDownloader(Context context, ArrayList<String> wordList, ArrayAdapter<String> wordListAdapter)
    {
    	this.context = context;
    	this.downloadedWords = wordList;
    	this.wordListAdapter = wordListAdapter;
    }

    // This code is to provide an animated dialog showing progress
    // Think of this code executing on the Gui thread

    protected void onPreExecute() {
        pleaseWaitDialog = ProgressDialog.show(context, 
                "Downloading file(back arrow to cancel)", 
                downloadMsg, true, true);
        pleaseWaitDialog.setOnCancelListener(new OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                Log.d("Mine", "onCancel");
                cancel(true);
            }

        });
    }
    



    // This code executes in the created Thread
    protected ArrayList<String> doInBackground(String... parm) {
        String url=parm[0];
        ArrayList<String> contents =loadFile(url);     

        Log.d("Mine"," Done");
        return contents;
    }

    //This code executes in the GUI Thread
    protected void onProgressUpdate(Integer... progress) {
        
    	if (pleaseWaitDialog != null)
            pleaseWaitDialog.setMessage(downloadMsg+(progress[0]!=-1 ? progress[0]:""));
    }

    //This code executes in theGUI Thread
    protected void onPostExecute(ArrayList<String> contents) {
        Log.d("Mine","onPostExecute");
        
        if(pleaseWaitDialog != null)
        	pleaseWaitDialog.dismiss();
        
        if (contents.isEmpty())
        	toastMessage("Could not find any words");
        else {
        	downloadedWords.addAll(contents);
        	wordListAdapter.notifyDataSetChanged();
        }
    }
    
    public void toastMessage(String s) {
		Toast toast = Toast.makeText(context, s, Toast.LENGTH_SHORT);
        toast.show();	
	}
    private ArrayList<String> loadFile(String urlStr)
    {
    	if (!urlStr.endsWith(".txt"))
        	return loadUrl(urlStr);
    	
        ArrayList<String> downloadedWords = new ArrayList<String>();
        URL url;
        URLConnection connection;
        HttpURLConnection httpConnection=null;
        Scanner scan=null;
        downloadMsg = "Adding words: ";
        
        try {
            url = new URL(urlStr);
            connection = url.openConnection();

            httpConnection = (HttpURLConnection)connection; 
            int responseCode = httpConnection.getResponseCode(); 

            if (responseCode == HttpURLConnection.HTTP_OK) {
                Log.d("Mine", "Good HttpURLConnection");
                InputStream in = httpConnection.getInputStream(); 
                scan = new Scanner(in);
                int count = 0;
                while(scan.hasNextLine())
                {
                    String str = scan.nextLine();
                    str = str.replaceAll("[^A-Za-z\\s]", "").replaceAll("^\\s+", "").trim();
                    count += 1;
                    publishProgress(count);
                    
                    downloadedWords.add(str.toLowerCase());                 
                }
                scan.close();
            } 
            else

                Log.d("Mine", "BAD HttpURLConnection");
        }
        catch (MalformedURLException e) {
            Log.d("Mine", "MalformedURLException "+e);
        } catch (IOException e) {
            Log.d("Mine", "IOException "+e);
        }
        finally {
            if (httpConnection != null)
                httpConnection.disconnect();
        }
        return downloadedWords;
    }
    
    private ArrayList<String> loadUrl(String url) {
    	
    	ArrayList<String> hashedParagraphs = new ArrayList<String>();
    	int count = 0;
    	
    	try {
			URL url2 = new URL(url);
		} catch (MalformedURLException e1) {
			url = url.replaceAll("[^A-Za-z0-9_]", "");
			url = "http://en.wikipedia.org/wiki/" + url;
			Log.d("New URL", url);
			e1.printStackTrace();
		}
    	
    	try {
    		downloadMsg = "Parsing HTML... ";
    		publishProgress(-1);
			Document doc = Jsoup.connect(url).get();
			Elements paragraphs = doc.select("p");
			
			StringBuilder sb = new StringBuilder();
			for(Element paragraph: paragraphs) {
				sb.append(paragraph.text() + "\n");
				
			}
			
			String allParagraphs = sb.toString();
			StringTokenizer st = new StringTokenizer(allParagraphs, " \n");
			HashSet<String> paragraphHash = new HashSet<String>();
			
			downloadMsg = "Adding words: ";
			count = 0;
			while(st.hasMoreTokens()) {
				String s = st.nextToken().replaceAll("[^A-Za-z\\s]", "").replaceAll("^\\s+", "").trim();
				if (!s.equals("") && s.length() > 2){
					count++;
					publishProgress(count);
					paragraphHash.add(s.toLowerCase());
				}	
			}
			
			hashedParagraphs = new ArrayList<String>(paragraphHash);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return hashedParagraphs;
    	
    }
    
    public ArrayList<String> getDownloadedWordsAsArrayList()
    {
    	return downloadedWords;
    }
    
    

}
