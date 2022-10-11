package com.smiles;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExpDBServerApplication {

    public static void main(String[] args)
            throws IOException, InterruptedException {
        SpringApplication.run(ExpDBServerApplication.class, args);
        System.out.print("Started the spring server");
        // The default server port
        int localport = 50051;
        Server server = null;
        try {
            System.out.println("TomCat is Running Successfully");
            System.out.println(
                    "Starting the gRPC services for SMILES Data Catalog (Experimental Database)");
            server = ServerBuilder.forPort(localport).addService(new MoleculeImpl()).build();
            server.start();
            Thread.sleep(1000);
            System.out.println(
                    "\n \n -> -> -> -> Started gRPC services for an Experimental database at port :: localhost:" +
                            localport + " <- <- <- <-");
        } catch (Exception e) {
            System.out.println(
                    " -- -- -- -- Unable to start the gRPC services -- -- -- --");
        }

        Server finalServer = server;
        Runtime
                .getRuntime()
                .addShutdownHook(
                        new Thread(
                                () -> {
                                    try {
                                        Thread.sleep(1000);
                                        System.out.println("\n \nReceived Shutdown Request");
                                        assert finalServer != null;
                                        Thread.sleep(1000);
                                        (finalServer).shutdown();
                                        System.out.println("\n -> -> -> Successfully stopped the gRPC services <- <- <-");
                                    } catch (InterruptedException e) {
                                        throw new RuntimeException(e);
                                    }
                                }));
        assert server != null;
        server.awaitTermination();
    }
}
