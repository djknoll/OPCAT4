package util;

/**
 * Created by IntelliJ IDEA.
 * User: raanan
 * Date: 7/17/11
 * Time: 2:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class MathUtils {

    public static double round2(double num) {
        double result = num * 100;
        result = Math.round(result);
        result = result / 100;
        return result;
    }
}
