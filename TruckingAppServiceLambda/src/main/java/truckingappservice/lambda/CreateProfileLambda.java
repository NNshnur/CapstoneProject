package truckingappservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import truckingappservice.activity.request.CreateProfileRequest;
import truckingappservice.activity.request.GetAllExpensesRequest;
import truckingappservice.activity.results.CreateProfileResult;

public class CreateProfileLambda extends LambdaActivityRunner<CreateProfileRequest, CreateProfileResult>
        implements RequestHandler<AuthenticatedLambdaRequest<CreateProfileRequest>,LambdaResponse> {
    /**
     * Handles a Lambda Function request
     *
     * @param input   The Lambda Function input
     * @param context The Lambda execution environment context object.
     * @return The Lambda Function output
     */

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<CreateProfileRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    CreateProfileRequest unauthenticatedRequest = input.fromBody(CreateProfileRequest.class);
                    return input.fromUserClaims(claims ->
                            CreateProfileRequest.builder()
                                    .withId(claims.get("email"))
                                    .withFirstName(unauthenticatedRequest.getFirstName())
                                    .withLastName(unauthenticatedRequest.getLastName())
                                    .withCompanyName(unauthenticatedRequest.getCompanyName())
                                    .withTruckId(unauthenticatedRequest.getTruckId())
                                    .build());

                },
                (request,serviceComponent) ->
                        serviceComponent.provideCreateProfileActivity().handleRequest(request)
        );
    }
}

