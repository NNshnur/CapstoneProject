package truckingappservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import truckingappservice.activity.request.CreateProfileRequest;
import truckingappservice.activity.request.GetAllExpensesRequest;
import truckingappservice.activity.request.UpdateExpenseRequest;
import truckingappservice.activity.results.GetAllExpensesResult;

public class GetAllExpensesLambda extends LambdaActivityRunner<GetAllExpensesRequest, GetAllExpensesResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetAllExpensesRequest>, LambdaResponse>

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
        public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetAllExpensesRequest> input, Context context) {
        log.error("We are in Lambda of Get All Expenses");
        return super.runActivity(
                () -> {
                    GetAllExpensesRequest unauthenticatedRequest = input.fromBody(GetAllExpensesRequest.class);
                    log.error("unauthenticatedRequest{}", unauthenticatedRequest);
                    return input.fromUserClaims(claims ->
                        GetAllExpensesRequest.builder()
                                .withId(claims.get("email"))
                                .build());

                },
                (request,serviceComponent) -> {
                    try {
                        return serviceComponent.provideGetAllExpensesActivity().handleRequest(request.getId());
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                    }

                }

        );
        }
    }


//                },
//                        (request,serviceComponent) ->
//                        serviceComponent.provideGetAllExpensesActivity().handleRequest(request.getId())
//                        );
//                        }
//                        }
