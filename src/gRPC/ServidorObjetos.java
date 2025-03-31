package gRPC;

import com.google.protobuf.ByteString;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import com.ejemplo.grpc.ObjetoDistribuidoProto;
import com.ejemplo.grpc.ServicioObjetosGrpc;

public class ServidorObjetos {
    private final int puerto;
    private final Server servidor;
    private final ConcurrentHashMap<String, ObjetoDistribuidoProto.ObjetoResponse> almacen = new ConcurrentHashMap<>();

    public ServidorObjetos(int puerto) {
        this.puerto = puerto;
        this.servidor = ServerBuilder.forPort(puerto)
                .addService(new ServicioObjetosImpl())
                .build();
    }

    public void iniciar() throws IOException {
        servidor.start();
        System.out.println("Servidor iniciado, escuchando en " + puerto);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("Apagando servidor...");
            ServidorObjetos.this.detener();
            System.err.println("Servidor apagado.");
        }));
    }

    public void detener() {
        if (servidor != null) {
            servidor.shutdown();
        }
    }

    private void esperarTerminacion() throws InterruptedException {
        if (servidor != null) {
            servidor.awaitTermination();
        }
    }

    private class ServicioObjetosImpl extends ServicioObjetosGrpc.ServicioObjetosImplBase {
        @Override
        public void almacenarObjeto(ObjetoDistribuidoProto.ObjetoRequest request,
                                    StreamObserver<ObjetoDistribuidoProto.Respuesta> responseObserver) {

            ObjetoDistribuidoProto.ObjetoResponse objeto = ObjetoDistribuidoProto.ObjetoResponse.newBuilder()
                    .setId(request.getId())
                    .setTipo(request.getTipo())
                    .setDatos(request.getDatos())
                    .build();

            almacen.put(request.getId(), objeto);

            ObjetoDistribuidoProto.Respuesta respuesta = ObjetoDistribuidoProto.Respuesta.newBuilder()
                    .setExito(true)
                    .setMensaje("Objeto almacenado correctamente")
                    .build();

            responseObserver.onNext(respuesta);
            responseObserver.onCompleted();
        }

        @Override
        public void recuperarObjeto(ObjetoDistribuidoProto.ConsultaRequest request,
                                    StreamObserver<ObjetoDistribuidoProto.ObjetoResponse> responseObserver) {

            ObjetoDistribuidoProto.ObjetoResponse objeto = almacen.get(request.getId());

            if (objeto == null) {
                objeto = ObjetoDistribuidoProto.ObjetoResponse.newBuilder()
                        .setId("")
                        .setTipo("")
                        .setDatos(ByteString.EMPTY)
                        .build();
            }

            responseObserver.onNext(objeto);
            responseObserver.onCompleted();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        ServidorObjetos servidor = new ServidorObjetos(8080);
        servidor.iniciar();
        servidor.esperarTerminacion();
    }
}