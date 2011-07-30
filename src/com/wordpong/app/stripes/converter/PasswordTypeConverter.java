package com.wordpong.app.stripes.converter;

import java.util.Collection;
import java.util.Locale;

import net.sourceforge.stripes.validation.TypeConverter;
import net.sourceforge.stripes.validation.ValidationError;

import com.wordpong.util.secure.Encrypt;

public class PasswordTypeConverter implements TypeConverter<String> {

    public String convert(String input, Class<? extends String> cls, Collection<ValidationError> errors) {

        return Encrypt.hashSha1(input);
    }

    public void setLocale(Locale locale) {
    }
}
