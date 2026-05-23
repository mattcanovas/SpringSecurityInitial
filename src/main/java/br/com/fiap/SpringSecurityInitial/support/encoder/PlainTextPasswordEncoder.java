package br.com.fiap.SpringSecurityInitial.support.encoder;

import org.jspecify.annotations.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PlainTextPasswordEncoder implements PasswordEncoder {

    @Override
    public @Nullable String encode(@Nullable CharSequence arg0) {
        return arg0.toString();
    }

    @Override
    public boolean matches(@Nullable CharSequence arg0, @Nullable String arg1) {
        return arg0.toString().equals(arg1);
    }
    
}
