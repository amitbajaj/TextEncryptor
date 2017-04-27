package online.buzzzz.security;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class AESCrypto {

	static final int KEYLENGTH = 16;
        //static final String ALGORITHM = "AES/CTR/NOPADDING";
        static final String ALGORITHM = "AES/CBC/PKCS5PADDING";
	
	public static String encrypt(String key, String value){
		try{
			SecureRandom random = new SecureRandom();
			byte[] randBytes = new byte[16];
			random.nextBytes(randBytes);
			IvParameterSpec iv = new IvParameterSpec(randBytes);
			String key2use=key;
			
			if(key.length()%KEYLENGTH!=0){
				key2use = String.format("%0"+(KEYLENGTH-(key.length()%KEYLENGTH))+"d%s", 0, key);
			}
			SecretKeySpec skeySpec = new SecretKeySpec(key2use.getBytes("UTF-8"), "AES");
			
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
			
			byte[] staged = cipher.doFinal(value.getBytes());
			byte[] encrypted = new byte[staged.length+randBytes.length];
			System.arraycopy(randBytes, 0, encrypted, 0, randBytes.length);
			System.arraycopy(staged, 0, encrypted, randBytes.length, staged.length);
			
			return DatatypeConverter.printBase64Binary(encrypted);
		} catch (Exception ex) {
		    return ex.toString();
		}
	}
    public static String decrypt(String key, String value) {
    	try {
            byte[] ivBytes = new byte[16];
            byte[] staged = DatatypeConverter.parseBase64Binary(value);
            byte[] encrypted = new byte[staged.length-16];
			String key2use=key;
			
			if(key.length()%KEYLENGTH!=0){
				key2use = String.format("%0"+(KEYLENGTH-(key.length()%KEYLENGTH))+"d%s", 0, key);
			}
            
            System.arraycopy(staged, 0, ivBytes, 0, ivBytes.length);
            System.arraycopy(staged, ivBytes.length, encrypted, 0, staged.length-ivBytes.length);
        	
            IvParameterSpec iv = new IvParameterSpec(ivBytes);
            SecretKeySpec skeySpec = new SecretKeySpec(key2use.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] original = cipher.doFinal(encrypted);

            return new String(original);
        } catch (Exception ex) {
            return ex.toString();
        }
    }

}
