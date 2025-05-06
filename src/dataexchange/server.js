const grpc = require('@grpc/grpc-js');
const protoLoader = require('@grpc/proto-loader');
const PROTO_PATH = './dataexchange.proto';

// Sample dataset stored on the server
const serverDataset = [
  { id: '1', content: 'First data item from Node.js server' },
  { id: '2', content: 'Second data item from Node.js server' }
];

// Load the protobuf
const packageDefinition = protoLoader.loadSync(PROTO_PATH, {
  keepCase: true,
  longs: String,
  enums: String,
  defaults: true,
  oneofs: true
});

const dataProto = grpc.loadPackageDefinition(packageDefinition).dataexchange;

// Implement the service
const server = new grpc.Server();

server.addService(dataProto.DataService.service, {
  GetData: (call, callback) => {
    const requestId = call.request.request_id;
    const dataItem = serverDataset.find(item => item.id === requestId) || 
                    { id: 'not-found', content: 'Requested data not found' };
    callback(null, dataItem);
  },
  
  SendData: (call, callback) => {
    const receivedData = call.request;
    console.log(`Received data from client: ID=${receivedData.id}, Content=${receivedData.content}`);
    
    // Store the received data in our dataset
    serverDataset.push({
      id: receivedData.id,
      content: receivedData.content
    });
    
    callback(null, { success: true, message: 'Data stored successfully' });
  }
});

// Start the server
server.bindAsync(
  '0.0.0.0:50051',
  grpc.ServerCredentials.createInsecure(),
  (err, port) => {
    if (err) {
      console.error(err);
      return;
    }
    server.start();
    console.log(`Node.js gRPC server running on port ${port}`);
  }
);