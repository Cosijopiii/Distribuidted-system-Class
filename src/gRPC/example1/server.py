# server.py
from concurrent import futures
import grpc
import procesamiento_pb2
import procesamiento_pb2_grpc

class ProcesadorServicer(procesamiento_pb2_grpc.ProcesadorServicer):
    def ProcesarObjeto(self, request, context):
        print(f"Recibido objeto ID: {request.id}, Tipo: {request.tipo}")

        # Procesamiento de ejemplo (invertir los datos)
        datos_procesados = request.datos[::-1].decode('utf-8')

        return procesamiento_pb2.ObjetoResponse(
            id=request.id,
            exito=True,
            resultado=f"Datos procesados: {datos_procesados}"
        )

def servir():
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
    procesamiento_pb2_grpc.add_ProcesadorServicer_to_server(
        ProcesadorServicer(), server)
    server.add_insecure_port('[::]:50051')
    server.start()
    print("Servidor Python escuchando en puerto 50051...")
    server.wait_for_termination()

if __name__ == '__main__':
    servir()