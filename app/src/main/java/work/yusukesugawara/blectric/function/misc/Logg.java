package work.yusukesugawara.blectric.function.misc;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

public class Logg {
	private static final String TAG = "Logg";

	public static void d(@NonNull String tag, @Nullable String format, @Nullable Object... args) {
		Log.d(tag, format(format, args));
	}

	public static void w(@NonNull String tag, @Nullable String format, @Nullable Object... args) {
		Log.w(tag, format(format, args));
	}

	public static void i(@NonNull String tag, @Nullable String format, @Nullable Object... args) {
		Log.i(tag, format(format, args));
	}

	public static void e(@NonNull String tag, @Nullable String format, @Nullable Object... args) {
		Log.e(tag, format(format, args));
	}

	private static String format(@Nullable String format, @Nullable Object... args) {
		if (format == null) {
			return "";
		}

		try {
			return Str.format(format, args);
		}
		catch (RuntimeException e) {
			return Str.format("%s - %s", e.toString(), format);
		}
	}
}
