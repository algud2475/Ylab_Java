public class MultTable {
    public static void main(String[] args) {
        StringBuilder multTable = new StringBuilder();
        for (int i=1; i<10; i++) {
            for (int j=1; j<10; j++) {
                multTable.append(i + "x" + j + " = " + i*j + "\n");
            }
        }
        System.out.println(multTable);
    }
}
