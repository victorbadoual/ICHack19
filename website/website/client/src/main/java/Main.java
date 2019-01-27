import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Main {

  public static void main(String[] args) {

    File imageFile = new File(args[0]);
    byte bytes[] = new byte[(int) imageFile.length()];
    BufferedInputStream bis = null;
    try {
      bis = new BufferedInputStream(new FileInputStream(imageFile));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    DataInputStream dis = new DataInputStream(bis);
    try {
      dis.readFully(bytes);
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println(encrypt(bytes));
  }

  public static String encrypt(byte[] image) {
    MessageDigest md = null;
    try {
      md = MessageDigest.getInstance("SHA-256");
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    assert md != null;
    md.update(image);

    byte[] encoded_bytes = md.digest();

    return bytesToHex(encoded_bytes);
  }


  private static String bytesToHex(byte[] hash) {
    StringBuffer hexString = new StringBuffer();
    for (int i = 0; i < hash.length; i++) {
      String hex = Integer.toHexString(0xff & hash[i]);
      if(hex.length() == 1) hexString.append('0');
      hexString.append(hex);
    }
    return hexString.toString();
  }

}

