package io.geewit.snowflake.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * NetUtils
 *
 * @author geewit
 */
public abstract class NetUtils {

    /**
     * Pre-loaded local address
     */
    public static InetAddress localAddress;

    public static NetworkInterface network;

    static {
        try {
            network = getNetwork();
            localAddress = getLocalInetAddress(network);
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

        throw new RuntimeException("No validated local address!");
    }

    /**
     * Retrieve the first validated local ip address(the Public and LAN ip addresses are validated).
     *
     * @return the local address
     * @throws SocketException the socket exception
     */
    public static InetAddress getLocalInetAddress(NetworkInterface network) throws SocketException {
        Enumeration<InetAddress> addressEnumeration = network.getInetAddresses();
        while (addressEnumeration.hasMoreElements()) {
            InetAddress address = addressEnumeration.nextElement();

            // ignores all invalidated addresses
            if (address.isLinkLocalAddress() || address.isLoopbackAddress() || address.isAnyLocalAddress()) {
                continue;
            }

            return address;
        }

        throw new RuntimeException("No validated local address!");
    }

    /**
     * Retrieve local address
     *
     * @return the string local address
     */
    public static String getLocalAddress() {
        return localAddress.getHostAddress();
    }

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
                | (0xff0000000000L & ((long) mac_bytes[5] << (8 * 5)))
                | (0xff000000000000L & ((long) mac_bytes[6] << (8 * 6)))
                | (0xff00000000000000L & ((long) mac_bytes[7] << (8 * 7)));
    }
}
