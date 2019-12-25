package io.geewit.snowflake.utils;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * NetUtils
 *
 * @author geewit
 */
public class NetUtils {
    public static NetworkInterface network;

    static {
        try {
            network = getNetwork();
        } catch (SocketException e) {
            throw new RuntimeException("fail to get local ip.");
        }
    }

    public static NetworkInterface getNetwork() throws SocketException {
        // enumerates all network interfaces
        Enumeration<NetworkInterface> enu = NetworkInterface.getNetworkInterfaces();

        while (enu.hasMoreElements()) {
            NetworkInterface network = enu.nextElement();
            if (network.isLoopback()) {
                continue;
            }

            return network;
        }

        throw new RuntimeException("No validated local network!");
    }

    /**
     * Retrieve local mac
     *
     * @return the string local mac
     */
    public static byte[] getMac() throws SocketException {
        return network.getHardwareAddress();
    }

    public static long getLongMac() throws SocketException {
        byte[] mac_bytes = getMac();
        return (0xffL & (long) mac_bytes[0])
                | (0xff00L & ((long) mac_bytes[1] << 8))
                | (0xff0000L & ((long) mac_bytes[2] << (8 * 2)))
                | (0xff000000L & ((long) mac_bytes[3] << (8 * 3)))
                | (0xff00000000L & ((long) mac_bytes[4] << (8 * 4)))
                | (0xff0000000000L & ((long) mac_bytes[5] << (8 * 5)));
    }
}
