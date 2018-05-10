import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Main {

	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(12345);
			while(true) {
				Socket socket = serverSocket.accept();
				new Thread() {
					@Override
					public void run() {
						try {
							InputStream is = socket.getInputStream();
							File f = new File("saved.txt");
							if(!f.exists())
								f.createNewFile();
							OutputStream os = new FileOutputStream(f);
							//TODO 读取ｊｓｏｎ格式的信息
							/**
							 * json 格式如下：
							 * ｛＂filename":"DIYdataV4.zip.02-233cccx",
							 * 		"step":"500",
							 * 		"filelength":"123123123"
							 * }
							 * 
							 */
							byte[] buf = new byte[1024*1024];
							
							int len = 0;
							while((len=is.read(buf))>0) {
								os.write(buf, 0, len);
								os.flush();
							}
							os.close();
							is.close();
							
							System.out.println("接收完毕");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
				}.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	}

	public void receiveData(String filename,long filelength, int ip, int port) throws Exception{
		String parentPath = "~/Develop/ReceiveData/";
		File folder = new File(parentPath);
		if(!folder.exists())
			folder.mkdirs();
		
		File saveFile = new File(parentPath+filename);
		OutputStream fos = new FileOutputStream(saveFile);
		
		Socket socket = new Socket(ip + "", port);
		long sendedlength = 0;
		InputStream is = socket.getInputStream();
		byte[] buf = new byte[1024*1024];
		int len = 0;
		long currentTime = System.currentTimeMillis();
		long lastTime = System.currentTimeMillis();
		long remainMillsTime = 0;
		while ((len = is.read(buf)) > 0) {
			currentTime = System.currentTimeMillis();
			sendedlength+=len;
			fos.write(buf, 0, len);
			remainMillsTime = (filelength-sendedlength)/(currentTime-lastTime);
			lastTime = currentTime;
			System.out.println(filename+" \t接收了 "+(sendedlength*1.0/1024/1024) +" Mb;"
					+ "还剩:"+((filelength-sendedlength)*1.0/1024/1024)+"Mb;"
					+ "估计剩余时间:"+(remainMillsTime*1.0/1000)+"秒");
		
			fos.flush();
		}
		System.out.println(filename+"\t接收完毕");
		is.close();
		fos.close();
		socket.close();
	}

}
