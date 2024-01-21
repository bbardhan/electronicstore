package com.checkout.util;

import org.modelmapper.ModelMapper;

public final class AppUtil {
    private AppUtil(){}
    public static ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
