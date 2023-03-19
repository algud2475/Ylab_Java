package filesort;

import java.io.*;

public class FileWriter {
    public static void writeArrayToFile(String name, long[] array) throws IOException {
        File tmpDir = new File("tmp");
        if (!tmpDir.exists()) {
            tmpDir.mkdirs();
        }
        File file = new File("tmp/" + name);
        try (PrintWriter pw = new PrintWriter(file)) {
            for (int i = 0; i < array.length; i++) {
                pw.println(array[i]);
            }
            pw.flush();
        }
    }

    public static void copyContent(File a, File b)
            throws IOException
    {
        FileInputStream in = new FileInputStream(a);
        FileOutputStream out = new FileOutputStream(b);

        try {
            int n;
            while ((n = in.read()) != -1) {
                out.write(n);
            }
        }
        finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }

}
