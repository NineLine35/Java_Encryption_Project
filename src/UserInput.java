// Class to hold all user input conversions
public class UserInput {

    //Method to read a string, breakdown each char to ASCII and return a
    //corresponding array containing each ASCII value.
    public int[] asciiConvert(String input){
        int[] output = new int[input.length()];

        for (int i =0; i< input.length(); i++) {

            char c = input.charAt(i);
            int ascii = (int) c;
            output[i]= ascii;
            
        }
        return output;
    }

}
