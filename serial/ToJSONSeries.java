package serial;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * Tool to convert data to JSON data fit for consumption by the Rickshaw package.
 * This tool is currently only targeted at converting data output from my Arduino Hygrometer,
 * so not very generic. Just needed a convenient way to transform the data.
 *
 * Example:
 * <pre>
 * {@code
 *[
 *	{
 *		"color": "blue",
 *		"name": "New York",
 *		"data": [ { "x": 0, "y": 40 }, { "x": 1, "y": 49 }, { "x": 2, "y": 38 }, { "x": 3, "y": 30 }, { "x": 4, "y": 32"} ]
 *	}, {
 *		"name": "London",
 *		"data": [ { "x": 0, "y": 19 }, { "x": 1, "y": 22 }, { "x": 2, "y": 29 }, { "x": 3, "y": 20 }, { "x": 4, "y": 14 } ]
 *	}, {
 *		"name": "Tokyo",
 *		"data": [ { "x": 0, "y": 8 }, { "x": 1, "y": 12 }, { "x": 2, "y": 15 }, { "x": 3, "y": 11 }, { "x": 4, "y": 10 } ]
 *	}
 *]
 * }
 * </pre>
 */
public class ToJSONSeries {


    public static void main ( String[] args ) {
	PrintStream out = System.out;

	out.println("[");
	out.println("]");
    }
}