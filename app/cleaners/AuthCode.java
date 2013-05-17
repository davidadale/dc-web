package cleaners;


public class AuthCode{

	public String value;

	public AuthCode(){
		this.value = generateAuthCode();
	}

    //Java code to generate 8 or 9 digit alphanumeric code
    private String generateAuthCode() {
        System.out.println("Entering generateAuthCode()");
        //getting the current time in nanoseconds
        long decimalNumber=System.nanoTime();
        System.out.println("current time in nanoseconds: "+decimalNumber);
 
        //To convert time stamp to alphanumeric code.
        //We need to convert base10(decimal) to base36
        String strBaseDigits = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        String strTempVal = "";
        int mod = 0;
        // String concat is costly, instead we could have use stringbuffer or stringbuilder
        //but here it wont make much difference.
        while(decimalNumber!= 0){
            mod=(int) (decimalNumber % 62);
            strTempVal=strBaseDigits.substring(mod,mod+1)+strTempVal;
            decimalNumber=decimalNumber/62;
        }
        System.out.println("alphanumeric code generated from TimeStamp : "+strTempVal);
        return strTempVal;
 
	}	
}