package br.com.securityprofit.img;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ImageUtils {
    private static final String TAG = ImageUtils.class.getSimpleName();

    public static byte[] convertImageToByteArray(Context context, Uri imageUri) {
        try {
            // Carrega a imagem do arquivo
            Bitmap bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(imageUri));

            // Converte a imagem em um array de bytes
            return convertBitmapToByteArray(bitmap);
        } catch (IOException e) {
            Log.e(TAG, "Erro ao converter imagem para byte array", e);
            return null;
        }
    }

    public static String convertImageToHexadecimal(Context context, Uri imageUri) {
        byte[] byteArray = convertImageToByteArray(context, imageUri);
        if (byteArray != null) {
            return bytesToHex(byteArray);
        }
        return null;
    }

    public static String convertBitmapToHexadecimal(Bitmap bitmap) {
        byte[] byteArray = convertBitmapToByteArray(bitmap);
        if (byteArray != null) {
            return bytesToHex(byteArray);
        }
        return null;
    }

    private static byte[] convertBitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte aByte : bytes) {
            stringBuilder.append(String.format("%02X", aByte));
        }
        return stringBuilder.toString();
    }
}
