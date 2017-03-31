import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedHashSet;
import java.util.Set;

public class DigitalAssetTestClass {
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String s;
		Integer[][] incomingPuzzleElements = new Integer[9][9]; //the value defaults to 0
		boolean isValidPuzzle = true;
		while ((s = in.readLine()) != null) {
		  String[] incomingNumbersInArray = s.split(","); 
		  isValidPuzzle = isValidPuzzle(incomingNumbersInArray, incomingPuzzleElements);
		 
		  if(isValidPuzzle)
			  System.out.println("True");
		  else {
			  System.out.println("False");
		  }
		}
		in.close();
	}
	
	private static boolean isRowsDoesNotHaveDuplicateElements(Integer[][] incomingPuzzleElements) {
		  boolean rowDoesNotHaveDuplicate = true;
		  for (int row = 0; row <=8 ; row++) {
			  //using set as it filter the duplicate items can use array too
			  Set<Integer> elementSet = new LinkedHashSet<Integer>();
			  
			  for (int column = 0 ; column <=8 ; column++) {
				  Integer elementInPuzzle = incomingPuzzleElements[row][column];
				  elementSet.add(elementInPuzzle);
			  }
			  
			  /**
			   * right now we are making soduku so already checked the length
			   * can be more generic instead of checking length as 9
			   */
			  if(elementSet.size() != 9){
				  rowDoesNotHaveDuplicate = false;
				  break;
			  }
		  }
		  return rowDoesNotHaveDuplicate;
	}
	
	private static boolean isColumnsHaveDuplicateElements(Integer[][] incomingPuzzleElements) {
		
		 boolean columnDoesNotHaveDuplicate = true;
		  for (int column = 0; column <=8 ; column++) {
			  //using set as it filter the duplicate items can use array too
			  Set<Integer> elementSet = new LinkedHashSet<Integer>();
			  
			  for (int row = 0 ; row <=8 ; row++) {
				  Integer elementInPuzzle = incomingPuzzleElements[row][column];
				  elementSet.add(elementInPuzzle);
			  }
			  
			  /**
			   * right now we are making soduku so already checked the length
			   * can be more generic instead of checking length as 9
			   */
			  if(elementSet.size() != 9){
				  columnDoesNotHaveDuplicate = false;
				  break;
			  }
		  }
		  return columnDoesNotHaveDuplicate;
	}
	
    //converts string into two dimensional array of integers
	private static Integer[][] makeTwoDimensionalInput(String actualStringForWork, Integer[][] incomingPuzzleElements) {
		  for (int row = 0; row <=8 ; row++) {
			  for (int column = 0 ; column <=8 ; column++) {
				  //we are not handling whether the character is string or number, we can do that too here
				  incomingPuzzleElements[row][column] = Integer.parseInt(String.valueOf(actualStringForWork.charAt(row*9 + column)));
			  }
		  }
    	return incomingPuzzleElements;
    }
	
	//convert comma separated input to working string
	private static String convertIntoWorkingString(String[] incomingString) {
		StringBuffer stringBufferForIncomingItems = new StringBuffer();
		String actualStringForWork;
		 for(String item : incomingString) {
			  stringBufferForIncomingItems.append(item);
		  }
		 actualStringForWork = stringBufferForIncomingItems.toString();
		 return  actualStringForWork;
	}
	
	// function to check each 3 x 3 sub-grid contains no duplicate digit
	private static boolean checkIfSquareHasDulicates(Integer[][] incomingPuzzleElements, Integer subSquareSize){
		
		 // Check that the subsquares contain no duplicate values
	    for (int baseRow = 0; baseRow < incomingPuzzleElements.length; baseRow += subSquareSize)
	    {
	      for (int baseCol = 0; baseCol < incomingPuzzleElements.length; baseCol += subSquareSize)
	      {
	        if (!checkSquare(incomingPuzzleElements, baseRow, baseCol, subSquareSize))
	        {
	          return false;
	        }
	      }
	    }
	    return true;
	}
	
	private static boolean checkSquare(Integer[][] incomingPuzzleElements, int baseRow, int baseCol, int subSquareSize)
	  {
	    boolean[] found = new boolean[incomingPuzzleElements.length];
	    for (int row = baseRow; row < (baseRow + subSquareSize); ++row)
	    {
	      for (int col = baseCol; col < (baseCol + subSquareSize); ++col)
	      {
	        // set found[x - 1] to be true if we find x in the row
	        int index = incomingPuzzleElements[row][col] - 1;
	        if (!found[index])
	        {
	          found[index] = true;
	        }
	        else
	        {
	          // found it twice, so return false
	          return false;
	        }
	      }
	    }
	    
	    return true;
	  }
	
	private static boolean isValidPuzzle(String[] incomingNumbersInArray, Integer[][] incomingPuzzleElements) {
		boolean isValidPuzzle = true;
		
		if(incomingNumbersInArray.length != 81) {
			return false;
		}
		
		String actualStringForWork= convertIntoWorkingString(incomingNumbersInArray);
		incomingPuzzleElements =  makeTwoDimensionalInput(actualStringForWork, incomingPuzzleElements);
		
		//checking for duplicate element in rows
		isValidPuzzle = isRowsDoesNotHaveDuplicateElements(incomingPuzzleElements);
		if(!isValidPuzzle) {
			 return isValidPuzzle;
		}
		
		//checking for duplicate element in columns
		isValidPuzzle = isColumnsHaveDuplicateElements(incomingPuzzleElements);
		if(!isValidPuzzle) {
			 return isValidPuzzle;
		}
		
		//check if 3 x 3 won't contains duplicates
		isValidPuzzle = checkIfSquareHasDulicates(incomingPuzzleElements, 3);
		if(!isValidPuzzle) {
			return isValidPuzzle;
		}
		
		return isValidPuzzle;
	}
	
	/*
	  *  function to add the digits of number after doubling the second digit
	  *  If after doubling the second digit it is more than 9 than add the digits 
	  *  like double of 8 is 8 * 2 = 16 and 1+ 6 = 7
	  *   
	  */
	 private static Integer checkSum(Integer inputNumber,Boolean multiplyByTwo){
		 int sum = 0;
		 int input = inputNumber; 
		 int secondDigitCounter = 0;
		 while (input != 0)
		 {
			 secondDigitCounter ++;
		     int lastdigit = input % 10;
		     if(secondDigitCounter == 2) {
		    	 //multiply by 2 if actual digit in number
		    	 if(multiplyByTwo)
		    		 lastdigit = lastdigit *2;
		    	 if(lastdigit > 9) {
		    		 /*
		    		  * don't multiply by 2 if digits are multiple of 2 
		    		  * and greater than 9
		    		  */
		    		 lastdigit = checkSum(lastdigit, false);
		    	 }
		    	 secondDigitCounter = 0;
		     }
			 sum += lastdigit;
			 input /= 10;
		 }
		 return sum;
	 }
}

