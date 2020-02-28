import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class FileAccess {
    private String rootPath;
    private FileSystem hdfs;

    /**
     * Initializes the class, using rootPath as "/" directory
     *
     * @param rootPath - the path to the root of HDFS,
     *                 for example, hdfs://localhost:32771
     */
    public FileAccess(String rootPath) throws Exception {
        this.rootPath = rootPath;

        Configuration configuration = new Configuration();
        configuration.set("dfs.client.use.datanode.hostname", "true");
        System.setProperty("HADOOP_USER_NAME", "root");

        this.hdfs = FileSystem.get(
                new URI(rootPath), configuration
        );
    }

    public void close() throws IOException {
        hdfs.close();
    }

    /**
     * Creates empty file or directory
     *
     * @param path
     */
    public void create(String path) throws Exception {
        Path filePath = new Path(rootPath + path);
        if (hdfs.exists(filePath)) {
            hdfs.delete(filePath, true);
        }
        hdfs.create(filePath);
    }

    /**
     * Appends content to the file
     *
     * @param path
     * @param content
     */
    public void append(String path, String content) throws Exception {
        String fileText = read(path);

        Path filePath = new Path(rootPath + path);
        OutputStream os = hdfs.create(filePath);
        BufferedWriter br = new BufferedWriter(
                new OutputStreamWriter(os, "UTF-8")
        );

        br.write(fileText + content);
        br.flush();
        br.close();
    }

    /**
     * Returns content of the file
     *
     * @param path
     * @return
     */
    public String read(String path) throws Exception {
        Path filePath = new Path(rootPath + path);

        if (hdfs.isFile(filePath)) {
            InputStream is = hdfs.open(filePath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder builder = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }

            reader.close();
            return builder.toString();
        }
        System.out.println("Файл не найден: " + path);
        return null;
    }

    /**
     * Deletes file or directory
     *
     * @param path
     */
    public void delete(String path) throws Exception {
        Path filePath = new Path(rootPath + path);
        boolean isDirectory = hdfs.isDirectory(filePath);
        if (hdfs.exists(filePath)) {
            hdfs.delete(filePath, true);
            if (isDirectory) {
                System.out.println("Папка удалена " + path);
            } else {
                System.out.println("Файл удален " + path);
            }
        }
    }

    /**
     * Checks, is the "path" is directory or file
     *
     * @param path
     * @return
     */
    public boolean isDirectory(String path) throws Exception {
        Path filePath = new Path(rootPath + path);
        return hdfs.isDirectory(filePath);
    }

    /**
     * Return the list of files and subdirectories on any directory
     *
     * @param path
     * @return
     */
    public List<String> list(String path) throws Exception {
        ArrayList<String> resultList = new ArrayList<>();

        Path dirPath = new Path(rootPath + path);
        if (hdfs.isDirectory(dirPath)) {
            FileStatus[] fileStatus = hdfs.listStatus(dirPath);
            for (FileStatus status : fileStatus) {
                String childPath = status.getPath().toString().replace((rootPath + path), "");
                resultList.add(childPath);
            }
            return resultList;
        }
        System.out.println(path + " не является папкой!");
        return null;
    }


}
