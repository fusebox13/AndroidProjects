package fileutils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import android.annotation.SuppressLint;
import android.content.Context;

@SuppressLint("NewApi")
public class FileUtils {
	
	Context context;
	File homeDirectory;
	
	public FileUtils(Context context) {
		this.context = context;
		homeDirectory = context.getFilesDir();
	}
	
	public void createFile(String fileName) {
		File f = new File(homeDirectory, fileName);
		try {
			PrintStream ps = new PrintStream(f);
			ps.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean deleteFile(String fileName) {
		return new File(homeDirectory, fileName).delete();
	}
	
	public List<File> getFileList() {
		File[] filesList = homeDirectory.listFiles();
		return Arrays.asList(filesList);
	}
	
	public ArrayList<String> getFileArrayList() {
		File[] filesList = homeDirectory.listFiles();
		ArrayList<String> filesArrayList = new ArrayList<String>();
		
		for(int i = 0; i < filesList.length; i++) {
			
			String fileName = filesList[i].getName();
			if (filesList[i].getName().endsWith(".txt")) {
				
				filesArrayList.add(fileName.substring(0, fileName.length() - 4));	
			}
			
		}
			
			
		return filesArrayList;
	}
	
	public ArrayList<String> getWordListAsArrayList(String fileName) {
		Scanner s = getScanner(fileName);
		ArrayList<String>wordList = new ArrayList<String>();
		
		while(s.hasNext()) {
			String str = s.nextLine();
			wordList.add(str);
		}
		
		s.close();
		
		return wordList;
	}
	
	public void writeArrayListToFile(String fileName, ArrayList<String> aList) {
		FileWriter fw = getFileWriter(fileName, false);
		
		if (aList != null) {
			try {
				for(int i = 0; i < aList.size(); i++) {
					fw.write(aList.get(i) + System.lineSeparator());	
				}
				
				fw.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
	}
	
	
	public InputStream getInputStream(String fileName) {
		InputStream is = null;
		try {
			is = new FileInputStream(new File(homeDirectory, fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return is;
	}
	
	public Scanner getScanner(String fileName) {
		Scanner s = new Scanner(getInputStream(fileName));
		return s;
	}
	
	public FileWriter getFileWriter(String fileName, boolean append) {
		FileWriter fw = null;
		try {
			fw = new FileWriter(new File(homeDirectory, fileName), append);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fw;
		
	}
	
	public boolean addStringToFile(String fileName, String s) {
		try {
			PrintStream ps = new PrintStream(new File(homeDirectory,fileName));
			ps.println(s);
			ps.close();
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}
	
	public void restoreDefaultCategories() {
		ArrayList<String> wordList = new ArrayList<String>();
		
		String[] assets = null;
		try {
			assets = context.getAssets().list("");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		for (int i = 0; i < assets.length; i++) {
			if(assets != null && assets[i].endsWith(".txt")) {
				try {
					File f = new File(homeDirectory, assets[i]);
					
					if(!f.exists()) {
						Scanner s = new Scanner(context.getAssets().open(assets[i]));
						while(s.hasNext()) {
							wordList.add(s.nextLine());
						}
						s.close();
						writeArrayListToFile(assets[i], wordList);
						wordList.clear();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public boolean isEmpty(String fileName)
	{
		File f = new File(homeDirectory, fileName);
		
		if(f.length() == 0)
			return true;
		
		return false;
		
	}
}
