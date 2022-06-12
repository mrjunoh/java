package 자바폴더;
import java.io.*;
import java.net.*;
import java.util.*;
public class clientEx {
    public static void main(String[] args){
        BufferedReader in=null;
		BufferedWriter out=null;
		Socket socket=null;
		Scanner sca=new Scanner(System.in);
		try {
			socket=new Socket("localhost",9999);//클라이언트 소켓 생성 서버의 ip 포트번호 입력
			System.out.println("서버에 접속하였습니다.");
			in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			//서버로부터 받은 데이터를 읽을 스트림 객체 생성
			out=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			//서버로 보낼 데이터를 스트림 객체 생성
			while(true) {
				System.out.print("클라이언트>>");
				String outputMessage=sca.nextLine();
				if(outputMessage.equalsIgnoreCase("bye")) {
					out.write(outputMessage+"\n");
					out.flush();
					System.out.println("연결을 종료합니다.");
					break;
				}
				out.write(outputMessage+"\n");//입력한 문자를 서버로 보냄
				out.flush();
				String inputMessage=in.readLine();//서버로부터 받은 데이터를 한개씩 읽음
				System.out.println("서버 : "+inputMessage);//받은 데이터 클라이언트 화면에 출력
			}
		}catch(IOException e) {
			System.out.println(e.getMessage());
		}finally {
			try {
				sca.close();
				if(socket!=null) socket.close();
			}catch(IOException e) {
				System.out.println("오류 발생");
			}
		}
    }
}
