package com.jy.recycle.util;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Title:
 * @author jy_xiong
 * @create 2006.08.12 Description:  version 1.0
 */

public class FileOperate {
	public FileOperate() {
	}

	/**
	 *
	 * @param folderPath
	 *            String c:/jy
	 * @return boolean
	 */
	public static void newFolder(String folderPath) {
		try {
			String filePath = folderPath;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.mkdir();
			}
		} catch (Exception e) {
			System.out.println("Error");
			e.printStackTrace();
		}
	}

	/**
	 *
	 * @param filePathAndName
	 *            String
	 * @param fileContent
	 *            String
	 * @return boolean
	 */
	public static void newFile(String filePathAndName, String fileContent) {

		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.createNewFile();
			}
			FileWriter resultFile = new FileWriter(myFilePath);
			PrintWriter myFile = new PrintWriter(resultFile);
			String strContent = fileContent;
			myFile.println(strContent);
			resultFile.close();
			resultFile = null;
		} catch (Exception e) {
			System.out.println("Error");
			e.printStackTrace();

		}

	}

	/**
	 *
	 * @param filePathAndName
	 *            String c:/jy.txt
	 * @return boolean
	 */
	public static void delFile(String filePathAndName) {
		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			File myDelFile = new File(filePath);
			myDelFile.delete();
		} catch (Exception e) {
			System.out.println("Error");
			e.printStackTrace();

		}

	}

	/**
	 *
	 * @param folderPath
	 *            Stringc:/jy
	 * @return boolean
	 */
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath);
			String filePath = folderPath;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			myFilePath.delete();

		} catch (Exception e) {
			System.out.println("Error");
			e.printStackTrace();

		}

	}

	/**
	 *
	 * @param path
	 *            String  c:/jy
	 */
	public static void delAllFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			return;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);
				delFolder(path + "/" + tempList[i]);
			}
		}
		file.delete();
	}

	/**
	 *
	 * 
	 * @param oldFile
	 *            String
	 * @param newFile
	 *            String
	 * @return boolean
	 */
	public static void copyFile(String oldFile, String newFile) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldFile);
			if (oldfile.exists()) {
				InputStream inStream = new FileInputStream(oldFile);
				FileOutputStream fs = new FileOutputStream(newFile);
				byte[] buffer = new byte[8192];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread;
					fs.write(buffer, 0, byteread);
				}
				fs.flush();
				inStream.close();
				inStream = null;
				fs.close();
				fs = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 *
	 * @param oldPath
	 *            String
	 * @param newPath
	 *            String
	 * @return boolean
	 */
	public static void copyFolder(String oldPath, String newPath) {

		try {
			(new File(newPath)).mkdirs();
			File a = new File(oldPath);
			String[] file = a.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + file[i]);
				} else {
					temp = new File(oldPath + File.separator + file[i]);
				}

				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(newPath
							+ "/" + (temp.getName()).toString());
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					output = null;
					input.close();
					input = null;
				}
				if (temp.isDirectory()) {
					copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
				}
			}
		} catch (Exception e) {
			System.out.println("Error");
			e.printStackTrace();

		}

	}

	/**
	 *
	 * @param oldPath
	 *            String £ºc:/jy.txt
	 * @param newPath
	 *            String £ºd:/jy.txt
	 */
	public static void moveFile(String oldPath, String newPath) {
		copyFile(oldPath, newPath);
		delFile(oldPath);

	}

	/**
	 *
	 * @param oldPath
	 *            String £ºc:/jy.txt
	 * @param newPath
	 *            String £ºd:/jy.txt
	 */
	public static void moveFolder(String oldPath, String newPath) {
		copyFolder(oldPath, newPath);
		delFolder(oldPath);
	}

	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("...");
		}
		return dest;
	}

}
