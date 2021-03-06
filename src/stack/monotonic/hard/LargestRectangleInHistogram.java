package stack.monotonic.hard;

import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.assertEquals;

/**
 * Created by Yiyun
 *
 * 84. Largest Rectangle in Histogram
 */
public class LargestRectangleInHistogram {

    // depends on shorter bar: increase stack
    public int largestRectangleArea_review20200910(int[] heights) {
        if (heights == null || heights.length == 0) return 0;

        LinkedList<Integer> stack = new LinkedList<>();
        int bottom = -1;
        stack.push(bottom);
        int max = heights[0];

        for (int i = 0; i < heights.length; i++) {
            int cur = heights[i];

            while (stack.peek() != bottom && heights[stack.peek()] > cur) {
                max = Math.max(max, heights[stack.pop()] * (i-stack.peek()-1));
            }
            stack.push(i);
        }

        while (stack.peek() != -1) {
            max = Math.max(max, heights[stack.pop()] * (heights.length-stack.peek()-1));
        }

        return max;
    }

    // monotonically increasing stack
    // we can't use decreasing stack bcs the shorter bar is popped thus it can't be used to evaluate further
    // the rectangle's area depends on the lowest bar, while 11. Container With Most Water is not the case
    public int largestRectangleArea(int[] heights) {
        if (heights == null || heights.length == 0)
            return 0;

        LinkedList<Integer> stack = new LinkedList<>();
        int bottom = -1;        // because it's the number left next to index of zero
        stack.push(bottom);     // WARN: DON'T USE .add(), which means addLast!!!
        int max = heights[0];   // don't set 0

        for (int i = 0; i < heights.length; i++) {
            int cur = heights[i];
            while (stack.peek() != bottom && heights[stack.peek()] > cur) {
                int peekIdx = stack.pop();
                // Watch out the "stack.peek()": we aren't comparing with peedIdx, think carefully....
                int area = heights[peekIdx] * (i-stack.peek()-1);
                max = Math.max(max, area);
            }
            stack.push(i);
        }

        // WARN!!! Don't forget about this case...
        while (stack.peek() != -1)
            max = Math.max(max, heights[stack.pop()] * (heights.length - stack.peek() -1));

        return max;
    }

    @Test
    public void case1() {
        assertEquals(10, largestRectangleArea(new int[]{2,1,5,6,2,3}));
    }

    @Test
    public void case2() {
        assertEquals(2, largestRectangleArea(new int[]{1, 1}));
    }

    @Test
    public void case3() {
        assertEquals(10, largestRectangleArea(new int[]{2,1,5,6,2,3}));
    }

}
