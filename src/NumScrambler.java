import java.util.Random;

public class NumScrambler {

    public int[] Scramble(int qty){
        int[] nums = new int[qty];

        Random rand = new Random();

        //ASCII Usable range (Excluding non-character returns) need to set
        //range for "netInt" calls.  Max - min +1 = int range = 127 - (33 +1)
        int range = 127;

        for(int i =0; i < qty; i++){
            int rand_int1 = rand.nextInt(range);
            nums[i] = rand_int1;
        }
        
        return nums;

    }


  }


