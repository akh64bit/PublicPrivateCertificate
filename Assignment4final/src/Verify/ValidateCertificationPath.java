package Verify;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidator;
import java.security.cert.CertPathValidatorResult;
import java.security.cert.PKIXCertPathValidatorResult;
import java.security.cert.PKIXParameters;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;

public class ValidateCertificationPath {
  public static void main(String[] argv) throws Exception {
    String filename = System.getProperty("java.home")
        + "/lib/security/cacerts".replace('/', File.separatorChar);
    FileInputStream is = new FileInputStream(filename);
    KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
    String password = "password";
    keystore.load(is, password.toCharArray());

    PKIXParameters params = new PKIXParameters(keystore);

    params.setRevocationEnabled(false);

    CertPathValidator certPathValidator = CertPathValidator.getInstance(CertPathValidator
        .getDefaultType());
    CertPath certPath = null;
    CertPathValidatorResult result = certPathValidator.validate(certPath, params);

    PKIXCertPathValidatorResult pkixResult = (PKIXCertPathValidatorResult) result;
    TrustAnchor ta = pkixResult.getTrustAnchor();
    X509Certificate cert = ta.getTrustedCert();
  }
}
