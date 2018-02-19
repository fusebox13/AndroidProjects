package com.example.droidcalc;

import calculator.Calculator;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener{
	
	TextView display, display2= null;
	Calculator calculator = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		int[] resources = { R.id.ADD, R.id.BACKSPACE, R.id.CLEAR, R.id.CLEARENTRY, R.id.DECIMAL, R.id.DIVIDE,
				R.id.EIGHT, R.id.EQUALS, R.id.FIVE, R.id.FOUR, R.id.MC, R.id.MMINUS, R.id.MPLUS, R.id.MR, R.id.MS,
				R.id.MULTIPLY, R.id.NINE, R.id.ONE, R.id.PERCENT, R.id.POSNEG, R.id.RECIPROCAL, R.id.SEVEN, R.id.SIX,
				R.id.SQUAREROOT, R.id.SUBTRACT, R.id.THREE, R.id.TWO, R.id.ZERO};
		
		for (int i = 0; i < resources.length; i++)
		{
			Button b = (Button)findViewById(resources[i]);
			b.setOnClickListener(this);
		}
		
		display = (TextView) findViewById(R.id.calc);
		display2 = (TextView) findViewById(R.id.equation);
		calculator = new Calculator(display);
		
	 
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		Button b = (Button)v;
		Button MS = (Button) findViewById(R.id.MS);
		switch (v.getId())
		{
		case R.id.ADD:
			calculator.operatorCommand("+");
			break;
		case R.id.BACKSPACE:
			calculator.backSpaceCommand();
			break;
		case R.id.CLEAR:
			calculator.clearCommand();
			break;
		case R.id.CLEARENTRY:
			calculator.clearEntryCommand();
			break;
		case R.id.DECIMAL:
			calculator.decimalCommand();
			break;
		case R.id.DIVIDE:
			calculator.operatorCommand("/");
			break;
		case R.id.EIGHT:
			calculator.numericCommand(8);
			break;
		case R.id.EQUALS:
			calculator.operatorCommand("=");
			break;
		case R.id.FIVE:
			calculator.numericCommand(5);
			break;
		case R.id.FOUR:
			calculator.numericCommand(4);
			break;
		case R.id.MC:
			calculator.memoryClearCommand(MS);
			break;
		case R.id.MMINUS:
			calculator.memorySubtractCommand();
			break;
		case R.id.MPLUS:
			calculator.memoryAddCommand();
			break;
		case R.id.MR:
			calculator.memoryRecallCommand();
			break;
		case R.id.MS:
			calculator.memoryStoreCommand(MS);
			break;
		case R.id.MULTIPLY:
			calculator.operatorCommand("*");
			break;
		case R.id.NINE:
			calculator.numericCommand(9);
			break;
		case R.id.ONE:
			calculator.numericCommand(1);
			break;
		case R.id.PERCENT:
			calculator.percentCommand();
			break;
		case R.id.POSNEG:
			calculator.posnegCommand();
			break;
		case R.id.RECIPROCAL:
			calculator.reciprocalCommand();
			break;
		case R.id.SEVEN:
			calculator.numericCommand(7);
			break;
		case R.id.SIX:
			calculator.numericCommand(6);
			break;
		case R.id.SQUAREROOT:
			calculator.squareRootCommand();
			break;
		case R.id.SUBTRACT:
			calculator.operatorCommand("-");
			break;
		case R.id.THREE:
			calculator.numericCommand(3);
			break;
		case R.id.TWO:
			calculator.numericCommand(2);
			break;
		case R.id.ZERO:
			calculator.numericCommand(0);
			break;
			
		}
		
		display2.setText(calculator.displayInputs());
		
	}

}
