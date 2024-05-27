package jobs4u.base.presentation;


import jobs4u.base.followup.server.DisconnectRequest;
import jobs4u.base.followup.server.FollowUpMessageParser;
import jobs4u.base.followup.server.FollowUpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class FollowUpServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(FollowUpServer.class);

    private static class ClientHandler extends Thread {

        private Socket clientSocket;
        private final FollowUpMessageParser parser;

        public ClientHandler(final Socket socket, final FollowUpMessageParser parser) {
            this.clientSocket = socket;
            this.parser = parser;
        }

        @Override
        public void run(){
            final var clientIP = clientSocket.getInetAddress();
            LOGGER.debug("Accepted connection from {}:{}", clientIP.getHostAddress(), clientSocket.getPort());

            try (var out = new DataOutputStream(clientSocket.getOutputStream());
                 var in = new DataInputStream(clientSocket.getInputStream())){

                byte[] input = readMessage(in);



                while ((input != null) && (input.length > 0)){
                    LOGGER.debug("Received message:----\n{}\n----", input);
                    final FollowUpRequest request = parser.parse(input);
                    final byte[] response = request.execute();
                    out.write(response);
                    LOGGER.debug("Sent message:----\n{}\n----", response);
                    if (request.getClass().equals(DisconnectRequest.class)){
                        break;
                    }


                }
            } catch (final IOException e){
                LOGGER.error("ERROR OPENING SOCKET CONNECTION",e);
            } finally {
                try {
                    clientSocket.close();
                } catch (final IOException e){
                    LOGGER.error("ERROR CLOSING SOCKET CONNECTION",e);
                }
            }
        }

    }

    private final FollowUpMessageParser parser;

    public FollowUpServer(final FollowUpMessageParser parser){
        this.parser = parser;
    }

    private void listen(final int port){
        try (var serverSocket = new ServerSocket(port)){
            while (true){
                final var clientSocket = serverSocket.accept();
                new ClientHandler(clientSocket, parser).start();
            }
        } catch (final IOException e){
            LOGGER.error("ERROR OPENING SERVER SOCKET",e);
        }
    }

    public void start(final int port, final boolean wait){
        if (wait){
            listen(port);
        } else {
            new Thread(() -> listen(port)).start();
        }
    }

    private static byte [] readMessage(DataInputStream in) throws IOException {
        List<Byte> input = new ArrayList<>();
        byte b = in.readByte();
        while (b != -1){
            input.add(b);
            b = in.readByte();
        }
        final byte[] message = new byte[input.size()];
        for (int i = 0; i < input.size(); i++){
            message[i] = input.get(i);
        }
        return message;
    }
}