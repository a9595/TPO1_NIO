import java.io.*;
import java.nio.IntBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by Andrii on 3/12/2015.
 */
public class Reader {

    String fname = "test";

    public Reader() throws Exception {
//        init();         // initializing test file
//        mapAndChange(); // mapping and data modification
        checkResult();  // verifying the results
    }


    void checkResult() throws IOException {
        DataInputStream in = null;
        try {
            in = new DataInputStream(new FileInputStream(fname));
            System.out.println("checked");
            while (true) System.out.println(in.readInt());

        } catch (EOFException exc) {
            return;
        } finally {
            in.close();
        }


    }

    public static void main(String[] args) throws Exception {
        new Reader();
    }

}
