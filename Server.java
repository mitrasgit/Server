import javax.net.ssl.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
	public static void main(String[] args){
		System.setProperty("javax.net.debug","all");
		System.setProperty("javax.net.ssl.keyStore", "D:\\Dokument\\1516-KTH\\Intnet\\Labb6v2\\keystore.jks");
		System.setProperty("javax.net.ssl.keyStorePassword", "password");

		//bygg SSLserversocket	
		SSLServerSocketFactory ssf =
			(SSLServerSocketFactory)SSLServerSocketFactory.getDefault();
		
		System.out.println("Stöder:"); //cipher suites som servern kan använda för att sätta upp connection
		for(int i = 0; i < ssf.getSupportedCipherSuites().length; i++)
			System.out.println(ssf.getSupportedCipherSuites()[i]);
		
		SSLServerSocket ss = null;
		try{
			InetAddress ia = InetAddress.getByName("localhost"); //server hello redo
			ss = (SSLServerSocket)ssf.createServerSocket(8080, 0, ia);

			System.out.println("Vald:"); //cipher suites som även clienten kan använda
			for(int i = 0; i < ss.getEnabledCipherSuites().length; i++)
				System.out.println(ss.getEnabledCipherSuites()[i]);

			while(true) {
				//ta emot clientens sslsocket
				SSLSocket s = (SSLSocket)ss.accept();
				//client hello, certifikatutbyte/verifiering, keys, kommer överens om cipher spec
				PrintWriter pw = new PrintWriter(s.getOutputStream());
				//handshake klar, skickar krypterat data (symmetriskt?)
				pw.println("<h1>Wazzup World</h1>");
				pw.flush();
				pw.close();
				s.close();
			}
		}
		catch(IOException e){
			System.out.println(e.getMessage());
		}
	}
}