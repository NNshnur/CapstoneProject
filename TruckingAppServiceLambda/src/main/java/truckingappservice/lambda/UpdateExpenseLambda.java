package truckingappservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import truckingappservice.activity.request.UpdateExpenseRequest;
import truckingappservice.activity.results.UpdateExpenseResult;

public class UpdateExpenseLambda extends LambdaActivityRunner<UpdateExpenseRequest, UpdateExpenseResult>
        implements RequestHandler<AuthenticatedLambdaRequest<UpdateExpenseRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<UpdateExpenseRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    UpdateExpenseRequest unauthenticatedRequest = input.fromBody(UpdateExpenseRequest.class);
                    String expenseIdFromPath = input.getPathParameters().get("id");
                    return input.fromUserClaims(claims ->
                            UpdateExpenseRequest.builder()
                                    .withProfileId(claims.get("email"))
                                    .withExpenseId(expenseIdFromPath)
                                    .withTruckId(unauthenticatedRequest.getTruckId())
                                    .withVendorName(unauthenticatedRequest.getVendorName())
                                    .withCategory(unauthenticatedRequest.getCategory())
                                    .withDate(unauthenticatedRequest.getDate())
                                    .withAmount(unauthenticatedRequest.getAmount())
                                    .withPaymentType(unauthenticatedRequest.getPaymentType())
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideUpdateExpenseActivity().handleRequest(request)
        );
    }
}
