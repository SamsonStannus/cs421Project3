import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class getdate{
	public static void main(String[] args){
		DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String theDate = dateformat.format(date);
		System.out.println(dateformat.format(date));
		System.out.println(theDate);

	}
}