package common.util;

import java.util.HashMap;
import java.util.Map;

public class MonthDict {

	public static enum Month {
		JAN("Jan", "01"), FEB("Feb", "02"), MAR("Mar", "03"), APR("Apr", "04"), MAY("May", "05"), JUN("Jun", "06"),
		JUL("Jul", "07"), AUG("Aug", "08"), SEP("Sep", "09"), OCT("Oct", "10"), NOV("Nov", "11"), DEC("Dec", "12");

		private String name;
		private String value;

		Month(String name, String value) {
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public String getValue() {
			return value;
		}
	}

	public static Map<String, String> dict = new HashMap<String, String>() {
		{
			put(Month.JAN.getValue(), Month.JAN.getName());
			put(Month.FEB.getValue(), Month.FEB.getName());
			put(Month.MAR.getValue(), Month.MAR.getName());
			put(Month.APR.getValue(), Month.APR.getName());
			put(Month.MAY.getValue(), Month.MAY.getName());
			put(Month.JUN.getValue(), Month.JUN.getName());
			put(Month.JUL.getValue(), Month.JUL.getName());
			put(Month.AUG.getValue(), Month.AUG.getName());
			put(Month.SEP.getValue(), Month.SEP.getName());
			put(Month.OCT.getValue(), Month.OCT.getName());
			put(Month.NOV.getValue(), Month.NOV.getName());
			put(Month.DEC.getValue(), Month.DEC.getName());
		}
	};
}
