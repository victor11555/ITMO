public class BagTest {
    public static void main(String[] args) {
        Bag bag1 = new Bag();
        Bag bag2 = new Bag();
        Bag bag3 = new Bag();

        String str = "str";
        Integer integer = 321;
        Double l = 13.37;

        bag1.add(str);
        bag1.add(str);
        bag1.add(integer);
        bag1.add(integer);
        bag1.add(integer);
        bag1.add(l);
        bag1.add(l);
        bag1.add(l);
        bag1.add(l);
        bag1.add(l);

        bag2.add(str);
        bag2.add(str);
        bag2.add(l);
        bag2.add(l);
        bag2.add(l);

        bag3.add(str);
        bag3.add(str);
        bag3.add(integer);
        bag3.add(integer);
        bag3.add(integer);
        bag3.add(l);
        bag3.add(l);
        bag3.add(l);
        bag3.add(l);
        bag3.add(l);

        System.out.println("bag1 = " + bag1.toString());
        System.out.println("bag2 = " + bag2.toString());
        System.out.println("bag3 = " + bag3.toString());
        System.out.println();

//        bag1.remove(integer);
//        System.out.println("bag1 = " + bag1.toString());
//        System.out.println();

        bag1.intersect(bag2);
        System.out.println("bag2 = " + bag2.toString());
        System.out.println("bag1 = " + bag1.toString());
        System.out.println();

        bag3.union(bag2);
        System.out.println("bag2 = " + bag2.toString());
        System.out.println("bag3 = " + bag3.toString());
        System.out.println();

    }
}
