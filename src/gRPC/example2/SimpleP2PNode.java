package gRPC.example2;

import com.ejemplo.grpc.SimpleP2PProto;
import com.ejemplo.grpc.SimpleP2PGrpc;
import io.grpc.*;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.Scanner;

// 192.168.1.113

public class SimpleP2PNode {
    private final String nodeName;
    private final int port;
    private Server server;
    private SimpleP2PGrpc.SimpleP2PBlockingStub peerStub;

    public SimpleP2PNode(String nodeName, int port) {
        this.nodeName = nodeName;
        this.port = port;
    }

    public void start() throws IOException {
        // Iniciar servidor
        server = ServerBuilder.forPort(port)
                .addService(new SimpleP2PService())
                .build()
                .start();

        System.out.println("Nodo " + nodeName + " escuchando en puerto " + port);

        // Mantener el nodo activo
        new Thread(() -> {
            try {
                server.awaitTermination();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void connectToPeer(String peerAddress) {
        ManagedChannel channel = ManagedChannelBuilder.forTarget(peerAddress)
                .usePlaintext()
                .build();
        peerStub = SimpleP2PGrpc.newBlockingStub(channel);
        System.out.println("Conectado a peer en: " + peerAddress);
    }

    private class SimpleP2PService extends SimpleP2PGrpc.SimpleP2PImplBase {
        @Override
        public void sendMessage(SimpleP2PProto.SimpleMessage request, StreamObserver<SimpleP2PProto.SimpleAck> responseObserver) {
            System.out.println("\n[Mensaje recibido de " + request.getSender() + "] " + request.getText());
            responseObserver.onNext(SimpleP2PProto.SimpleAck.newBuilder().setReceived(true).build());
            responseObserver.onCompleted();
        }

        @Override
        public void ping(SimpleP2PProto.Empty request, StreamObserver<SimpleP2PProto.Pong> responseObserver) {
            responseObserver.onNext(SimpleP2PProto.Pong.newBuilder().setStatus("Activo").build());
            responseObserver.onCompleted();
        }
    }

    public void sendMessage(String message) {
        if (peerStub != null) {
            SimpleP2PProto.SimpleMessage request = SimpleP2PProto.SimpleMessage.newBuilder()
                    .setText(message)
                    .setSender(nodeName)
                    .build();

            SimpleP2PProto.SimpleAck response = peerStub.sendMessage(request);
            System.out.println("Confirmación recibida: " + response.getReceived());
        } else {
            System.out.println("No hay peers conectados!");
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Nombre del nodo: ");
        String name = scanner.nextLine();

        System.out.print("Puerto local: ");
        int port = scanner.nextInt();
        scanner.nextLine();  // Limpiar buffer

        SimpleP2PNode node = new SimpleP2PNode(name, port);
        node.start();

        System.out.print("Dirección del peer a conectar (ej. localhost:50051): ");
        String peerAddress = scanner.nextLine();
        if (!peerAddress.isEmpty()) {
            node.connectToPeer(peerAddress);
        }

        while (true) {
            System.out.print("Mensaje a enviar (o 'exit' para salir): ");
            String msg = scanner.nextLine();

            if (msg.equalsIgnoreCase("exit")) {
                break;
            }

            node.sendMessage(msg);
        }

        scanner.close();
        System.exit(0);
    }
}

// mvn exec:java -Dexec.mainClass="com.ejemplo.p2p.simple.SimpleP2PNode"