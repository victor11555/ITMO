import java.io.*;
import java.util.*;



public class Z {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("nextsetpartition.in"));
        int n = sc.nextInt();
        int k = sc.nextInt();
        try (FileWriter writer = new FileWriter("nextsetpartition.out")) {
            while (n != 0 && k != 0) {
                sc.nextLine();

                ArrayList<ArrayList<Integer>> partition = new ArrayList<>();

                for (int i = 0; i < k; i++) {
                    ArrayList<Integer> part = new ArrayList<>();
                    String str = sc.nextLine();
                    String[] parts = str.split(" ");
                    for (int j = 0; j < parts.length; j++) {
                        part.add(Integer.parseInt(parts[j]));
                    }
                    partition.add(part);
                }

                int m = 0;
                ArrayList<Integer> used = new ArrayList<>();
                boolean flag = false;
                for (int i = partition.size() - 1; i >= 0; i--) {
                    if ((used.size() != 0) && (used.get(used.size() - 1) > partition.get(i).get(partition.get(i).size() - 1))) {

                        for (int l = 0; l < used.size(); l++) {
                            if (used.get(l) > partition.get(i).get(partition.get(i).size() - 1)) {
                                m = l;
                                break;
                            }
                        }
                        partition.get(i).add(used.get(m));
                        used.remove(m);
                        break;
                    }

                    for (int j = partition.get(i).size() - 1; j >= 0; j--) {
                        if ((used.size() != 0) && (j != 0) && (used.get(used.size() - 1) > partition.get(i).get(j))) {
                            for (int l = 0; l < used.size(); l++) {
                                if (used.get(l) > partition.get(i).get(partition.get(i).size() - 1)) {
                                    m = l;

                                    break;
                                }
                            }
                            int ch = used.get(m);
                            used.set(m, partition.get(i).get(j));
                            partition.get(i).set(j, ch);
                            flag = true;
                            break;
                        }

                        if (flag) break;

                        used.add(partition.get(i).get(j));
                        if (partition.get(i).size() > 1) {
                            partition.get(i).remove(j);
                        } else partition.remove(i);
                        Collections.sort(used);
                    }
                    if (flag) break;

                }

                for (int i = 0; i < used.size(); i++) {
                    ArrayList<Integer> tmp = new ArrayList<>(Arrays.asList(used.get(i)));
                    partition.add(tmp);
                }
                k = partition.size();


                writer.write(n + " " + k);
                writer.write(System.lineSeparator());
                for (int i = 0; i < partition.size(); i++) {
                    for (int j = 0; j < partition.get(i).size(); j++) {
                        writer.write(partition.get(i).get(j) + " ");
                    }
                    writer.write(System.lineSeparator());
                }
                writer.write(System.lineSeparator());

                n = sc.nextInt();
                k = sc.nextInt();
            }
        } catch (IOException ex) {}
    }
}