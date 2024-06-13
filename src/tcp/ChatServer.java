package tcp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
    public static void main(String[] args) {
        final int PORT = 12345;

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is running and waiting for connections...");

            // クライアントからの接続を待ち、接続があれば新しいスレッドを開始
            while (true) {
                Socket clientSocket = serverSocket.accept(); // クライアントからの接続待ち（ブロック）
                System.out.println("Client connected: " + clientSocket);

                // 新しいスレッドを開始し、クライアントハンドラを実行
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                new Thread(clientHandler).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // クライアントハンドラクラス
    static class ClientHandler implements Runnable {
        private Socket clientSocket;
        private PrintWriter out; // クライアントへの出力用
        private BufferedReader in; // クライアントからの入力用

        // コンストラクタ
        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try {
                // クライアントからの入出力ストリームを作成
                out = new PrintWriter(clientSocket.getOutputStream(), true); // 自動フラッシュ有効
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                // クライアントからのメッセージを受信し、全クライアントにブロードキャスト
                String inputLine;
                while ((inputLine = in.readLine()) != null) { // クライアントからのメッセージ待ち（ブロック）
                    System.out.println("Client: " + inputLine);
                    // クライアントからのメッセージを全クライアントに送信
                    out.println("Server: " + inputLine);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    // 接続を閉じる
                    clientSocket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
