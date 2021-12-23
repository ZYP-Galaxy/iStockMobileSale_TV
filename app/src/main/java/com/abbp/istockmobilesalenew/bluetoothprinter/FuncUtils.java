package com.abbp.istockmobilesalenew.bluetoothprinter;

import android.os.Environment;

import com.rt.printerlibrary.bean.PrinterStatusBean;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author
 *数据转换工具
 */
public class FuncUtils {
	//-------------------------------------------------------
	// 判断奇数或偶数，位运算，最后一位是1则为奇数，为0是偶数
	static public int isOdd(int num) {
		return num & 0x1;
	}

	//-------------------------------------------------------
	static public int HexToInt(String inHex)//Hex字符串转int
	{
		return Integer.parseInt(inHex, 16);
	}

	//-------------------------------------------------------
	static public byte HexToByte(String inHex)//Hex字符串转byte
	{
		return (byte) Integer.parseInt(inHex, 16);
	}

	//-------------------------------------------------------
	static public String Byte2Hex(Byte inByte)//1字节转2个Hex字符
	{
		return String.format("%02x", inByte).toUpperCase();
	}

	//-------------------------------------------------------
	static public String ByteArrToHex(byte[] inBytArr)//字节数组转转hex字符串
	{
		StringBuilder strBuilder = new StringBuilder();
		int j = inBytArr.length;
		for (int i = 0; i < j; i++) {
			strBuilder.append(Byte2Hex(inBytArr[i]));
			strBuilder.append(" ");
		}
		return strBuilder.toString();
	}

	//-------------------------------------------------------
	static public String ByteArrToHex(byte[] inBytArr, int offset, int byteCount)//字节数组转转hex字符串，可选长度
	{
		StringBuilder strBuilder = new StringBuilder();
		int j = byteCount;
		for (int i = offset; i < j; i++) {
			strBuilder.append(Byte2Hex(inBytArr[i]));
		}
		return strBuilder.toString();
	}

	//-------------------------------------------------------
	//转hex字符串转字节数组
	static public byte[] HexToByteArr(String inHex)//hex字符串转字节数组
	{
		int hexlen = inHex.length();
		byte[] result;
		if (isOdd(hexlen) == 1) {//奇数
			hexlen++;
			result = new byte[(hexlen / 2)];
			inHex = "0" + inHex;
		} else {//偶数
			result = new byte[(hexlen / 2)];
		}
		int j = 0;
		for (int i = 0; i < hexlen; i += 2) {
			result[j] = HexToByte(inHex.substring(i, i + 2));
			j++;
		}
		return result;
	}

	static public String ByteArrToHex2(byte[] inBytArr, int offset, int byteCount)//字节数组转转hex字符串，可选长度
	{
		StringBuilder strBuilder = new StringBuilder();
		int j = byteCount;
		for (int i = offset; i < j; i++) {
			strBuilder.append(Byte2Hex(inBytArr[i]));
			strBuilder.append(" ");
		}
		return strBuilder.toString();
	}

	/**
	 * 打印bts的Log
	 *
	 * @param bts
	 * @param groupPerCnt 每组个数
	 */
	static public void ByteArrToHexLog(byte[] bts, int groupPerCnt) {
//		LogUtils.e("data", "===============begin===================");
		System.out.print("===============begin===================");
		StringBuilder s = new StringBuilder("");
		int i = 0;
		int iend = 0;
		while (i < bts.length) {
			if (i + groupPerCnt < bts.length)
				iend = i + groupPerCnt;
			else
				iend = bts.length;
			s.append(ByteArrToHex2(bts, i, iend) + "\r\n");
			i += groupPerCnt;
		}
//		LogUtils.e("data", s.toString());
		System.out.print(s);
		System.out.print("===============end===================");
//		LogUtils.e("data", "===============end===================");

	}


	static public String PrinterStatusToStr(PrinterStatusBean StatusBean) {
		StringBuffer str = new StringBuffer("");
		switch (StatusBean.printStatusCmd) {
			case cmd_Print_RPP02N:
				if (StatusBean.blPrintSucc)
					str.append("Print ok\n"); //打印完成
				else {
					if (StatusBean.blNoPrinterHead)
						str.append("No Printer Head\n");//没有打印头
					if (StatusBean.blPrinting)
						str.append("Printing...\n");//正在打印中
					if (StatusBean.blOverHeated)
						str.append("Higher temperature\n"); //温度较高
					if (StatusBean.blHighervoltage)
						str.append("Higher voltage\n"); //温度较高
					if (StatusBean.blLowPower)
						str.append("low power\n"); //低电量
					if (StatusBean.blNoPaper)
						str.append("Out of paper\n"); //缺纸
					if (StatusBean.blNoflash)
						str.append("No flash\n"); //没有flash
					if (StatusBean.blPrintReady)
						str.append("The printer is ready\n"); //准备就绪
				}
				break;
		}
		return str.toString();
	}


	public static String geCurrTime() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date(System.currentTimeMillis());
		return simpleDateFormat.format(date);
	}

	public static void writeLog(String str) {
		String filePath = null;
		boolean hasSDCard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
		if (hasSDCard) {
			filePath =Environment.getExternalStorageDirectory().toString() + File.separator +"aa"+File.separator+"mylog.bin";
		} else
			filePath =Environment.getDownloadCacheDirectory().toString() + File.separator +"aa"+File.separator+"mylog.bin";

		try {

			File file = new File(filePath);
			if (!file.exists()) {
				File dir = new File(file.getParent());
				dir.mkdirs();
				file.createNewFile();
			}

			FileOutputStream outStream = new FileOutputStream(file);
			outStream.write(str.getBytes());
			outStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}