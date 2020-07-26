package feedbackserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author Koda
 */
public class SharedSocket {

    public synchronized static void setSocket(Socket _socket) {
        synchronized (m_socketLock) {
            m_socket = _socket;
        }
    }

    public synchronized static Socket getSocket() {
        synchronized (m_socketLock) {
            return m_socket;
        }
    }

    public synchronized static void createDataInputStream(InputStream _is) {
        synchronized (m_socketLock) {
            m_dis = new DataInputStream(_is);
        }
    }

    public synchronized static DataInputStream getDataInputStream() {
        synchronized (m_socketLock) {
            return m_dis;
        }
    }

    public synchronized static void createDataOutputStream(OutputStream _os) {
        synchronized (m_socketLock) {
            m_dos = new DataOutputStream(_os);
        }
    }

    public synchronized static DataOutputStream getDataOutputStream() {
        synchronized (m_socketLock) {
            return m_dos;
        }
    }

    private static Object m_socketLock = new Object();
    private static Socket m_socket;
    private static DataInputStream m_dis;
    private static DataOutputStream m_dos;
}
