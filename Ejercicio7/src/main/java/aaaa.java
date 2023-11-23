import java.util.Random;

public class aaaa {

    public static void main(String[] args) {
        Random rnd=new Random();
        int aleatorio=rnd.nextInt();
        System.out.println(aleatorio);

        float ale=rnd.nextFloat();
        System.out.println(ale);
        double ale2=rnd.nextDouble();
        System.out.println(ale2);
        long ale3=rnd.nextLong();
        System.out.println(ale3);
        boolean ale4=rnd.nextBoolean();
        System.out.println(ale4);
    }

}