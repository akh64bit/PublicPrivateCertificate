package Verify;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

public class RSAKeyConvert {
        public static void main( String args[] ) {

                BigInteger modulus  = new BigInteger("350871044328208704010580786055405681");
                BigInteger exponent = new BigInteger("545161406957801571");

                try {                    
                        RSAPublicKeySpec spec = new RSAPublicKeySpec(modulus, exponent);
                        RSAPrivateKeySpec privateSpec = new RSAPrivateKeySpec(modulus, exponent);

                        KeyFactory factory = KeyFactory.getInstance("RSA");

                        PublicKey pub = factory.generatePublic(spec);
                        PrivateKey priv = factory.generatePrivate(privateSpec); 

                        System.out.println("Public Key : "+ byteArrayToHexString( pub.getEncoded() ));
                        System.out.println("Private Key : "+ byteArrayToHexString( priv.getEncoded() ));
                }                        
                catch( Exception e ) {   
                        System.out.println(e.toString());       
                }                        
        }         
        public static String byteArrayToHexString(byte[] bytes)          
        {         
                StringBuffer buffer = new StringBuffer();
                for(int i=0; i<bytes.length; i++)
                {                        
                        if(((int)bytes[i] & 0xff) < 0x10)       
                                buffer.append("0");                               
                        buffer.append(Long.toString((int) bytes[i] & 0xff, 16));
                }                        
                return buffer.toString();
        }         
}
