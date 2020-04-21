package com.cat.zeus.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileUtil {
	public static void copyAssets(Context con, String fromAssetDir, String toDir) {
		if (con == null) {
			return;
		}
		AssetManager assetManager = con.getAssets();
		String[] files = null;
		try {
			files = assetManager.list(fromAssetDir);
//			DisplayUtil.showArrayContent(files, "AssetFiles");
//			LogUtil.log("asset file num:"+files.length);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		FileOperator.createDirectory( toDir, true);
		
		/**
		 * 判断目标目录下边的文件数据，如果不等于目标数（目前为5），则删除所有重新拷贝
		 * 
		 */
		File outDir = new File(toDir);
		File[] filesUnderTargetDir = outDir.listFiles();
		if(filesUnderTargetDir != null && filesUnderTargetDir.length == 5){
			return ;
		}
		FileOperator.clearDir(outDir, null);
		
		
		if (files != null) {
			for (String filename : files) {
				InputStream in = null;
				OutputStream out = null;
				try {
					String fromFilePath = fromAssetDir + "/" + filename;
					String toFilePath = toDir  + filename;
					in = assetManager.open(fromFilePath);
					
					File outFile = new File(toDir, filename);
					if(!outFile.exists()){
						outFile.createNewFile();
					}
					out = new FileOutputStream(outFile);
					copyFile(in, out);
				} catch (IOException e) {
					Log.e("tag", "Failed to copy asset file: " + filename, e);
				} finally {
					if (in != null) {
						try {
							in.close();
						} catch (IOException e) {
							// NOOP
						}
					}
					if (out != null) {
						try {
							out.close();
						} catch (IOException e) {
							// NOOP
						}
					}
				}
			}
		}

	}

	public static void copyFile(InputStream in, OutputStream out) throws IOException {
		byte[] buffer = new byte[1024];
		int read;
		while ((read = in.read(buffer)) != -1) {
			out.write(buffer, 0, read);
		}
	}

	public static void UnZipFolder(String zipFileString, String outPathString) throws Exception {
		FileOperator.createDirectory( outPathString, true);
		ZipInputStream inZip = new ZipInputStream(new FileInputStream(zipFileString));
		ZipEntry zipEntry;
		String szName = "";
		while ((zipEntry = inZip.getNextEntry()) != null) {
			szName = zipEntry.getName();
			if (zipEntry.isDirectory()) {
				// get the folder name of the widget
				szName = szName.substring(0, szName.length() - 1);
				File folder = new File(outPathString + File.separator + szName);
				folder.mkdirs();
			} else {

				File file = new File(outPathString + File.separator + szName);
//				Log.d("TAG", file.getAbsolutePath());
				file.createNewFile();
				// get the output stream of the file
				FileOutputStream out = new FileOutputStream(file);
				int len;
				byte[] buffer = new byte[1024];
				// read (len) bytes into buffer
				while ((len = inZip.read(buffer)) != -1) {
					// write (len) byte from buffer at the position 0
					out.write(buffer, 0, len);
					out.flush();
				}
				out.close();
			}
		}
		inZip.close();
	}
}
