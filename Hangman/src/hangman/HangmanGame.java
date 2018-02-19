package hangman;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import com.example.hangman.MainActivity;

import fileutils.FileUtils;

import android.graphics.Color;
import android.widget.TextView;
import android.widget.Toast;

public class HangmanGame {
	
	private ArrayList<String> words;
	private String[] assets = {"Animals.txt", "State Capitals.txt", "Countries.txt", "Elements.txt", "Presidents.txt", 
			"Scientists.txt", "States.txt"};
	private String categoryFileName;
	private String categoryName;
	
	private String word;
	private String displayWord = "";
	private String guesses = " ";
	
	private int guessCount = 0;
	
	FileUtils fileUtils;
	
	TextView display = null;
	MainActivity activity = null;
	Toast toast = null;
	
	public HangmanGame(MainActivity activity, TextView display) {
		this.display = display;
		this.activity = activity;
		words = new ArrayList<String>();
		fileUtils = new FileUtils(activity);
		
		getCategory();
		chooseWord();
		updateDisplay();
	}
	
	public String getCategoryName() {
		return categoryName;
	}
	
	public boolean makeGuess(String s) {
		guesses += s.charAt(0);
		
		if (!word.contains(s)) {
			guessCount+=1;
			updateDisplay();
			return false;
		}
		updateDisplay();
		return true;
	}
	
	public void updateDisplay() {
		displayWord = "";
		for (int i = 0; i < word.length(); i++) {
			if (guesses.indexOf(word.charAt(i)) != -1)
				displayWord += word.charAt(i) + " ";
			else
				displayWord += "_ ";
		}
		
		if (!displayWord.contains("_")) {
			toastMessage("You win!");
            displayWord = word;
            display.setTextColor(Color.GREEN);
            activity.disableButtons();
		}
		
		if(guessCount == 6) {
			toastMessage("You lose!");
            displayWord = word;
            display.setTextColor(Color.RED);
            activity.disableButtons();
		}
		
		display.setText(displayWord);
	}
	
	public void toastMessage(String s) {
		toast = Toast.makeText(activity, s, Toast.LENGTH_SHORT);
        toast.show();	
	}
	
	public void getCategory() {
		Random rand = new Random();
		int index = rand.nextInt(assets.length);
		categoryFileName = assets[index];
		categoryName = categoryFileName.substring(0, categoryFileName.length() - 4);
		
		if (!words.isEmpty())
			words.clear();
		
		try {
			InputStream is = activity.getAssets().open(categoryFileName);
			Scanner s = new Scanner(is);
			while(s.hasNext()) {
				words.add(s.nextLine());
			}
			s.close();
			is.close();
		} catch (IOException e) { }
		
	}
	
	public void setCategory(String categoryFileName) {
		if(!words.isEmpty())
			words.clear();
		
		categoryName = categoryFileName.substring(0, categoryFileName.length() - 4);
		words = fileUtils.getWordListAsArrayList(categoryFileName);
	}
	
	public void chooseWord() {
		Random rand = new Random();
		int index = rand.nextInt(words.size());
		word = words.get(index);
	}
	
	public void newGame() {
		toastMessage("New game!");
		display.setTextColor(Color.BLACK);
		displayWord = "";
		guesses = " ";
		guessCount = 0;
		chooseWord();
		updateDisplay();
	}
	
}
