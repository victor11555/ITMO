import java.io.*;
import java.util.*;



public class D {

    public static void main(String[] args) throws FileNotFoundException {

        Scanner sc = new Scanner(new File("chaincode.in"));
        int n = sc.nextInt();
        StringBuilder sb = new StringBuilder();
        List<String> res = new ArrayList<String>();
        HashMap<String, Integer> map = new HashMap<>();

        for (int i = 0; i < n; i++) {
            sb.append("0");
        }

        res.add(sb.toString());
        map.put(sb.toString(), 1);
        for (int i = 1; i < (1<<n);i++) {
            sb.deleteCharAt(0);
            if (!map.containsKey(sb.append(1).toString())){
                res.add(sb.toString());
                map.put(sb.toString(), 0);
            } else {
                sb.deleteCharAt(n-1);
                if (!map.containsKey(sb.append(0).toString())) {
                    res.add(sb.toString());
                    map.put(sb.toString(), 0);
                }
            }
        }

        try(FileWriter writer=new FileWriter("chaincode.out")){
            for (int i = 0; i < res.size(); i++) {
                writer.write(res.get(i));
                writer.write(System.lineSeparator());
            }
        }catch(IOException ex){}
    }
}