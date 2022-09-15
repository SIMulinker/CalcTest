import java.io.*;
//import java.util.*;


public class Main {

	static int num1, num2;
	static String math;
	static STATus Modes;

	public static void main(String[] args)	{
		BufferedReader d = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("mini calc started ...");
		try {
			System.out.print("Введите выражение для расчета. Поддерживаются арабские (1 - 10) \r\nи римские (I - X) цифры, операции +,-,*,/ \r\n->");
			String sIn = d.readLine();
			Modes = checker(sIn);
			switch (Modes) {
				case IDLE -> sIn = "Пустое выражение, результата нет";
				case NOMATH -> sIn = "Не указано действие";
				case NOPARSE -> sIn = "Цифры из разных систем, расчет невозможен";
				case ARAB -> sIn = String.valueOf(calculate());
				case ROMAN -> sIn= IntegerConverter.intToRoman(calculate());
				case ERROR -> sIn = "Выражение не распознано";
			}
			System.out.println( sIn );
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}


	static int calculate() throws Exception {
		switch (math) {
			case "+" -> num1 += num2;
			case "-" -> num1 -= num2;
			case "/" -> num1 /= num2;
			case "*" -> num1 *= num2;
			default -> throw new Exception("Недопустимая операция " + math);
		}
		if (Modes == STATus.ROMAN && num1 <=0 ) throw new Exception("throws Exception //т.к. в римской системе нет отрицательных чисел");
		else return num1;
	}

	static STATus checker(String Inline) throws Exception {
		STATus op1, op2;
		String[] opm = Inline.split( " ");

		if ( opm.length == 3  ) {
			try {
				if( opm[0].matches("[IVX]+") ) { op1 = STATus.ROMAN; num1 = rom_arab( opm[ 0 ]); }
				else if ( opm[0].matches("[0-9]+") ) {
					op1 = STATus.ARAB; num1 = Integer.parseInt(opm[0]);
					if(num1 < 1 || num1 > 10 ) {throw new Exception(" Должно быть число от 1 до 10 включительно" );}
				}
				else {throw new Exception(" Должно быть число " );}

				if( opm[2].matches("[IVX]+") ) { op2 = STATus.ROMAN; num2 = rom_arab( opm[ 2 ]); }
				else if ( opm[0].matches("[0-9]+") ) {
					op2 = STATus.ARAB; num2 = Integer.parseInt(opm[2]);
					if(num2 < 1 || num2 > 10 ) {throw new Exception(" Должно быть число от 1 до 10 включительно" );}
				}
				else {throw new Exception(" Должно быть число римское или арабское" );}

				if( opm[ 1 ].matches("[/*+-]") )
				{
					if (op1 == op2) { Modes = op1; math = opm[1]; }
					else { Modes= STATus.NOPARSE ; return Modes;}
				} else { return STATus.NOMATH ; }
				//if ( ) throw new Exception("Неверное количество данных для операции " );
			} catch (Exception e){ throw new Exception("system parse error");}
		} else {  throw new Exception("throws Exception //т.к. строка не является математической операцией" );}
		return Modes;
	}


	static int rom_arab(String test) throws Exception {
		int result = 0;
		int[] decimal = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
		String[] roman = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

// Test string, the number 2897
		if( test.isEmpty() ) { test = "MMDCCCXCVII"; }

		try {
			for (int i = 0 ; i < decimal.length ; i++) { //YES, this is not good... be worked
				if (test.isEmpty()) break;
				while (test.indexOf(roman[ i ]) == 0) {
					result += decimal[ i ];
					test = test.substring(roman[ i ].length());
				}
			}
		}catch (Exception e){ throw new Exception("Symbol parse error");}
//System.out.println(result);
		return result ;
	}


}