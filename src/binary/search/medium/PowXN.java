package binary.search.medium;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Yiyun On 2020/7/5 16:59
 *
 * 50. Pow(x, n)
 */
public class PowXN {

    public double myPow_review20200729(double x, int n) {
        // WARN: simply negate a positive int n might lead to overflow error, thus we need to define a long for n.
        // i.e. DO NOT write as: if (n < 0) return myPow(x, -n).
        long N = n;
        if (N < 0) {
            x = 1/x;
            N = -N;
        }

        double currX = x;
        double res = 1;
        while (N > 0) {
            long lsb = N & 1;    // least significant bit
            N >>= 1;
            if (lsb == 1) res *= currX;
            currX *= currX;
        }
        return res;
    }

    // n could be negative
    // eg.x^10 = x^{(1010)_2}
    //         = x^{2+2^3} = x^{2^3}*x^2.
    // X(n) = x^{2^n} = (x^{2^{n-1}})^2
    //      = (X(n-1))^2
    // represent N as (b1b2...bi)_2, then i is at most O(logN), thus time is O(logN)
    public double myPow(double x, int n) {
        long N = n;
        if (N < 0) {
            x = 1/x;
            N = -N;
        }

        double res = 1;
        double curr = x;
        for (long i = N; i > 0; i/=2) {
            if (i % 2 == 1)
                res *= curr;
            // x^{2^n} = ( x^{2^{n-1}} )^2
            curr *= curr;
        }

        return res;
    }

    @Test
    public void case1() {
        assertEquals(1024, myPow_review20200729(2.0, 10), 0.00001);
    }

}
