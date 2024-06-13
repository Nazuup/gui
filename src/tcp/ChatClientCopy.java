package tcp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClientCopy {
    public static void main(String[] args) {
        final String SERVER_IP = "localhost";
        final int SERVER_PORT = 12345;

        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT); // サーバーへの接続
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true); // サーバーへの出力用
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // サーバーからの入力用
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) { // 標準入力用

            System.out.println("Connected to server. You can start chatting.");

            // ユーザー入力をサーバーに送信
            String userInput;
            while ((userInput = stdIn.readLine()) != null) { // ユーザーの入力待ち（ブロック）
                out.println(userInput); // サーバーにメッセージを送信

                // サーバーからの応答を表示
                String serverResponse = in.readLine(); // サーバーからの応答待ち（ブロック）
                System.out.println("Server: " + serverResponse);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
