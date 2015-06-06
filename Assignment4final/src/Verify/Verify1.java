package Verify;

import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;

import javax.crypto.Cipher;

import java.security.spec.RSAPrivateKeySpec;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

public class Verify1 {

  public static void main(String[] args) throws Exception {

	//Input the files.
	 FileInputStream Trustcenter = new FileInputStream("Trustcenter.cer");
	 FileInputStream Raghupub = new FileInputStream("Raghupub.cer");
	//Create the Object of Certificate Factory
    CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
    
    //Create an instance of KeyStore
    KeyStore ks = KeyStore.getInstance("PKCS12");
    FileInputStream fis = new FileInputStream("Raghupri.pfx");
    char[] password = "raghu".toCharArray();
    ks.load(fis, password);

    String alias = ks.aliases().nextElement(); 
    Certificate[] cc = ks.getCertificateChain(alias); 
    Key privateKey = ks.getKey(alias, password);
    
    //Print the Private Key
    System.out.println("\tPrivate Key of TA in encoded form is:"+privateKey.getEncoded());
    
    //Pulling out parameters which makes up Key
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    RSAPrivateKeySpec rsaPrivKeySpec = keyFactory.getKeySpec(privateKey, RSAPrivateKeySpec.class);
    System.out.println("PrivKey Modulus : " + rsaPrivKeySpec.getModulus());  
    System.out.println("PrivKey Exponent : " + rsaPrivKeySpec.getPrivateExponent());  
    
    X509Certificate certTA = (X509Certificate) certFactory.generateCertificate(Raghupub);
    X509Certificate certCA = (X509Certificate) certFactory.generateCertificate(Trustcenter);
    
    PublicKey pukCA = (PublicKey) certCA.getPublicKey();
    PublicKey pukTA = (PublicKey) certTA.getPublicKey();
    //Print the certificate for CA
    System.out.println("\tCertificate for CA: " + certCA.getSubjectDN());
    System.out.println("\tCertificate issued by for CA: " + certCA.getIssuerDN());
    System.out.println("\tThe certificate is valid from  for CA:" + certCA.getNotBefore() + " to "
        + certCA.getNotAfter());
    System.out.println("\tCertificate SN# for CA:" + certCA.getSerialNumber());
    System.out.println("\tGenerated with for CA:" + certCA.getSigAlgName());
    
    //Print the certificate for TA
    System.out.println("\tCertificate for TA: " + certTA.getSubjectDN());
    System.out.println("\tCertificate issued by for TA: " + certTA.getIssuerDN());
    System.out.println("\tThe certificate is valid from  for TA:" + certTA.getNotBefore() + " to "
        + certTA.getNotAfter());
    System.out.println("\tCertificate SN# for TA:" + certTA.getSerialNumber());
    System.out.println("\tGenerated with for TA:" + certTA.getSigAlgName());
 
    //Print the values of keys for TA and CA certificate
    System.out.println("\tPublic Key of Raghu is:"+pukTA);
    System.out.println("\tPublic Key of Raghu in encoded format:"+pukTA.getEncoded());
    System.out.println("\tPublic Key of CA is:"+pukCA);
    System.out.println("\tPublic Key of CA in encoded format:"+pukCA.getEncoded());
    //Print the Signature of the certificate
    System.out.println("\tSignature of the TA certificate is:"+new BigInteger(certTA.getSignature()).toString(16));
    System.out.println("\tSignature of the CA certificate is:"+new BigInteger(certCA.getSignature()).toString(16));
    //Verify that the certificate is valid
    try{
    certTA.verify(pukCA);
    System.out.println("\tThis Certicate is valid");
    }
    catch (CertificateException e){
        e.toString();
        System.out.println("\tCertificate is Invalid");
    }
    
    //Encrypt the DATA now 
    	  System.out.println("\n----------------ENCRYPTION STARTED------------");  
    	  String data = "Our names are Akhilesh Kumar and Deepesh Mittal. We are enrolled in CSE 539.";
    	  System.out.println("\tData to be encrypted is:"+data);  
    	  byte[] dataToEncrypt = data.getBytes();  
    	  byte[] encryptedData = null;  
    	  try {  
    	   Cipher cipher = Cipher.getInstance("RSA");  
    	   cipher.init(Cipher.ENCRYPT_MODE, pukTA);  
    	   encryptedData = cipher.doFinal(dataToEncrypt);  
    	   System.out.println("\tEncryted Data in Encoded format:" +encryptedData);
    	   System.out.println("\tEncryted Data in hex format: " +new BigInteger(encryptedData).toString(16)); 
    	   
    	  } catch (Exception e) {  
    	   e.printStackTrace();  
    	  }
     //Decrypt the encrypted DATA now
    	  System.out.println("\n----------------DECRYPTION STARTED------------");  
    	  byte[] descryptedData = null;    
    	  try {   
    	   Cipher cipher = Cipher.getInstance("RSA");  
    	   cipher.init(Cipher.DECRYPT_MODE, privateKey);  
    	   descryptedData = cipher.doFinal(encryptedData);  
    	   System.out.println("Decrypted Data: " + new String(descryptedData));  
    	     
    	  } catch (Exception e) {  
    	   e.printStackTrace();  
    	  } 
    	  //Generate the Public KeyPair
    	  System.out.println("-------GENRATE PUBLIC and PRIVATE KEY-------------");  
    	   KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");  
    	   keyPairGenerator.initialize(1024); 
    	   KeyPair keyPair = keyPairGenerator.generateKeyPair();  
    	   PublicKey publicKeyGenerated = keyPair.getPublic();  
    	   PrivateKey privateKeyGenerated = keyPair.getPrivate();  
    	   System.out.println("Public Key - " + publicKeyGenerated);  
    	   System.out.println("Private Key - " + privateKeyGenerated);
    	   
    	 //Encrypt the DATA now 
     	  System.out.println("\n----------------ENCRYPTION STARTED WITH GENERATED KEYPAIR------------"); 
     	  System.out.println("\tData to be encrypted is:"+data);   
     	  try {  
     	   Cipher cipher = Cipher.getInstance("RSA");  
     	   cipher.init(Cipher.ENCRYPT_MODE, publicKeyGenerated);  
     	   encryptedData = cipher.doFinal(dataToEncrypt);  
     	   System.out.println("\tEncryted Data in Encoded format:" +encryptedData);
     	   System.out.println("\tEncryted Data in hex format: " +new BigInteger(encryptedData).toString(16)); 
     	   
     	  } catch (Exception e) {  
     	   e.printStackTrace();  
     	  }
      //Decrypt the encrypted DATA now
     	  System.out.println("\n----------------DECRYPTION STARTED WITH GENERATED KEYPAIR------------");      
     	  try {   
     	   Cipher cipher = Cipher.getInstance("RSA");  
     	   cipher.init(Cipher.DECRYPT_MODE, privateKeyGenerated);  
     	   descryptedData = cipher.doFinal(encryptedData);  
     	   System.out.println("Decrypted Data: " + new String(descryptedData));  
     	     
     	  } catch (Exception e) {  
     	   e.printStackTrace();  
     	  } 
        
    Trustcenter.close();
    Raghupub.close();
  }
}
