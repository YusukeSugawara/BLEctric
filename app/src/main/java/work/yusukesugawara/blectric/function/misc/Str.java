package work.yusukesugawara.blectric.function.misc;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.nio.ByteBuffer;
import java.util.Locale;

public class Str {
	private static final String TAG = "Str";

	@NonNull
	public static String format(@NonNull String format, @Nullable Object... args) {
		return String.format(Locale.US, format, args);
	}

	@NonNull
	public static String hexString(@Nullable byte[] bytes) {
		if (bytes == null) {
			return "";
		}

		StringBuilder stringBuilder = new StringBuilder();
		for (byte b : bytes) {
			stringBuilder.append(Str.format("%02X", b));
		}
		return stringBuilder.toString();
	}

    @NonNull
	public static byte[] bytes(@NonNull String hexString) throws IllegalArgumentException {
		if (hexString.length()%2 != 0) {
			hexString = "0" + hexString;
		}

		if (!hexString.matches("([0-9a-fA-F]{2})*")) {
			throw new IllegalArgumentException(Str.format("`%s` is not hexString", hexString));
		}

		ByteBuffer byteBuffer = ByteBuffer.allocate(hexString.length()/2);
		for (int i=0; i<hexString.length(); i+=2) {
			byteBuffer.put((byte) Integer.parseInt(hexString.substring(i, i+2), 16));
		}

		return byteBuffer.array();
	}
}
