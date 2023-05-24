package truckingappservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import truckingappservice.activity.request.GetAllExpensesRequest;
import truckingappservice.activity.results.GetAllExpensesResult;

public class GetAllExpensesLambda extends LambdaActivityRunner<GetAllExpensesRequest, GetAllExpensesResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetAllExpensesRequest>, LambdaResponse>

    {

        /**
         * Handles a Lambda Function request
         *
         * @param input   The Lambda Function input
         * @param context The Lambda execution environment context object.
         * @return The Lambda Function output
         */
        @Override
        public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetAllExpensesRequest> input, Context context) {

        return super.runActivity(
                () -> input.fromPath(path ->
                        GetAllExpensesRequest.builder()
                                .build()),
                (request, serviceComponent) ->
                        serviceComponent.provideGetAllExpensesActivity().handleRequest());
    }
    }
