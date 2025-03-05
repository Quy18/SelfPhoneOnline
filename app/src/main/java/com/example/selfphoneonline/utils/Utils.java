package com.example.selfphoneonline.utils;

import com.example.selfphoneonline.model.GioHang;
import com.example.selfphoneonline.model.User;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static final String BASE_URL = "http://10.0.2.2/banhang/";
    public static List<GioHang> manggiohang;
    public static List<GioHang> mangmuahang = new ArrayList<>();
    public static User user_current = new User();
}
