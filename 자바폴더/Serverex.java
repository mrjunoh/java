package 자바폴더;
import java.io.*;
import java.net.*;
import java.util.*;
class MyThread extends Thread{
	
	BufferedReader in;
	BufferedWriter out;
	Socket socket;
	Vector<String> wordList=new Vector<String>();
	public MyThread(Socket socket){
		this.socket=socket;
	}
	public void readWordList(){
		//File file=new File("C:\\Users\\junoh park\\Desktop\\알파펫.txt");
		FileInputStream fileInputStream=null;
		BufferedReader br=null;
		String words;
		try{
			fileInputStream=new FileInputStream("C:\\Users\\junoh park\\Desktop\\알파펫.txt");
			br=new BufferedReader(new InputStreamReader(fileInputStream));
			while((words=br.readLine())!=null){
				wordList.add(words);
				System.out.println(words);
			}
		}
		catch(Exception e){
			System.out.println("파일 오픈 오류"+e.getMessage());
		}
	}
	public void run(){
		try{
			in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			while(true){
				String inputMessage=in.readLine();
				if(inputMessage.equalsIgnoreCase("bye")){
					System.out.println("종료 합니다.");
					socket.close();

					break;
				}
				if(wordList.contains(inputMessage)){
					System.out.println(inputMessage+"="+"YES"+"\n");
					out.write(inputMessage+"는"+"YES"+"\n");
					out.flush();
				}
				else{
					System.out.println(inputMessage+"="+"NO"+"\n");
					out.write(inputMessage+"는"+"NO"+"\n");
					out.flush();
				}
			}
		}catch(Exception e){
			System.out.println("마이스레드 오류"+e.getMessage());
		}
		
	}
	
}
public class Serverex {
	
	
	
    public static void main(String[] args) {
		//Scanner sca=new Scanner(System.in);
		ServerSocket listen=null;
		Socket socket=null;
		System.out.println("서버 입니다..");
		try {
			listen=new ServerSocket(9999);
			while(true){
				socket=listen.accept();
				System.out.println("연결되었습니다.");
				MyThread th=new MyThread(socket);
				th.start();
			}

		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			try{
				socket.close();
				listen.close();
			}
			catch(IOException e)
			{
				System.out.println("클라이언트와 채팅 중 오류 발생");
			}
			
		}
    }
}
