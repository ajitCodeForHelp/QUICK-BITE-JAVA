//package com.quickBite.utils;
//
//import com.google.zxing.BarcodeFormat;
//import com.google.zxing.EncodeHintType;
//import com.google.zxing.MultiFormatWriter;
//import com.google.zxing.client.j2se.MatrixToImageWriter;
//import com.google.zxing.common.BitMatrix;
//import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
//import jakarta.servlet.http.HttpServletRequest;
//
//import javax.imageio.ImageIO;
//import java.awt.image.BufferedImage;
//import java.io.ByteArrayOutputStream;
//import java.util.HashMap;
//import java.util.Map;
//
//public class QRCodeUtils {
//
//    private static QRCodeUtils instance;
//
//    private QRCodeUtils() {
//
//    }
//
//    public static QRCodeUtils getInstance() {
//        if (instance != null)
//            return instance;
//        instance = new QRCodeUtils();
//        return instance;
//    }
//
//    public String getDineInCashBackQRCode(HttpServletRequest httpServletRequest, String uuid){
//        String qrString = "<URL>?t=DineInCashBack&i=" + uuid;
//        // TODO > qrString.replace("<URL>", "");
//        return getBase64QRImageOfString(qrString);
//    }
//
//    public String getQRCodeCashBack(HttpServletRequest httpServletRequest, String uuid){
//        String qrString = "<URL>?t=QRCodeCashBack&i=" + uuid;
//        // TODO > qrString.replace("<URL>", "");
//        return getBase64QRImageOfString(qrString);
//    }
//
//    public String getTableQRCode(HttpServletRequest httpServletRequest, String tableQRCode){
//        String qrString = "<URL>?t=TableNumber&i=" + tableQRCode;
//        // TODO > qrString.replace("<URL>", "");
//        return getBase64QRImageOfString(qrString);
//    }
//
//    private String getBase64QRImageOfString(String qrCode) {
//        String qrCodeData = qrCode;
//        try {
//            String charset = "UTF-8"; // or "ISO-8859-1"
//            Map hintMap = new HashMap();
//            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
//            BitMatrix matrix = new MultiFormatWriter()
//                    .encode(new String(qrCodeData.getBytes(charset), charset), BarcodeFormat.QR_CODE, 300, 300, hintMap);
//            BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(matrix);
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            ImageIO.write(bufferedImage, "jpg", baos);
//            baos.flush();
//            byte[] imageInByteArray = baos.toByteArray();
//            baos.close();
//            String b64ImageData = javax.xml.bind.DatatypeConverter.printBase64Binary(imageInByteArray);
//            return b64ImageData;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//}
