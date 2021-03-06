package graph.medium;

import org.junit.Test;

import java.util.*;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * Created by Yiyun On 2020/7/15 22:41
 *
 * 210. Course Schedule II
 */
public class CourseScheduleII {

    public int[] findOrder_review20200730(int numCourses, int[][] prerequisites) {
        return new Helper_20200730(numCourses, prerequisites).res;
    }

    private static class Helper_20200730 {
        Map<Integer, List<Integer>> adjList = new HashMap<>();
        boolean cyclic;
        Map<Integer, T> types = new HashMap<>();
        LinkedList<Integer> resStack = new LinkedList<>();
        int[] res;

        // given parameters
        int numCourses;
        int[][] preqs;

        Helper_20200730(int numCourses, int[][] prerequisites) {
            this.numCourses = numCourses;
            this.preqs = prerequisites;

            init();

            for (int i = 0; i < numCourses; i++) {
                if (cyclic) break;
                if (types.get(i) == T.UNSEEN) dfs(i);
            }

            if (cyclic) res = new int[0];
            else {
                this.res = new int[numCourses];
                int i = 0;
                while (!resStack.isEmpty()) res[i++] = resStack.pop();
            }
        }

        private void init() {
            for (int[] preq : preqs) {
                List<Integer> values = adjList.getOrDefault(preq[1], new ArrayList<>());
                values.add(preq[0]);
                adjList.put(preq[1], values);
            }

            for (int i = 0; i < numCourses; i++) {
                adjList.putIfAbsent(i, Collections.emptyList());
                types.put(i, T.UNSEEN);
            }
        }

        private void dfs(Integer node) {
            if (types.get(node) != T.UNSEEN || cyclic) return;

            types.put(node, T.PROCESSING);

            List<Integer> children = adjList.get(node);
            for (Integer chd : children) {
                if (types.get(chd) == T.PROCESSING) {
                    this.cyclic = true;
                    break;
                }
                dfs(chd);
            }

            resStack.push(node);
            types.put(node, T.DONE);
        }

        private enum T {
            UNSEEN,
            DONE,
            PROCESSING      // all processing nodes are in a same dfs route, we use it for detecting cycle
        }

    }







    // DFS: Time O(N), Space O(N)
    // The following codes are mostly learned from LC Solution
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        return new DFSHelper(numCourses, prerequisites).findOrder();
    }

    private enum NODE_T {
        UNSEEN, PROCESSING, DONE
    }

    private static class DFSHelper {
        int[][] prerequisites;
        int numCourses;
        Map<Integer, List<Integer>> adjList = new HashMap<>();  // pre -> nextList
        boolean cyclic;                                         // cyclic means dead end
        Map<Integer, NODE_T> nodeTypes = new HashMap<>();
        LinkedList<Integer> topologicalOrder = new LinkedList<>();


        private DFSHelper(int numCourses, int[][] prerequisites) {
            this.numCourses = numCourses;
            this.prerequisites = prerequisites;
            for (int[] pre : prerequisites) {
                // init adjacent list
                List<Integer> val = adjList.getOrDefault(pre[1], new ArrayList<>());
                val.add(pre[0]);
                adjList.put(pre[1], val);
            }

            for (int i = 0; i < numCourses; i++) {
                nodeTypes.put(i, NODE_T.UNSEEN);
            }
        }

        private void dfs(Integer currNode) {
            if (cyclic) return;

            nodeTypes.put(currNode, NODE_T.PROCESSING);
            for (Integer child : adjList.getOrDefault(currNode, Collections.emptyList())) {
                NODE_T childType = nodeTypes.get(child);
                if (childType == NODE_T.PROCESSING)
                    cyclic = true;
                else if (childType == NODE_T.UNSEEN)
                    dfs(child);
            }

            nodeTypes.put(currNode, NODE_T.DONE);
            this.topologicalOrder.push(currNode);
        }

        private int[] findOrder() {
            for (int i = 0; i < numCourses; i++) {
                if (nodeTypes.get(i) == NODE_T.UNSEEN)
                    dfs(i);
            }

            if (cyclic) return new int[0];
            int[] res = new int[numCourses];
            int i = 0;
            while (!topologicalOrder.isEmpty())
                res[i++] = topologicalOrder.pop();
            return res;
        }

    }

    @Test
    public void case1() {
        assertEquals("[0, 1]", Arrays.toString(findOrder_review20200730(2, new int[][]{{1, 0}})));

    }

    @Test
    public void case2() {
        assertThat(Arrays.toString(findOrder_review20200730(4, new int[][]{{1, 0}, {2, 0}, {3, 1}, {3, 2}})),
                anyOf(is("[0, 1, 2, 3]"), is("[0, 2, 1, 3]"))
        );
    }

    @Test
    public void case3() {
        assertEquals("[]", Arrays.toString(findOrder_review20200730(2, new int[][]{{1, 0}, {0, 1}})));
    }

}
