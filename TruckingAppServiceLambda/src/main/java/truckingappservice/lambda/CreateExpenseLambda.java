package truckingappservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import truckingappservice.activity.request.CreateExpenseRequest;
import truckingappservice.activity.results.CreateExpenseResult;

public class CreateExpenseLambda
        extends LambdaActivityRunner<CreateExpenseRequest, CreateExpenseResult>
        implements RequestHandler<AuthenticatedLambdaRequest<CreateExpenseRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<CreateExpenseRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    CreateExpenseRequest unauthenticatedRequest = input.fromBody(CreateExpenseRequest.class);
                    return input.fromUserClaims(claims ->
                            CreateExpenseRequest.builder()
                                    .withTruckId(unauthenticatedRequest.getTruckId())
                                    .withAmount(unauthenticatedRequest.getAmount())
                                    .withVendorName(unauthenticatedRequest.getVendorName())
                                    .withCategory(unauthenticatedRequest.getCategory())
                                    .withDate(unauthenticatedRequest.getDate())
                                    .withPaymentType(unauthenticatedRequest.getPaymentType())
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideCreateExpenseActivity().handleRequest(request)
        );
    }
}