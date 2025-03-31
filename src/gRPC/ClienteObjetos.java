package gRPC;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.util.Scanner;
import com.ejemplo.grpc.ServicioObjetosGrpc;
import com.ejemplo.grpc.ObjetoDistribuidoProto;
import com.google.protobuf.ByteString;

public class ClienteObjetos {
    private final ManagedChannel canal;
    private final ServicioObjetosGrpc.ServicioObjetosBlockingStub bloqueanteStub;

    public ClienteObjetos(String host, int puerto) {
        this.canal = ManagedChannelBuilder.forAddress(host, puerto)
                .usePlaintext()
                .build();
        this.bloqueanteStub = ServicioObjetosGrpc.newBlockingStub(canal);
    }

    public void almacenarObjeto(String id, String tipo, byte[] datos) {
        ObjetoDistribuidoProto.ObjetoRequest request = ObjetoDistribuidoProto.ObjetoRequest.newBuilder()
                .setId(id)
                .setTipo(tipo)
                .setDatos(ByteString.copyFrom(datos))
                .build();

        ObjetoDistribuidoProto.Respuesta respuesta = bloqueanteStub.almacenarObjeto(request);
        System.out.println("Respuesta del servidor: " + respuesta.getMensaje());
    }

    public byte[] recuperarObjeto(String id) {
        ObjetoDistribuidoProto.ConsultaRequest request = ObjetoDistribuidoProto.ConsultaRequest.newBuilder()
                .setId(id)
                .build();

        ObjetoDistribuidoProto.ObjetoResponse response = bloqueanteStub.recuperarObjeto(request);
        return response.getDatos().toByteArray();
    }

    public void cerrar() {
        canal.shutdown();
    }

    public static void main(String[] args) {
        ClienteObjetos cliente = new ClienteObjetos("localhost", 8080);
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("1. Almacenar objeto");
            System.out.println("2. Recuperar objeto");
            System.out.print("Seleccione opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            if (opcion == 1) {
                System.out.print("Ingrese ID del objeto: ");
                String id = scanner.nextLine();
                System.out.print("Ingrese tipo del objeto: ");
                String tipo = scanner.nextLine();
                System.out.print("Ingrese datos del objeto: ");
                String datos = scanner.nextLine();

                cliente.almacenarObjeto(id, tipo, datos.getBytes());
            } else if (opcion == 2) {
                System.out.print("Ingrese ID del objeto a recuperar: ");
                String id = scanner.nextLine();

                byte[] datos = cliente.recuperarObjeto(id);
                System.out.println("Datos recuperados: " + new String(datos));
            }
        } finally {
            cliente.cerrar();
            scanner.close();
        }
    }
}