package com.wordpong.app.stripes.converter;

import java.util.Collection;
import java.util.Locale;

import net.sourceforge.stripes.validation.ScopedLocalizableError;
import net.sourceforge.stripes.validation.TypeConverter;
import net.sourceforge.stripes.validation.ValidationError;

public class ImageUrlTypeConverter implements TypeConverter<String> {
    public String convert(String input, Class<? extends String> targetType, Collection<ValidationError> errors) {
        String result = input;
        if (!isValidImageURLPrefix(input)) {
            errors.add(new ScopedLocalizableError("converter.imageurl", "invalidImageUrlPrefix"));
        }
        if (!isValidImageURLSuffix(input)) {
            errors.add(new ScopedLocalizableError("converter.imageurl", "invalidImageUrlSuffix"));
        }
        return result;
    }

    public void setLocale(Locale locale) {
    }

    public static boolean isValidImageURL(String url) {
        boolean result = true;
        if (isValidImageURLPrefix(url) == false) {
            result = false;
        }
        if (isValidImageURLSuffix(url) == false) {
            result = false;
        }
        return result;
    }

    public static boolean isValidImageURLPrefix(String url) {
        boolean result = true;
        if (url != null && !url.startsWith("http://") && !url.startsWith("https://")) {
            result = false;
        }
        return result;
    }

    public static boolean isValidImageURLSuffix(String url) {
        boolean result = true;
        if (url != null) {
            url = url.toLowerCase();
            if (url != null && !url.endsWith(".jpg") && !url.endsWith(".gif") && !url.endsWith(".png")) {
                result = false;
            }
        }
        return result;
    }

}
