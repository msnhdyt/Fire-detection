# reference: https://github.com/Rocksus/object-detection-hls/blob/master/generator/index.js
const HLSServer = require('hls-server');
const http = require('http');
const path = require('path');
const PORT = 8000

const server = http.createServer();
const hls = new HLSServer(server, {
    path: '/streams',
    dir: path.join(__dirname, 'streams'),
});

console.log("HLS Server Running on Port "+PORT+"...");
server.listen(PORT);
