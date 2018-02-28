package com.jeecg.p3.qrcode.service;

import java.awt.image.BufferedImage;
import java.io.IOException;

import com.google.zxing.WriterException;

public interface QRCodeService {  
	  
    public BufferedImage createQRCode(final String url) throws WriterException, IOException;  
  
}  
