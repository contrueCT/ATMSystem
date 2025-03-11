package util;

import java.math.BigDecimal;
import java.util.Scanner;

/**
 * @author confff
 */
public class InputValidator {
    static Scanner sc = new Scanner(System.in);

    public static boolean isValidYes() {
        while(true){
            String input = sc.nextLine();
            if(input.length() != 1){
                System.out.println("请输入Y/N");
                continue;
            }
            if(input.charAt(0)!='Y'&&input.charAt(0)!='N'){
                System.out.println("请输入Y/N");
                continue;
            }
            return input.charAt(0) == 'Y';
        }
    }

    public static BigDecimal isValidBigDecimal() {

        while (true) {
            try {
                String input = sc.nextLine().trim().replace(",", "");
                if(input.isEmpty()){
                    System.out.println("输入格式错误，请重新输入");
                    continue;
                }
                BigDecimal money = new BigDecimal(input);
                if(money.compareTo(BigDecimal.ZERO)>0){
                    return money;
                }else{
                    System.out.println("请输入正数");
                    continue;
                }
            } catch(NumberFormatException e)  {
                System.out.println("输入格式错误，请重新输入");
                SystemLogger.logError(e.getMessage(),e);
            }
        }
    }

    public static boolean isValidBigDecimal(BigDecimal money) {
        if(money!=null&&money.compareTo(BigDecimal.ZERO)>0){
            return true;
        }
        return false;
    }



    public static String isValidCardId() {
        while (true) {
            String input = sc.nextLine().trim().replace(",", "");
            if(input.isEmpty()){
                System.out.println("输入格式错误，请重新输入");
                continue;
            }
            if(input.length()<16||input.length()>19){
                System.out.println("输入格式错误，请重新输入");
                continue;
            }
            return input;

        }
    }

    public static int isValidInt(int max){
        while (true) {
            String input = sc.nextLine().trim().replace(",", "");
            if(input.length()!=1){
                System.out.println("输入格式错误，请重新输入");
                continue;
            }
            int num = Integer.parseInt(input);
            if(num>max||num<0){
                System.out.println("输入格式错误，请输入0到"+max+"的数");
                continue;
            }
            return num;
        }
    }
}
