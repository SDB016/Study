public class Lec {
    public static void main(String[] args) {
        String month;
        if ( (month == "1월") || (month == "3월") || (month == "5월") || (month == "7월") || (month == "8월") || (month == "10월") || (month == "12월") ){

            System.out.println("31일인 달입니다");
        }
        else if (month == "2월"){
            System.out.println("28일인 달입니다");
        }
        else if ( (month == "4월") || (month == "6월") || (month == "9월") || (month == "11월")) {
            System.out.println("30일인 달입니다");
        }
        else{
            System.out.println("입력이 올바르지 않습니다.");
        }
    }
}





