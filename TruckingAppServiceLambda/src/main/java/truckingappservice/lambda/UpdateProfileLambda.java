package truckingappservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import truckingappservice.activity.request.UpdateProfileRequest;
import truckingappservice.activity.results.UpdateProfileResult;

public class UpdateProfileLambda extends LambdaActivityRunner<UpdateProfileRequest, UpdateProfileResult>
        implements RequestHandler<AuthenticatedLambdaRequest<UpdateProfileRequest>,LambdaResponse> {

    /**
     * Handles a Lambda Function request
     *
     * @param input   The Lambda Function input
     * @param context The Lambda execution environment context object.
     * @return The Lambda Function output
     */
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<UpdateProfileRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    UpdateProfileRequest unauthenticatedRequest = input.fromBody(UpdateProfileRequest.class);

                    String profileIdFromPath = input.getPathParameters().get("id");

                    return UpdateProfileRequest.builder()
                            .withFirstName(unauthenticatedRequest.getFirstName())
                            .withLastName(unauthenticatedRequest.getLastName())
                            .withCompanyName(unauthenticatedRequest.getCompanyName())
                            .withTruckId(unauthenticatedRequest.getTruckId())
                            .withId(profileIdFromPath)
                            .build();
                },
                (request, serviceComponent) -> serviceComponent.provideUpdateProfileActivity().handleRequest(request)
        );
    }
}
