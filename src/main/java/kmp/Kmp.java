package kmp;

/**
 * <pre>
 * Might refer to https://github.com/torvalds/linux/blob/b3a3a9c441e2c8f6b6760de9331023a7906a4ac6/lib/ts_kmp.c
 * </pre>
 * 
 * @author ralph
 * 
 */
public class Kmp {

    private final String pattern;

    private final int[] kmpNext;

    public Kmp(String pattern) {
        this.pattern = pattern;
        this.kmpNext = new int[this.pattern.length()];
        buildKmpBits();
    }

    private void buildKmpBits() {
        /**
         * <pre>
         * 1. kmpNext[0] = -1
         * 2. Given kmpNext[i] = j, means  Pattern[0, ..., j-1] = Pattern[i - j, .... i - 1]
         *    Then loop:
         *      given this j
         *      if Pattern[i] == Pattern[j], then kmpNext[i + 1] = j + 1; break loop
         *      else
         *          j = kmpNext[j]
         * </pre>
         * 
         * Sample:
         * <pre>
         * a    b   a   b   a
         * -1   0   0   1   2
         * </pre>
         */
        kmpNext[0] = -1;
        for (int i = 1; i < kmpNext.length; i++) {
            // decide kmpBits[i]
            int j = i - 1;
            int val = kmpNext[j];
            while (true) {
                if (val < 0) {
                    kmpNext[i] = 0;
                    break;
                }

                if (pattern.charAt(val) == pattern.charAt(i-1)) {
                    kmpNext[i] = val + 1;
                    break;
                } else {
                    val = kmpNext[val];
                }
            }
        }
        // TODO
        for (int i : kmpNext) {
            System.out.print(i + ",");
        }
    }

    public int indexIn(String target) {
        int j = 0;
        int i = 0;
        for (; i < target.length(); i++) {
            while (true) {
                if (target.charAt(i) == pattern.charAt(j)) {
                    j++;
                    break;
                } else {
                    j = kmpNext[j];
                }
                if (j < 0) {
                    j = 0;
                    break;
                }
            }

            if (j == pattern.length()) {
                return i - (j - 1);
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        Kmp kmp = new Kmp("ababa");
        int i = kmp.indexIn("abadfasdfasdfasdfbcababa");
        System.out.println(" index at : " + i);
        assert i == 19;
        
        i = kmp.indexIn("ababb");
        System.out.println(" index at : " + i);
        assert i == -1;
    }

}
