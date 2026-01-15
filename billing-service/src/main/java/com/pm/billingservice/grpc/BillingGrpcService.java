package com.pm.billingservice.grpc;


import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
public class BillingGrpcService  extends BillingServiceGrpc.BillingServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(BillingGrpcService.class);

    @Override
    public void createBillingAccount(BillingRequest billingRequest,
                                     StreamObserver<BillingResponse> streamObserver
                                     ){
        log.info("create billing account {}", billingRequest.toString());

        BillingResponse response = BillingResponse.newBuilder()
                .setAccountId("1234")
                .setStatus("ACTIVE")
                .build();

        streamObserver.onNext(response);
        streamObserver.onCompleted();

    }
}
