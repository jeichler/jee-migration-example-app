package com.acme.anvil.service.socket;

import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.rmi.Remote;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/** some imports only to trigger cloud-readiness rules **/
import javax.xml.rpc.Service;
import com.sun.jna.Callback;


import javax.ejb.Stateless;

@Stateless
public class SocketCommunicationService implements Remote{

	public void someSocketStuff() {
		AsynchronousServerSocketChannel server;
		try {
			server = AsynchronousServerSocketChannel.open();
			server.bind(null);
			Future<AsynchronousSocketChannel> acceptFuture = server.accept();
			AsynchronousSocketChannel worker = acceptFuture.get(10, TimeUnit.SECONDS);
			System.out.println(worker.getClass());
			// some nonsense JNI stuff to trigger the rule
			System.loadLibrary("idontexist.so");
		} catch (Throwable e) {
			e.printStackTrace();
		}

	}

}
