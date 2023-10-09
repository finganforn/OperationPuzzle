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
			
			if (generatedRes == wantedRes)
				return res;
			
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
					//if (operationsDone < fastestSolution) {
						return "TRUE SOLUTION: " + res;
					//}
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
		//if (fastestRes.length() > 0)
		//	return "TRUE SOLUTION: "+ fastestRes;
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
	private static String operationsGameOld(ArrayList<Integer> nums, int res) {
		ArrayList<Integer> ops = new ArrayList<Integer>();
		int realPossibilites = (int) Math.pow(4, nums.size()-1);
		int possibilities = (int) Math.pow(10, nums.size()-1);
		for (int i = 0; i < possibilities; i++) {
			String s = Integer.toString(i);
			boolean validString = true;
			for (int j = 0; j < s.length(); j++) {
				char cx = s.charAt(j);
				if (charToInt(cx) > 3)
					validString = false;
			}
			if (validString)
				ops.add(i);
		}
		//System.out.println("possible solutions " + realPossibilites + " generated: " + ops.size());
		ArrayList<String> opsS = new ArrayList<String>();
		for (int i = 0; i < ops.size(); i++) {
			String s = ops.get(i).toString();
			while (s.length() != nums.size()-1)
				s = "0" + s;
			s = s.replace('0', '+');
			s = s.replace('1', '-');
			s = s.replace('2', '*');
			s = s.replace('3', '/');
			opsS.add(s);
		}
		
		ArrayList<String> opsS2 = new ArrayList<String>();
		for (int i = 0; i < opsS.size(); i++) {
			String r = "";
			for (int j = 0; j < opsS.get(0).length(); j++) {
				r += nums.get(j).toString();
				r += opsS.get(i).charAt(j);
			}
			int lastNum = nums.get(nums.size()-1);
			String lastNumS = Integer.toString(lastNum);
			r += lastNumS;
			opsS2.add(r);
		}
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("js");
		for (int i = 0; i < opsS2.size(); i++) {
			String s = opsS2.get(i);
			
			
			//ScriptEngineManager manager = new ScriptEngineManager();
			//ScriptEngine engine = manager.getEngineByName("js");
			try {
				Object result = engine.eval(s);
				String resMaybe = "" + result;
				try {
				int resM = Integer.parseInt(resMaybe);
				if (resM == res)
					{
						//System.out.println(s + " = " + resM);
						return s;
					}
				}
				catch (NumberFormatException ex) {
					
				}
			} catch (ScriptException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
		return "none";
	}
	private static int charToInt(char c ) {
		if (c == '0')
			return 0;
		if (c == '1')
			return 1;
		if (c == '2')
			return 2;
		if (c == '3')
			return 3;
		if (c == '4')
			return 4;
		if (c == '5')
			return 5;
		if (c == '6')
			return 6;
		if (c == '7')
			return 7;
		if (c == '8')
			return 8;
		if (c == '9')
			return 9;
		
		return -1;
	}
}
