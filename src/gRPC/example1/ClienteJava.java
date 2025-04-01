package gRPC.example1;


import com.ejemplo.grpc.ProcesamientoProto;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import com.google.protobuf.ByteString;
import com.ejemplo.grpc.ProcesadorGrpc;

public class ClienteJava {
    public static void main(String[] args) {
        // Crear canal de comunicación
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        // Crear stub
        com.ejemplo.grpc.ProcesadorGrpc.ProcesadorBlockingStub stub = ProcesadorGrpc.newBlockingStub(channel);

        // Preparar solicitud
        ProcesamientoProto.ObjetoRequest request = ProcesamientoProto.ObjetoRequest.newBuilder()
                .setId("123")
                .setTipo("ejemplo")
                .setDatos(ByteString.copyFromUtf8("Hola desde Java"))
                .build();

        // Enviar y recibir respuesta
        ProcesamientoProto.ObjetoResponse response = stub.procesarObjeto(request);

        // Mostrar resultados
        System.out.println("Respuesta del servidor Python:");
        System.out.println("ID: " + response.getId());
        System.out.println("Éxito: " + response.getExito());
        System.out.println("Resultado: " + response.getResultado());

        // Cerrar canal
        channel.shutdown();
    }
}