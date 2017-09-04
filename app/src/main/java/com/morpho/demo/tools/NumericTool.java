/**
 * 
 */
package com.morpho.demo.tools;

/**
 * @author Alex
 *
 */
public class NumericTool {

	public static boolean isStringNunnable(String val){
        if(val == null)
            return true;
        else if(val.equals(""))
            return true;

        return false;
    }

	public static boolean isInteger(String val){
		try{
			Integer.parseInt(val);
		}catch(NumberFormatException e){
			return false;
		}

		return true;
	}
	
	public static boolean isDouble(String val){
		try{
			Double.parseDouble(val);
		}catch(NumberFormatException e){
			return false;
		}

		return true;
	}
	
	public static boolean isLong(String val){
		try{
			Long.parseLong(val);
		}catch(NumberFormatException e){
			return false;
		}

		return true;
	}
	
	
	public static boolean isFloat(String val){
		try{
			Float.parseFloat(val);
		}catch(NumberFormatException e){
			return false;
		}

		return true;
	}

    public static String parserZeroNumbers(String val){
        String value = "0";
        if(isInteger(val)){
            int par = Integer.parseInt(val);
            if(par < 9){
                value = value+val;
            }else
                value = val;
        }

        return value;
    }
}
