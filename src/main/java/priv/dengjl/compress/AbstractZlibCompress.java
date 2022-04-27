package priv.dengjl.compress;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class AbstractZlibCompress implements Compress {
    @Override
    public byte[] compress(byte[] data) throws IOException {
        byte[] output = new byte[0];
        Deflater compresser = new Deflater();
        compresser.reset();
        compresser.setInput(data);
        compresser.finish();
        ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length);
        try {
            byte[] buf = new byte[1024];
            while (!compresser.finished()) {
                int i = compresser.deflate(buf);
                bos.write(buf, 0, i);
            }
            output = bos.toByteArray();
        } catch (Exception e) {
            output = data;
            e.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        compresser.end();
        return output;
    }


    @Override
    public byte[] uncompress(byte[] data) throws IOException {
        byte[] output = new byte[0];
        Inflater inflater = new Inflater();
        inflater.reset();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream  = new ByteArrayOutputStream(data.length);
        try {
            byte[] result  = new byte[1024];
            while (!inflater.finished()) {
                int count  = inflater.inflate(result );
                outputStream .write(result , 0, count );
            }
            output = outputStream .toByteArray();
        } catch (Exception e) {
            output = data;
            e.printStackTrace();
        } finally {
            try {
                outputStream .close();
                inflater.end();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return output;
    }
}
