import java.io.*;
import java.net.*;
import java.util.*;
class MyThread extends Thread{
	private BufferedWriter out;
	private BufferedReader in;
	private Socket socket;
	public MyThread(Socket socket){
		this.socket=socket;
	}
	public void run(){
		try{
			in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			while(true){
				
				String inputMessage=in.readLine();
				if(inputMessage.equalsIgnoreCase("bye")){
					System.out.println("종료");
					break;
				}
				String res=calc(inputMessage);
				System.out.println("클라이언트 : "+inputMessage+"="+res+"\n");
				out.write(inputMessage+"="+res+"\n");
				out.flush();
				
			}
			
		}
		catch(Exception e){
			System.out.println("마이스레드 오류"+e.getMessage());
		}
	}
	public static String calc(String exp){
		StringTokenizer st=new StringTokenizer(exp," ");
		if(st.countTokens()!=3) return "error";

		String res="";
		int op1=Integer.parseInt(st.nextToken());
		String opcode=st.nextToken();
		int op2=Integer.parseInt(st.nextToken());
		switch(opcode){
			case "+": res=Integer.toString(op1+op2); break;
			case "-": res=Integer.toString(op1-op2); break;
			case "*": res=Integer.toString(op1*op2); break;
			default: res="error";
		}
		return res;
	}
}
public class Serverex {
	
    public static void main(String[] args) {
		ServerSocket listen=null;//서버 소켓
    	Socket socket=null;
		//Scanner sca=new Scanner(System.in);
		System.out.println("서버 입니다..");
		
		try {
			listen=new ServerSocket(9999);
			while(true)
			{
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
