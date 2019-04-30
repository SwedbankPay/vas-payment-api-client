package com.payex.vas.demo.service;

import com.payex.vas.demo.domain.dto.GenericPaymentRequest;
import com.payex.vas.demo.domain.dto.GenericPaymentResponse;
import com.payex.vas.demo.domain.entities.PaymentInstrument;
import com.payex.vas.demo.domain.entities.PaymentOperation;
import com.payex.vas.demo.domain.entities.SimulatedMerchant;
import com.payex.vas.demo.domain.payex.base.AccountIdentifier;
import com.payex.vas.demo.domain.payex.base.Merchant;
import com.payex.vas.demo.domain.payex.request.BalanceRequest;
import com.payex.vas.demo.domain.payex.request.OperationRequest;
import com.payex.vas.demo.domain.payex.request.PaymentRequest;
import com.payex.vas.demo.domain.payex.request.TransactionRequest;
import com.payex.vas.demo.domain.payex.response.PaymentAccountResponse;
import com.payex.vas.demo.repository.PaymentInstrumentRepository;
import com.payex.vas.demo.repository.PaymentOperationRepository;
import com.payex.vas.demo.repository.SimulatedMerchantRepository;
import com.payex.vas.demo.repository.external.VasPaymentApiRepository;
import com.payex.vas.demo.service.helper.GenericPaymentResponseBuilder;
import com.payex.vas.demo.service.helper.PaymentOperationBuilder;
import com.payex.vas.demo.util.error.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentOperationRepository paymentOperationRepository;
    private final PaymentInstrumentRepository paymentInstrumentRepository;
    private final SimulatedMerchantRepository simulatedMerchantRepository;

    private final VasPaymentApiRepository vasPaymentApiRepository;


    public PaymentAccountResponse balance(PaymentInstrument paymentInstrument, String agreementId) {

        var balanceRequest = BalanceRequest.builder()
            .accountIdentifier(buildAccountIdentifier(paymentInstrument))
            .build();

        return vasPaymentApiRepository.balance(balanceRequest, agreementId);
    }

    public GenericPaymentResponse deposit(Long paymentInstrumentId, GenericPaymentRequest request) {
        var paymentInstrument = findPaymentInstrument(paymentInstrumentId);
        var merchant = findMerchant(request.getMerchantId());

        var paymentRequest = buildPaymentRequest(request, paymentInstrument, merchant);
        var paymentResponse = vasPaymentApiRepository.deposit(paymentRequest, paymentInstrument.getExternalAccountId(), merchant.getAgreementId());

        var paymentOperation = PaymentOperationBuilder.build("Deposit", paymentInstrument, merchant.getId(), paymentResponse);

        paymentOperationRepository.save(paymentOperation);

        return GenericPaymentResponseBuilder.convertToGenericPaymentResponse(paymentOperation);
    }

    public GenericPaymentResponse authorize(Long paymentInstrumentId, GenericPaymentRequest request) {
        var paymentInstrument = findPaymentInstrument(paymentInstrumentId);
        var merchant = findMerchant(request.getMerchantId());

        var paymentRequest = buildPaymentRequest(request, paymentInstrument, merchant);
        var paymentResponse = vasPaymentApiRepository.authorize(paymentRequest, paymentInstrument.getExternalAccountId(), merchant.getAgreementId());

        var paymentOperation = PaymentOperationBuilder.build("Authorize", paymentInstrument, merchant.getId(), paymentResponse);

        paymentOperationRepository.save(paymentOperation);

        return GenericPaymentResponseBuilder.convertToGenericPaymentResponse(paymentOperation);
    }

    public GenericPaymentResponse purchase(Long paymentInstrumentId, GenericPaymentRequest request) {
        var paymentInstrument = findPaymentInstrument(paymentInstrumentId);
        var merchant = findMerchant(request.getMerchantId());

        var paymentRequest = buildPaymentRequest(request, paymentInstrument, merchant);
        var paymentResponse = vasPaymentApiRepository.purchase(paymentRequest, paymentInstrument.getExternalAccountId(), merchant.getAgreementId());

        var paymentOperation = PaymentOperationBuilder.build("Purchase", paymentInstrument, merchant.getId(), paymentResponse);

        paymentOperationRepository.save(paymentOperation);

        return GenericPaymentResponseBuilder.convertToGenericPaymentResponse(paymentOperation);
    }

    public GenericPaymentResponse capture(Long paymentInstrumentId, Long paymentOperationId) {
        var orgPaymentOperation = findPaymentOperation(paymentOperationId);
        var paymentInstrument = findPaymentInstrument(paymentInstrumentId);
        var merchant = findMerchant(orgPaymentOperation.getMerchantId());

        var paymentRequest = buildTransactionRequest("Capture", orgPaymentOperation);
        var paymentResponse = vasPaymentApiRepository.capture(paymentRequest,
            paymentInstrument.getExternalAccountId(),
            orgPaymentOperation.getExternalPaymentId(),
            merchant.getAgreementId());

        var paymentOperation = PaymentOperationBuilder.build("Capture", paymentInstrument, merchant.getId(), paymentResponse);

        paymentOperationRepository.save(paymentOperation);

        return GenericPaymentResponseBuilder.convertToGenericPaymentResponse(paymentOperation);
    }

    public GenericPaymentResponse reversal(Long paymentInstrumentId, Long paymentOperationId) {
        var orgPaymentOperation = findPaymentOperation(paymentOperationId);
        var paymentInstrument = findPaymentInstrument(paymentInstrumentId);
        var merchant = findMerchant(orgPaymentOperation.getMerchantId());

        var paymentRequest = buildTransactionRequest("Reversal", orgPaymentOperation);
        var paymentResponse = vasPaymentApiRepository.reversal(paymentRequest,
            paymentInstrument.getExternalAccountId(),
            orgPaymentOperation.getExternalPaymentId(),
            merchant.getAgreementId());

        var paymentOperation = PaymentOperationBuilder.build("Reversal", paymentInstrument, merchant.getId(), paymentResponse);

        paymentOperationRepository.save(paymentOperation);

        return GenericPaymentResponseBuilder.convertToGenericPaymentResponse(paymentOperation);
    }

    public GenericPaymentResponse cancel(Long paymentInstrumentId, Long paymentOperationId) {
        var orgPaymentOperation = findPaymentOperation(paymentOperationId);
        var paymentInstrument = findPaymentInstrument(paymentInstrumentId);
        var merchant = findMerchant(orgPaymentOperation.getMerchantId());

        var paymentRequest = buildOperationRequest(orgPaymentOperation, merchant);
        var paymentResponse = vasPaymentApiRepository.cancel(paymentRequest,
            paymentInstrument.getExternalAccountId(),
            orgPaymentOperation.getExternalPaymentId(),
            merchant.getAgreementId());

        var paymentOperation = PaymentOperationBuilder.build("Cancel", paymentInstrument, merchant.getId(), paymentResponse);

        paymentOperationRepository.save(paymentOperation);

        return GenericPaymentResponseBuilder.convertToGenericPaymentResponse(paymentOperation);
    }

    public List<GenericPaymentResponse> listPaymentsForPaymentInstrument(Long paymentInstrumentId) {
        var paymentList = paymentOperationRepository.findTop100ByPaymentInstrumentIdOrderByCreatedDesc(paymentInstrumentId);
        return paymentList.stream()
            .map(GenericPaymentResponseBuilder::convertToGenericPaymentResponse)
            .collect(Collectors.toList());
    }

    private SimulatedMerchant findMerchant(Long merchantId) {
        return simulatedMerchantRepository.findById(merchantId).orElseThrow(() -> new BadRequestException("Unable to find Merchant by id: " + merchantId));
    }

    private PaymentInstrument findPaymentInstrument(Long paymentInstrumentId) {
        return paymentInstrumentRepository.findById(paymentInstrumentId).orElseThrow(() -> new BadRequestException("Unable to find PaymentInstrument by id: " + paymentInstrumentId));
    }

    private PaymentOperation findPaymentOperation(Long findPaymentOperation) {
        return paymentOperationRepository.findById(findPaymentOperation).orElseThrow(() -> new BadRequestException("Unable to find PaymentOperation by id: " + findPaymentOperation));
    }

    private TransactionRequest buildTransactionRequest(String operation, PaymentOperation orgRequest) {
        return TransactionRequest.builder()
            .amount(orgRequest.getAmount())
            .description(operation + " of: " + orgRequest.getExternalPaymentId())
            .paymentOrderRef(orgRequest.getPaymentOrderRef())
            .paymentTransactionRef(UUID.randomUUID().toString())
            .build();
    }

    private OperationRequest buildOperationRequest(PaymentOperation orgRequest, SimulatedMerchant merchant) {
        return OperationRequest.builder()
            .description("Cancel of: " + orgRequest.getExternalPaymentId())
            .paymentOrderRef(orgRequest.getPaymentOrderRef())
            .paymentTransactionRef(UUID.randomUUID().toString())
            .merchant(Merchant.builder()
                .merchantName(merchant.getMerchantName())
                .build())
            .build();
    }

    private PaymentRequest buildPaymentRequest(GenericPaymentRequest request, PaymentInstrument paymentInstrument, SimulatedMerchant merchant) {
        return PaymentRequest.builder()
            .amount(request.getAmount())
            .description(request.getDescription())
            .currency(merchant.getCurrencyIso().getValue())
            .paymentOrderRef(UUID.randomUUID().toString())
            .paymentTransactionRef(UUID.randomUUID().toString())
            .merchant(Merchant.builder()
                .merchantName(merchant.getMerchantName())
                .build())
            .accountIdentifier(buildAccountIdentifier(paymentInstrument))
            .build();
    }

    private AccountIdentifier buildAccountIdentifier(PaymentInstrument paymentInstrument) {
        return AccountIdentifier.builder()
            .accountId(paymentInstrument.getExternalAccountId())
            .accountKey(paymentInstrument.getPan())
            .cvc(paymentInstrument.getCvc()) //TODO:: ExpiryDate?
            .build();
    }
}
