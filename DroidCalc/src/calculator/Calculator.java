package calculator;

import java.math.BigInteger;
import java.util.ArrayList;
import android.graphics.Color;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class Calculator 
{
	ArrayList<String> inputs = new ArrayList<String>();
	ArrayList<String> ops = new ArrayList<String>();
	
	String currentInput = null;
	String memoryStore = null;
	
	private boolean equalsWasPressed = false;
	private boolean opWasPressed = false;
	private boolean decimalMode = false;
	private boolean decimalPointAllowed = true;
	
	private String lastOp;
	private String lastInput;

	public TextView display;
	
	
	public Calculator(TextView display)
	{
		this.display = display;
	}
	
	
	public void posnegCommand()
	{
		if (has(currentInput) && !hasErrors(currentInput))
		{
			Object i;
			if (!decimalMode)
			{
				BigInteger j = new BigInteger(currentInput);
				j = j.negate();
				i=(BigInteger)j;
			}
			else
			{
				double j = Double.parseDouble(currentInput);
				j = j * -1.0d;
				i=(Double)j;
			}
			currentInput = i.toString(); 
			display.setText(i.toString());
			
		}
		
	}
	
	
	public void reciprocalCommand()
	{
		if(has(currentInput) && !hasErrors(currentInput))
		{
			decimalMode = true;
			
			currentInput = String.valueOf(1.0d / Double.parseDouble(currentInput));
			if (currentInput.equals("Infinity"))
			{
				currentInput = "Cannot divide by zero";
				decimalMode = false;
			}
			display.setText(currentInput);
		}
	}
	
	
	public void squareRootCommand()
	{
		if(has(currentInput) && !hasErrors(currentInput))
		{
			decimalMode = true;
			
			currentInput = String.valueOf(Math.sqrt(Double.parseDouble(currentInput)));
			
			//switch back to integer mode if the result is an even square
			if (currentInput.endsWith(".0"))
			{
				decimalMode = false;
				currentInput = currentInput.substring(0, currentInput.length() - 2);
			}
			if (currentInput.equals("NaN"))
				currentInput = "Invalid input";
			
			display.setText(currentInput);
		}
		
	}
	
	
	public void memoryStoreCommand(Button b)
	{
		if(has(currentInput) && !hasErrors(currentInput))
		{
			memoryStore = currentInput;
			b.setTextColor(Color.WHITE);
		}
		
	}
	
	
	public void memoryClearCommand(Button b)
	{
		memoryStore = null;
		b.setTextColor(Color.BLACK);
	}
	
	
	public void memoryRecallCommand()
	{
		if (memoryStore != null)
		{
			currentInput = memoryStore;
			
			if(currentInput.contains(".")) 
				decimalMode = true;
			
			display.setText(currentInput);
		}
		
	}
	
	
	public void memoryAddCommand()
	{
		if(has(currentInput) && !hasErrors(currentInput))
		{
			if(decimalMode || memoryStore.contains("."))
			{
				memoryStore = String.valueOf(Double.parseDouble(memoryStore) + Double.parseDouble(currentInput));
				decimalMode = true;
			}
			else
				memoryStore = new BigInteger(memoryStore).add(new BigInteger(currentInput)).toString();
		}
		
	}
	
	
	public void memorySubtractCommand()
	{
		if(has(currentInput) && !hasErrors(currentInput))
		{
			if(decimalMode || memoryStore.contains("."))
			{
				memoryStore = String.valueOf(Double.parseDouble(memoryStore) - Double.parseDouble(currentInput));
				decimalMode = true;
			}
			else
				memoryStore = new BigInteger(memoryStore).subtract(new BigInteger(currentInput)).toString();
		}
		
	}
	
	
	public void decimalCommand()
	{
		decimalMode = true;
		
		opWasPressed = false;
		
		if(decimalPointAllowed)
		{
			if (equalsWasPressed)
			{
				clearDisplay();
				equalsWasPressed = false;	
			}
			else
			{
				display.append(".");
				currentInput = display.getText().toString();
				decimalPointAllowed = false;
			}
			

		}
		
	}

	
	public void numericCommand(int i)
	{

		if(has(currentInput) && hasErrors(currentInput))
			clearDisplay();
		
		if (has(currentInput) && equalsWasPressed)
		{
			clearDisplay();
			equalsWasPressed = false;
		}
		
		if (has(currentInput) && opWasPressed)
		{
			clearDisplay();
			opWasPressed = false;
		}
		
		
		display.append("" + i);
		currentInput = display.getText().toString();
	}
	
	
	public void clearEntryCommand()
	{
		clearDisplay();
		if (currentInput.contains("."))
			decimalMode = false;
		
		if (inputs.size() > 0)
		{
			for(int i = 0; i < inputs.size(); i++)
			{
				if(inputs.get(i).contains("."))
					decimalMode = true;
			}
		}
		
		currentInput = null;
		decimalPointAllowed = true;
		equalsWasPressed = false;
		opWasPressed = false;
		
	}
	
	
	public void backSpaceCommand()
	{
		equalsWasPressed = false;
		
		if(currentInput.length() > 0 && !hasErrors(currentInput))
		{	
			currentInput = currentInput.substring(0, currentInput.length() - 1);
			
			if (currentInput.contains("."))
				decimalMode = true;
			else
				decimalMode = false;
			
			display.setText(currentInput);
		}
	}
	
	
	public void clearCommand()
	{
		clearDisplay();
		clearMemory(true);
		equalsWasPressed = false;
		opWasPressed = false;
		
	}
	
	
	public void clearMemory(boolean all)
	{
		if (all == true)
		{
			currentInput = null;
		}
		
		inputs.clear();
		ops.clear();
		decimalMode = false;
		
		try{
			if(currentInput.contains("."))
				decimalMode = true;		
		} catch (NullPointerException e){ }
			
		
		decimalPointAllowed = true;
		
	}
	
	
	public void clearDisplay()
	{
		display.setText("");
	}
	
	
	public void percentCommand()
	{
		String sum;
		
		if (inputs.size() > 0 && has(currentInput))
		{
			decimalMode = true;
			if (inputs.size() > 1)
				sum = computedSum();
			else
				sum = inputs.get(0);
			
			currentInput = display.getText().toString();
			double percent = Double.parseDouble(currentInput) * 0.01d;
			currentInput = String.valueOf(Double.parseDouble(sum) * percent);
			display.setText(currentInput);
			
		}
	}
	
	
	public boolean hasErrors(String s)
	{
		if (s.equals("Cannot divide by zero") || s.equals("Invalid input"))
			return true;
		
		return false;
	}
	
	
	public boolean has(String s)
	{
		if(s != null && !s.equals(""))
			return true;
		
		return false;
	}
	
	
	public void operatorCommand(String op)
	{
		opWasPressed = true;
		decimalPointAllowed = true;
		
		if (!op.equals("="))
			equalsWasPressed = false;
		
		
		currentInput = display.getText().toString();
		if (has(currentInput))
		{
			
			if(currentInput.equals(".") || hasErrors(currentInput))
			{
				currentInput = "0";
				decimalMode = false;
			}
			
			inputs.add(currentInput);
			
			if (equalsWasPressed)
			{
				ops.add(lastOp);
				inputs.add(lastInput);
				ops.add("=");
			}
			else
				ops.add(op);
			
			clearDisplay();
			
			if (inputs.size() >= 2)
			{
				currentInput = computedSum();
				display.setText(currentInput);
			}
			
			if(op.equals("="))
			{
				clearMemory(false);
				equalsWasPressed = true;
			}
				 
			
		}		
	}
	
	
	public String computedSum()
	{
		Object sum = null;
		BigInteger tempSum = null;
		
		if (decimalMode)
			sum = (double)Double.parseDouble(inputs.get(0));
		else
			sum = new BigInteger(inputs.get(0));
		
		for (int i = 1; i < inputs.size(); i++)
		{
			switch(ops.get(i-1).charAt(0))
			{
			case '+':
				sum = decimalMode ? (Double)sum + Double.parseDouble(inputs.get(i)) : new BigInteger(inputs.get(i)).add((BigInteger)sum);
				break;
			case '-':
				tempSum = decimalMode ? null : (BigInteger)sum;
				sum = decimalMode ? (Double)sum - Double.parseDouble(inputs.get(i)) : tempSum.subtract(new BigInteger(inputs.get(i))); 
				break;
			case '*':
				sum = decimalMode ? (Double)sum * Double.parseDouble(inputs.get(i)) : new BigInteger(inputs.get(i)).multiply((BigInteger)sum);
				break;
			case '/':
				tempSum = decimalMode ? null : (BigInteger)sum;
				
				if (!inputs.get(i).equals("0"))
				{
					//Checks integer division to see if the outcome should be represented as a decimal
					if (!decimalMode)
					{
						BigInteger[] divisorAndRemainder = tempSum.divideAndRemainder(new BigInteger(inputs.get(i)));
						
						if (!divisorAndRemainder[1].equals(BigInteger.ZERO))
						{
							decimalMode = true;
							sum = tempSum.doubleValue();
						}
					}
					sum = decimalMode ? (Double)sum / Double.parseDouble(inputs.get(i)) : tempSum.divide(new BigInteger(inputs.get(i)));
				}
				else
					sum = "Cannot divide by zero";
				break;
			default:
				break;
			}
			
		}
		
		lastOp = ops.get(ops.size() - 2);
		lastInput = inputs.get(ops.size() - 1);
		
		currentInput = sum.toString();
		return currentInput;
	}
	
	
	public String displayInputs()
	{
		if(inputs.size() > 0)
		{
			String inputDisplay = "";
			for(int i = 0; i < inputs.size(); i++)
			{
				inputDisplay += inputs.get(i) + ops.get(i);
			}
			return inputDisplay;
		}
		return "";	
	}

}
