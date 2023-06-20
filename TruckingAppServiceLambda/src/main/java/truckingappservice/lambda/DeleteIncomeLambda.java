package truckingappservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import truckingappservice.activity.request.DeleteIncomeRequest;
import truckingappservice.activity.results.DeleteIncomeResult;

public class DeleteIncomeLambda extends LambdaActivityRunner<DeleteIncomeRequest, DeleteIncomeResult>
        implements RequestHandler<AuthenticatedLambdaRequest<DeleteIncomeRequest>, LambdaResponse>

{
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<DeleteIncomeRequest> input, Context context) {
        return super.runActivity(
                () -> input.fromPath(path ->
                        DeleteIncomeRequest.builder()
                                .withIncomeId(path.get("incomeId"))
                                .build()),
                (request, serviceComponent) ->
                        serviceComponent.provideDeleteIncomeActivity().handleRequest(request)

        );
    }
}


