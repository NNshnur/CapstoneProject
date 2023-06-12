package truckingappservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import truckingappservice.activity.request.UpdateExpenseRequest;
import truckingappservice.activity.request.UpdateIncomeRequest;
import truckingappservice.activity.results.UpdateExpenseResult;
import truckingappservice.activity.results.UpdateIncomeResult;

public class UpdateIncomeLambda extends LambdaActivityRunner<UpdateIncomeRequest, UpdateIncomeResult>
        implements RequestHandler<AuthenticatedLambdaRequest<UpdateIncomeRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<UpdateIncomeRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    UpdateIncomeRequest unauthenticatedRequest = input.fromBody(UpdateIncomeRequest.class);
                    String incomeIdFromPath = input.getPathParameters().get("id");
                    return input.fromUserClaims(claims ->
                            UpdateIncomeRequest.builder()
                                    .withProfileId(claims.get("email"))
                                    .withIncomeId(incomeIdFromPath)
                                    .withTruckId(unauthenticatedRequest.getTruckId())
                                    .withDate(unauthenticatedRequest.getDate())
                                    .withDeadHeadMiles(unauthenticatedRequest.getDeadHeadMiles())
                                    .withLoadedMiles(unauthenticatedRequest.getLoadedMiles())
                                    .withGrossIncome(unauthenticatedRequest.getGrossIncome())
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideUpdateIncomeActivity().handleRequest(request)
        );
    }
}
