package Verify;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.security.cert.Certificate;

public class ExportCertificate {
  public static void main(String[] argv) throws Exception {
    FileInputStream is = new FileInputStream("your.keystore");

    KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
    keystore.load(is, "my-keystore-password".toCharArray());

    String alias = "myalias";
    Certificate cert = keystore.getCertificate(alias);

    File file = null;
    byte[] buf = cert.getEncoded();

    FileOutputStream os = new FileOutputStream(file);
    os.write(buf);
    os.close();

    Writer wr = new OutputStreamWriter(os, Charset.forName("UTF-8"));
    //wr.write(new sun.misc.BASE64Encoder().encode(buf));
    wr.flush();

  }
}
