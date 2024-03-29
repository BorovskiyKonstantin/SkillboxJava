public class Main {
    private static final String ROOT_PATH = "hdfs://d3c0e1a0fd34:8020";
    private static String symbols = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static void main(String[] args) throws Exception {
        test();
    }

    public static void test() throws Exception {
        FileAccess fileAccess = new FileAccess(ROOT_PATH);
        fileAccess.create("/test/file.txt");
        fileAccess.append("/test/file.txt", getRandomWord());
        fileAccess.append("/test/file.txt", " 123 " + getRandomWord());

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 10_000_000; i++) {
            builder.append(getRandomWord() + " ");
        }
        fileAccess.append("/test/file.txt", builder.toString());

        System.out.println(fileAccess.read("/test/file.txt"));
        fileAccess.create("/test/1/file1.txt");
        fileAccess.create("/test/file1.txt");
        fileAccess.create("/test/file2.txt");
        fileAccess.create("/test/file3.txt");

        System.out.println("\n" +
                "===============\n" +
                "Содержимое папки \"/test/\":");
        fileAccess.list("/test/")
                .forEach(System.out::println);

        System.out.println();

        fileAccess.delete("/test/1/file1.txt");
        fileAccess.delete("/test/1/");
        fileAccess.delete("/test/file1.txt");
        fileAccess.delete("/test/file2.txt");
        fileAccess.delete("/test/file3.txt");

//        fileAccess.close();
    }

    private static String getRandomWord() {
        StringBuilder builder = new StringBuilder();
        int length = 2 + (int) Math.round(10 * Math.random());
        int symbolsCount = symbols.length();
        for (int i = 0; i < length; i++) {
            builder.append(symbols.charAt((int) (symbolsCount * Math.random())));
        }
        return builder.toString();
    }
}
