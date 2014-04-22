package com.purbon.data.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class DataUtils {

	public static byte[] toByteArray(Object v) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream out = null;   
		byte[] data = null;
		  try {
			out = new ObjectOutputStream(bos);   
			out.writeObject(v);
			data = bos.toByteArray();
		} catch (IOException e) {
			
		} finally {
			if (out != null)
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if (bos != null)
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return  data;
	}
}
