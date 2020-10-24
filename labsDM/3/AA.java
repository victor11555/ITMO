import java.io.*;
import java.util.*;



public class AA {
    private static StringBuilder nextComb(StringBuilder sb){
            int cntL = 0;
            int cntR = 0;
            int n = sb.length();
            for(int i = n-1 ; i >= 0  ; i--) {
                if (sb.charAt(i) == '(') {
                    cntL ++;
                    if (cntR > cntL) break;
                } else {
                    cntR ++;
                }
            }
            sb.delete(n - cntL - cntR, n);
            if (sb.length() == 0) {
                sb.append("-");
                return sb;
            } else {
                sb.append(')');
                for (int j = 0; j < cntL; j++) {
                    sb.append('(');
                }
                for (int j = 0; j < cntR - 1; j++) {
                    sb.append(')');
                }
                return sb;
            }
        }


        public static void main(String[] args) throws FileNotFoundException {

            Scanner sc = new Scanner(new File("nextbrackets.in"));
            StringBuilder sb = new StringBuilder();
            sb.append(sc.nextLine());
            nextComb(sb);
            try(FileWriter writer=new FileWriter("nextbrackets.out")){
                writer.write(sb.toString());
            }catch(IOException ex){}
        }
    }








