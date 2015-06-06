package Verify;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.CertificateFactory;

public class ImportCertificate {
  public static void main(String args[]) throws Exception {
    String cacert = "mytest.cer";
    String lfcert = "lf_signed.cer";
    String lfstore = "lfkeystore";
    char[] lfstorepass = "wshr.ut".toCharArray();
    char[] lfkeypass = "wshr.ut".toCharArray();
    CertificateFactory cf = CertificateFactory.getInstance("X.509");
    FileInputStream in1 = new FileInputStream(cacert);
    java.security.cert.Certificate cac = cf.generateCertificate(in1);
    in1.close();
    FileInputStream in2 = new FileInputStream(lfcert);
    java.security.cert.Certificate lfc = cf.generateCertificate(in2);
    in2.close();
    java.security.cert.Certificate[] cchain = { lfc, cac };
    FileInputStream in3 = new FileInputStream(lfstore);
    KeyStore ks = KeyStore.getInstance("JKS");
    ks.load(in3, lfstorepass);
    PrivateKey prk = (PrivateKey) ks.getKey("lf", lfkeypass);
    ks.setKeyEntry("lf_signed", prk, lfstorepass, cchain);
    FileOutputStream out4 = new FileOutputStream("lfnewstore");
    ks.store(out4, "newpass".toCharArray());
    out4.close();
  }
}