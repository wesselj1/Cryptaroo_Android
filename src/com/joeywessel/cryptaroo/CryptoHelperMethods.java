package com.joeywessel.cryptaroo;

// Counter class is used for helper in getBigraphs, getTrigraphs, and getNgraphs
class Counter
{
    public int length = 0;
    
    public String sArray[] = new String[10000];
    public int iArray[] = new int[10000];
    public int pArray[][] = new int[10000][100];

    public void add(String s, int pos)
    {
        sArray[length] = s;
        iArray[length] = 1;
        pArray[length][0] = pos;
        length++;
    }
    public boolean contains(String s)
    {
        for (int x = 0; x < length; x++)
        {
            if (sArray[x].equals(s)) return true;
        }
        return false;
    }
    public void inc(int pos)
    {
        iArray[length - 1]++;
        pArray[length - 1][iArray[length - 1]-1] = pos;
    }
}

public class CryptoHelperMethods {
	
	private static char[][] tabulaRecta = new char[][]{
		    {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'},
		    {'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'A'},
		    {'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'A', 'B'},
		    {'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'A', 'B', 'C'},
		    {'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'A', 'B', 'C', 'D'},
		    {'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'A', 'B', 'C', 'D', 'E'},
		    {'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'A', 'B', 'C', 'D', 'E', 'F'},
		    {'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'A', 'B', 'C', 'D', 'E', 'F', 'G'},
		    {'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'},
		    {'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I'},
		    {'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'},
		    {'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K'},
		    {'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L'},
		    {'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M'},
		    {'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N'},
		    {'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O'},
		    {'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P'},
		    {'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q'},
		    {'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R'},
		    {'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S'},
		    {'U', 'V', 'W', 'X', 'Y', 'Z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T'},
		    {'V', 'W', 'X', 'Y', 'Z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U'},
		    {'W', 'X', 'Y', 'Z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V'},
		    {'X', 'Y', 'Z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W'},
		    {'Y', 'Z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X'},
		    {'Z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y'}};

	// Frequency Count
	public static String frequencyCount(String inputText)
	{
        String inputString = removePunctuationSpecialCharacters(inputText);
        inputString = inputString.toUpperCase();
        String inputStringWithWhiteSpace = removePunctuationSpecialCharactersExceptSpace(inputText);
        inputStringWithWhiteSpace = inputStringWithWhiteSpace.toUpperCase();
        int array[] = new int[26];
        String sArray[];
        String resultString = "";
        
        for (int x = 0; x < inputString.length(); x++)
        {
            switch (inputString.charAt(x))
            {
                case 'A': array[0]++; break;
                case 'B': array[1]++; break;
                case 'C': array[2]++; break;
                case 'D': array[3]++; break;
                case 'E': array[4]++; break;
                case 'F': array[5]++; break;
                case 'G': array[6]++; break;
                case 'H': array[7]++; break;
                case 'I': array[8]++; break;
                case 'J': array[9]++; break;
                case 'K': array[10]++; break;
                case 'L': array[11]++; break;
                case 'M': array[12]++; break;
                case 'N': array[13]++; break;
                case 'O': array[14]++; break;
                case 'P': array[15]++; break;
                case 'Q': array[16]++; break;
                case 'R': array[17]++; break;
                case 'S': array[18]++; break;
                case 'T': array[19]++; break;
                case 'U': array[20]++; break;
                case 'V': array[21]++; break;
                case 'W': array[22]++; break;
                case 'X': array[23]++; break;
                case 'Y': array[24]++; break;
                case 'Z': array[25]++; break;               
            }
        }
        
        for (int x = 0; x < 26; x++)
        {
        	resultString += (char)('A'+x);
        	resultString += " = ";
        	resultString += array[x];
        	if( array[x] > 9 )
        		resultString += " = ";
        	else
        		resultString += "  = ";
            for (int y = 0; y<array[x]; y++)
            	resultString += "I";
            if (x!=25)
            	resultString += "\n";
        }
        
       resultString += "\n";

        sArray = inputStringWithWhiteSpace.split(" ");
        
        resultString += "\nHere are all the 1 letter words\n";
        for (int x = 0; x < sArray.length; x++)
        {
            if (sArray[x].length() == 1) {resultString += sArray[x]; resultString += "\n";}
        }
        
        resultString += "\nHere are all the 2 letter words\n";
        for (int x = 0; x < sArray.length; x++)
        {
            if (sArray[x].length() == 2) {resultString += sArray[x]; resultString += "\n";}
        }
        
        resultString += "\nHere are all the 3 letter words\n";
        for (int x = 0; x < sArray.length; x++)
        {
            if (sArray[x].length() == 3) {resultString += sArray[x]; resultString += "\n";}
        }
  
        resultString += "\nHere are all the initial letters\n";
        for (int x = 0; x < sArray.length; x++)
        {
            if (sArray[x].length()>0)
            	resultString += sArray[x].charAt(0);
        }
        resultString += "\n";
        
        resultString += "\nHere are all the final letters\n";
        for (int x = 0; x < sArray.length; x++)
        {
            if (sArray[x].length()>0)
            	resultString += sArray[x].charAt(sArray[x].length()-1);
        }
        resultString += "\n";
        
        resultString += "\nHere are all the doubled letters\n";
        char c = inputString.charAt(0);
        for (int x = 1; x < inputString.length(); x++)
        {
            if (inputString.charAt(x) == c) 
            {
            	resultString += new Character(c).toString();
            	resultString += new Character(c).toString();
            	resultString += "\n";
            }
            c = inputString.charAt(x);
        }
        
        resultString += "\nFrequencies for English\n";
        
        resultString += "a = 07 = IIIIIII\n";
        resultString += "b = 01 = I\n";
        resultString += "c = 03 = III\n";
        resultString += "d = 04 = IIII\n";
        resultString += "e = 13 = IIIIIIIIIIIII\n";
        resultString += "f = 03 = III\n";
        resultString += "g = 02 = II\n";
        resultString += "h = 04 = IIII\n";
        resultString += "i = 07 = IIIIIII\n";
        resultString += "j =\n";
        resultString += "k =\n";
        resultString += "l = 04 = IIII\n";
        resultString += "m = 03 = III\n";
        resultString += "n = 08 = IIIIIIII\n";
        resultString += "o = 07 = IIIIIII\n";
        resultString += "p = 03 = III\n";
        resultString += "q =\n";
        resultString += "r = 08 = IIIIIIII\n";
        resultString += "s = 06 = IIIIII\n";
        resultString += "t = 09 = IIIIIIIII\n";
        resultString += "u = 03 = III\n";
        resultString += "v = 01 = I\n";
        resultString += "w = 02 = II\n";
        resultString += "x =\n";
        resultString += "y = 02 = II\n";
        resultString += "z =";
        
        return resultString;
	}
	
	// Run the Alphabet
	public static String runTheAlphabet(String inputText)
	{
        String inputString = inputText;
        inputString = inputString.toLowerCase();
        String resultString = "";
        
        for (int x = 0; x < 26; x++)
        {
            for (int y = 0; y < inputString.length(); y++)
            {
                if (inputString.charAt(y) >='a' && inputString.charAt(y) <= 'z')
                {
                	resultString += new Character((char) ((inputString.charAt(y)+x)%('z'+1)+((inputString.charAt(y)+x)/('z'+1))*'a'));
                    //jTextArea3.append(new Character((char)((inputString.charAt(y)+x)%('z'+1)+((int)((inputString.charAt(y)+x)/('z'+1))*'a'))).toString());
                }
                else
                	resultString += " ";
            }
            resultString += "\n\n";
        }
        
        return resultString;
	}
	
	// Get Bigraphs
	public static String getBigraphs(String inputText)
	{
        String inputString = formatString(inputText);
        String resultString = "";
        
        String tempString;
        Counter counter = new Counter();
        
        for (int x = 0; x < inputString.length() - 1; x++)
        {
            tempString = inputString.substring(x, x+2);
            if (inputString.indexOf(tempString, x)>=0)
            {
                if (!counter.contains(tempString))
                {
                    counter.add(tempString, x);
                    for (int y = x; y >= 0;)
                    {
                        if ((y = inputString.indexOf(tempString, y+2)) >= 0) counter.inc(y);
                    }
                }
            }
        }
        
        for (int x = 0; x < counter.length; x++)
        {
        	resultString += counter.sArray[x];
        	resultString += " = ";
        	resultString += counter.iArray[x];
        	resultString += " at positions ";
            for (int y = 0; y < counter.iArray[x]; y++)
            {
                resultString += counter.pArray[x][y];
                if (y!=counter.iArray[x] - 1)
                   resultString += ",";
            }
            resultString += "\n";
        }
        
        if( resultString.equalsIgnoreCase("\n") || resultString.equalsIgnoreCase("") ) {
        	resultString = "No BiGraphs";
        }
        
        return resultString;
	}
	
	// Get Trigraphs
	public static String getTrigraphs(String inputText)
	{
        String inputString = formatString(inputText);
        String resultString = "";

        String tempString;
        Counter counter = new Counter();
        
        for (int x = 0; x < inputString.length() - 2; x++)
        {
            tempString = inputString.substring(x, x+3);
            if (inputString.indexOf(tempString, x)>=0)
            {
                if (!counter.contains(tempString))
                {
                    counter.add(tempString, x);
                    for (int y = x; y >= 0;)
                    {
                        if ((y = inputString.indexOf(tempString, y+3)) >= 0) counter.inc(y);
                    }
                }
            }
        }
        
        for (int x = 0; x < counter.length; x++)
        {
        	resultString += counter.sArray[x];
        	resultString += " = ";
        	resultString += counter.iArray[x];
        	resultString += " at positions ";
            for (int y = 0; y < counter.iArray[x]; y++)
            {
                resultString += counter.pArray[x][y];
                if (y!=counter.iArray[x] - 1)
                    resultString += ",";
            }
            resultString += "\n";
        }
        
        if( resultString.equalsIgnoreCase("\n") || resultString.equalsIgnoreCase("") ) {
        	resultString = "No TriGraphs";
        }
        
        return resultString;
	}
	
	// NGraphs
	public static String getNgraphs(String inputText, int lenghtOfNgraphs)
	{
        String inputString = formatString(inputText);
        int js = lenghtOfNgraphs;
        String resultString = "";

        String tempString;
        Counter counter = new Counter();
        
        for (int x = 0; x < inputString.length() - (js - 1); x++)
        {
            tempString = inputString.substring(x, x+js);
            if (inputString.indexOf(tempString, x)>=0)
            {
                if (!counter.contains(tempString))
                {
                    counter.add(tempString, x);
                    for (int y = x; y >= 0;)
                    {
                        if ((y = inputString.indexOf(tempString, y+js)) >= 0) counter.inc(y);
                    }
                }
            }
        }
        
        for (int x = 0; x < counter.length; x++)
        {
        	resultString += counter.sArray[x];
        	resultString += " = ";
        	resultString += counter.iArray[x];
        	resultString += " at positions ";
            for (int y = 0; y < counter.iArray[x]; y++)
            {
            	resultString += counter.pArray[x][y];
                if (y!=counter.iArray[x] - 1)
                	resultString += ",";
            }
            resultString += "\n";
        }  
        
        if( resultString.equalsIgnoreCase("\n") || resultString.equalsIgnoreCase("") ) {
        	resultString = "No graphs of size " + lenghtOfNgraphs;
        }
        
        return resultString;
	}
	
	// Affine Known Plaintext Attack
	public static String affineKnownPlaintextAttack(String inputText, String keyword, boolean shiftFirst)
	{
        String inputString = formatString(inputText);
        String searchString = formatString(keyword);
        String resultString = "The following are all the possible combinations of \"" + searchString + "\"\n"
        		+ "which are contained in the cyphertext message and the affine\n"
        		+ "keys used to encrypt them.\n\n";
        
        if (!shiftFirst)
        {
            resultString += searchCeasar(1, inputString, searchString);
            resultString += searchCeasar(3, inputString, searchString);
            resultString += searchCeasar(5, inputString, searchString);
            resultString += searchCeasar(7, inputString, searchString);
            resultString += searchCeasar(9, inputString, searchString);
            resultString += searchCeasar(11, inputString, searchString);
            resultString += searchCeasar(15, inputString, searchString);
            resultString += searchCeasar(17, inputString, searchString);
            resultString += searchCeasar(19, inputString, searchString);
            resultString += searchCeasar(21, inputString, searchString);
            resultString += searchCeasar(23, inputString, searchString);
            resultString += searchCeasar(25, inputString, searchString);
        }
        else
        {
        	resultString += searchCeasarReverse(1, inputString, searchString);
        	resultString += searchCeasarReverse(3, inputString, searchString);
        	resultString += searchCeasarReverse(5, inputString, searchString);
        	resultString += searchCeasarReverse(7, inputString, searchString);
        	resultString += searchCeasarReverse(9, inputString, searchString);
        	resultString += searchCeasarReverse(11, inputString, searchString);
        	resultString += searchCeasarReverse(15, inputString, searchString);
        	resultString += searchCeasarReverse(17, inputString, searchString);
        	resultString += searchCeasarReverse(19, inputString, searchString);
        	resultString += searchCeasarReverse(21, inputString, searchString);
        	resultString += searchCeasarReverse(23, inputString, searchString);
        	resultString += searchCeasarReverse(25, inputString, searchString);            
        }
        
        return resultString;
	}
	
    private static String searchCeasar(int m, String inputString, String searchString)
    {
        int counter;
        String mSearchString;
        char[] charArray;
        String resultString = "";
        
        for (int x = 0; x < 26; x++)
        {
            charArray = searchString.toCharArray(); 
            for (int y = 0; y < charArray.length; y++) 
            {
                charArray[y] = (char)(charArray[y] - 64);
                charArray[y] = (char)((charArray[y] * m)%26);
                charArray[y] = (char)((charArray[y] + x)%26);
                if (charArray[y] == 0)
                	charArray[y] = (char)26;
                charArray[y] = (char)(charArray[y] + 64);                    
            }
            mSearchString = new String(charArray);
            counter = 0;
            
            if (inputString.indexOf(mSearchString, 0)==0)
            	counter++;
            for (int y = 0; y >= 0;)
            {
                if ((y = inputString.indexOf(mSearchString, y+1)) >= 0)
                	counter++;
            }  
            if (counter > 0)
            	resultString += mSearchString + " appears " + counter + " times with a multiplicative key = " 
            			+ m + " and an additive key = " + x + "\n";
        }
        
        return resultString;
    }

    private static String searchCeasarReverse(int m, String inputString, String searchString)
    {
        int counter;
        String mSearchString;
        char[] charArray;
        String resultString = "";
        
        for (int x = 0; x < 26; x++)
        {
            charArray = searchString.toCharArray(); 
            for (int y = 0; y < charArray.length; y++) 
            {
                charArray[y] = (char)(charArray[y] - 64);
                charArray[y] = (char)((charArray[y] + x)%26);
                charArray[y] = (char)((charArray[y] * m)%26);
                if (charArray[y] == 0) charArray[y] = (char)26;
                charArray[y] = (char)(charArray[y] + 64);                    
            }
            mSearchString = new String(charArray); 
            counter = 0;
            
            if (inputString.indexOf(mSearchString, 0)==0) counter++;
            for (int y = 0; y >= 0;)
            {
                if ((y = inputString.indexOf(mSearchString, y+1)) >= 0) counter++;
            }                
            if (counter > 0)
            	resultString += mSearchString + " appears " + counter + " times with a multiplicative key = " 
            			+ m + " and an additive key = " + x + "\n";
        }
        
        return resultString;
    }
	
	// Affine Encipher
	public static String affineEncipher(String inputText, int multiplier, int additive)
	{
        int m = multiplier;
        int a = additive;
        String inputString = formatString(inputText);
        String resultString = "";
        
        char[] charArray = inputString.toCharArray();
        
        for (int x = 0; x < inputString.length(); x++)
        {
            charArray[x] = (char)(charArray[x] - 64);
            charArray[x] = (char)((charArray[x] * m)%26);
            charArray[x] = (char)((charArray[x] + a)%26);
            if (charArray[x] == 0) charArray[x] = (char)26;
            charArray[x] = (char)(charArray[x] + 64);
        }
        
        for (int x = 0; x < charArray.length; x++)
        {
            resultString += charArray[x];
            if ((x+1)%5 == 0 && (x+1)!= charArray.length)
            	resultString += ' ';
        }
        
        return resultString;
	}
	
	// Affine Decipher
	public static String affineDecipher(String inputText, int multiplier, int additive)
	{
        int m = 0;
        int a = additive;
        String inputString = formatString(inputText);
        String resultString = "";
        
        char[] charArray = inputString.toCharArray();

        if (multiplier == 1) m = 1;
        else if (multiplier == 3) m = 9;
        else if (multiplier == 5) m = 21;
        else if (multiplier == 7) m = 15;
        else if (multiplier == 9) m = 3;
        else if (multiplier == 11) m = 19;
        else if (multiplier == 15) m = 7;
        else if (multiplier == 17) m = 23;
        else if (multiplier == 19) m = 11;
        else if (multiplier == 21) m = 5;
        else if (multiplier == 23) m = 17;
        else if (multiplier == 25) m = 25;
        
        for (int x = 0; x < inputString.length(); x++)
        {
            charArray[x] = (char)(charArray[x] - 64);
            charArray[x] = (char)((charArray[x] + (26-a))%26);
            charArray[x] = (char)((charArray[x] * m)%26);
            if (charArray[x] == 0) charArray[x] = (char)26;
            charArray[x] = (char)(charArray[x] + 64);
        }
        
        for (int x = 0; x < charArray.length; x++)
        {
        	resultString += charArray[x];
            if ((x+1)%5 == 0 && (x+1)!= charArray.length)
            	resultString += ' ';
        }
        
        return resultString;
	}
	
	// Split Off Alphabets
	public static String splitOffAlphabets(String inputText, int keywordSize)
	{
        String inputString = formatString(inputText);
        int wordLength = keywordSize;
        String sArray[] = new String[wordLength];
        String resultString = "";
        
        if( wordLength != 0 )
        {
	        for (int x = 0; x < wordLength; x++)
	            sArray[x] = "";
	        
	        for (int x = 0; x < inputString.length(); x++)
	            sArray[x%wordLength] = sArray[x%wordLength] + inputString.charAt(x);
	        
	        for (int x = 0; x < sArray.length; x++)
	        	resultString += sArray[x] + "\n\n";
        }
        else
        	resultString = "Wordlength must be greater than 0";
        
        return resultString;
	}
	
	// Poly/Mono Calculator
	public static String polyMonoCalculator(String inputText, int keywordSize)
	{
        String inputString = formatString(inputText);
        String tempString;
        double array[] = new double[256];
        double friedman = 0;
        int spinnerValue = keywordSize;
        String resultString = ".065 = Monoalphabetic, .038 means polyalphabetic.\n\n";
        
        for (int z = 0; z < spinnerValue; z++)
        {
            tempString = "";
            friedman = 0;
            
            for (int x = 0; x < inputString.length(); x++)
            {
                if ((x-z%spinnerValue)%spinnerValue==0) tempString = tempString + inputString.charAt(x);
            }
        
            for (int x = 0; x < 256; x++)
                array[x] = 0;
        
            for (int x = 0; x < tempString.length(); x++)
            {
                for (char y = 'A'; y <= 'Z'; y++)
                {
                    if (tempString.charAt(x) == y) array[y]++;
                }
            }
            for (char x = 'A'; x <= 'Z'; x++)
            {
                friedman += array[x]/tempString.length() * ((array[x]-1)/(tempString.length()-1));
            }
            
            resultString += friedman + "\n";
        }
        
        return resultString;
	}
	
	// Vigenere Encipher
	public static String vigenereEncipher(String inputText, String keyword)
	{
        String inputString = formatString(inputText);
        String outputString = "";
        String s1 = formatString(keyword);
        String resultString = "";
        
        for (int x = 0; x < inputString.length(); x++)
        	outputString += tabulaRecta[s1.charAt(x%s1.length())-'A'][inputString.charAt(x)-'A'];
        
        for (int x = 0; x < outputString.length(); x++)
        {
        	resultString += outputString.charAt(x);
            if ((x+1)%5 == 0 && (x+1)!= outputString.length())
            	resultString += ' ';
        }
        
        return resultString;
	}
	
	// Vigenere Decipher
	public static String vigenereDecipher(String inputText, String keyword)
	{
        String inputString = formatString(inputText);
        String outputString = "";
        String s1 = formatString(keyword);
        String resultString = "";

        for (int x = 0; x < inputString.length(); x++)
        {
            for (int y = 0; y < 26; y++) 
            {
            	if( tabulaRecta[s1.charAt(x%s1.length())-'A'][y] == inputString.charAt(x) )
                    outputString = outputString + tabulaRecta[0][y];
            }
        }
        
        for (int x = 0; x < outputString.length(); x++)
        {
        	resultString += outputString.charAt(x);
            if ((x+1)%5 == 0 && (x+1)!= outputString.length())
            	resultString += ' ';
        }
        
        return resultString;
	}
	
	// Autokey Cyphertext Attack
	public static String autoKeyCyphertextAttack(String inputText, int keywordLength)
	{
		String inputString = formatString(inputText);
        String outputString = "";
        String s1 = "";
        String resultString = "";
        double array[] = new double[256];
        double friedman = 0;        
        
        for (int x = 0; x < keywordLength; x++)
        {
            s1 = s1 + "A";
        }
        
        s1 = s1 + inputString;

        for (int x = 0; x < inputString.length(); x++)
        {
            for (int y = 0; y < 26; y++) 
            {
            	if( tabulaRecta[s1.charAt(x%s1.length()) -'A'][y] == inputString.charAt(x) ) 
                    outputString = outputString + tabulaRecta[0][y];
            }
        }
        
        for (int x = 0, y = 0; x < outputString.length(); x++, y++)
        {
        	resultString += outputString.charAt(x);
            if ((x+1)%5 == 0 && (x+1)!= outputString.length()) 
            	resultString += ' ';
        }
        
        outputString = outputString.toUpperCase();
        
        for (int x = 0; x < 256; x++)
            array[x] = 0;   
        
        for (int x = 0; x < outputString.length(); x++)
        {
            for (char y = 'A'; y <= 'Z'; y++)
            {
                if (outputString.charAt(x) == y) array[y]++;
            }
        }
        for (char x = 'A'; x <= 'Z'; x++)
        {
            friedman += array[x]/outputString.length() * ((array[x]-1)/(outputString.length()-1));
        }
        
        resultString += "\n\nFriedman value = " + friedman;
        return resultString;
	}
	
	// Autokey Plaintext Attack
 	public static String autoKeyPlainTextAttack(String inputText, int maxKeywordLength, double lowerFriedmanCutoff, double upperFriedmanCutoff)
	{
		/* possibilities for letter 1 of keyword
		 * n key length = 5 fieldman rating 0.50
		 * possibilities for letter 2 of keyword
		 * 0 key length = 5 fieldman rating 0.55
		 */

        String inputString = formatString(inputText);
        String outputString = "";
        String s1 = "";
        String tempString = "";
        String resultString = "";
        double array[] = new double[256];
        double friedman = 0;        
        
        int js = maxKeywordLength;
        double friedman_cutoff_low = lowerFriedmanCutoff;
        double friedman_cutoff_hi = upperFriedmanCutoff;

        int x, z, t, w;
        char y;
        
        for (x = 0; x < js; x++)
        {
            for (y = 'A'; y<='Z'; y++)
            {
                for (z = 1; z <= js; z++)
                {
                    s1 = "";
                    tempString = "";
                    outputString = "";
                    friedman = 0;

                    for ( t = x; t < inputString.length(); t++)
                    {
                        if ((t-x)%z==0) 
                        	tempString += inputString.charAt(t);
                    }                    
                    
                    
                    
                    s1 = "" + y;
                    for (t = 0; t < tempString.length(); t++)
                    {
                        for (w = 0; w < 26; w++) 
                        {
                        	if( tabulaRecta[ (s1.charAt(t%s1.length())) - 'A'][w] == tempString.charAt(t) ) 
                                outputString += tabulaRecta[0][w];
                        }
                        if( outputString.length() > 0 & t < outputString.length() )
                        	s1 += outputString.charAt(t);
                        s1 = s1.toUpperCase();
                    }
                    
                    outputString = outputString.toUpperCase();

                    for (t = 0; t < 256; t++)
                        array[t] = 0;   
        
                    for (t = 0; t < outputString.length(); t++)
                    {
                        for (char c = 'A'; c <= 'Z'; c++)
                        {
                            if (outputString.charAt(t) == c) 
                            	array[c]++;
                        }
                    }
                    
                    for (char c = 'A'; c <= 'Z'; c++)
                        friedman += array[c]/outputString.length() * ((array[c]-1)/(outputString.length()-1));       
                    
                    
                    if(friedman >= friedman_cutoff_low && friedman <= friedman_cutoff_hi)
                    {
                    	resultString += "Possibilities for letter  " + (x+1) + "  of keyword  ";
                    	resultString += "" + y + "  keylength =  " + z + "  friedman =  " + friedman + "\n";
                    }
                }
            }
        }
        
        return resultString;
	}
	
	// Autokey Decipher
	public static String autoKeyDecipher(String inputText, String keyword, boolean plaintextInKeyword)
	{
        String inputString = formatString(inputText);
        String outputString = "";
        String s1 = formatString(keyword);
        String resultString = "";
        char charArray[];
        
        if (!plaintextInKeyword)
        {
            s1 += inputString;
            
            for (int x = 0; x < inputString.length(); x++)
            {
                for (int y = 0; y < 26; y++) 
                {
                	s1.length();
                	if( tabulaRecta[s1.charAt(x%s1.length()) - 'A'][y] == inputString.charAt(x) )
                        outputString += tabulaRecta[0][y];
                }
            }
            
            for (int x = 0; x < outputString.length(); x++)
            {
                resultString += outputString.charAt(x);
                if ((x+1)%5 == 0 && (x+1)!= outputString.length())
                	resultString += ' ';
            }
        }
        else
        {
            for (int x = 0; x < inputString.length(); x++)
            {
                for (int y = 0; y < 26; y++) 
                {
                	if( tabulaRecta[s1.charAt(x%s1.length()) - 'A'][y] == inputString.charAt(x) )
                        outputString += tabulaRecta[0][y];
                }
                s1 += outputString.charAt(x);
                s1 = s1.toUpperCase();
            }
            
            for (int x = 0, y = 0; x < outputString.length(); x++, y++)
            {
                resultString += outputString.charAt(x);
                if ((x+1)%5 == 0 && (x+1)!= outputString.length())
                	resultString += ' ';
            }            
        }
            
        return resultString;
	}
	
	// GCD and Inverse
	public static String[] GCDandInverse(int number, int modulus)
	{
        double y = Math.max((double)number,(double)modulus);
        double z = Math.min((double)number,(double)modulus);
        double w = 0;
        double ans = 0;
        String inverse = "";
        double gcd = 0;
        
   
        // The inverse algorithm used here is not euclid's algo, rather it is one I [Gary Watson] came up with that is not as efficient

        boolean exit = false;
		
        if( y == 0 ^ z == 0 )
            gcd = Math.max(y, z);
        while(!exit)
        {
            w = y%z;
            if (w != 0)
            {
                y = z;
                z = w;
            }
            else
            {
                exit = true;
                ans = z;
            }
        }
        gcd = ans;
        

        if( modulus == 0 )
        	inverse = "Cannot divide by 0";
        else
        {
	        if (ans==1)
	        {
	            y = (double)modulus;
	            z = (double)number;
	
	            for (int x = 1; x <= y; x++)
	            {
	                w = ((-(y*x-1)/z));
	                w = (w + ((int)(-w/y) + 1)*y);
	                if (w-((int)w)==0)
	                {
	                    inverse = "" + w;
	                    break;
	                }
	            }
	        }
	        else
	        	inverse = "No inverse";
        }
        
        // Since inverse may be either parseable as an int or not, try it, if not we need to return the string
        int inverseInt = 0;
        String inverseStr = "";
        try
        {
        	inverseInt = (int)Double.parseDouble(inverse);
        }
        catch (NumberFormatException e)
        {
        	inverseStr = inverse;
        }
        
        // If the inverse result was an integer return the integer, otherwise return it as a string
        if( inverseStr == "" )
        	return new String[]{""+(int)gcd, Integer.toString(inverseInt)};
        return new String[]{""+(int)gcd, inverse};
	}
	
	// Sanitation methods
	// Removes only punctuation and special characters
    private static String removePunctuationSpecialCharactersExceptSpace(String string)
    {
        String inputString = string;
        inputString = inputString.toUpperCase();
        inputString = inputString.replace(',', ' ');
        inputString = inputString.replace('.', ' ');
        inputString = inputString.replace('?', ' ');
        inputString = inputString.replace('\"', ' ');
        inputString = inputString.replace('!', ' ');
        inputString = inputString.replace('@', ' ');
        inputString = inputString.replace('#', ' ');
        inputString = inputString.replace('$', ' ');
        inputString = inputString.replace('%', ' ');
        inputString = inputString.replace('^', ' ');
        inputString = inputString.replace('&', ' ');
        inputString = inputString.replace('*', ' ');
        inputString = inputString.replace('(', ' ');
        inputString = inputString.replace(')', ' ');
        inputString = inputString.replace('-', ' ');
        inputString = inputString.replace('+', ' ');
        inputString = inputString.replace('/', ' ');
        inputString = inputString.replace('\\', ' ');
        inputString = inputString.replace('<', ' ');
        inputString = inputString.replace('>', ' ');
        inputString = inputString.replace('\'', ' ');
        inputString = inputString.replace('~', ' ');
        inputString = inputString.replace('`', ' ');
        inputString = inputString.replace('[', ' ');
        inputString = inputString.replace(']', ' ');
        inputString = inputString.replace('|', ' ');
        inputString = inputString.replace('{', ' ');
        inputString = inputString.replace('}', ' ');
        inputString = inputString.replace('=', ' ');
        inputString = inputString.replace(':', ' ');
        inputString = inputString.replace(';', ' ');
        return inputString;
    }
    
    private static String removePunctuationSpecialCharacters(String string)
    {
        String inputString = string;
        inputString = inputString.toUpperCase();
        inputString = inputString.replace(',', ' ');
        inputString = inputString.replace('.', ' ');
        inputString = inputString.replace('?', ' ');
        inputString = inputString.replace('\"', ' ');
        inputString = inputString.replace('!', ' ');
        inputString = inputString.replace('@', ' ');
        inputString = inputString.replace('#', ' ');
        inputString = inputString.replace('$', ' ');
        inputString = inputString.replace('%', ' ');
        inputString = inputString.replace('^', ' ');
        inputString = inputString.replace('&', ' ');
        inputString = inputString.replace('*', ' ');
        inputString = inputString.replace('(', ' ');
        inputString = inputString.replace(')', ' ');
        inputString = inputString.replace('-', ' ');
        inputString = inputString.replace('+', ' ');
        inputString = inputString.replace('/', ' ');
        inputString = inputString.replace('\\', ' ');
        inputString = inputString.replace('<', ' ');
        inputString = inputString.replace('>', ' ');
        inputString = inputString.replace('\'', ' ');
        inputString = inputString.replace('~', ' ');
        inputString = inputString.replace('`', ' ');
        inputString = inputString.replace('[', ' ');
        inputString = inputString.replace(']', ' ');
        inputString = inputString.replace('|', ' ');
        inputString = inputString.replace('{', ' ');
        inputString = inputString.replace('}', ' ');
        inputString = inputString.replace('=', ' ');
        inputString = inputString.replace(':', ' ');
        inputString = inputString.replace(';', ' ');
        inputString = inputString.replaceAll(" ", "");
        inputString = inputString.replaceAll("\n", "");
        inputString = inputString.replaceAll("\t", "");
        return inputString;
    }
    
    // Removes numbers, special characters, punctuation, and spaces from a string
    private static String formatString(String string)
    {
        String inputString = string;
        inputString = inputString.toUpperCase();
        inputString = inputString.replace(',', ' ');
        inputString = inputString.replace('.', ' ');
        inputString = inputString.replace('?', ' ');
        inputString = inputString.replace('\"', ' ');
        inputString = inputString.replace('!', ' ');
        inputString = inputString.replace('@', ' ');
        inputString = inputString.replace('#', ' ');
        inputString = inputString.replace('$', ' ');
        inputString = inputString.replace('%', ' ');
        inputString = inputString.replace('^', ' ');
        inputString = inputString.replace('&', ' ');
        inputString = inputString.replace('*', ' ');
        inputString = inputString.replace('(', ' ');
        inputString = inputString.replace(')', ' ');
        inputString = inputString.replace('-', ' ');
        inputString = inputString.replace('+', ' ');
        inputString = inputString.replace('/', ' ');
        inputString = inputString.replace('\\', ' ');
        inputString = inputString.replace('<', ' ');
        inputString = inputString.replace('>', ' ');
        inputString = inputString.replace('\'', ' ');
        inputString = inputString.replace('~', ' ');
        inputString = inputString.replace('`', ' ');
        inputString = inputString.replace('[', ' ');
        inputString = inputString.replace(']', ' ');
        inputString = inputString.replace('|', ' ');
        inputString = inputString.replace('{', ' ');
        inputString = inputString.replace('}', ' ');
        inputString = inputString.replace('=', ' ');
        inputString = inputString.replace(':', ' ');
        inputString = inputString.replace(';', ' ');
        inputString = inputString.replace('0', ' ');
        inputString = inputString.replace('1', ' ');
        inputString = inputString.replace('2', ' ');
        inputString = inputString.replace('3', ' ');
        inputString = inputString.replace('4', ' ');
        inputString = inputString.replace('5', ' ');
        inputString = inputString.replace('6', ' ');
        inputString = inputString.replace('7', ' ');
        inputString = inputString.replace('8', ' ');
        inputString = inputString.replace('9', ' ');
        inputString = inputString.replaceAll(" ", "");
        inputString = inputString.replaceAll("\n", "");
        inputString = inputString.replaceAll("\t", "");
        return inputString;
    }
}
