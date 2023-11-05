import java.util.ArrayList;

public class BICS {

    static void sort(ArrayList<Integer> array){
        int sl = 0;
        int sr = array.size() - 1;
        while (sl < sr){
            swap(array, sr, (sl + (sr - sl)/2));
            
            if (array.get(sl) == array.get(sr)){
                if (isEqual(array, sl, sr) == - 1){
                    return;
                }
            }

            if (array.get(sl) > array.get(sr)) swap(array, sl, sr); 

            int i = sl + 1;
            if ((sr - sl) >= 100){
                /* THINGS TO CONSIDER :
                 * The Paper's implementation seems to have logic flaw. The for loop below 
                 * theoritically should bound the sort trip to happens sqrt(N) times.
                 * but the code only does it for half of it, that is roughly the first 0 until sqrt(N) sort trip.
                 * what we actually want is perhaps current SL until SL + sqrt(N).
                 * this guarantees us that every sort trip is done efficiently
                 * therefore, the code should be
                 * 
                 * for (;i < sl+Math.sqrt(sr - sl); i++)
                 * 
                 * already tested on random dataset, it improves running time around 10 times faster.
                 */
                for (;i < Math.sqrt(sr - sl); i++){
                    if (array.get(sr) < array.get(i)) swap(array, sr, i);
                    else if(array.get(sl) > array.get(i)) swap(array, sl, i);
                }
            }
            
            
            int lc = array.get(sl);
            int rc = array.get(sr);
            while (i < sr){
                int curr = array.get(i);
                if (curr >= rc){
                    array.set(i, array.get(sr - 1));
                    insertRight(array, curr, sr, array.size() - 1);
                    sr--;
                }else if (curr <= lc){
                    array.set(i, array.get(sl + 1));
                    insertLeft(array, curr, sl, 0);
                    sl++;
                    i++;
                }else{
                    i++;
                }
                
                
            }

            sl++;
            sr--;
        }
    }

    static void insertLeft(ArrayList<Integer> array, int curr, int sl, int left){
    int j = sl;
    while (j >= left && curr < array.get(j)){
        array.set(j+1, array.get(j));
        j--;
    }

    array.set(j+1, curr);
    }


    static void insertRight(ArrayList<Integer> array, int curr, int sr, int right){
        int j = sr;
        while (j <= right && curr > array.get(j)){
            array.set(j - 1,array.get(j));
            j++;
        }

        array.set(j - 1, curr);
    }


    static int isEqual(ArrayList<Integer> array, int sl, int sr){
        for (int j = sl + 1 ; j < sr; j++){
            if (array.get(j) != array.get(sl)){
                swap(array, j , sl);
                
                return j;
            }
        }
        return -1;
    }


    static void swap(ArrayList<Integer> arr, int i, int j){
        int temp = arr.get(i);
        arr.set(i, arr.get(j));
        arr.set(j ,temp);
        return;
    }
    
}
