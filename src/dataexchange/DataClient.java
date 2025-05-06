package dataexchange;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataClient {
    private static final Logger logger = Logger.getLogger(DataClient.class.getName());

    private final ManagedChannel channel;
    private final DataServiceGrpc.DataServiceBlockingStub blockingStub;

    public DataClient(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build());
    }

    DataClient(ManagedChannel channel) {
        this.channel = channel;
        blockingStub = DataServiceGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public void getData(String requestId) {
        logger.info("Requesting data with ID: " + requestId);
        Dataexchange.DataRequest request = Dataexchange.DataRequest.newBuilder().setRequestId(requestId).build();
        Dataexchange.DataResponse response;
        try {
            response = blockingStub.getData(request);
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            return;
        }
        logger.info("Received from server: ID=" + response.getId() + ", Content=" + response.getContent());
    }

    public void sendData(String id, String content) {
        logger.info("Sending data to server: ID=" + id + ", Content=" + content);
        Dataexchange.DataMessage request = Dataexchange.DataMessage.newBuilder()
                .setId(id)
                .setContent(content)
                .build();
        Dataexchange.DataAck response;
        try {
            response = blockingStub.sendData(request);
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            return;
        }
        if (response.getSuccess()) {
            logger.info("Server acknowledged: " + response.getMessage());
        } else {
            logger.warning("Server failed to store data: " + response.getMessage());
        }
    }

    public static void main(String[] args) throws Exception {
        DataClient client = new DataClient("localhost", 50051);
        try {
            // Get data from server
            client.getData("1");
            client.getData("2");
            client.getData("3"); // This one doesn't exist

            // Send data to server
            client.sendData("3", "New data from Java client");

            // Verify the new data exists
            client.getData("3");
        } finally {
            client.shutdown();
        }
    }
}