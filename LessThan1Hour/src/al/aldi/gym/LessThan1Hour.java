package al.aldi.gym;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by http://aldi.al on 08.05.2015.
 * <p>
 * These are solution to a set of simple programming problems taken from
 * https://blog.svpino.com/2015/05/07/five-programming-problems-every-software-engineer-should-be-able-to-solve-in-less-than-1-hour
 * that should be able to be solved within an hour by those who call themselves
 * "Programmers"
 */
public class LessThan1Hour {

    public static final int SUM_VAL = 100;

    /* ************************************************************* */
    /*  Problem 1                                                    */
    /* ************************************************************* */

    public static int sumWithForLoop(int[] list) {
        int sum = 0;
        for (int i = 0; i < list.length; i++) {
            sum += list[i];
        }
        return sum;
    }

    public static int sumWithWhileLoop(int[] list) {
        int sum = 0;
        int i = 0;
        while (i < list.length) {
            sum += list[i];
            i++;
        }
        return sum;
    }

    public static int sumWithRecursionLoop(int[] list) {
        int sum = 0;
        if (list != null && list.length > 0) {
            sum = list[0];
            int[] newArray = Arrays.copyOfRange(list, 1, list.length);
            sum += sumWithRecursionLoop(newArray);
        }
        return sum;
    }

    // Bonus if you know lambdas
    public static int sumWithLambdas(int[] list) {
        List<Integer> s = Arrays.stream(list).boxed().collect(Collectors.toList());
        return s.stream().mapToInt(i -> i).sum();
    }

    /* ************************************************************* */
    /*  Problem 2                                                    */
    /* ************************************************************* */

    public static String[] combineLists(String[] list1, String[] list2) {
        ArrayList<String> strList = new ArrayList<>();

        int maxArrLen = Math.max(list1.length, list2.length);
        for (int i = 0; i < maxArrLen; i++) {
            if (i < list1.length) strList.add(list1[i]);
            if (i < list2.length) strList.add(list2[i]);
        }

        return strList.toArray(new String[strList.size()]);
    }

    /* ************************************************************* */
    /*  Problem 3                                                    */
    /* ************************************************************* */

    public static Integer[] getFibonaciAt(int position) {
        ArrayList<Integer> result = new ArrayList<>();
        result.add(0);
        result.add(1);
        for (int i = 0; i < position - 2; i++) {
            result.add(result.get(i) + result.get(i + 1));
        }

        return result.toArray(new Integer[result.size()]);
    }

    /* ************************************************************* */
    /*  Problem 4                                                    */
    /* ************************************************************* */

