package Verify;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;

public class KeyStoreExample {

  public static void main(String[] args) throws Exception {
    String keystoreFilename = "my.keystore";

    char[] password = "password".toCharArray();
    String alias = "alias";

    FileInputStream fIn = new FileInputStream(keystoreFilename);
    KeyStore keystore = KeyStore.getInstance("JKS");

    keystore.load(fIn, password);

    Certificate cert = keystore.getCertificate(alias);

    System.out.println(cert);
  }
}