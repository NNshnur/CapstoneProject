package truckingappservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import truckingappservice.activity.request.CreateIncomeRequest;
import truckingappservice.activity.results.CreateIncomeResult;

public class CreateIncomeLambda extends LambdaActivityRunner<CreateIncomeRequest, CreateIncomeResult>
        implements RequestHandler<AuthenticatedLambdaRequest<CreateIncomeRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<CreateIncomeRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    CreateIncomeRequest unauthenticatedRequest = input.fromBody(CreateIncomeRequest.class);
                    return input.fromUserClaims(claims ->
                            CreateIncomeRequest.builder()
                                    .withTruckId(unauthenticatedRequest.getTruckId())
                                    .withDate(unauthenticatedRequest.getDate())
                                    .withDeadHeadMiles(unauthenticatedRequest.getDeadHeadMiles())
                                    .withLoadedMiles(unauthenticatedRequest.getLoadedMiles())
                                    .withGrossIncome(unauthenticatedRequest.getGrossIncome())
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideCreateIncomeActivity().handleRequest(request)
        );
    }
}