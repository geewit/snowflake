package io.geewit.snowflake.utils;

import org.apache.commons.lang3.RandomUtils;

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
        byte[] macBytes = getMac();
        if(macBytes == null) {
            return RandomUtils.nextLong();
        }
        return (0xffL & (long) macBytes[0])
                | (0xff00L & ((long) macBytes[1] << 8))
                | (0xff0000L & ((long) macBytes[2] << (8 * 2)))
                | (0xff000000L & ((long) macBytes[3] << (8 * 3)))
                | (0xff00000000L & ((long) macBytes[4] << (8 * 4)))
                | (0xff0000000000L & ((long) macBytes[5] << (8 * 5)));
    }
}
