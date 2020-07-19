import java.time.ZonedDateTime;

/**
 * 断言中时间格式生成器
 * application.yml中gateway断言中的After，Before和Between中的时间格式
 */
public class T2 {
    public static void main(String[] args) {
        ZonedDateTime zbj = ZonedDateTime.now();
        System.out.println(zbj);
    }
}
