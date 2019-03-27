package com.payex.vas.demo.service;

import com.payex.vas.demo.domain.dto.GenericPaymentRequest;
import com.payex.vas.demo.domain.dto.GenericPaymentResponse;
import com.payex.vas.demo.domain.entities.PaymentAccount;
import com.payex.vas.demo.domain.entities.PaymentOperation;
import com.payex.vas.demo.domain.entities.SimulatedMerchant;
import com.payex.vas.demo.domain.payex.base.AccountIdentifier;
import com.payex.vas.demo.domain.payex.base.Merchant;
import com.payex.vas.demo.domain.payex.request.OperationRequest;
import com.payex.vas.demo.domain.payex.request.PaymentRequest;
import com.payex.vas.demo.domain.payex.request.TransactionRequest;
import com.payex.vas.demo.repository.PaymentAccountRepository;
import com.payex.vas.demo.repository.PaymentOperationRepository;
import com.payex.vas.demo.repository.SimulatedMerchantRepository;
import com.payex.vas.demo.repository.external.VasPaymentApiRepository;
import com.payex.vas.demo.service.helper.GenericPaymentResponseBuilder;
import com.payex.vas.demo.service.helper.PaymentOperationBuilder;
import com.payex.vas.demo.util.error.BadRequestException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class PaymentService {

    private final PaymentOperationRepository paymentOperationRepository;
    private final PaymentAccountRepository paymentAccountRepository;
    private final SimulatedMerchantRepository simulatedMerchantRepository;

    private final VasPaymentApiRepository vasPaymentApiRepository;

    public GenericPaymentResponse authorize(Long accountId, GenericPaymentRequest request) {
        var paymentAccount = findPaymentAccount(accountId);
        var merchant = findMerchant(request.getMerchantId());

        var paymentRequest = buildPaymentRequest(request, paymentAccount, merchant);
        var paymentResponse = vasPaymentApiRepository.authorize(paymentRequest, paymentAccount.getExternalAccountId(), merchant.getAgreementId());

        var paymentOperation = PaymentOperationBuilder.build("Authorize", paymentAccount, merchant.getId(), paymentResponse);

        paymentOperationRepository.save(paymentOperation);

        return GenericPaymentResponseBuilder.convertToGenericPaymentResponse(paymentOperation);
    }

    public GenericPaymentResponse purchase(Long accountId, GenericPaymentRequest request) {
        var paymentAccount = findPaymentAccount(accountId);
        var merchant = findMerchant(request.getMerchantId());

        var paymentRequest = buildPaymentRequest(request, paymentAccount, merchant);
        var paymentResponse = vasPaymentApiRepository.purchase(paymentRequest, paymentAccount.getExternalAccountId(), merchant.getAgreementId());

        var paymentOperation = PaymentOperationBuilder.build("Purchase", paymentAccount, merchant.getId(), paymentResponse);

        paymentOperationRepository.save(paymentOperation);

        return GenericPaymentResponseBuilder.convertToGenericPaymentResponse(paymentOperation);
    }

    public GenericPaymentResponse capture(Long paymentOperationId) {
        var orgPaymentOperation = findPaymentOperation(paymentOperationId);
        var paymentAccount = findPaymentAccount(orgPaymentOperation.getPaymentAccountId());
        var merchant = findMerchant(orgPaymentOperation.getMerchantId());

        var paymentRequest = buildTransactionRequest("Capture", orgPaymentOperation);
        var paymentResponse = vasPaymentApiRepository.capture(paymentRequest,
            paymentAccount.getExternalAccountId(),
            orgPaymentOperation.getExternalPaymentId(),
            merchant.getAgreementId());

        var paymentOperation = PaymentOperationBuilder.build("Capture", paymentAccount, merchant.getId(), paymentResponse);

        paymentOperationRepository.save(paymentOperation);

        return GenericPaymentResponseBuilder.convertToGenericPaymentResponse(paymentOperation);
    }

    public GenericPaymentResponse reversal(Long paymentOperationId) {
        var orgPaymentOperation = findPaymentOperation(paymentOperationId);
        var paymentAccount = findPaymentAccount(orgPaymentOperation.getPaymentAccountId());
        var merchant = findMerchant(orgPaymentOperation.getMerchantId());

        var paymentRequest = buildTransactionRequest("Reversal", orgPaymentOperation);
        var paymentResponse = vasPaymentApiRepository.reversal(paymentRequest,
            paymentAccount.getExternalAccountId(),
            orgPaymentOperation.getExternalPaymentId(),
            merchant.getAgreementId());

        var paymentOperation = PaymentOperationBuilder.build("Reversal", paymentAccount, merchant.getId(), paymentResponse);

        paymentOperationRepository.save(paymentOperation);

        return GenericPaymentResponseBuilder.convertToGenericPaymentResponse(paymentOperation);
    }

    public GenericPaymentResponse cancel(Long paymentOperationId) {
        var orgPaymentOperation = findPaymentOperation(paymentOperationId);
        var paymentAccount = findPaymentAccount(orgPaymentOperation.getPaymentAccountId());
        var merchant = findMerchant(orgPaymentOperation.getMerchantId());

        var paymentRequest = buildOperationRequest(orgPaymentOperation, merchant);
        var paymentResponse = vasPaymentApiRepository.cancel(paymentRequest,
            paymentAccount.getExternalAccountId(),
            orgPaymentOperation.getExternalPaymentId(),
            merchant.getAgreementId());

        var paymentOperation = PaymentOperationBuilder.build("Cancel", paymentAccount, merchant.getId(), paymentResponse);

        paymentOperationRepository.save(paymentOperation);

        return GenericPaymentResponseBuilder.convertToGenericPaymentResponse(paymentOperation);
    }

    private SimulatedMerchant findMerchant(Long merchantId) {
        return simulatedMerchantRepository.findById(merchantId).orElseThrow(() -> new BadRequestException("Unable to find Merchant by id: " + merchantId));
    }

    private PaymentAccount findPaymentAccount(Long accountId) {
        return paymentAccountRepository.findById(accountId).orElseThrow(() -> new BadRequestException("Unable to find PaymentAccount by id: " + accountId));
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

    private PaymentRequest buildPaymentRequest(GenericPaymentRequest request, PaymentAccount paymentAccount, SimulatedMerchant merchant) {
        return PaymentRequest.builder()
            .amount(request.getAmount())
            .description(request.getDescription())
            .currency(merchant.getCurrencyIso().getValue())
            .paymentOrderRef(UUID.randomUUID().toString())
            .paymentTransactionRef(UUID.randomUUID().toString())
            .merchant(Merchant.builder()
                .merchantName(merchant.getMerchantName())
                .build())
            .accountIdentifier(AccountIdentifier.builder()
                .accountId(paymentAccount.getExternalAccountId())
                .accountKey(paymentAccount.getPan())
                .cvc(paymentAccount.getCvc()) //TODO:: ExpiryDate?
                .build())
            .build();
    }
}
