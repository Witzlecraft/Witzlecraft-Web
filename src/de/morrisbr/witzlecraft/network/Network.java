package de.morrisbr.witzlecraft.network;

import java.util.UUID;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import de.morrisbr.witzlecraft.network.events.BankPayEvent;
import de.morrisbr.witzlecraft.network.events.VerifyEvent;
import de.morrisbr.witzlecraft.network.objects.EventPlayer;

public class Network {
    static public final int port = 54555;

    // This registers objects that are going to be sent over the network.
    static public void register(EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();

        kryo.register(BankPayEvent.class);
        kryo.register(VerifyEvent.class);
        kryo.register(EventPlayer.class);
    }
}
