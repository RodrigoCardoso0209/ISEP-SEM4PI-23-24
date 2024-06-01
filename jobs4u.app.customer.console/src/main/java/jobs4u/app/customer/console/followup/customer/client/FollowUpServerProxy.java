package jobs4u.app.customer.console.followup.customer.client;


import jobs4u.base.jobOpeningsManagement.domain.JobOpeningDTO;
import jobs4u.base.utils.ClientCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;



public class FollowUpServerProxy {


    protected final static byte DATA1_LEN_L = 1;
    protected final static byte DATA1_LEN_M = 1;
    protected final static byte DATA2_LEN_L = 1;
    protected final static byte DATA_LEN_M = 1;

    protected final static byte VERSION = 1;

    protected final static byte COMM_TEST = 0;
    protected final static byte DISCONN = 1;
    protected final static byte ACK = 2;
    protected final static byte ERR = 3;
    protected final static byte AUTH = 4;

    protected final static String DEI_IP = "10.9.20.231";
    protected final static String ALT_IP = "127.0.0.1";
    protected final static int DEI_PORT = 9999;



    protected final static int DATA1_PREFIX = 4;
    protected final static int DATA2_PREFIX = DATA1_LEN_M * 256 + DATA1_LEN_L + 2;

    private static final Logger LOGGER = LoggerFactory.getLogger(FollowUpServerProxy.class);


    private boolean authenticated = false;

    private static class ClientSocket {

        private Socket sock;
        private DataOutputStream sOut;
        private DataInputStream in;




        public void connect(final String address, final int port) throws IOException {
            System.out.println(1);

            try {
                sock = new Socket(InetAddress.getByName(address), port);
                System.out.println(2);
                sOut = new DataOutputStream(sock.getOutputStream());
                sOut.flush();

                System.out.println();

                in = new DataInputStream(sock.getInputStream());

                System.out.println("connected to " + address + " on port " + port);
                LOGGER.debug("Connected to {}", address);
            } catch (IOException e) {
                LOGGER.error("Failed to connect to {}", address);
                throw e;
            }

        }

        public void send(final byte[] request) throws IOException {

            if(in == null) {
                System.out.println("in is null");
            }
            if(sOut == null) {
                System.out.println("sOut is null");
            }

            sOut.write(request);
            LOGGER.debug("Sent message\n-----\n{}\n-----", request);
        }

        /*public boolean auth(String username, String password) throws IOException {
            final var auth = new byte[4+ DATA1_LEN_L + DATA1_LEN_M * 256 + DATA2_LEN_L + DATA_LEN_M * 256];
            auth[0] = VERSION;
            auth[1] = AUTH;
            auth[2] = DATA1_LEN_L;
            auth[3] = DATA1_LEN_M;

            // Ensure that username and password do not exceed their respective lengths
            int usernameLength = Math.min(username.length(), DATA1_LEN_M * 256 + DATA1_LEN_L);
            int passwordLength = Math.min(password.length(), DATA2_LEN_L + DATA_LEN_M * 256);

            auth[DATA1_PREFIX - 2] = DATA2_LEN_L;
            auth[DATA1_PREFIX - 1] = DATA_LEN_M;

            System.arraycopy(username.getBytes(), 0, auth, DATA1_PREFIX, usernameLength);
            System.arraycopy(password.getBytes(), 0, auth, DATA2_PREFIX, passwordLength);

            System.out.println("Sending authentication request");
            final var socket = new ClientSocket();
            System.out.println("Connecting to DEI server");

            socket.connect(ALT_IP, DEI_PORT);
            System.out.println(in == null);
            System.out.println(sOut == null);
            System.out.println("Connected to DEI server");
            System.out.println(auth.length);
            for (byte b : auth)
                System.out.println(b);
            send(auth);

            byte [] response = recv();

            if (response[1] == ACK) {
                authenticated = true;
                stop();
                return true;
            } else {
                authenticated = false;
                LOGGER.error("Authentication failed");
                stop();
                return false;
            }
        }*/

        public void stop() throws IOException {
            in.close();
            sOut.close();
            sock.close();
        }

        public byte[] recv() throws IOException {

            final List<Byte> resp = new ArrayList<Byte>();

            while (in.available() > 0){
                resp.add(in.readByte());
            }

            byte[] response = new byte[resp.size()];

            for (int i = 0; i < resp.size(); i++) {
                response[i] = resp.get(i);
            }

            return response;
        }
    }

    private boolean auth(String username, String password) throws IOException {
        final var auth = new byte[4+ DATA1_LEN_L + DATA1_LEN_M * 256 + DATA2_LEN_L + DATA_LEN_M * 256];
        auth[0] = VERSION;
        auth[1] = AUTH;
        auth[2] = DATA1_LEN_L;
        auth[3] = DATA1_LEN_M;

        // Ensure that username and password do not exceed their respective lengths
        int usernameLength = Math.min(username.length(), DATA1_LEN_M * 256 + DATA1_LEN_L);
        int passwordLength = Math.min(password.length(), DATA2_LEN_L + DATA_LEN_M * 256);

        auth[DATA1_PREFIX - 2] = DATA2_LEN_L;
        auth[DATA1_PREFIX - 1] = DATA_LEN_M;

        System.arraycopy(username.getBytes(), 0, auth, DATA1_PREFIX, usernameLength);
        System.arraycopy(password.getBytes(), 0, auth, DATA2_PREFIX, passwordLength);

        //System.out.println("Sending authentication request");
        final var socket = new ClientSocket();
        //System.out.println("Connecting to DEI server");

        socket.connect(ALT_IP, DEI_PORT);

        //System.out.println("Connected to DEI server");
        System.out.println(auth.length);

        socket.send(auth);

        byte [] response = socket.recv();
        System.out.println(response.length);

        if (response[1] == ACK) {
            authenticated = true;
            socket.stop();
            return true;
        } else {
            authenticated = false;
            LOGGER.error("Authentication failed");
            socket.stop();
            return false;
        }
    }

    public Iterable<JobOpeningDTO> getJobOpeningsForCustomer(final ClientCode code)
            throws IOException {
        final var socket = new ClientSocket();
        auth("customer@gmail.com","Password1");

        final  byte[] request = new GetJobOpeningForCustomerDTO(code).toRequest();
        socket.send(request);
        final byte[] response = socket.recv();

        socket.stop();

        final MarshlerUnmarshler mu = new MarshlerUnmarshler();
        return mu.parseResponseMessageGetAvailableMeals(response);
    }



}
