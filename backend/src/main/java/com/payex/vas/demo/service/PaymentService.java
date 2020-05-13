package com.swedbankpay.vas.demo.service;

import com.swedbankpay.vas.demo.domain.dto.BalanceResponse;
import com.swedbankpay.vas.demo.domain.dto.GenericPaymentRequest;
import com.swedbankpay.vas.demo.domain.dto.GenericPaymentResponse;
import com.swedbankpay.vas.demo.domain.entities.PaymentInstrument;
import com.swedbankpay.vas.demo.domain.entities.PaymentOperation;
import com.swedbankpay.vas.demo.domain.entities.SimulatedMerchant;
import com.swedbankpay.vas.demo.domain.swedbankpay.request.BalanceRequest;
import com.swedbankpay.vas.demo.repository.PaymentInstrumentRepository;
import com.swedbankpay.vas.demo.repository.PaymentOperationRepository;
import com.swedbankpay.vas.demo.repository.SimulatedMerchantRepository;
import com.swedbankpay.vas.demo.repository.external.VasPaymentApiRepository;
import com.swedbankpay.vas.demo.service.helper.BalanceResponseBuilder;
import com.swedbankpay.vas.demo.service.helper.ExternalRequestBuilder;
import com.swedbankpay.vas.demo.service.helper.GenericPaymentResponseBuilder;
import com.swedbankpay.vas.demo.service.helper.PaymentOperationBuilder;
import com.swedbankpay.vas.demo.util.error.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentOperationRepository paymentOperationRepository;
    private final PaymentInstrumentRepository paymentInstrumentRepository;
    private final SimulatedMerchantRepository simulatedMerchantRepository;

    private final VasPaymentApiRepository vasPaymentApiRepository;

    public BalanceResponse balance(PaymentInstrument paymentInstrument, String agreementId) {

        var balanceRequest = BalanceRequest.builder()
            .accountIdentifier(ExternalRequestBuilder.buildAccountIdentifier(paymentInstrument))
            .build();

        var response = vasPaymentApiRepository.balance(balanceRequest, agreementId);
        return BalanceResponseBuilder.convertToBalanceResponse(response);
    }

    public BalanceResponse balance(Long paymentInstrumentId, String agreementId) {
        var paymentInstrument = findPaymentInstrument(paymentInstrumentId);
        var balanceResponse = balance(paymentInstrument, agreementId);
        paymentInstrument.setLastBalanceSync(OffsetDateTime.now());
        paymentInstrument.setBalance(balanceResponse.getBalance());
        paymentInstrumentRepository.save(paymentInstrument);
        return balanceResponse;
    }

    public GenericPaymentResponse deposit(Long paymentInstrumentId, GenericPaymentRequest request) {
        var paymentInstrument = findPaymentInstrument(paymentInstrumentId);
        var merchant = findMerchant(request.getMerchantId());

        var paymentRequest = ExternalRequestBuilder.buildPaymentRequest(request, paymentInstrument, merchant);
        var paymentResponse = vasPaymentApiRepository.deposit(paymentRequest, paymentInstrument.getExternalAccountId(), merchant.getAgreementId());

        var paymentOperation = PaymentOperationBuilder.build("Deposit", paymentInstrument, merchant.getId(), paymentResponse);

        paymentOperationRepository.save(paymentOperation);

        return GenericPaymentResponseBuilder.convertToGenericPaymentResponse(paymentOperation);
    }

    public GenericPaymentResponse credit(Long paymentInstrumentId, GenericPaymentRequest request) {
        var paymentInstrument = findPaymentInstrument(paymentInstrumentId);
        var merchant = findMerchant(request.getMerchantId());

        var paymentRequest = ExternalRequestBuilder.buildPaymentRequest(request, paymentInstrument, merchant);
        var paymentResponse = vasPaymentApiRepository.credit(paymentRequest, paymentInstrument.getExternalAccountId(), merchant.getAgreementId());

        var paymentOperation = PaymentOperationBuilder.build("Credit", paymentInstrument, merchant.getId(), paymentResponse);

        paymentOperationRepository.save(paymentOperation);

        return GenericPaymentResponseBuilder.convertToGenericPaymentResponse(paymentOperation);
    }

    public GenericPaymentResponse authorize(Long paymentInstrumentId, GenericPaymentRequest request) {
        var paymentInstrument = findPaymentInstrument(paymentInstrumentId);
        var merchant = findMerchant(request.getMerchantId());

        var paymentRequest = ExternalRequestBuilder.buildPaymentRequest(request, paymentInstrument, merchant);
        var paymentResponse = vasPaymentApiRepository.authorize(paymentRequest, paymentInstrument.getExternalAccountId(), merchant.getAgreementId());

        var paymentOperation = PaymentOperationBuilder.build("Authorize", paymentInstrument, merchant.getId(), paymentResponse);

        paymentOperationRepository.save(paymentOperation);

        return GenericPaymentResponseBuilder.convertToGenericPaymentResponse(paymentOperation);
    }

    public GenericPaymentResponse purchase(Long paymentInstrumentId, GenericPaymentRequest request) {
        var paymentInstrument = findPaymentInstrument(paymentInstrumentId);
        var merchant = findMerchant(request.getMerchantId());

        var paymentRequest = ExternalRequestBuilder.buildPaymentRequest(request, paymentInstrument, merchant);
        var paymentResponse = vasPaymentApiRepository.purchase(paymentRequest, paymentInstrument.getExternalAccountId(), merchant.getAgreementId());

        var paymentOperation = PaymentOperationBuilder.build("Purchase", paymentInstrument, merchant.getId(), paymentResponse);

        paymentOperationRepository.save(paymentOperation);

        return GenericPaymentResponseBuilder.convertToGenericPaymentResponse(paymentOperation);
    }

    public GenericPaymentResponse capture(Long paymentInstrumentId, Long paymentOperationId) {
        var orgPaymentOperation = findPaymentOperation(paymentOperationId);
        var paymentInstrument = findPaymentInstrument(paymentInstrumentId);
        var merchant = findMerchant(orgPaymentOperation.getMerchantId());

        var transactionRequest = ExternalRequestBuilder.buildTransactionRequest("Capture", orgPaymentOperation);
        var paymentResponse = vasPaymentApiRepository.capture(transactionRequest,
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

        var transactionRequest = ExternalRequestBuilder.buildTransactionRequest("Reversal", orgPaymentOperation);
        var paymentResponse = vasPaymentApiRepository.reversal(transactionRequest,
            paymentInstrument.getExternalAccountId(),
            orgPaymentOperation.getExternalPaymentId(),
            merchant.getAgreementId());

        var paymentOperation = PaymentOperationBuilder.build("Reversal", paymentInstrument, merchant.getId(), paymentResponse);

        paymentOperationRepository.save(paymentOperation);

        updateStateOnOriginalPaymentOperation(orgPaymentOperation, "REVERSED");

        return GenericPaymentResponseBuilder.convertToGenericPaymentResponse(paymentOperation);
    }

    public GenericPaymentResponse cancel(Long paymentInstrumentId, Long paymentOperationId) {
        var orgPaymentOperation = findPaymentOperation(paymentOperationId);
        var paymentInstrument = findPaymentInstrument(paymentInstrumentId);
        var merchant = findMerchant(orgPaymentOperation.getMerchantId());

        var operationRequest = ExternalRequestBuilder.buildCancelRequest(orgPaymentOperation, merchant);
        var paymentResponse = vasPaymentApiRepository.cancel(operationRequest,
            paymentInstrument.getExternalAccountId(),
            orgPaymentOperation.getExternalPaymentId(),
            merchant.getAgreementId());

        var paymentOperation = PaymentOperationBuilder.build("Cancel", paymentInstrument, merchant.getId(), paymentResponse);

        paymentOperationRepository.save(paymentOperation);

        updateStateOnOriginalPaymentOperation(orgPaymentOperation, "CANCELED");

        return GenericPaymentResponseBuilder.convertToGenericPaymentResponse(paymentOperation);
    }

    private void updateStateOnOriginalPaymentOperation(PaymentOperation orgPaymentOperation, String state) {
        orgPaymentOperation.setState(state);
        paymentOperationRepository.save(orgPaymentOperation);
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

}
