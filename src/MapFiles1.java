import java.io.*;
import java.nio.*;
import java.nio.channels.*;

class MapFiles1 {

    String fname = "test";

    public MapFiles1() throws Exception {
        init();         // initializing test file
        mapAndChange(); // mapping and data modification
        checkResult();  // verifying the results
    }

    void init() throws IOException {
        int[] data = {10, 11, 12, 13};
        DataOutputStream out = new DataOutputStream(
                new FileOutputStream(fname)
        );
        for (int i = 0; i < data.length; i++) out.writeInt(data[i]);
        out.close();
    }

    void mapAndChange() throws IOException {
        // in order to introduce any modification we need to open
        // channel in read-write mode
        RandomAccessFile file = new RandomAccessFile(fname, "rw");
        FileChannel channel = file.getChannel();

        // mapping file
        MappedByteBuffer buf;
        buf = channel.map(
                FileChannel.MapMode.READ_WRITE,  // read-write mode
                0,  // starting from the beginning of the file
                (int) channel.size()  // map the whole file
        );

        // getting a view of the buffer
        IntBuffer ibuf = buf.asIntBuffer();

        // display properties of view
        System.out.println(ibuf + " --- Direct: " + ibuf.isDirect());

        int i = 0;
        while (ibuf.hasRemaining()) {
            int num = ibuf.get();       // getting next element
            ibuf.put(i++, num * 10);    // multiplying the value by 10
            // and writing at the given position
        }

        // reflect changes to the file
        buf.force();

        channel.close();
    }

    void checkResult() throws IOException {
        DataInputStream in = null;
        try {
            in = new DataInputStream(new FileInputStream(fname));
            while (true) System.out.println(in.readInt());
        } catch (EOFException exc) {
            return;
        } finally {
            in.close();
        }
    }

    public static void main(String[] args) throws Exception {
        new MapFiles1();
    }
}