    public static int largestNumCombined(int[] list) {
        List<Integer> resultList = new ArrayList<>();

        List<Integer> intList = Arrays.stream(list).boxed().collect(Collectors.toList());

        Collections.sort(intList, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                int minLen = Math.min(o1.toString().length(), o2.toString().length());
                int r = o1.toString().substring(0, minLen).compareTo(o2.toString().substring(0, minLen));
                if (r == 0 && o1.toString().length() != o2.toString().length()) {
                    if (o1.toString().length() > o2.toString().length()) {
                        Integer i1 = Integer.valueOf(o1.toString().substring(minLen, o1.toString().length()));
                        Integer i2 = Integer.valueOf(o2.toString().substring(0, minLen));
                        r = this.compare(i1, i2);
                    } else {
                        Integer i1 = Integer.valueOf(o1.toString().substring(0, minLen));
                        Integer i2 = Integer.valueOf(o2.toString().substring(minLen, o2.toString().length()));
                        r = this.compare(i1, i2);
                    }
                }
                return r;
            }
        });
        Collections.reverse(intList);

        String strResutl = intList.stream().map(o -> o.toString()).collect(Collectors.joining(""));

        return Integer.valueOf(strResutl);
    }

    /* ************************************************************* */
    /*  Problem 5                                                    */
    /* ************************************************************* */

    /**
     * Adds num to every sub list in first place.
     *
     * @param num
     * @param list
     */
    private static void addFistToLists(int num, LinkedList<LinkedList<Integer>> list) {
        for (LinkedList<Integer> l : list) {
            l.addFirst(num);
            int sum = l.stream().mapToInt(s -> s).sum();
            if (sum == SUM_VAL)
                System.out.println(l.stream().map(s -> s.toString()).collect(Collectors.joining(", ")));
        }
    }

    public static LinkedList<LinkedList<Integer>> allSumsOfX(int expected, LinkedList<Integer> nums) {
        LinkedList<LinkedList<Integer>> passed = new LinkedList<>();
        nums = new LinkedList<Integer>(nums);
        if (nums.isEmpty()) return passed;

        int a = nums.pop();
        if (!nums.isEmpty()) {
            LinkedList<LinkedList<Integer>> l1 = allSumsOfX(expected - a, nums);
            if (!l1.isEmpty()) {
                addFistToLists(a, l1);
                passed.addAll(l1);
            }

            int b = nums.getFirst();
            nums.set(0, -b);
            LinkedList<LinkedList<Integer>> l2 = allSumsOfX(expected - a, nums);
            if (!l2.isEmpty()) {
                addFistToLists(a, l2);
                passed.addAll(l2);
            }
            nums.set(0, Integer.valueOf(a + "" + b));
            LinkedList<LinkedList<Integer>> l3 = allSumsOfX(expected, nums);
            if (!l3.isEmpty()) {
                passed.addAll(l3);
            }
        } else {
            if (a == expected) {
                LinkedList<Integer> la = new LinkedList<>();
                la.add(a);
                passed.add(la);
            }
        }


        return passed;
    }

    public static void main(String[] args) {
        // Problem 1 tests
        int res = 0;
        final int RESULT = 15;
        int[] sum = {1, 2, 3, 4, 5};

        res = sumWithForLoop(sum);
        System.out.println("Expected: " + RESULT + " Result: " + res + " Passed: " + (RESULT == res));

        res = sumWithWhileLoop(sum);
        System.out.println("Expected: " + RESULT + " Result: " + res + " Passed: " + (RESULT == res));

        res = sumWithRecursionLoop(sum);
        System.out.println("Expected: " + RESULT + " Result: " + res + " Passed: " + (RESULT == res));

        res = sumWithLambdas(sum);
        System.out.println("Expected: " + RESULT + " Result: " + res + " Passed: " + (RESULT == res));
        System.out.println("/* ************************************************************* */");

        // Problem 2 tests
        String[] strRes = null;
        String[] list1 = {"1", "2", "3"};
        String[] list2 = {"a", "b", "c"};
        final String[] STR_RESULT = {"1", "a", "2", "b", "3", "c"};
        strRes = combineLists(list1, list2);
        String l1str = Arrays.asList(STR_RESULT).stream().collect(Collectors.joining(", "));
        String l2str = Arrays.asList(strRes).stream().collect(Collectors.joining(", "));
        boolean cmpResult = Arrays.equals(strRes, STR_RESULT);
        System.out.println("Expected: {" + l1str + "} Result: {" + l2str + "} Passed: " + cmpResult);
        System.out.println("/* ************************************************************* */");

        // Problem 3 tests
        Integer[] fibsAt = null;
        final Integer[] FIB_RESULT = {0, 1, 1, 2, 3, 5, 8, 13, 21, 34};
        fibsAt = getFibonaciAt(10);
        String fibAtStr = Arrays.asList(fibsAt).stream().map(s -> s.toString()).collect(Collectors.joining(", "));
        String fibRes = Arrays.asList(FIB_RESULT).stream().map(s -> s.toString()).collect(Collectors.joining(", "));
        boolean cmpFibResult = Arrays.equals(fibsAt, FIB_RESULT);
        System.out.println("Expected: {" + fibRes + "} Result: {" + fibAtStr + "} Passed: " + cmpFibResult);
        System.out.println("/* ************************************************************* */");

        // Problem 4 tests
        int combiRes = 0;
        final int[] INPUT_VALS = {1, 9, 54, 11, 6};
        final int combiExpected = 9654111;
        combiRes = largestNumCombined(INPUT_VALS);
        System.out.println("Expected: " + combiExpected + " Result: " + combiRes + " Passed: " + (combiRes == combiExpected));
        System.out.println("/* ************************************************************* */");


        // Problem 5 tests
        LinkedList<Integer> list = new LinkedList<>();
        list.addAll(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        allSumsOfX(SUM_VAL, list);
    }
}
