import java.util.Random;

// Class that holds the random number generation for the key file
public class NumScrambler {

    public int[] Scramble(int qty){
        int[] nums = new int[qty];

        Random rand = new Random();

        /* ASCII Usable range (Excluding non-character returns) need to set
         range for "netInt" calls.  Had originally set the range to 127+, however
         ran into all sorts of issues with the extended ASCII not being able to be read
         on some systems and in some encrypted text.  This does not fully correct the problem,
         but mitigates it until I can come up with a better fix. */

        int range = 8;

        for(int i =0; i < qty; i++){
            int rand_int1 = rand.nextInt(range);
            nums[i] = rand_int1;
        }
        
        return nums;

    }


  }


