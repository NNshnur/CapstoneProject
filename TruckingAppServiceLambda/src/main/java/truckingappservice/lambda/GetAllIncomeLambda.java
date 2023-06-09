package truckingappservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import truckingappservice.activity.request.GetAllExpensesRequest;
import truckingappservice.activity.request.GetAllIncomeRequest;
import truckingappservice.activity.results.GetAllExpensesResult;
import truckingappservice.activity.results.GetAllIncomeResult;

public class GetAllIncomeLambda extends LambdaActivityRunner<GetAllIncomeRequest, GetAllIncomeResult>
            implements RequestHandler<AuthenticatedLambdaRequest<GetAllIncomeRequest>, LambdaResponse>

    {
        private final Logger log = LogManager.getLogger();

        /**
         * Handles a Lambda Function request
         *
         * @param input   The Lambda Function input
         * @param context The Lambda execution environment context object.
         * @return The Lambda Function output
         */
        @Override
        public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetAllIncomeRequest> input, Context context) {
            return super.runActivity(
                    () -> {
                        return input.fromUserClaims(claims ->
                                GetAllIncomeRequest.builder()
                                        .withId(claims.get("email"))
                                        .build());

                    },
                    (request,serviceComponent) ->
                            serviceComponent.provideGetAllIncomeActivity().handleRequest(request)
            );
        }
    }

