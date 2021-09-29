import java.util.Random;

public class CryptKey {

        Random rand = new Random();
        NumScrambler n = new NumScrambler();
        UserInput u = new UserInput();
        String encryptFinal;  // Variable to hold encrypted string
        String decryptFinal;  // Variable to hold decrypted string


        //Run random number generator
        int[] randNumbers = n.Scramble(10000);


        // Method to create a key, set the length based on the size of the input string.  Return int[] holding key digits
        public int[] keyBuilder(String input){
            //Array to hold key
            int[] key = new int[input.length()];

            //Create Key
            for (int i =0; i <input.length(); i++){
                int nextDigit = rand.nextInt(10000);

                int x = randNumbers[nextDigit];

                key[i] = x;
            }

            return key;
        }


        // Method to encrypt the user input and return an encrypted string
        public String encrypt(String input, int[] key){

            //Array to hold encrypted text
            char[]cryptText = new char[input.length()];

            //Convert userText to ASCII
            int[] userWord = u.asciiConvert(input);

            // Encrypt!
            for(int y =0; y <input.length(); y++){
                int letter;

                letter = userWord[y]+ key[y];
                cryptText[y] = (char) letter;

                //Old code to be used to encrypt without extended ASCII
           /*     if(userWord[y] + key[y] <= 127){
                    letter = userWord[y]+ key[y];
                    cryptText[y] = (char) letter;
                }
                else {
                    letter = Math.round((userWord[y]+ key[y]) / 2);
                    cryptText[y] = (char) letter;
                }*/

            }

            return encryptFinal = new String(cryptText);
        }


        public String decrypt(String text, int[] key){

            //Array to hold unencrypted text
            char[] clearTxt = new char[text.length()];

            //Convert userText to ASCII
            int[] userWord = u.asciiConvert(text);

            //Decrypt!
            for(int y = 0; y < text.length(); y++){
                int letter;

                letter = userWord[y] - key[y];
                clearTxt[y] = (char) letter;

                //Old code to be used to encrypt without extended ASCII
                /*   if(((userWord[y] - key[y]) + userWord[y]) >= 127){
                    letter = userWord[y] - key[y];
                    clearTxt[y] = (char) letter;
                }
                else{

                    letter = (userWord[y] - key[y]) + userWord[y];
                    clearTxt[y] = (char) letter;
                }*/

            }

            return decryptFinal = new String(clearTxt);

        }



}
