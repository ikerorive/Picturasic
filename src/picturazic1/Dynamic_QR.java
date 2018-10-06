package picturazic1;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

///SACADO DE http://chillyfacts.com/generate-read-qr-code-dynamically-using-java
public class Dynamic_QR {

		/*String encriptarStr = "olasito";
		String email = "iker.orive@alumni.mondragon.edu";
		Dynamic_QR.generate_qr("qr", encriptarStr, email);*/
		//LLAMAR A DYNAMIC_QR.GENERATE_QR Y PASARLE "qr", EL STRING A ENCRIPTAR (usuario$contrase�a$) Y EL EMAIL DEL USUARIO

	

	public static String encriptar(String txt) {
		// String str = "someString";
		char[] str = txt.toCharArray();
		for (int i = 0; i < txt.length(); i++) {
                    if(str[i]=='$'){
                    
                    }else{
			str[i] = (char) (str[i] + 3); // the key for encryption is 3 that is added to ASCII value
			// System.out.println(str[i]);
                    }
		}
		String resp = String.valueOf(str);
		// System.out.println(resp);
		return resp;
	}

	public static void generate_qr(String image_name, String str, String email) {
		String qrCodeData = encriptar(str);
		try {
			String filePath = "img\\" + image_name + ".png";
			// "D:\\Escritorio\\"+image_name+".png";
			String charset = "UTF-8"; // or "ISO-8859-1"
			Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
			hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
			BitMatrix matrix = new MultiFormatWriter().encode(new String(qrCodeData.getBytes(charset), charset),
					BarcodeFormat.QR_CODE, 200, 200, hintMap);
			MatrixToImageWriter.writeToFile(matrix, filePath.substring(filePath.lastIndexOf('.') + 1),
					new File(filePath));
			System.out.println("QR Code image created successfully!");
			SendAttachmentInEmail.mail(filePath, email);
			// SendAttachmentInEmail.Send("picturasic", "12345678aA@", "gmail.com",
			// "ikormu@gmail.com", "User", "ola k ase");
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}