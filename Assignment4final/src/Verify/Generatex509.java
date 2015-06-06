package Verify;

import java.io.FileInputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class Generatex509 {
  public static void main(String args[]) throws Exception {
    FileInputStream fr = new FileInputStream("Raghupub.cer");
    CertificateFactory cf = CertificateFactory.getInstance("X509");
    X509Certificate c = (X509Certificate) cf.generateCertificate(fr);
    byte[] all=c.getSignature();
    System.out.println("\tSignature is: " +all);
    System.out.println("\tCertificate issued by: " + c.getIssuerDN());
    System.out.println("\tThe certificate is valid from " + c.getNotBefore() + " to "
        + c.getNotAfter());
    System.out.println("\tCertificate SN# " + c.getSerialNumber());
    System.out.println("\tGenerated with " + c.getSigAlgName());
  }
}