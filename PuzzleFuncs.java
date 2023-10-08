import java.util.ArrayList;
import java.util.Random;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class PuzzleFuncs {

	public static String operationsGame(ArrayList<Integer> nums, int wantedRes) {
		String res = "";
		int numsSize = nums.size();
		
		double closestOne = 10000;
		String closestString = "";
		
		int operationsDone = 0;
		int fastestSolution = 10000;
		String fastestRes = "";
		double generatedRes = 0;
		
		for (int i = 0; i < 10000; i++) {
			
			operationsDone = 0;
			
			ArrayList<Integer> numsAvailable = new ArrayList<Integer>();
			for (int i2 : nums) numsAvailable.add(i2);
			res = "";
			generatedRes = 0;
			boolean addSubDone = false;
			
			Random rand = new Random();
			int firstNumIndex = rand.nextInt(numsAvailable.size());
			generatedRes = numsAvailable.get(firstNumIndex);
			res += (int)generatedRes;
			numsAvailable.remove(firstNumIndex);
			
			
			for (int j = 0; j < numsSize && numsAvailable.size() > 0; j++ ) {
				//res = "";
				int op = rand.nextInt(4);
				int numIndex = rand.nextInt(numsAvailable.size());
				int numVal = numsAvailable.get(numIndex);
				
				operationsDone++;
				
				//numsAvailable.remove(numIndex);
				
				if (op == 0 && numVal != 0) {//+
					addSubDone = true;
					res += "+" + (int)numsAvailable.get(numIndex);
					generatedRes += numsAvailable.get(numIndex);
					
				}
				else if (op == 1 && numVal != 0) {//-
					addSubDone = true;
					res += "-" + (int)numsAvailable.get(numIndex);
					generatedRes -= numsAvailable.get(numIndex);
					
				}
				else if (op == 2) {//*
					if (addSubDone) {
						res += "+" + (int)numsAvailable.get(numIndex);
						generatedRes += numsAvailable.get(numIndex);
					}
					else 
					{
						res += "*" + (int)numsAvailable.get(numIndex);
						generatedRes *= numsAvailable.get(numIndex);
					}
					
				}
				else if (op == 3) {// /
					if (addSubDone || numVal == 0) 
					{
						res += "-" + (int)numsAvailable.get(numIndex);
						generatedRes -= numsAvailable.get(numIndex);
					}
					else {
						res += "/" + (int)numsAvailable.get(numIndex);
						generatedRes /= numsAvailable.get(numIndex);
					}
					
				}	
				numsAvailable.remove(numIndex);
				if (generatedRes == wantedRes)
				{
					return "TRUE SOLUTION: " + res;
				}
				else {
					double thisDiff = wantedRes - generatedRes;
					if (thisDiff < 0 )
						thisDiff = thisDiff*-1;
					if (thisDiff < closestOne) {
						closestOne = thisDiff;
						closestString = res + " = " + generatedRes;
					}
				}
			}
		}
		res = "closest solution: " + closestString;
		return res;
	}
	public static ArrayList<Integer> generateProb() {
		
		ArrayList<Integer> res = new ArrayList<Integer>();
		Random rnd = new Random();		
		int nums = 0;
		
		
		
		while (nums < 4)
			nums = rnd.nextInt(7);
		for (int i = 0; i < nums; i++)
		{
			int term = rnd.nextInt(20);
			res.add(term);
		}
		String probString = "";
		for (int i = 0; i < nums-1; i++)
		{
			probString += res.get(i).toString();
			int operationInt = rnd.nextInt(4);
			if (operationInt == 0)
				probString += "+";
			if (operationInt == 1)
				probString += "-";
			if (operationInt == 2)
				probString += "*";
			if (operationInt == 3)
				probString += "/";
		}
		
		probString += res.get(res.size()-1);
		
		
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("js");
		try {
			Object result = engine.eval(probString);
			String result2 = "" + result;
			try {
				int resInt = Integer.parseInt(result2);
				if (resInt < 1)
					return null;
				res.add(resInt);
				return res;
			}
			catch (Exception ex) {
				return null;
			}
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return res;
	}
}
